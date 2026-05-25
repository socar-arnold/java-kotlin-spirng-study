# Stage 1 · Phase 1-B 학습 노트 — OOP & sealed class

> 날짜: 2026-05-25
> 주제: sealed class, 망라적(exhaustive) when, 스마트 캐스트, 다형성
> 페르소나: TypeScript 개발자 → 판별 유니온(discriminated union) 매핑

---

## ① WHY — "타입이 여러 개인 것"을 안전하게 표현하기
TS는 판별 유니온으로 모델링: `type Shape = {kind:"circle",...} | {kind:"rectangle",...}` → `switch(s.kind)`에서 케이스 빠뜨리면 컴파일러가 경고(exhaustiveness).
순진한 JS/Java(`if (kind === "circle")`)는 문자열 오타도, 빠진 케이스도 못 잡아 조용히 버그.

## ② WHAT — sealed class + 망라적 when
```kotlin
import kotlin.math.PI

sealed class Shape
data class Circle(val radius: Double) : Shape()
data class Rectangle(val width: Double, val height: Double) : Shape()
data class Triangle(val base: Double, val height: Double) : Shape()

fun area(shape: Shape): Double = when (shape) {
    is Circle -> PI * shape.radius * shape.radius
    is Rectangle -> shape.width * shape.height
    is Triangle -> 0.5 * shape.base * shape.height
    // else 불필요 — 자식이 전부임을 컴파일러가 앎
}
```

| 개념 | Kotlin | TS |
|---|---|---|
| 닫힌 타입 집합 | `sealed class` + 자식 | `type = A \| B` 유니온 |
| 타입 분기 | `when (x) { is Circle -> }` | `switch (x.kind)` |
| 타입 좁히기 | **스마트 캐스트**(`is Circle` 후 `shape.radius` 바로) | 타입 내로잉 |
| 빠짐 방지 | `when` 망라 강제(else 불필요) | exhaustiveness 체크 |

- **sealed** = "자식은 여기 선언된 게 전부"라는 컴파일러와의 약속 → 닫힌 집합.
- **스마트 캐스트**: `is Circle` 통과한 가지 안에선 캐스팅 없이 `Circle`로 취급.
- Trade-off: 경우의 수가 **닫혀 있고** 각각 다르게 처리할 때(결제 상태, API 성공/실패) 최적. 외부에서 새 타입을 자유 추가해야 하면 일반 인터페이스.

## ③ HOW + 🧪 실험으로 본 sealed의 가치
- TDD: `area` RED → sealed Shape 구현 GREEN.
- **exhaustiveness 실험:** `Triangle`을 추가하고 `area`는 안 고치니 →
  `'when' expression must be exhaustive. Add the 'is Triangle' branch...` **컴파일 에러**.
  → 타입을 늘리면 **고쳐야 할 모든 when을 컴파일러가 콕 집어줌.** (open class면 조용히 else로 빠져 버그 — 어제 본 "컴파일타임에 잡힌다"의 결정판)
- Q&A: `open class`로 하면 컴파일러가 전부인지 못 알아 → `else` 강제 → 새 자식이 조용히 else로 빠지는 버그. (NPE 아님; "빠진 케이스가 조용히 잘못 동작")
- TDD 규율: Triangle 가지(프로덕션 코드) 추가했으면 Triangle 테스트도 추가(테스트 없이 프로덕션 금지).
- 리팩터: `Math.PI`(자바) → `kotlin.math.PI`(idiomatic).

## 핵심 한 줄 요약
- `sealed class` = TS 판별 유니온의 Kotlin판. `when (x) { is Type -> }` + **스마트 캐스트**.
- 자식이 닫혀 있어 `when`이 **망라 강제**(else 불필요), 새 자식 추가 시 누락을 **컴파일 에러**로 잡음.

## 다음 예고
Phase 1-B 이어서/마무리 → 인터페이스 vs 추상클래스, 다형성, 그리고 **숫자야구 미션**(Phase 1 종합) 착수.
