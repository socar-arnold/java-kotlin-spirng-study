# Stage 2 · Phase 3-E ① 학습 노트 — 코루틴 입문

> 날짜: 2026-06-03
> 주제: suspend, delay, runBlocking, async/await, 직접 측정한 병렬 속도
> 페르소나: TypeScript 개발자 → async/await 직접 매핑

---

## ① WHY
- 스레드는 무거움(~1MB/개): 수만 동시 작업 불가
- 콜백 지옥 → async/await로 해결된 TS의 사상을 코틀린에 + 진짜 멀티스레드 결합

## ② WHAT — 핵심 4가지 (TS 매핑)

| 개념 | Kotlin | TS |
|---|---|---|
| 일시중지 함수 | `suspend fun foo(): T` | `async function foo(): Promise<T>` |
| 비동기 대기 | `delay(1000)` (스레드 안 막음!) | `await new Promise(r=>setTimeout(r,1000))` |
| 코루틴 시작(반환 X) | `launch { }` → Job | Promise를 그냥 호출(await X) |
| 코루틴 시작(값 받음) | `async { }` → Deferred → `.await()` | `async fn` → `Promise<T>` → `await` |
| 동기 ↔ 코루틴 다리 | `runBlocking { }` | (없음 — JS는 항상 async 가능) |

### 핵심 차이
- `delay(ms)` ≠ `Thread.sleep(ms)`
  - `delay`: 코루틴만 일시중지, **스레드는 다른 코루틴 처리 가능** → 가벼움
  - `Thread.sleep`: 스레드 통째로 막음 → 코루틴 이점 사라짐
- `coroutineScope { }` 안에서 `async`를 쓰면 **부모는 자식 다 끝나기 전엔 반환 안 함**(=구조적 동시성).
  - TS의 누락된 await 위험을 언어가 구조적으로 차단.
  - 취소·에러 전파도 자동.

### 병렬 패턴 (제일 자주 씀)
```kotlin
suspend fun loadParallel() = coroutineScope {
    val user = async { fetchUser() }      // 동시 시작
    val posts = async { fetchPosts() }    // 동시 시작
    user.await() to posts.await()         // 결과 받기
}
// = TS의 Promise.all([fetchUser(), fetchPosts()])
```

## ③ HOW — TDD로 직접 측정

```kotlin
suspend fun fetchUser(): String { delay(1000); return "Arnold" }
suspend fun fetchPosts(): List<String> { delay(1000); return listOf("post1","post2") }
suspend fun loadParallel(): Pair<String, List<String>> = coroutineScope {
    val u = async { fetchUser() }
    val p = async { fetchPosts() }
    u.await() to p.await()
}
```

테스트:
```kotlin
@Test
fun `순차 실행은 두 작업 시간 합만큼 걸린다`(): Unit = runBlocking {
    val elapsed = measureTimeMillis {
        fetchUser(); fetchPosts()     // 순차
    }
    println("순차: ${elapsed}ms")     // ≈2013ms
    elapsed.toInt() shouldBeGreaterThanOrEqual 2000
}

@Test
fun `병렬은 더 오래 걸리는 작업 하나만큼만`(): Unit = runBlocking {
    val elapsed = measureTimeMillis { loadParallel() }
    println("병렬: ${elapsed}ms")     // ≈1011ms
    elapsed.toInt() shouldBeLessThan 1500
}
```

### 실측 결과
- 순차: **2013ms** / 병렬: **1011ms** → 거의 정확히 절반.
- **두 작업이 같은 한 스레드에서 돌면서도 동시 진행**(스레드 추가 X). `delay`가 스레드를 안 막기 때문 — 가벼움의 본질.

## 🔬 심화 — "스레드 점유하지 않고 일시중지"가 실제로 어떻게?

### ① Continuation은 Heap에 산다 (Phase 2-A와 연결)
일반 함수 호출은 **Stack 프레임**을 쓰는데(스레드 전용), `delay` 같은 일시중지 지점에선:
1. "여기까지 한 것 + 지역변수"를 통째로 **Continuation 객체로 Heap에 저장**
2. Stack 프레임 pop → **스레드 즉시 자유**
3. 시간 되면 (아무) 스레드가 Continuation을 집어 resume → 같은 자리부터 이어 실행

→ Heap은 모든 스레드 공유라, **어느 스레드든 Continuation을 집어 이어 실행 가능**. 그래서 스레드 N개로 코루틴 M개(N << M)를 굴릴 수 있음 — "가벼움"의 본질.

### ② 누가 타이머를 지키고 누가 깨우나 (= Node 비교)
| 역할 | Node.js | Kotlin coroutines |
|---|---|---|
| 타이머 큐 | libuv (Web APIs) | `DefaultExecutor` 스레드(또는 `runBlocking` 자체) |
| 만료 후 디스패치 | 이벤트 루프 | `Dispatcher`(Default/IO/Main) |
| 실행 스레드 | 메인 1개 | 디스패처 풀의 N개 |

흐름:
```
delay(1000)
  → ① 타이머 지킴이: "1000ms 뒤 이 Continuation 깨워줘" 등록
       (시간 지킴이는 우선순위 큐로 만료 시각 보관, 가장 가까운 만료까지 parkNanos로 잠)
  → (시간 경과)
  → ② Dispatcher: Continuation을 가용 스레드에 던져 resume() 호출
```

### ③ `runBlocking`은 두 역할이 한 스레드에 합쳐짐 (우리 실험에서 1011ms 나온 이유)
호출한 스레드 위에서 자기만의 작은 이벤트 루프를 돔:
```
테스트 메인 스레드:
  async{fetchUser} 시작 → delay(1000) → Continuation을 자기 큐에 등록
  async{fetchPosts} 시작 → delay(1000) → Continuation을 자기 큐에 등록
  "재개할 거 없음 → 가장 가까운 만료까지 1000ms parkNanos로 잠"   ← 타이머 지킴이
  (1000ms 후 깨어남)                                              ← 재개자
  큐의 두 Continuation 모두 resume → "Arnold" / posts 반환
```
→ 새 스레드 0개로, 같은 한 스레드가 잠시 자다 깨서 두 Continuation을 *순차로* resume → **두 delay가 동시에 등록돼 있었기에 한 번 깨면 둘 다 끝.** 그래서 ~1초.

### 핵심 정리
- **Continuation = Heap 객체** → 어느 스레드든 이어 실행 가능 → "가벼움"의 본질.
- **타이머 지킴이 + Dispatcher** = Node의 libuv/이벤트 루프 분리판. 다음 세션 Dispatchers 주제 직결.

---

## ⚠️ JUnit + 코루틴 함정 (오늘 디버깅 교훈)
```kotlin
@Test
fun foo() = runBlocking { ... shouldBeGreaterThanOrEqual 2000 }   // ❌ Int 반환
```
- `shouldBeGreaterThanOrEqual`이 Int(수신자) 반환 → 단일 식 함수가 `fun foo(): Int` 가 됨.
- **JUnit @Test는 Unit 반환 메서드만 인식** → "No tests found"로 *조용히* 무시.
- 해결: **`(): Unit = runBlocking { ... }`** 또는 `runBlocking<Unit> { ... }`.
- 시니어 관행: 코틀린+JUnit 코루틴 테스트는 항상 `: Unit` 명시.

## 핵심 한 줄 요약
- 코루틴 = "async/await + 진짜 멀티스레드 + 누락된 await 구조적 차단".
- `delay`는 스레드 안 막음 → 같은 스레드 위에서 수만 동시도 가벼움.
- `coroutineScope { async{}; async{} }` 안에서 `.await()` 로 병렬 → TS `Promise.all`과 동일.

## 다음 (Phase 3-E ②)
- **구조적 동시성** 깊이 — Job/취소/에러 전파
- **Dispatchers**: Main / IO / Default 차이, withContext
- **미션** — RSS리더(I/O 병렬), 코루틴 레이싱 → Stage 2 종료
