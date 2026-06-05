# Stage 2 · 미션 ① 코루틴 레이싱 🏎️💨

> 날짜: 2026-06-04
> 의미: Stage 1 자동차경주의 코루틴 버전. async/awaitAll/coroutineScope 응용.

---

## 변화 (Stage 1 vs 이번)
- Stage 1: 라운드별 동기 전진(같은 라운드 모두 한 칸씩, 시간 개념 X)
- 이번: 각 차가 **자기 lap time으로 동시 주행** → 가장 먼저 결승선 도달 = 우승

## 설계
```kotlin
data class RaceCar(val name: String, val lapTimeMs: Long)
data class RaceResult(val winner: String, val finishOrder: List<String>)

suspend fun race(cars: List<RaceCar>): RaceResult = coroutineScope {
    val sortedNames = cars
        .map { car -> async { delay(car.lapTimeMs); car } }   // 동시 출발
        .awaitAll()                                           // 다 도착할 때까지
        .sortedBy { it.lapTimeMs }                             // 빠른 순
        .map { it.name }                                       // 이름만
    RaceResult(winner = sortedNames[0], finishOrder = sortedNames)
}
```

## 측정 (병렬 증명)
| 입력 lap times | 직렬이면 | 병렬(측정값) |
|---|---|---|
| 300 + 500 + 800 | 1600ms | **812ms** ✅ |

→ 가장 느린 차 시간(800) + 작은 오버헤드. async가 진짜로 동시 처리한 증거.

## 디버깅 교훈 — TS 습관 세 가지 발견
| TS 습관 | Kotlin |
|---|---|
| `return {a: 1, b: 2}` (객체 리터럴) | `return MyClass(a = 1, b = 2)` (생성자 호출) |
| 람다 안 `return` | 단일 식 함수의 람다는 **마지막 식이 자동 반환**, return 안 씀 |
| `result[0].name` (이미 String인데 또 .name) | 체이닝 후 타입 추적 — IntelliJ 타입 힌트 활용 |

→ 체인 흐름(`map → awaitAll → sortedBy → map`)은 정확. **문법만 Kotlin스럽게**.

## 핵심 한 줄 요약
- **`cars.map { async { ... } }.awaitAll()`** = 동시 출발 + 결과 다 받음 패턴(반복 사용 가치 ★★★).
- coroutineScope 안에서 async를 써야 구조적 동시성(고아 코루틴 방지).
- TS→Kotlin: 객체 생성은 생성자 호출, 단일 식 함수는 return 없이 마지막 식.

## 다음 (미션 ②)
**RSS리더** — HTTP 병렬 fetch + XML 파싱. `supervisorScope`로 일부 피드 실패 격리. 그 다음 **Stage 2 졸업** + **LeetCode 트랙 정식 가동**.
