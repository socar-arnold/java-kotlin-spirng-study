# Stage 1 · Phase 1-B 학습 노트 ② — 인터페이스 · 다형성 (vs sealed+when)

> 날짜: 2026-05-26
> 주제: interface / abstract class / 다형성, 그리고 "when vs 다형성" 트레이드오프(표현 문제)
> 페르소나: TypeScript 개발자
> ⭐ 이 노트의 핵심은 아래 **"표(格子)로 이해하기"** — 헷갈리면 여기부터 다시 보기.

---

## ⭐ 표(格子)로 이해하기 — when vs 다형성

도형 코드는 사실 **표**다. 세로 = **종류(타입)**, 가로 = **동작**:

```
            area()      perimeter()
         ┌───────────┬─────────────┐
 Circle  │  πr²      │   2πr        │
         ├───────────┼─────────────┤
 Rect    │  w×h      │   2(w+h)     │
         └───────────┴─────────────┘
```
칸 하나 = "이 종류는 이 동작을 이렇게 한다". 코드를 짤 때 **이 표를 가로로 묶을지 세로로 묶을지**를 고른다.

### 방법 ① 가로줄(종류)로 묶기 = 다형성 / interface
`Circle` 한 덩어리에 Circle의 모든 동작을 넣음.
- 새 **종류**(Triangle) 추가 → 새 가로줄(클래스) 하나 추가. ✅ 쉬움
- 새 **동작**(draw) 추가 → 모든 가로줄(클래스)을 다 열어 한 칸씩. 😫 귀찮음

### 방법 ② 세로줄(동작)로 묶기 = when
`area()` 함수 하나에 모든 종류를 `when`으로 처리.
- 새 **동작**(draw) 추가 → 새 세로줄(함수) 하나 추가. ✅ 쉬움
- 새 **종류**(Triangle) 추가 → 모든 세로줄(함수)의 when에 한 칸씩. 😫 귀찮음

### 한 장 요약 표
```
              새 타입 추가          새 동작 추가
 sealed+when   ✏️✏️✏️ (모든 when)    ✏️ (함수 1개)       ← 동작 잘 늘 때 좋음
 다형성         ✏️ (클래스 1개)       ✏️✏️✏️ (모든 클래스)  ← 타입 잘 늘 때 좋음
```
> **한 문장:** 내가 묶은 방향으로 늘리면 쉽고, 직각 방향으로 늘리면 다 고쳐야 한다.
> **고르는 법:** 내 도메인에서 **종류가 자주 늘면 다형성**, **동작이 자주 늘면 when**. (정답 없음 = "표현 문제")

### 동물원 비유
- **다형성** = 각 동물이 **자기 재주 카드**를 들고 다님. 새 동물 오면 카드 한 장 추가 ✅ / 모든 동물에 새 재주 가르치면 카드 전부 수정 😫
- **when** = **재주별 설명서**. 새 재주 추가는 설명서 한 권 ✅ / 새 동물 오면 모든 설명서 수정 😫

### 실무 감
- "종류가 느는" 것(결제수단·알림채널 등 도메인 모델) → **다형성/인터페이스**
- "처리 동작이 느는" 것(상태머신·파서·결과 처리) → **sealed + when**

---

## 개념 정리

**다형성(polymorphism):** 같은 타입(`Shape`)으로 다루지만 실제 객체에 맞는 구현이 실행됨. 호출하는 쪽은 타입을 **묻지 않음**(no `when`).

| 도구 | 언제 | Kotlin |
|---|---|---|
| **interface** | "할 수 있는 것(계약)"만, 상태 없음, **여러 개** 구현 | `class C : A, B` |
| **abstract class** | 공통 **상태/구현 일부** 공유 + 일부 강제, **하나만** 상속 | `abstract class S { abstract fun f() }` |
- 기본은 **interface 우선**(유연). 공통 상태/코드가 꼭 필요할 때만 abstract class.
- 구현 시 `override` 필수(Phase 1-B에서 배운 것). interface 구현은 `: Shape2`(괄호 없음), 클래스 상속은 `: Shape()`(괄호 있음).

## 실습 코드 (TDD)
```kotlin
interface Shape2 { fun area(): Double }
class Circle2(val radius: Double) : Shape2 { override fun area() = PI * radius * radius }
class Rectangle2(val width: Double, val height: Double) : Shape2 { override fun area() = width * height }
class Triangle2(val base: Double, val height: Double) : Shape2 { override fun area() = 0.5 * base * height }

fun totalArea(shapes: List<Shape2>): Double = shapes.sumOf { it.area() }  // when 없음! 다형성
```

## 🧪 두 실험의 거울짝 (개념 증명)
| 어제 (sealed + when) | 오늘 (다형성) |
|---|---|
| Triangle 추가 → `area`의 `when`을 **수정해야** 함 (exhaustiveness 컴파일 에러로 강제) | Triangle2 추가 → `totalArea` **한 글자도 안 고침**, 그냥 동작 ✅ |
→ "종류가 자주 늘면 다형성"이 코드로 증명됨. (반대로 동작이 자주 늘면 when이 유리)

## 핵심 한 줄 요약
- 타입×동작 = 표. **다형성=가로(종류)로 묶기 / when=세로(동작)로 묶기.**
- 묶은 방향으로 늘리면 쉽고 직각이면 다 고침 → **자주 변하는 축**으로 선택.
- `totalArea`는 타입을 안 물음(`it.area()`) = 다형성. interface 우선, 공통상태 필요시 abstract.

## 다음 예고
Phase 1-C 제네릭 → 1-D 컬렉션(여기서 CS-3 해시/Big-O 연계) → **숫자야구 미션**.
