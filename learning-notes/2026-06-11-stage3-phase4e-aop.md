# Stage 3 · Phase 4-E — AOP (+Filter/Interceptor 비교) 🎭

> 날짜: 2026-06-11
> 의미: 횡단 관심사 자동 주입. `@Transactional`의 비밀 풀림.
> 페르소나: TypeScript 개발자 (NestJS Interceptor 사고 활용)

---

## ① WHY
컨트롤러 100개에 로깅/시간측정 코드 박으면 100번 반복 + 한 줄 바꾸면 100번 수정. **DRY 폭사.** AOP = *선언적으로* 횡단 관심사(로깅·트랜잭션·캐싱·인증)를 한 곳에 박고 자동 적용.

## ② WHAT — AOP 핵심
| 용어 | 의미 | NestJS 매핑 |
|---|---|---|
| **Aspect** | 횡단 관심사 모듈화한 클래스 | Interceptor/Guard 묶음 |
| **Pointcut** | "어디에 적용?" 표현식 | `@UseInterceptors(...)` 대상 |
| **Advice** | "언제·무엇" Before/After/Around | `intercept()` 메서드 |
| **JoinPoint** | 호출 메서드 정보 | `ExecutionContext` |

### Advice 종류
- `@Before`: 메서드 실행 *전*
- `@After`: try-finally의 finally처럼 *항상* 실행
- `@AfterReturning`: 정상 반환된 경우만
- `@AfterThrowing`: 예외 발생한 경우만
- **`@Around`**: 가장 강력. proceed() 직접 호출, 인자/반환값 가공·예외 가로채기

### Pointcut 표현식
```
execution(* com.example.baseball..*Controller.*(..))
         │ │                       │           │
         │ │                       │           └ 모든 파라미터
         │ │                       └ 모든 메서드
         │ └ 패키지(.. = 하위포함) + *Controller 클래스
         └ 반환 타입 무관
```
⚠️ **`execution`은 public만** 잡음 (private/protected 안 됨).

### 프록시 메커니즘 (마법의 정체)
Spring이 부팅 시 `@Aspect` 발견 → **대상 빈을 프록시로 감싸 등록** → 호출 시 프록시가 받아 Advice 실행 후 진짜 메서드 호출. Phase 2-D Reflection + 동적 프록시의 응용.

**`@Transactional`도 같은 메커니즘** — Spring이 BEGIN/COMMIT을 Around Advice로 자동 주입.

## ③ HOW — LoggingAspect
```kotlin
@Aspect
@Component
class LoggingAspect {
    @Around("execution(* com.example.baseball..*Controller.*(..))")
    fun logExecution(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature.toShortString()
        val start = System.currentTimeMillis()
        println("➡️ [START] $signature args=${joinPoint.args.toList()}")
        return try {
            val result = joinPoint.proceed()                  // ⭐ 진짜 메서드 호출
            println("✅ [END]   $signature  took ${System.currentTimeMillis()-start}ms")
            result
        } catch (e: Exception) {
            println("❌ [FAIL]  $signature  ex=${e.javaClass.simpleName}")
            throw e
        }
    }
}
```

### JoinPoint API 정리
- `signature.toShortString()` = "Class.method(..)" 짧은 로그용
- `signature.toString()` / `toLongString()` = 더 자세 (디버깅용)
- `args` = 메서드 인자들
- `target` = **진짜 객체** (프록시 안의 진짜)
- `this` = **프록시 객체** ⭐
- `proceed()` = 실제 메서드 호출 (Around에서만)
- `MethodSignature`로 캐스팅 시 parameterNames/returnType/method(어노테이션 읽기) 등

## ④ 실측
```
➡️ [START] HelloController.hello()    args=[]
✅ [END]   HelloController.hello()    took 0ms

➡️ [START] UserController.create(..)  args=[CreateUserRequest(...)]
   ... org.hibernate.SQL : insert into users ...
✅ [END]   UserController.create(..)  took 37ms

➡️ [START] UserController.getOne(..)  args=[1]
   ... org.hibernate.SQL : select ...
✅ [END]   UserController.getOne(..)  took 17ms
```
- **컨트롤러 코드 안 건드림** + 모든 호출에 자동 로그·시간
- SQL이 START/END *사이에* — Service+Repository+DB 왕복까지 포함된 전체 시간
- 첫 호출 워밍업 = JIT(Phase 2-C) 영향
- → **APM(DataDog/NewRelic)이 하는 일의 근본 원리.** 우리도 이제 그 원리를 손으로 짠 거.

## ⑤ Filter vs Interceptor vs AOP
```
요청 → [Filter] → DispatcherServlet → [Interceptor] → @RestController → [AOP] → @Service
```
| | Filter | Interceptor | AOP |
|---|---|---|---|
| 레이어 | 서블릿 | Spring MVC | Spring 빈 메서드 |
| 알 수 있는 것 | HttpRequest 원시 | 핸들러 정보 | 메서드 시그니처·인자·반환 |
| 잡을 범위 | 모든 요청 | 컨트롤러 호출 | **모든 빈 메서드** |
| TS 매핑 | Express middleware | Nest Interceptor | Nest Interceptor + AOP |

**고르는 법:**
- HTTP 원시·인증·CORS·인코딩 → **Filter**
- 컨트롤러 호출 전후·URL 기반 권한 → **Interceptor**
- 메서드 단위 횡단 관심사(서비스 로직 포함) → **AOP**

---

## ⑥ 시니어 함정 — Self-Invocation (Phase 4-E 핵심)
**문제:** 같은 클래스 안에서 `this.method()` 호출 시 그 메서드의 `@Transactional`/AOP 안 먹힘.

**왜:**
- Spring AOP = 프록시 기반.
- 외부 호출 → 프록시 → 진짜 객체.
- 메서드 안에서 `this.save()` = **진짜 객체의 save()** = 프록시 우회 = Advice 안 적용.

```kotlin
@Service class OrderService(private val repo: OrderRepository) {
    fun placeOrder(req) {
        save(req)         // ← this.save() — 프록시 우회 → @Transactional 무시 ❌
    }
    @Transactional fun save(req) { repo.save(...) }
}
```

**해결:**
1. **클래스 분리** (가장 깔끔, 실무 표준) — 다른 빈을 통해 호출하면 프록시 거침
2. 자기 자신 주입 (`self: OrderService`) → `self.save()` (프록시 호출)
3. AspectJ 직접 (Spring AOP 대신, 복잡)

**joinPoint API와 연결:**
- `joinPoint.this` = 프록시
- `joinPoint.target` = 진짜 객체
- 메서드 안의 `this` 키워드 = `target` = 프록시 우회의 원인

> "Spring AOP는 프록시 기반 — 외부 호출은 프록시 거치지만, *같은 클래스 self-call*은 프록시 우회 → AOP 안 먹힘."

## ⑦ 핵심 한 줄 요약
- **AOP = 어노테이션 + 프록시 + 동적 호출.** `@Transactional`이 그 일상 사례.
- Pointcut으로 *어디*, Advice로 *언제·무엇*.
- 횡단 관심사는 메서드 단위면 AOP / URL 기반이면 Interceptor / HTTP 원시면 Filter.
- ⚠️ **Self-invocation 함정** — Spring AOP의 가장 유명한 핀치 포인트.

## ⑧ 다음 — Phase 4-F Security & JWT
- Spring Security Filter Chain
- JWT 토큰 인증을 Filter로
- `OncePerRequestFilter` 상속해서 직접 만들기
- 인증·인가 차이 (`@PreAuthorize` 등)

---

## 📝 Phase 종료 미니 퀴즈 (적극적 회상)

### Q1 회상 — Pointcut 표현식
**답:** `*`=반환무관, `com.example.baseball..` 패키지(하위 포함), `*Controller` Controller로 끝나는 클래스, `*(..)` 모든 메서드 모든 인자.
**리뷰:** ✅ 완벽. 추가: `execution`은 기본 public만 잡음.

### Q2 회상 — Advice 차이
**답:** Before=실행 전, After=실행 후, Around=둘 다.
**리뷰:** ✅ 정확. 보완: After 3종(@After always / @AfterReturning 정상만 / @AfterThrowing 예외만). Around가 가장 강력(proceed·인자/반환 가공·예외 가로채기).

### Q3 응용 — 서비스 캐싱 AOP
**답:** `@Around("execution(* com.example.baseball..*Service.*(..))")` + `@Cache(minute=5)` 커스텀 어노테이션.
**리뷰:** ✅ 사고 방향 정확. Spring 내장 `@Cacheable + @EnableCaching`이 거의 같은 일 자동화. `execution`=public 기본.

### Q4 ⚠️ Self-Invocation — 새로 인지!
**답:** 떠올리지 못함. "@Transactional이 뭐였는지", "repo가 어디서 왔는지" 혼란.
**리뷰:** 가장 어려운 함정. 정리:
- `@Transactional`(Phase 4-C 복습) = 메서드 = 한 DB 트랜잭션 (BEGIN/COMMIT/ROLLBACK).
- AOP는 프록시 기반 → 외부 호출 시 프록시가 Advice 적용 → 진짜 객체 호출.
- 메서드 안 `this.save()` = 진짜 객체의 save() = **프록시 우회** → @Transactional 안 먹힘.
- 해결: **클래스 분리**(권장) / self 주입 / AspectJ.
- joinPoint: `this`=프록시, `target`=진짜. 메서드 안 `this` 키워드 = target.

### Q5 🔭 스트레치 — JWT 어디서?
**답:** Filter — 서블릿 레벨에서 토큰 없으면 컷.
**리뷰:** ✅ **시니어 답변.** 정확. Spring Security가 정확히 이 구조 (~15개 Filter Chain). JWT는 보통 `OncePerRequestFilter` 상속.

### 종합 평가
| 문항 | 결과 |
|---|---|
| Q1 | ✅ 완벽 |
| Q2 | ✅ 정확 + 보완 |
| Q3 | ✅ 사고 방향 + Spring 내장 인지 |
| Q4 | ⚠️ **새 인지** — Self-invocation 함정 |
| Q5 | ✅ **시니어 답변** |

**오늘 박힌 한 줄:** *"Spring AOP는 프록시 기반 — 같은 클래스 self-call은 프록시 우회 → AOP 안 먹힘. @Transactional이 사실 AOP의 우리 일상 사례."*
