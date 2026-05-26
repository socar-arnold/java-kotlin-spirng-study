# Stage 1 · Phase 1-C 학습 노트 — Generics & 변성(variance)

> 날짜: 2026-05-26
> 주제: 제네릭 함수/클래스, 타입 추론, 타입 제약, 타입 소거, 변성(out/in)
> 페르소나: TypeScript 개발자 → 제네릭은 거의 동일, JVM 디테일만 추가

---

## ① WHY
타입마다 똑같은 로직을 복붙(`firstInt`, `firstString`...)하거나, `Any`로 받으면 타입 정보를 잃어 캐스팅 지옥. (TS `any` 쓰면 안 되는 통증과 동일)

## ② WHAT — 제네릭 (TS와 거의 동일)
```kotlin
fun <T> first(list: List<T>): T = list[0]      // 함수: fun <T> 이름
class Box<T>(val value: T) { fun get(): T = value }   // 클래스: class 이름<T>
```
- `<T>` = 아무 타입이나 받되 그 타입을 기억. `first(listOf(10,20,30))` → 컴파일러가 인자 보고 `T=Int` **자동 추론**.
- 위치 주의: **함수는 `fun <T>`**(fun 다음), **클래스는 `class 이름<T>`**(이름 다음).

| | Kotlin | TS |
|---|---|---|
| 제네릭 함수 | `fun <T> first(l: List<T>): T` | `function first<T>(l: T[]): T` |
| 제네릭 클래스 | `class Box<T>(val value: T)` | `class Box<T> {}` |
| 타입 제약 | `fun <T : Number> f(...)` | `function f<T extends number>` |

타입 제약 예: `fun <T : Comparable<T>> maxOf2(a: T, b: T): T = if (a > b) a else b` (`>` 쓰려면 비교 가능 타입만).

## ③ 타입 소거(type erasure) — JVM 디테일
런타임엔 `<T>`가 사라짐. `if (T is String)` 불가(런타임에 T 없음). **TS도 동일**(JS엔 제네릭 없음) → 이미 아는 감각. (Kotlin은 `inline fun <reified T>`로 일부 우회 — 나중에 Phase 2)

---

## ④ 변성(variance) — out / in ⭐

질문: `ReadBox<Cat>`을 `ReadBox<Animal>` 자리에 넣을 수 있나? (Cat은 Animal인데?)
- 기본은 **금지**. 만약 값을 넣을 수도 있으면(`ReadBox<Animal>`로 받아 Dog 삽입) 원래 Cat 상자가 깨지니까.

### out = "꺼내기만 함 → 안전 → 공변(covariant)"
```kotlin
open class Animal(val name: String)
class Cat(name: String) : Animal(name)

class ReadBox<out T>(val value: T) {   // out: T가 '나가는' 자리에만
    fun get(): T = value
}

val catBox: ReadBox<Cat> = ReadBox(Cat("나비"))
val animalBox: ReadBox<Animal> = catBox   // ✅ out 덕에 OK (공변)
```
- **out** = output = 생산자(producer) = 꺼내기 전용 → **하위타입 상자를 상위타입 상자로** 취급 가능.
- **in** = input = 소비자(consumer) = 받기 전용 → 반대 방향(반공변).
- 외우기: **PECS** — Producer-`extends`(out) / Consumer-`super`(in). (Java `? extends`/`? super`와 동일)
- 대표 예: `List<out T>`(읽기 전용, 공변), `Comparable<in T>`.

### 🧪 실험으로 증명 (out 스위치)
`ReadBox<out T>`에서 **out을 빼면** →
`Initializer type mismatch: expected 'ReadBox<Animal>', actual 'ReadBox<Cat>'` **컴파일 에러**(공변 OFF).
out을 붙이면 초록(공변 ON). → **out 한 단어가 공변 스위치.** (잘못된 대입이 런타임 전 컴파일타임에 차단)

> **TS 매핑:** TS도 공변/반공변 사고가 있음(배열 공변, 함수 인자 반공변). 같은 개념.

---

## 디버깅 교훈 (이번 세션 사고)
`GenericTest.kt`에서 `first`/`Box` Unresolved 에러가 떴는데, 원인은 변성 코드가 아니라 **`Generics.kt`가 비어 있어서**였음. → **에러가 가리키는 파일·심볼을 먼저 보라.** "방금 만진 곳"이 아니라 "에러가 말하는 곳"이 원인일 때가 많다.

## 핵심 한 줄 요약
- 제네릭 `<T>`: 타입만 다른 로직 재사용, 컴파일러가 인자로 T 추론. (TS와 동일)
- 런타임 타입 소거(reified로 일부 우회).
- 변성: **out=꺼내기/공변, in=넣기/반공변** (PECS). out 한 단어가 공변 스위치.

## 다음 예고
Phase 1-D 컬렉션 (List/Set/Map, 불변 우선, CS-3 해시/Big-O 연계) → **숫자야구 미션**.
