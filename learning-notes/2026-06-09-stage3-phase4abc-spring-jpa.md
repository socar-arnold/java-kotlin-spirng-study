# Stage 3 · Phase 4-A/B/C 학습 노트 — REST + Spring Boot + JPA

> 날짜: 2026-06-09
> 의미: 진짜 백엔드 진입. Spring 마법 까보기 + JPA CRUD 동작까지.
> 페르소나: TypeScript 개발자 (NestJS/TypeORM 사고 활용)

---

## Phase 4-A — REST/HTTP

### 핵심 정리
| 메서드 | 멱등성 | 보통 응답 |
|---|---|---|
| GET | ✅ | 200 (없으면 404) |
| POST | ❌ | 201 Created + Location 헤더 |
| PUT | ✅ (전체 교체) | 200/204 |
| **PATCH** | ❌ (보통, 부분 수정) | 200/204 |
| DELETE | ✅ | 204 No Content |

**핵심 교정:** "제목만 수정"은 **PATCH**(부분), PUT은 *전체 교체*. 외우는 트릭: **P**ut=**P**ull all / **P**atch=**P**iece.

### 상태 코드 단골
400(클라 잘못) / 401(미인증) / 403(권한없음) / 404(없음) / **409 Conflict**(중복·충돌) / 500(서버 버그)

### CS-1 곁들임 — TCP
- HTTP는 TCP 위에서. TCP=신뢰성(연결·순서·재전송) / UDP=비신뢰·빠름.
- POST가 멱등 아닌데도 실무 OK인 건 TCP 신뢰성 덕. 단 네트워크 장애·재시도엔 **Idempotency-Key 헤더** 같은 상위 패턴 필요.

---

## Phase 4-B — Spring Boot 첫 발자국

### 의존성·플러그인 추가 (build.gradle.kts)
```kotlin
plugins {
    kotlin("plugin.spring") version "2.3.20"
    kotlin("plugin.jpa") version "2.3.20"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
}
springBoot { mainClass.set("com.example.baseball.HelloApplicationKt") }
```

### 트러블슈팅 ① — Default Package 금지
- 처음 `HelloApplication.kt`를 `src/main/kotlin/` 바로 밑에 두니 → `@ComponentScan`이 *클래스패스 전체*(JAR까지) 훑어 R2DBC 등 엉뚱한 에러 폭발.
- **해결:** 모든 코드를 `com.example.baseball` 패키지로 이동 → Spring이 거기만 깔끔히 스캔.

### Service 분리 + 생성자 주입
```kotlin
@Service class GreetingService { fun greet(name: String) = "Hello, $name!" }
@RestController class HelloController(private val gs: GreetingService) {
    @GetMapping("/hello/{name}") fun hello(@PathVariable name: String) = Greeting(gs.greet(name))
}
```

### 싱글톤 빈 + 동시성 (Phase 3-B 재등장)
```kotlin
@Service class VisitCounter {
    private val count = AtomicInteger(0)        // ⭐ 싱글톤 빈은 멀티스레드 공유 → atomic 필수
    fun increment() = count.incrementAndGet()
}
```

### 깊이 — Spring DI는 어떻게?
1. `@SpringBootApplication`이 패키지 스캔
2. `@Service`/`@RestController`/... 발견 → 컨테이너 등록 대상
3. **Reflection으로 생성자 시그니처 검사** → 의존성 그래프 빌드
4. 위상 정렬해서 잎부터 *Spring이 직접 생성자 호출* → 컨테이너 Map에 저장
5. 다른 빈에서 필요하면 컨테이너에서 꺼내 주입

→ TS 본능 ("누군가 ctor 호출해야 한다") 정답. 그 누군가 = Spring.

### 깊이 — CAS (Compare-And-Swap)
`AtomicInteger.incrementAndGet()` 안:
1. `current = 메모리[X]` 읽기
2. `next = current + 1`
3. `CAS(X, expected=current, new=next)` — *한 CPU 명령*. 성공 시 next 반환, 실패 시 1번부터 재시도.

락 vs CAS: 락은 비관(OS 락, 무거움), CAS는 낙관(CPU 명령, 가벼움). 단일 변수 단일 연산엔 CAS 압승. Phase 4-G DB 낙관적 락(version 컬럼)도 같은 사고.

---

## Phase 4-C — Spring Data JPA (절반)

### Entity / Repository / Controller
```kotlin
@Entity
@Table(name = "users")              // ⚠️ `user`는 H2 예약어, 반드시 명시
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0,
    val name: String,
    val email: String,
)

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?   // 이름만으로 SQL 자동 생성
}

@RestController @RequestMapping("/users")
class UserController(private val repo: UserRepository) {
    @PostMapping
    fun create(@RequestBody req: CreateUserRequest): ResponseEntity<User> {
        val saved = repo.save(User(name = req.name, email = req.email))
        return ResponseEntity.created(URI.create("/users/${saved.id}")).body(saved)
    }
    // GET, /by-email 등
}
```

### application.yml
```yaml
spring:
  datasource: { url: jdbc:h2:mem:testdb, ... }
  jpa:
    hibernate: { ddl-auto: create-drop }
    show-sql: true
    properties: { hibernate: { format_sql: true } }
logging:
  level:
    org.hibernate.SQL: DEBUG
    org.hibernate.orm.jdbc.bind: TRACE   # ⭐ ? 자리에 들어간 실제 값까지
```

### 깊이 — JpaRepository의 두 마법
1. **상속받은 메서드** — `save`/`findById`/`findAll`/`deleteById` 등 표준 CRUD 공짜 (부모 인터페이스 정의 + 구현 제공).
2. **메서드 이름 파싱** — `findByEmail`/`findByNameAndAge`/`findByEmailContaining`/`countBy...`/`deleteBy...`/`findTop10ByOrderBy...` 등 *이름으로* SQL 자동 생성.
   - Spring이 부팅 시 동적 프록시 클래스를 생성 (또 Reflection)
   - 메서드 이름을 토큰으로 분해 → JPQL/SQL 생성 → 캐싱
   - 복잡한 쿼리는 `@Query("...")` 어노테이션

### 트러블슈팅 — H2 예약어 `USER`
```
Caused by: ... Syntax error in SQL statement "create table [*]user (..."
                                                              ↑ 여기 식별자 와야 하는데?
```
→ `@Table(name = "users")` 로 해결. **시니어 디버깅 한 줄: 스택트레이스는 항상 `Caused by:` 부터.**

### 실측 — SQL 로그가 풀어주는 마법
```
DEBUG ... org.hibernate.SQL : insert into users (email,name,id) values (?,?,default)
TRACE ... org.hibernate.orm.jdbc.bind : binding parameter (1:VARCHAR) <- [arnold@example.com]
TRACE ... org.hibernate.orm.jdbc.bind : binding parameter (2:VARCHAR) <- [Arnold]
```
- 우리가 한 줄도 안 쓴 SQL이 자동 생성
- `?` 자리에 실제 값이 *분리*되어 바인딩 → SQL Injection 방어 본질
- `findByEmail` 이 진짜로 `WHERE email = ?` 으로 변환된 거 *눈으로* 확인

---

## 핵심 한 줄 요약 (Stage 3 진입)
- **Spring = "어노테이션 + 리플렉션 + 컨테이너"의 정교한 응용** (마법 아님)
- **빈은 기본 싱글톤** → 멀티스레드 공유 → 가변 상태엔 atomic/concurrent 필수 (Phase 3 재등장)
- **JPA = 어노테이션과 메서드 이름만으로 SQL 자동 생성** (TypeORM/Prisma의 자리)
- **`Caused by:` 가 진짜 원인** — 스택트레이스 읽는 시니어 습관

## 다음 (Phase 4-C 후반)
- **영속성 컨텍스트 + 변경 감지(Dirty Checking)** — `user.name = "new"` 만 해도 자동 UPDATE
- **`@Transactional`** — Service 계층의 표준
- **Service 추출** — Controller → Service → Repository 3계층
- 그 후 Phase 4-D 예외처리 & Validation → 4-E AOP → 4-F Security → 4-G JPA 심화 → 4-H 테스트 → 4-I 설정/Flyway → 미션
