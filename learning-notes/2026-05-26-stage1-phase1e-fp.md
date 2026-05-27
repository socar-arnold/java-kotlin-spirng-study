# Stage 1 · Phase 1-E 학습 노트 — 함수형 프로그래밍 (FP)

> 날짜: 2026-05-26
> 주제: 고차함수, map/filter/fold/reduce, 체이닝, 지연평가, Set 활용
> 페르소나: TypeScript 개발자 → map/filter/reduce 거의 동일

---

## ① WHY
여러 단계 변환(거르고→바꾸고→세고)을 명령형으로 짜면 단계마다 `var`·중간변수. 함수형은 데이터가 **파이프**를 통과하듯 한 줄로 흐름.

## ② WHAT — 핵심 고차함수 (TS 매핑)
"고차함수" = 함수를 인자로 받는 함수(map이 람다를 받듯).

| 하는 일 | Kotlin | TS |
|---|---|---|
| 변환 | `map { it*2 }` | `map(x=>x*2)` |
| 거르기 | `filter { it>0 }` | `filter` |
| 누적(접기) | `fold(0) { acc, x -> acc+x }` | `reduce((a,x)=>a+x, 0)` |
| 합계 | `sum()` / `sumOf { it.price }` | reduce |
| 그룹핑 | `groupBy { it.category }` | (직접) |
| 평탄화 | `flatMap { it.tags }` | `flatMap` |
| 정렬 | `sortedBy { it.age }` | `sort` |
| 첫/조건 | `first{}`, `any/all/none` | `find`, `some/every` |

**체이닝(파이프라인):**
```kotlin
scores.filter { it >= 60 }.map { toGrade(it) }.count()
```
= TS `scores.filter(...).map(...).length`.

**fold (핵심):**
```kotlin
listOf(1,2,3,4).fold(0) { acc, x -> acc + x }  // 0+1=1→3→6→10
//                   ↑초기값      ↑누적   ↑현재요소
```
- `acc`=지금까지 누적, `x`=현재 요소. = TS `reduce((acc,x)=>..., 초기값)`.
- `reduce`도 있으나 초기값 없어 빈 리스트에서 터짐 → Kotlin은 **fold(초기값 명시) 선호**.

> Trade-off: 체이닝은 가독성·불변·테스트 좋음. 단 초대용량은 단계마다 중간 컬렉션 생성 → `asSequence()`(지연평가)나 루프. 99%는 체이닝.

## ③ HOW — TDD
```kotlin
fun sumOfSquares(list: List<Int>): Int =
    list.fold(0) { acc, value -> acc + value * value }   // 또는 list.sumOf { it*it }

fun hasDuplicate(list: List<Int>): Boolean {
    val set = list.toSet()        // Set은 중복 자동 제거
    return list.size != set.size  // 줄었으면 중복이 있던 것 (CS-3 해시 O(1))
}
```
- `sumOfSquares`: fold로 누적. `hasDuplicate`: toSet 크기 비교.
- `hasDuplicate`는 **숫자야구 정답 검증("중복 없는 3자리")에 바로 재사용** 🎯.

## 핵심 한 줄 요약
- 고차함수 체이닝(`filter→map→count`)으로 루프 없이 선언적. TS와 동일.
- `fold(초기값){acc,x->}` = reduce(초기값 안전판). 합계는 `sumOf`.
- 중복 검사 = `toSet().size != size`.

## 다음 예고
Phase 1-F (예외): `require`/`check`/`error`로 입력 검증, try-catch는 식. → 그 후 **숫자야구 미션 완성**(랜덤 정답 생성 + 입력 검증 + 게임 루프).
