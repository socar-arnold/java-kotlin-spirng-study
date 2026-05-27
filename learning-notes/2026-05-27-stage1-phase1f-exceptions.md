# Stage 1 · Phase 1-F 학습 노트 — 예외 & 입력 검증

> 날짜: 2026-05-27
> 주제: require/check/error, 예외 던지기/잡기, try-catch는 식, fail-fast
> 페르소나: TypeScript 개발자 → TS엔 내장 없음(직접 throw), Kotlin은 require로 간결

---

## ① WHY
잘못된 입력이 함수 깊숙이 흘러들면 엉뚱한 곳에서 터지거나 조용히 틀린 결과. → **입구에서 즉시 막기(fail-fast, 방어적 프로그래밍).**

## ② WHAT — require / check / error
| 함수 | 검증 대상 | 실패 시 예외 | 의미 |
|---|---|---|---|
| `require(조건) { msg }` | **인자(argument)** | IllegalArgumentException | 호출한 쪽이 잘못 줌 |
| `check(조건) { msg }` | **상태(state)** | IllegalStateException | 객체 상태가 이상 |
| `error("msg")` | 도달 불가 지점 | IllegalStateException | 여기 오면 버그 |

```kotlin
require(age >= 0) { "나이는 음수 불가: $age" }   // false면 예외, 통과하면 보장
```
- `{ msg }`는 실패 시에만 평가(지연). TS는 내장 없어 `if(!cond) throw new Error(...)` 직접.
- **체크 예외 없음**(1주차): `throws` 강제 안 함, 모든 예외 unchecked (TS/JS처럼).
- `try-catch`도 **식**: `val x = try { parse() } catch (e: ...) { -1 }`.

> Trade-off: require는 "계약 위반(절대 안 됨)"에 fail-fast. "사용자가 흔히 틀리는 입력"은 예외보다 검증 결과를 값(sealed Result)으로 돌려주는 게 나을 때도.

## ③ HOW — 숫자야구 입력 검증 (TDD)
```kotlin
fun validateGuess(guess: List<Int>) {
    require(guess.size == 3) { "3자리여야 함" }
    require(guess.all { it in 1..9 }) { "1~9만" }       // all = 1-E FP 재사용
    require(!hasDuplicate(guess)) { "중복 불가" }         // hasDuplicate = 1-E 재사용
}
```
- Kotest 예외 매처: `shouldThrow<IllegalArgumentException> { ... }`, `shouldNotThrow<...> { }`.
- **책임 분리:** 검증은 `validateGuess`(입구)에서, 판정은 `judge`만 → fail-fast + 재사용 + 디버깅 쉬움.

## 핵심 한 줄 요약
- `require`(인자)/`check`(상태)/`error`(버그)로 fail-fast 검증.
- try-catch도 식. 체크 예외 없음(unchecked).
- 검증은 입구에서 분리(판정 로직과 책임 분리).

## 다음 — 🎯 숫자야구 미션 (Phase 1 종합)
재료 완성: judge/countStrikes/countBalls(1-D), hasDuplicate(1-E), validateGuess(1-F).
남은 것: 랜덤 정답 생성(1~9, 중복 없는 3자리) + 게임 루프(3스트라이크까지) + 입력 처리.
