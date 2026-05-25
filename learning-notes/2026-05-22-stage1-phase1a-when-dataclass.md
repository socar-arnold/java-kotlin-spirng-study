# Stage 1 · Phase 1-A 학습 노트 ② — when 식, 범위, data class

> 날짜: 2026-05-22
> 주제: `when` 식, 범위(`in a..b`), 경계값 테스트, `data class`(값 동등성/copy)
> 페르소나: TypeScript 개발자 → 모든 개념을 TS에 매핑

---

## A. `when` 식 + 범위 — `grade(score)`

### WHY
TS/Java의 `if-else if` 사다리는 길고, `result` 빈 변수 선언 후 대입(문장이라 값 안 돌려줌). `switch`는 `break` 깜빡 버그.

### WHAT
```kotlin
fun grade(score: Int): String = when {
    score >= 90 -> "A"
    score >= 80 -> "B"
    score >= 70 -> "C"
    score >= 60 -> "D"
    else -> "F"
}
```
- `when`은 **식**(값 반환) → `=`로 바로 반환. `break` 불필요. 식으로 쓰면 `else` 필수.
- 조건형 `when { 조건 -> 값 }`: 위→아래 첫 매칭. **큰 값부터** 위에 둬야 함(아니면 95가 D에서 잡힘).
- 범위형 대안: `when (score) { in 90..100 -> "A" ... }`. `90..100`=범위 객체, `in`으로 포함 검사. (TS엔 없는 문법)
- Trade-off: 조건형=자유·짧음 / 범위형=구간 또렷. 둘 다 정답.

### 경계값 테스트의 중요성
버그는 경계에 숨는다. `>=`를 `>`로 잘못 쓰면 95는 못 잡고 **90에서만** 틀림. 그래서 `90`(A여야)·`89`(B여야) 같은 **경계 양쪽**을 콕 집어 테스트. (실무 "경계값 분석" 1순위)

---

## B. `data class` — 값 동등성과 copy

### WHY
JS `{x:1}===  {x:1}` → false(주소 비교). Java는 내용 비교하려면 `equals/hashCode/toString` 전부 수작업.

### WHAT
```kotlin
data class Point(val x: Int, val y: Int)
```
한 줄로 자동 생성: `equals`(내용 비교), `hashCode`, `toString`(`Point(x=1, y=2)`), `copy`, 구조분해.
- **Kotlin `==` = 내용 비교(equals)**, **`===` = 주소 비교**(JS의 `===`에 해당).
- `copy(y=9)` ≈ TS 스프레드 `{...p, y:9}` (불변 업데이트).
- Trade-off: "값 묶음"엔 data class, "행위 덩어리"엔 일반 class.

### 🎯 직접 실험으로 본 컴파일타임 vs 런타임 (1주차 개념 재등장)
`data`를 빼고 일반 `class Point`로 실험:
| 사라진 것 | 본 에러 | 성격 |
|---|---|---|
| `copy()` | `Unresolved reference 'copy'` | **컴파일타임** (실행 전 차단) — copy는 Any에 없어 존재 자체가 없음 |
| `equals()` | `AssertionFailedError`(주소 비교라 내용 같아도 false) | **런타임** — `equals`는 Any로부터 이미 있어 컴파일 OK, 행동만 틀림 |
→ 한 실험으로 "컴파일러가 미리 잡는 것" vs "실행해야 드러나는 것"을 눈으로 체득.

---

## 교훈 (verification 습관)
"다 초록인 줄 알았는데" 실제론 저장이 안 돼 `BUILD FAILED`였던 순간 → **커밋 전 항상 `./gradlew test`로 진짜 초록 확인**. 이게 시니어의 "verification before completion" 습관.

## 핵심 한 줄 요약
- `when`은 식(값 반환), 범위 `in a..b`, 큰 값부터 위에. 경계값을 테스트하라.
- `data class` = 값 동등성(`==`)·`copy`·`toString` 자동. `===`만 주소 비교.
- copy 없음=컴파일에러 / equals 틀림=런타임실패 → 컴파일 vs 런타임 재확인.

## 다음 예고
Phase 1-B(OOP): 클래스/생성자, `val` 프로퍼티, 인터페이스, sealed class(=TS 유니온), 그리고 숫자야구 미션 준비.
