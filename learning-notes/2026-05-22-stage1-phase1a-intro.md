# Stage 1 · Phase 1-A 학습 노트 — Introduction to Kotlin (첫 TDD)

> 날짜: 2026-05-22
> 주제: val/var, 타입 추론, 문자열 템플릿, if/when 식, 널 안전성, 최상위 함수, 단일 식 함수
> 의미: 읽기 → **직접 타이핑 + TDD(RED→GREEN→REFACTOR)** 전환 첫 세션
> 페르소나: TypeScript 개발자 → 모든 개념을 TS에 매핑

---

## ① WHY — Java/JS에서 겪던 두 통증
1. **문장 vs 식:** TS/Java의 `if`는 값을 안 돌려줘서, 변수를 먼저 비워두고 나중에 대입 → 깜빡 버그.
2. **null:** JS/Java는 null이 어디든 숨어 **런타임에** 터짐(NPE).

## ② WHAT — 개념 (TS 매핑)
| 개념 | Kotlin | TS/JS |
|---|---|---|
| 불변/가변 | `val`(기본) / `var` | `const` / `let` |
| 타입 추론 | `val x = 1` | 동일 |
| 문자열 템플릿 | `"Hello, $name!"`, `"${식}"` | `` `Hello, ${name}!` `` |
| if/when = **식** | `val g = if (a) "x" else "y"` | 삼항 `?:` 만 |
| 널 가능 타입 | `String?` (물음표 있어야 null 허용) | `string \| null` |
| 엘비스 | `name ?: "Guest"` | `name ?? "Guest"` |
| 안전 호출 | `name?.uppercase()` | `name?.toUpperCase()` |

**핵심:** Kotlin에선 `String`과 `String?`이 **다른 타입**. `String`엔 null이 못 들어가 컴파일러가 **NPE를 컴파일 단계에서** 차단. (TS `strictNullChecks`를 언어가 강제)

### NPE = NullPointerException
null에 점(.) 찍어 뭔가 시키다 터지는 런타임 에러. JS의 `Cannot read properties of null`과 같은 병. Kotlin은 타입에 `?`가 없으면 null을 못 넣게 해서 컴파일 때 막음.

---

## ③ HOW — 첫 TDD: `greet(name: String?): String`

목표: 이름 있으면 `"Hello, {이름}!"`, null이면 `"Hello, Guest!"`.

### 🔴 RED
테스트 먼저 작성 → `greet` 없으니 `Unresolved reference 'greet'`로 실패. (곁가지 `Cannot infer T/U`는 greet가 생기면 사라지는 소음)

### 🟢 GREEN — 배운 교훈 2가지
1. **최상위 함수(top-level function):** 처음엔 Java 습관으로 `class Intro { fun greet... }`로 감쌌더니 메서드가 되어 `greet(...)` 직접 호출 불가 → 또 unresolved. **Kotlin은 TS/JS처럼 클래스 없이 파일 최상위에 함수**를 둘 수 있다. (TS `export function greet()`와 동일) → 클래스 껍데기 제거.
2. **섀도잉:** `val name = name ?: "Guest"` 로 널 가능 파라미터를 같은 이름의 널 불가 val로 좁히는 흔한 관용구.
   - (버그 교훈: 대체값을 `"guest"`로 썼다가 테스트 기대값 `"Guest"`와 불일치 → TDD가 정확히 어디 틀렸는지 짚어줌)

### 🔵 REFACTOR — 단일 식 함수(expression body)
```kotlin
fun greet(name: String?): String = "Hello, ${name ?: "Guest"}!"
```
- 본문이 `return` 하나뿐인 식이면 `{ return ... }` → **`= 식`** 으로. (if/when이 식이라 가능)
- TS 화살표 한 줄 `(name) => `Hello, ${name ?? "Guest"}!`` 와 같은 감각.
- Trade-off: 짧고 또렷할 때 선호. 본문이 길면 `{ }` 블록이 더 읽기 좋음.
- 참고: Kotlin은 **들여쓰기에 의미 없음**(Python과 다름) — 동작엔 무관하나 깔끔함은 챙길 것.

최종 결과: 20→1줄, 테스트 초록 유지. **RED→GREEN→REFACTOR 완주.**

---

## 핵심 한 줄 요약
- `val` 기본, `String` vs `String?`는 다른 타입 → **NPE를 컴파일 때 차단**.
- `?:`(엘비스), `?.`(안전호출), `"$x"`(템플릿), `if/when`은 **식**.
- Kotlin은 **최상위 함수** 허용(클래스 불필요), `= 식`으로 **단일 식 함수**.
- TDD: 실패하는 테스트 → 최소 구현 → 초록 유지하며 리팩터.

## 다음 예고 (Phase 1-A 이어서)
`when` 식으로 분기(점수→등급), 범위(`in 1..10`), 스마트 캐스트, 그리고 `data class` 맛보기.
