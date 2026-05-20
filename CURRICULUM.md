# Kotlin + TDD 클린코드 학습 커리큘럼

## 참고 자료

**JetBrains "Programming in Kotlin" 공식 코스** (슬라이드 링크 목록)
- Topic 1 — Introduction: https://docs.google.com/presentation/d/11mYc_tt2c7qw72i8gaQ9vePTcd0F0LbZCS6ep9PFG28/edit
- Topic 2 — OOP: https://docs.google.com/presentation/d/1hZTaQ1gdStte2aeQU78UmpVzpKErdeOCRQPOYH2p3DI/edit
- Topic 3 — Generics: https://docs.google.com/presentation/d/1n8rTULotZHei3ktajyupwRpKdPDACBAZeBo2GqwYhHY/edit
- Topic 4 — Collections: https://docs.google.com/presentation/d/1RvnmqWM-Q_hYi1dWwqN1ieK2pZAwlThOkLI9j5yqViU/edit
- Topic 5 — Functional Programming: https://docs.google.com/presentation/d/1R7n5plsn5caGpYrI9omxbEuX6pazjDj2d9X0IQ2AdLg/edit
- Topic 6 — Parallel & Concurrent: https://docs.google.com/presentation/d/19C10TZM1kT0AzEjqSfLZs1_HC3Ye0E9h6muVDikl4uo/edit
- Topic 7 — Async (Coroutines): https://docs.google.com/presentation/d/1_F9CVHdbXoRagLUpBGjpwS9DDbzB2K-Cfv4Pz5qKza4/edit
- Topic 8 — Exceptions (optional): https://docs.google.com/presentation/d/1o0c25j-5UKE1Qw94W26numHxMU_xL0uFchCWJfaOuUc/edit
- Topic 9 — Testing (optional): https://docs.google.com/presentation/d/1Plt2cpm-GRzxHt1Vu8C90FWnuSYToucWXPLaJKlBRMc/edit
- Topic 10 — Build Systems (advanced): https://docs.google.com/presentation/d/1njTh1B3wC2jycgqQAIdiQ6oTmAnClVgYyoW_KNoZ0fs/edit
- Topic 11 — JVM & Kotlin Compiler (advanced): https://docs.google.com/presentation/d/1WT0kVeLpZ8-cS1211oXVvjPesgPgTJxIuIJHkU6-49k/edit

추천 교재: *Kotlin in Action, Second Edition* — Roman Elizarov 외, Manning, 2022

---

## NEXTSTEP × JetBrains 커리큘럼 매핑

각 NEXTSTEP 챕터 진입 전에 해당 JetBrains 슬라이드를 함께 보는 것을 권장한다.

| NEXTSTEP 챕터 | 미션 | JetBrains Topic | 핵심 개념 |
|---|---|---|---|
| 1. 단위 테스트, 메소드 분리 | 문자열 계산기 | Topic 1 (Introduction) | 변수, 타입, 제어흐름, 함수 |
| 2. TDD, 클래스 분리 | 자동차 경주 | Topic 2 (OOP) | 클래스, data class, 불변성 |
| 3. 상속과 인터페이스 | 좌표계산기 | Topic 2 (OOP) | abstract, sealed, interface |
| 4. 함수형 프로그래밍 | 블랙잭 | Topic 4+5 (Collections + FP) | map, filter, reduce, 람다 |
| 5. 로또 | 로또 | Topic 4 (Collections) | 컬렉션 심화, Generics 입문 |
| 6. RSS리더 | RSS리더 | Topic 6 (Parallel) | 스레드, 동시성 기초 |
| 7. 코루틴 레이싱 | 코루틴 레이싱 | Topic 7 (Async) | 코루틴, suspend, Flow |

**선택 보충:**
- Topic 8 (Exceptions) — `require`/`check`/`error` 심화, 커스텀 예외 설계
- Topic 9 (Testing) — Kotest 심화, 테스트 전략
- Topic 3 (Generics) — 로또/컬렉션 단계에서 `List<T>` 타입 파라미터 이해 필요 시

## 학습자 프로필

- React/Next.js에 능숙한 FE 개발자
- Spring + Kotlin 백엔드 역량 강화 목적
- NEXTSTEP 클린코드 커리큘럼(Java 기반)을 Kotlin으로 진행
- 7월 전까지 Spring Boot + JPA + 실전 운영 개념 습득 목표
- 이후 `test-drive-portal-api` 직접 기여 예정

---

## 교육 원칙

### 1. WHY → WHAT → HOW 순서

코드부터 보여주지 않는다. 반드시 이 순서로 진행한다:

1. **왜 이게 문제인가** — 나쁜 코드 예시로 시작
2. **무엇을 만들 것인가** — 개념 설명
3. **어떻게 만드는가** — TDD로 구현

예) sealed class 설명 전에 `if/when` 분기의 문제점을 먼저 보여준다.

### 2. FE 개발자 언어로 설명

학습자가 TypeScript에 익숙하므로, 새 개념은 TS 비교로 먼저 연결한다.

```
// TypeScript
type Fuel = "Gasoline" | "Diesel" | "LPG"

// Kotlin sealed class — 이것의 클래스 버전
sealed class Fuel { ... }
```

### 3. TDD Red → Green → Refactor 철저히

- 테스트 없이 프로덕션 코드 절대 작성 금지
- 학습자가 직접 타이핑 (복붙 금지)
- 각 단계에서 실제로 실행해서 RED/GREEN 확인 후 진행
- REFACTOR는 "정리할 게 있는지 함께 검토"로 마무리

### 4. Trade-off 반드시 설명

개념 소개 시 항상 세 가지를 함께 설명한다:
- 이 상황에서 왜 이 선택인가
- 언제 이게 유리한가
- 언제 이게 과하거나 다른 선택이 더 나은가

### 5. YAGNI 엄수

지금 쓰이지 않는 코드는 넣지 않는다. 설계 시 추가한 속성/메서드가 실제로 쓰이지 않으면 즉시 제거하거나 쓰는 기능을 추가한다.

### 6. 실수는 솔직하게

설명 중 잘못된 코드 예시나 모순이 있으면 변명 없이 인정하고 바로잡는다.

---

## 환경 세팅

```
프로젝트명: kotlin-baseball
위치: ~/Desktop/dev/kotlin-baseball
IDE: IntelliJ IDEA
```

### build.gradle.kts

```kotlin
plugins {
    kotlin("jvm") version "2.3.20"
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

## 챕터별 진행 내용

### 챕터 1 — 단위 테스트, 메소드 분리 ✅

**학습 목표:** Kotest로 TDD 사이클 체험, 메소드 분리 감각 익히기

**구현:** StringCalculator — 문자열 사칙연산

```kotlin
object StringCalculator {
    fun calculate(input: String): Int {
        if (input.isEmpty()) return 0
        val numbers = input.split(" ").filter { it.toIntOrNull() != null }.map { it.toInt() }
        val operators = input.split(" ").filter { it.toIntOrNull() == null }
        return numbers.reduceIndexed { index, acc, value ->
            when (operators[index - 1]) {
                "+" -> acc + value
                "-" -> acc - value
                "*" -> acc * value
                "/" -> acc / value
                else -> throw IllegalArgumentException("Unknown operator")
            }
        }
    }
}
```

**테스트 케이스:**
- 빈 문자열 → 0
- 숫자 하나 → 그대로 반환
- 사칙연산 조합

---

### 챕터 2 — TDD, 클래스 분리 ✅

**학습 목표:** data class, 클래스 책임 분리, 의존성 주입(NumberGenerator)

**구현:** RacingGame — 자동차 경주 게임

핵심 클래스:
- `Car(name: String, position: Int = 0)` — data class, `move(number: Int): Car`
- `RacingGame(cars: List<Car>)` — `race(numberGenerator: () -> Int)`, `winners()`
- `RandomNumberGenerator` — `generate(): Int`

**핵심 개념:**
- `data class` — equals/hashCode/copy/toString 자동 생성
- 불변 객체: `move()`가 새 Car를 반환 (기존 객체 수정 X)
- 난수 생성기를 주입받아 테스트 가능하게 만들기

---

### 챕터 3 — 상속과 인터페이스 ✅

#### 파트 1: 연료 주입 (상속)

**학습 목표:** abstract class, sealed class, OCP, 다형성

**문제 제시 순서:**
1. `if/when` 분기로 연료 타입 처리하는 나쁜 코드 보여주기
2. enum의 한계 설명 (행동이 다른 경우)
3. sealed class 소개 — TypeScript union type과 비교
4. abstract class로 공통 로직 위치 설명

**구현:** Fuel, Car, GasStation

```kotlin
sealed class Fuel {
    abstract val pricePerLiter: Int
    object Gasoline : Fuel() { override val pricePerLiter = 1700 }
    object Diesel   : Fuel() { override val pricePerLiter = 1500 }
    object LPG      : Fuel() { override val pricePerLiter = 900  }
}

abstract class Car(val name: String, val fuelType: Fuel) {
    var tank: Int = 0
        private set

    fun fillUp(liters: Int, fuel: Fuel) {
        require(liters > 0) { "주유량은 0보다 커야 합니다" }
        require(fuel == fuelType) { "잘못된 연료 타입입니다: $fuel" }
        tank += liters
    }
}

class GasStation {
    fun refuel(car: Car, liters: Int): Int {
        car.fillUp(liters, car.fuelType)
        return car.fuelType.pricePerLiter * liters
    }
}
```

**수식어 비교표 (반드시 설명):**

| 수식어 | 인스턴스 생성 | 상속 | 자식 위치 제한 |
|---|---|---|---|
| `class` | 가능 | 불가(기본 final) | 없음 |
| `open class` | 가능 | 가능 | 없음 |
| `abstract class` | 불가 | 가능 | 없음 |
| `sealed class` | 불가 | 가능 | 같은 파일만 |

**검증 함수 비교 (반드시 설명):**

| 함수 | 용도 | 예외 |
|---|---|---|
| `require` | 파라미터 검증 | IllegalArgumentException |
| `check` | 객체 상태 검증 | IllegalStateException |
| `error` | 도달 불가 코드 | IllegalStateException |

#### 파트 2: 좌표계산기 (인터페이스)

**학습 목표:** interface, 다중 구현, abstract class와의 차이

**핵심 설명:**
- interface = "능력/행동 계약", 공통 로직 없음, 다중 구현 가능
- abstract class = 공통 로직 + 구현 강제 가능, 단일 상속만
- 케이스 고정 → sealed class / 외부 확장 필요 → interface

**구현:** Point, Measurable, Line, Triangle, Rectangle

```kotlin
data class Point(val x: Int, val y: Int) {
    init {
        require(x in 0..24) { "x 좌표는 0~24 사이여야 합니다" }
        require(y in 0..24) { "y 좌표는 0~24 사이여야 합니다" }
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

class Rectangle(val p1: Point, val p2: Point, val p3: Point, val p4: Point) : Measurable {
    init {
        require(isRightAngle(p1, p2, p3)) { "직사각형이 아닙니다" }
        require(isRightAngle(p4, p1, p2)) { "직사각형이 아닙니다" }
    }

    override fun measure(): Double {
        val width = Line(p1, p2).measure()
        val height = Line(p2, p3).measure()
        return width * height
    }

    private fun isRightAngle(a: Point, b: Point, c: Point): Boolean {
        val v1x = b.x - a.x
        val v1y = b.y - a.y
        val v2x = c.x - b.x
        val v2y = c.y - b.y
        return (v1x * v2x + v1y * v2y) == 0
    }
}
```

---

### 챕터 4 — 함수형 프로그래밍, 스트림, 람다 (진행 중)

**학습 목표:** 람다, 고차함수, 컬렉션 함수형 연산

**미션:** 블랙잭 구현 (Step 2 → Step 3)

**사전 설명:**
- 람다: `{ x -> x + 1 }` 형태의 익명 함수
- 고차함수: 함수를 파라미터로 받거나 반환
- `map`, `filter`, `reduce` — Java Stream의 Kotlin 버전
- 챕터 2에서 이미 `cars.map { it.move(...) }`로 맛봄

**블랙잭 규칙:**
- 카드 52장 (4문양 × 13종류)
- 숫자 카드: 액면가, J/Q/K: 10, A: 1 또는 11
- 플레이어/딜러 각 2장 시작
- 플레이어: 21 이하면 히트 가능, 그만하면 스탠드
- 딜러: 16 이하 무조건 히트, 17 이상 스탠드
- 21 초과 시 버스트(패배)

**게임 상태 흐름:**
```
[카드 2장] → FirstTurn
                 │
         ┌───────┴───────┐
      합==21          합<21
         │               │
     Blackjack          Hit
                         │
              ┌──────────┼──────┐
           합>21       합≤21  그만받기
              │           │       │
            Bust       다시Hit   Stay
```

---

## 다음 미션 로드맵

| 순서 | 미션 | 핵심 개념 | 사전 학습 자료 |
|---|---|---|---|
| 4 | 블랙잭 | 함수형, 람다, 고차함수 | JetBrains Topic 4+5 |
| 5 | 로또 | 컬렉션 연산 심화, 입출력 | JetBrains Topic 4 |
| 6 | RSS리더 | HTTP, 주기 실행, 병렬 처리 | 백엔드 기초 + JetBrains Topic 6 |
| 7 | 코루틴 레이싱 | 코루틴, suspend, Channel | 비동기 프로그래밍 PDF + JetBrains Topic 7 |

이후: Spring Boot + JPA 기초 → test-drive-portal-api 기여

---

## 보충 이론 A: 백엔드 네트워크 기초

> RSS리더(챕터 6) 진입 전에 이 섹션을 숙지한다.
> 출처: *Backend Development Basics* (JetBrains 강의 자료)

### 네트워크 기초

**IP 주소와 포트**
- IP 주소: 네트워크 상의 컴퓨터 식별자
- 포트: 같은 컴퓨터 내 여러 프로세스를 구분
- 연결 = 출발지 IP+포트 ↔ 목적지 IP+포트

**IPv4 특수 주소**
| 주소 | 의미 |
|---|---|
| `127.0.0.1` | localhost (자기 자신) |
| `0.0.0.0` | 모든 인터페이스에서 수신 |
| `255.255.255.255` | broadcast |
| `10.x.x.x`, `192.168.x.x` | private 주소 (외부에서 접근 불가) |

**TCP 프로토콜**
- 연결 지향적 (3-way handshake)
- 패킷 순서 보장, 유실 시 재전송
- 소켓 = TCP 연결의 끝점

### 소켓 프로그래밍 (Kotlin)

```kotlin
// 서버 (ServerSocket)
val serverSocket = ServerSocket(8080)
val clientSocket = serverSocket.accept()   // 연결 대기 (blocking)
val reader = BufferedReader(InputStreamReader(clientSocket.inputStream))
val writer = PrintWriter(clientSocket.outputStream, true)

// 클라이언트 (Socket)
val socket = Socket("127.0.0.1", 8080)
val writer = PrintWriter(socket.outputStream, true)
val reader = BufferedReader(InputStreamReader(socket.inputStream))
writer.println("Hello, Server!")
```

### Web API 종류

| 방식 | 특징 |
|---|---|
| SOAP | XML 기반, 엄격한 계약, 과거 엔터프라이즈 표준 |
| REST | HTTP 기반, 자원 중심, 현재 주류 |
| GraphQL | 쿼리 언어, 클라이언트가 필요한 데이터 선택 |
| RPC/gRPC | 함수 호출 스타일, 성능 중심 |

### HTTP 프로토콜

HTTP는 **텍스트 기반** 프로토콜이다. 요청/응답 모두 사람이 읽을 수 있는 텍스트다.

**요청 구조:**
```
GET /users/1 HTTP/1.1
Host: api.example.com
Accept: application/json
```

**응답 구조:**
```
HTTP/1.1 200 OK
Content-Type: application/json

{"id": 1, "name": "Arnold"}
```

**HTTP 메서드:**
| 메서드 | 의미 | 멱등성 |
|---|---|---|
| `GET` | 조회 | O |
| `POST` | 생성 | X |
| `PUT` | 전체 수정 | O |
| `PATCH` | 부분 수정 | O |
| `DELETE` | 삭제 | O |

**HTTP 상태 코드:**
| 범위 | 의미 | 예시 |
|---|---|---|
| 2xx | 성공 | 200 OK, 201 Created, 204 No Content |
| 3xx | 리다이렉션 | 301 Moved, 304 Not Modified |
| 4xx | 클라이언트 에러 | 400 Bad Request, 401 Unauthorized, 404 Not Found |
| 5xx | 서버 에러 | 500 Internal Server Error, 503 Service Unavailable |

### REST 핵심 원칙

- **자원(Resource)** 중심 URL: `/users/1`, `/posts/42/comments`
- **HTTP 메서드**로 동사 표현: `GET /users` vs `POST /users`
- **무상태(Stateless)**: 서버가 클라이언트 상태를 저장하지 않음
- **표현(Representation)**: 자원은 JSON, XML 등으로 표현

---

## 보충 이론 B: 비동기 프로그래밍 in Kotlin

> 코루틴 레이싱(챕터 7) 진입 전에 이 섹션을 숙지한다.
> 출처: *Asynchronous Programming in Kotlin* (JetBrains 강의 자료)

### 왜 비동기가 필요한가

**스레드의 문제점:**
1. **비용이 크다** — 스레드 하나가 OS 자원을 수MB 점유
2. **수가 제한된다** — 스레드 1,000,000개 생성 시 OOM 발생
3. **레이스 컨디션** — 여러 스레드가 같은 데이터에 동시 접근
4. **데드락** — 서로 상대가 잡은 락을 기다리며 영원히 멈춤

```kotlin
// 이렇게 하면 OutOfMemoryError
fun main() {
    repeat(1_000_000) {
        thread { sleep(1000L); println("Hello $it!") }
    }
}
// java.lang.OutOfMemoryError: unable to create native thread
```

**블로킹 코드의 문제:**
```kotlin
fun postItem(item: Item) {
    val token = preparePost()   // 스레드 블로킹! 이 동안 다른 일 못 함
    val post = submitPost(token, item)
    processPost(post)
}
```

### 비동기 해결 방법의 역사

**1단계: 콜백 (Callback Hell)**
```kotlin
preparePost { token ->
    submitPost(token, item) { post ->
        processPost(post) { result ->
            // 콜백 지옥: 깊이가 깊어질수록 읽기 어려움
        }
    }
}
```

**2단계: Futures/Promises**
```kotlin
CompletableFuture<Token>
    .supplyAsync { preparePost() }
    .thenCompose { token -> submitPost(token, item) }
    .thenAccept { post -> processPost(post) }
// 체인은 낫지만 여전히 콜백 스타일
```

**3단계: Kotlin 코루틴 (현재)**
```kotlin
suspend fun postItem(item: Item) {
    val token = preparePost()    // suspend — 스레드 블로킹 없이 기다림
    val post = submitPost(token, item)
    processPost(post)
}
```
코루틴은 **마치 동기 코드처럼 생겼지만 비동기로 동작**한다.

### 코루틴 기초 개념

**코루틴 역사:**
- 1958년 처음 개념 등장
- Go(2009), C#(2012), Kotlin(2017) 순으로 채택
- Kotlin 구현: `kotlin.coroutines` (stdlib) + `kotlinx.coroutines` (라이브러리)

**suspend 함수:**
- `suspend` 키워드 = "일시 중단 가능한 함수"
- 스레드를 블로킹하지 않고 코루틴만 일시 정지
- 내부적으로 `Continuation<T>` 콜백으로 변환됨

```kotlin
suspend fun preparePost(): Token {
    // 네트워크 I/O 대기 — 스레드는 해방, 코루틴만 대기
    return networkClient.fetch(...)
}
```

### 코루틴 빌더

```kotlin
// launch: 결과 반환 없이 코루틴 시작, Job 반환
val job = launch { doWork() }

// async: 결과를 나중에 받을 수 있는 코루틴, Deferred<T> 반환
val deferred: Deferred<Token> = async { preparePost() }
val token = deferred.await()  // 결과 기다리기

// runBlocking: 현재 스레드를 블로킹하며 코루틴 실행 (테스트/main 용)
fun main() = runBlocking { doWork() }

// produce: 채널로 값을 내보내는 코루틴
val channel: ReceiveChannel<Int> = produce { send(1); send(2) }
```

> **기본 동작은 순차(sequential)다.** 동시 실행을 원하면 명시적으로 `launch { }` 를 써야 한다.

### Dispatchers (어느 스레드에서 실행할지)

| Dispatcher | 용도 |
|---|---|
| `Dispatchers.Main` | UI 스레드 (Android/Compose) |
| `Dispatchers.IO` | 파일, 네트워크 등 I/O 작업 |
| `Dispatchers.Default` | CPU 집약적 연산 |

**Context switching:**
```kotlin
suspend fun postItem(item: Item) {
    val token = withContext(Dispatchers.IO) { preparePost() }   // IO 스레드
    val post = withContext(Dispatchers.IO) { submitPost(token, item) }
    withContext(Dispatchers.Default) { processPost(post) }       // Default 스레드
}
// postItem 자체는 원래 스코프(Main 등)에서 시작 → 완료 후 다시 돌아옴
```

**왜 스레드보다 좋은가:**
- Main 스레드가 블로킹되지 않음 (Not blocked 구간)
- 소수의 스레드로 수백만 코루틴 처리
- 스레드 전환 비용 없음 (OS 개입 불필요)

### CoroutineScope와 컨텍스트

**컨텍스트 조합:**
```kotlin
val scope = CoroutineScope(
    Dispatchers.Default
    + SupervisorJob()
    + CoroutineName("MyScope")
    + CoroutineExceptionHandler { context, error ->
        println("${context[CoroutineName]?.name}: $error")
    }
)
```
- 컨텍스트 요소들은 `+` 로 합산, 같은 Key면 오른쪽이 우선
- `SupervisorJob`: 자식 코루틴 예외가 다른 자식에게 전파되지 않음

**설계 관례:**
```kotlin
// 오래 기다리는 함수 → suspend fun
suspend fun work(...) { ... }

// 백그라운드 작업을 띄우고 빨리 반환하는 함수 → CoroutineScope 확장 함수
fun CoroutineScope.backgroundWork(...) {
    launch { ... }
}
```

### 예외 처리와 전파

**Job 계층 구조:**
```
Job (root)
├── launch            ← 예외 발생 시 부모로 전파
│   └── launch
└── SupervisorJob     ← 자식 예외가 다른 자식에게 전파되지 않음
    ├── launch X (예외)
    └── launch O (계속 실행)
```

- 일반 `launch`: 자식 예외 → 부모로 전파 → 형제 코루틴 취소
- `SupervisorJob` 하위: 각 자식이 독립적으로 실패
- `CancellationException`: 예외 전파에서 제외(정상 취소)

```kotlin
// 잘못된 예시: GlobalScope는 Job이 없어 위험
GlobalScope.launch { downloadContent(location) }  // 누수 가능

// 올바른 예시: coroutineScope로 구조화된 동시성
suspend fun processReferences(refs: List<Reference>) {
    coroutineScope {
        for (ref in refs) {
            launch { downloadContent(ref.resolveLocation()) }
        }
    }
}
```

### 코루틴 취소

```kotlin
val job = launch { repeat(1_000) { delay(500L) } }
job.cancel()       // 취소 신호 전송 (cooperative)
job.join()         // 완료까지 대기
// 또는
job.cancelAndJoin()

// 취소를 직접 확인하는 패턴
while (isActive && i < 5) { ... }

// finally에서 suspend 함수 사용 시 NonCancellable 필요
try {
    repeat(1_000) { delay(500L) }
} finally {
    withContext(NonCancellable) {
        delay(1000L)  // cleanup
    }
}
```

> **취소는 cooperative(협조적)이다.** 코루틴이 `delay`, `yield` 등 suspend 포인트에 도달해야 실제 취소된다.

### Mutex (코루틴 안전 락)

```kotlin
val mutex = Mutex()
var counter = 0

suspend fun withMutex() {
    repeat(1_000) {
        launch {
            mutex.withLock { counter++ }   // 코루틴 안전
        }
    }
}
// ReentrantLock은 코루틴에서 unsafe — resume이 다른 스레드에서 일어날 수 있음
```

### Channels

**채널 = 코루틴 간 통신 파이프**
```kotlin
// Channel은 BlockingQueue와 같지만 suspend 방식
// put → send (suspend), take → receive (suspend)

val channel = Channel<Int>()
launch {
    for (x in 1..5) channel.send(x * x)
}
repeat(5) { println(channel.receive()) }  // 1, 4, 9, 16, 25
```

**Fan-out / Fan-in:**
```kotlin
// 여러 생산자 → 하나의 채널 (fan-in)
// 하나의 채널 → 여러 소비자 (fan-out)
fun <T> CoroutineScope.production(ch: SendChannel<T>, msg: T) =
    launch { while (true) { delay(Random.nextLong(23)); ch.send(msg) } }

fun <T> CoroutineScope.processing(ch: ReceiveChannel<T>, name: String) =
    launch { for (msg in ch) println("$name: received $msg") }
```

**채널 특성:**
- 기본 버퍼: `RENDEZVOUS` (버퍼 없음, send-receive 동시에 일어남)
- FIFO 순서 보장
- `trySend`/`tryReceive`: non-suspending 버전

### Sequences (지연 평가 스트림)

```kotlin
val fibonacci = sequence {
    var cur = 1; var next = 1
    while (true) {
        yield(cur)       // suspend 포인트 — 여기서 일시 정지
        val tmp = next; next = cur + next; cur = tmp
    }
}

val iter = fibonacci.iterator()
println(iter.next())  // 1
println(iter.next())  // 1
println(iter.next())  // 2
// 무한 수열이지만 필요할 때만 계산됨 (lazy)
```

### 코루틴 빌더 목록

| 빌더 | 반환 타입 | 설명 |
|---|---|---|
| `launch` | `Job` | fire-and-forget, 결과 없음 |
| `async` | `Deferred<T>` | 결과를 나중에 `await()`로 수령 |
| `runBlocking` | `T` | 현재 스레드 블로킹, main/test용만 |
| `produce` | `ReceiveChannel<E>` | 채널로 값 방출 |
| `future` | `CompletableFuture<T>` | jdk8 호환 (experimental) |
| `actor` | `SendChannel<E>` | 코루틴 + 채널 조합 |

### async/await — Kotlin 관용 스타일

```kotlin
// C# 스타일 (가능하지만 Kotlin답지 않음)
val deferredToken = async { preparePost() }
val token = deferredToken.await()

// Kotlin 관용 스타일 — 그냥 suspend fun 직접 호출
val token = preparePost()         // suspend 함수라 동일하게 비동기
val post = submitPost(token, item)
processPost(post)
```

> `async/await`는 Kotlin에서도 가능하지만, 그냥 `suspend fun`을 직접 호출하는 게 더 자연스럽다.

---

## 챕터 5 — 로또 (예정)

**학습 목표:** 컬렉션 연산 심화, 입출력 처리, 에러 핸들링

**핵심 기능:**
- 구입 금액 입력 → 1,000원당 1장 발행
- 1~45 중 중복 없는 6개 숫자 랜덤 생성, 오름차순
- 당첨 번호 6개 + 보너스 번호 1개 입력
- 등수 판별 (1~5등, 2등=5개 일치 + 보너스)
- 수익률 계산 (소수점 둘째 자리 반올림)
- 잘못된 입력 시 `[ERROR]` 메시지 후 재입력

**등수 기준:**
| 등수 | 조건 | 상금 |
|---|---|---|
| 1등 | 6개 일치 | 2,000,000,000 |
| 2등 | 5개 + 보너스 | 30,000,000 |
| 3등 | 5개 | 1,500,000 |
| 4등 | 4개 | 50,000 |
| 5등 | 3개 | 5,000 |

**함수형 핵심:**
```kotlin
// 컬렉션 연산 조합 예시
val winners = lottos
    .map { it.countMatches(winningNumbers) }
    .groupBy { it }
    .mapValues { (_, list) -> list.size }
```

---

## 챕터 6 — RSS리더 (예정)

**학습 목표:** HTTP 통신, XML 파싱, 주기 실행, 병렬 처리

**사전 학습:** 보충 이론 A (백엔드 네트워크 기초) + JetBrains Topic 6

**핵심 기능:**
- 기술 블로그 RSS 가져와 최신 게시글 목록 구성
- 작성 날짜 기준 내림차순 정렬, 최대 10개
- 키워드 입력 시 제목 필터링
- 10분마다 RSS 피드 자동 확인 (구독 기능)
- 새 게시글 있으면 콘솔 알림

**핵심 개념:**
- `HttpURLConnection` 또는 `OkHttp`로 HTTP GET
- XML 파싱: `javax.xml.parsers.DocumentBuilder`
- `ScheduledExecutorService` 또는 코루틴 `delay()`로 주기 실행
- 복수 블로그를 병렬로 fetch

---

## 챕터 7 — 코루틴 레이싱 (예정)

**학습 목표:** 코루틴 실전 적용, 비동기 제어, 구조화된 동시성

**사전 학습:** 보충 이론 B (비동기 프로그래밍) + JetBrains Topic 7

**핵심 기능:**
- 자동차 이름 입력 + 목표 거리 입력
- 각 자동차를 **코루틴**으로 비동기 제어
- 0~500ms 랜덤 딜레이 후 한 칸 전진
- 실시간 위치 출력
- 경주 결과 출력
- 우승자 발생 시 나머지 코루틴 전부 취소

**핵심 코드 패턴:**
```kotlin
suspend fun race(cars: List<String>, targetDistance: Int) = coroutineScope {
    val jobs = cars.map { carName ->
        launch {
            var position = 0
            while (position < targetDistance) {
                delay(Random.nextLong(500))
                position++
                println("$carName: ${"=".repeat(position)}")
            }
            // 우승자 발생 시 나머지 취소
            coroutineContext.cancelChildren()
        }
    }
    jobs.forEach { it.join() }
}
```

**챕터 7에서 다루는 코루틴 심화:**
- `coroutineScope { }` vs `GlobalScope.launch { }` 차이
- `cancelChildren()` 으로 형제 코루틴 취소
- `Channel`을 이용한 실시간 위치 업데이트
- `isActive` 체크로 cooperative cancellation

---

## Kotlin 코딩 원칙 (매 세션 준수)

- `val` 우선, `var` 최소화
- `!!` 절대 금지 — `?.`, `?:`, `checkNotNull` 활용
- `if/when/try-catch` 표현식으로 사용
- `let`, `also`, `apply`, `run` 적절히 활용
- `require`/`check`/`error`로 검증
- sealed class에서 `when` 사용 시 `else` 불필요 (컴파일러가 보장)
