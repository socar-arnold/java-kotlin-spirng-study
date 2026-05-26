# Stage 1 · Phase 1-D 학습 노트 — Collections (+ 숫자야구 판정 로직)

> 날짜: 2026-05-26
> 주제: List/Set/Map, 불변 우선, 함수형 연산(map/filter/count/in), 숫자야구 스트라이크/볼/판정
> 페르소나: TypeScript 개발자 → 배열/Set/Map 매핑

---

## ① WHY
`var count=0` + for + 인덱스 수동 관리는 가변·장황·실수 유발. 함수형 연산(`count{}`/`filter`)이 의도가 또렷하고 `val`만 씀.

## ② WHAT — 컬렉션 (TS 매핑)
**불변 우선(Kotlin 철학):**
| | 읽기 전용(기본) | 변경 가능 |
|---|---|---|
| List | `listOf(1,2,3)` | `mutableListOf(...)` |
| Set | `setOf(...)` | `mutableSetOf(...)` |
| Map | `mapOf("a" to 1)` | `mutableMapOf(...)` |
- Kotlin은 기본이 읽기 전용(`listOf`엔 `.add` 자체가 없음). TS 배열은 기본 가변 → Kotlin이 더 안전.
- **Set** = 중복 없음, `contains` O(1)(해시). **Map** = key→value O(1). (CS-3 연계)

**함수형 연산 (TS와 거의 동일):**
| 하는 일 | Kotlin | TS |
|---|---|---|
| 변환 | `list.map { it*2 }` | `arr.map(x=>x*2)` |
| 거르기 | `list.filter { it>0 }` | `arr.filter` |
| 개수 | `list.count { 조건 }` | `arr.filter(...).length` |
| 포함? | `x in list` == `list.contains(x)` | `arr.includes(x)` |
| 찾기 | `list.find { }` | `arr.find` |
| 전부/하나 | `all{}` / `any{}` | `every`/`some` |
| 인덱스 | `list.indices`, `withIndex()` | — |
- `it` = 람다 기본 파라미터 이름. `{ it -> ... }`의 `it ->`는 생략 가능.
- ⭐ **`x in collection` == `collection.contains(x)`** (in은 contains의 문법 설탕). 둘 다 쓰면 중복.

---

## ③ HOW — 숫자야구 판정 로직 (TDD)
```kotlin
// 스트라이크: 같은 자리 같은 숫자
fun countStrikes(answer: List<Int>, guess: List<Int>): Int =
    answer.indices.count { answer[it] == guess[it] }

// 볼: 숫자는 있지만 자리가 다름 (스트라이크 제외)
fun countBalls(answer: List<Int>, guess: List<Int>): Int =
    guess.indices.count { i -> guess[i] in answer && answer[i] != guess[i] }

// 판정: 둘을 data class로 합치기 (어제 배운 data class 재활용)
data class Result(val strikes: Int, val balls: Int)

fun judge(answer: List<Int>, guess: List<Int>): Result =
    Result(countStrikes(answer, guess), countBalls(answer, guess))
```
- `data class Result(val strikes: Int, val balls: Int)`: 생성자+프로퍼티 한 줄, `==`/`toString` 자동 → 테스트 `shouldBe Result(2,0)`가 내용 비교로 통과.
- `judge`는 새 로직 없이 **이미 만든 함수 조립**만. (재사용)
- 리뷰 교훈: `countBalls`에서 `answer.contains(x) || x in answer`는 같은 것 OR → 중복, 하나만.

## 핵심 한 줄 요약
- Kotlin 컬렉션은 **불변 기본**(listOf), 변경은 mutableListOf.
- `map/filter/count/find` + `x in list`(=contains)로 루프 없이 선언적.
- 숫자야구 두뇌 = countStrikes + countBalls + judge(Result) 완성.

## 다음 예고
Phase 1-E(FP 심화: 람다·고차함수·reduce/fold) → 1-F(예외: require로 입력 검증) → **숫자야구 미션 완성**(랜덤 정답 생성 + 입력 루프 + 게임 진행).
