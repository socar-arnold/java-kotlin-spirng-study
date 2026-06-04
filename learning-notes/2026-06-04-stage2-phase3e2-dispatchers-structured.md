# Stage 2 · Phase 3-E ② — Dispatchers & 구조적 동시성

> 날짜: 2026-06-04
> 주제: Dispatchers(Default/IO/Main), withContext, launch vs async, 구조적 동시성(coroutineScope/supervisorScope), 협력적 취소
> 페르소나: TypeScript 개발자

---

## ① WHY
어제는 `delay`(non-blocking)였지만 실무엔 CPU 집약·블로킹 I/O도 섞임. 스레드를 점유하는 작업이라 **어떤 스레드 풀에서 돌릴지** 골라야 함.

## ② WHAT — Dispatchers

| Dispatcher | 용도 | 스레드 풀 |
|---|---|---|
| **Default** | CPU 집약 (계산·파싱·정렬) | CPU 코어 수 (~4~16) |
| **IO** | 블로킹 I/O (DB·파일·블로킹 HTTP) | 시작 64, 필요 시 확장 |
| **Main** | UI (Android/Compose) | 메인 스레드 1개 |
| Unconfined | 특수 (거의 안 씀) | — |

> TS엔 이 개념 자체 없음(JS는 메인 1개). JVM은 풀 선택이 본질.

**핵심 패턴 — `withContext`:**
```kotlin
suspend fun fetchUser(id: Long): User {
    val raw = withContext(Dispatchers.IO) {        // DB I/O는 IO 풀
        jdbcTemplate.query(...)
    }
    val parsed = withContext(Dispatchers.Default) { // CPU 파싱은 Default 풀
        heavyParse(raw)
    }
    return parsed
}
```
Spring Boot 코드에서 매우 흔함.

### launch vs async
| | 반환 | 언제 |
|---|---|---|
| `launch { }` | Job (결과 X) | fire-and-forget (로그·백그라운드 갱신·이벤트) |
| `async { }` | Deferred<T> (`.await()`) | 값 필요한 비동기 (=TS Promise) |

## ③ HOW ① — Dispatcher 차이 측정 (TDD)
블로킹 작업(`Thread.sleep(100)`) × 50개:
- **Default(코어 수만큼 동시): 519ms** — 코어 ~8개로 50개를 6~7배치
- **IO(64+ 동시): 110ms** — 거의 다 병렬, 1배치 + 오버헤드

→ 같은 작업, **Dispatcher만 바꿔서 ~5배 차이**. 블로킹 섞이면 Dispatcher 선택이 결정적.

> non-blocking(`delay`)만 쓰면 어떤 Dispatcher든 다 빠름 — 양보하니까 1개 스레드로도 동시 처리 가능. Dispatcher 선택이 중요해지는 건 *블로킹이 섞일 때*.

---

## ④ 구조적 동시성 (코루틴의 진짜 무기)

### 규칙 둘
1. **부모는 자식이 다 끝나기 전 반환 안 함** (`coroutineScope { }`)
2. **한 자식 실패 → 형제 자동 취소 + 예외 부모로 전파**

```kotlin
coroutineScope {
    launch { delay(50); throw RuntimeException("실패") }
    launch { delay(300); doImportant() }  // 50ms 시점에 자동 취소
}
// 예외 여기로 튀어나옴
```

### TS Promise.all과의 결정적 차이
- **TS:** 한 promise reject 시 → 결과만 reject. **다른 promise는 *계속 돌음*(취소 X)** → 자원 누수 위험.
- **Kotlin:** 형제 자동 *취소* (자원 정리·고아 작업 방지).

### 탈출구 — `supervisorScope`
"한 자식 실패해도 형제 계속"이 필요할 때(여러 RSS 피드 중 일부 404 등). 일반 규칙은 `coroutineScope`(all-or-nothing), 부분 실패 OK일 때만 `supervisorScope`.

### 협력적 취소(Cooperative)
취소는 자발적. `delay`/`yield`/suspending 함수 호출 시 체크. **CPU 집약 무한 루프엔 통하지 않음** → 주기적 `yield()`·`ensureActive()` 또는 `delay`로 양보 지점 만들기.

## ⑤ HOW ② — 형제 취소 직접 보기 (TDD)
```kotlin
// 1) coroutineScope: 한 자식 throw → 형제도 취소
val exception = runCatching {
    coroutineScope {
        launch { delay(50); throw RuntimeException("의도된 실패") }
        launch { delay(300); siblingProgressed = true }  // 도달 못함
    }
}.exceptionOrNull()
exception?.message shouldBe "의도된 실패"
siblingProgressed shouldBe false                          // ✅ 형제도 취소

// 2) supervisorScope: 형제 살아남음
supervisorScope {
    launch { try { delay(50); throw RuntimeException() } catch (e: Exception) {} }
    launch { delay(200); siblingFinished = true }
}
siblingFinished shouldBe true                              // ✅ 형제 정상
```
→ 두 테스트 모두 PASSED. 구조적 동시성·supervisorScope 격리 둘 다 확인.

## 핵심 한 줄 요약
- **Dispatchers** = "어디서 실행". 블로킹 I/O → `IO`, CPU 집약 → `Default`, `withContext`로 블록 단위 전환.
- **launch = fire-and-forget / async = 값 필요한 비동기**.
- **구조적 동시성**: 부모가 자식 다 기다림 + 자식 실패 시 형제 자동 취소. 고아 코루틴 구조적 불가능.
- **TS `Promise.all`과 차이**: Kotlin은 *실제로* 취소(누수 방지).

## 다음 — 미션
- **코루틴 레이싱** (간단, `async`·`first` 패턴) ← 추천 첫 미션
- **RSS리더** (HTTP·XML 변수 있음) ← 두 번째

→ Stage 2 졸업까지 1~2미션 남음.
