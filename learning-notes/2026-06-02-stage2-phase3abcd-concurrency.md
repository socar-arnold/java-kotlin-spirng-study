# Stage 2 · Phase 3-A/B/C/D 학습 노트 — 동시성 기초

> 날짜: 2026-06-02
> 주제: 스레드, race condition, 락/Atomic, JMM 가시성, 동시성 컬렉션
> 페르소나: TypeScript 개발자 → JS 이벤트 루프와의 차이가 본질
> 다음: Phase 3-E 코루틴 (TS Promise/async 매핑)

---

## 3-A — 스레드 기초

### TS와의 결정적 차이
- **JS:** 싱글 스레드 이벤트 루프. heavy() 도는 동안 UI 정지. 진짜 병렬은 Web Worker(별도 프로세스).
- **JVM:** 같은 프로세스 안에서 진짜 멀티스레드 → **진짜 병렬**. 강력하지만 **새로운 위험**(공유 메모리).

### 핵심 API
```kotlin
import kotlin.concurrent.thread
val t = thread { /* 작업 */ }    // 권장: 간결, start=true 기본
Thread.sleep(ms)                   // 현재 스레드 정지
t.join()                           // t 끝날 때까지 기다림 (= Promise await 감각)
Thread.currentThread().name        // 디버깅
```

### Trade-off
스레드는 **무겁다**(하나당 ~1MB 스택). 수천 개 띄우면 메모리 폭발. → 실무엔 **스레드 풀(ExecutorService)** 또는 **코루틴**(가벼움).

### CS-2 연결
프로세스 vs 스레드(메모리 공유), 컨텍스트 스위칭 비용, 데드락. 같은 Heap을 공유해서 race condition이 생긴다.

### 실험 관찰
3개 워커 스레드의 출력 순서가 **매번 다름** = 비결정적(non-deterministic). 동시성 코드의 본질적 어려움.

---

## 3-B — 락 & Race Condition (핵심)

### Race Condition (눈으로 본 것)
```kotlin
var counter = 0
thread { repeat(10_000) { counter++ } }
thread { repeat(10_000) { counter++ } }
// 기대: 20000 / 실제: 12047, 15425, 18775... 매번 다르고 항상 적음
```
이유: `counter++` = read → +1 → write **3단계**. 두 스레드가 끼어들며 서로의 작업을 덮어씀.

### 해결책 두 가지
**1) 락 — `@Synchronized`**
```kotlin
class SafeCounter {
    private var value = 0
    @Synchronized fun increment() { value++ }
    @Synchronized fun get() = value
}
```
- 임의 코드 블록 보호 가능 / 느림·데드락 위험.

**2) 원자(Atomic) — `AtomicInteger`**
```kotlin
private val value = AtomicInteger(0)
fun increment() { value.incrementAndGet() }
```
- 락 없이 빠름 / 한 변수 단일 연산에만 적합.

### 실험 결과 (Counter.kt + 5회 반복)
- Unsafe: 5/5 모두 20000 미만 (12047~18775). race condition은 *우연*이 아니라 *상시*.
- Safe/Atomic: 항상 정확히 20000.

### Trade-off 한 줄
**공유 상태를 줄이는 게 최고** (불변 + 메시지 전달 = 코루틴 철학).

---

## 3-C — JMM과 `@Volatile` (가시성)

### 가시성(visibility) 문제
원자성과 별개. CPU 코어마다 **캐시**가 있어, 한 스레드의 변경이 다른 스레드에 **안 보일 수** 있음.
```kotlin
var stop = false
val t = thread { while (!stop) { /* 워커 캐시값 false 고집 */ } }
Thread.sleep(1000); stop = true
// 워커는 안 멈출 수 있음 (변경이 안 보임)
```

### JMM이 정의하는 약속
| 도구 | 보장 |
|---|---|
| `@Volatile var` | **가시성**만 (원자성 X) — 단순 플래그용 |
| `@Synchronized` | 가시성 + 원자성 (락 풀며 캐시 동기화) |
| `Atomic*` | 가시성 + 원자성 (CAS, lock-free) |

**TS와의 차이:** JS는 싱글 스레드라 가시성 문제 자체가 없음. JVM 멀티 코어+캐시가 본질.

---

## 3-D — 동시성 컬렉션

`HashMap`/`ArrayList` 등은 **스레드 안전 아님**. 동시 쓰기 시 데이터 손상·무한 루프.

| 일반 ❌ | 동시성 ✅ |
|---|---|
| HashMap | **ConcurrentHashMap** (외워둘 한 개) |
| ArrayList | CopyOnWriteArrayList (읽기 多/쓰기 少) |
| LinkedList(큐) | ConcurrentLinkedQueue / BlockingQueue |

원칙: 공유 컬렉션이면 동시성 버전. (Spring 내부·캐시 등에 ConcurrentHashMap이 어디서나 등장)

---

## 핵심 한 줄 요약
- JS≠JVM: JVM은 진짜 병렬 → 공유 변경에서 **race condition**(원자성) + **가시성** 문제.
- 해결: 락/Atomic(원자성), `@Volatile`/락/Atomic(가시성), 동시성 컬렉션(공유 자료구조).
- 진짜 해법은 **공유 상태 자체 줄이기** → 다음 주제 **코루틴**의 철학.

## 다음 (Phase 3-E)
**코루틴** — TS Promise/async-await가 매핑되는 자리. 수만 개 동시도 가벼움. `suspend`/`launch`/`async`/`Dispatchers`. Stage 2의 진짜 무기.
