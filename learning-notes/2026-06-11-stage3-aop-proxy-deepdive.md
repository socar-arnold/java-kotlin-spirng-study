# 🎭 Spring AOP & Proxy — Deep Dive (면접 자산)

> 날짜: 2026-06-11
> 의미: Phase 4-E 본론 이후, self-invocation/프록시 본질을 끝까지 캔 정리.
>        면접 직전 *이 한 장*만 다시 보면 됨.
> 페르소나: TypeScript/NestJS 개발자 → 같은 패턴이라 직관 활용

---

## 0️⃣ 한 줄 요약 (시니어 답변 템플릿)

> **Spring AOP의 프록시는 *성능*이 아니라 *선언적 프로그래밍의 편의*를 위한 메커니즘.** 부팅 시 런타임으로 프록시 객체를 생성해 진짜 객체를 감싸고, 어노테이션(`@Transactional` 등) 한 줄로 BEGIN/COMMIT 같은 횡단 관심사를 자동 주입.
> 부산물: 미세 성능 비용 + **self-invocation 함정**(같은 클래스 안 `this.method()` 호출은 프록시 우회 → advice 미적용).
> 90% 사용 케이스에선 편의가 이김. 함정은 *클래스 분리*로 회피 가능.

---

## 1️⃣ 프록시 = 무엇

**정의:** 원본 객체와 *같은 모습으로 위장한* 중간객체. 호출자는 원본인 줄 알고 부르지만, 사실 프록시가 받아 *추가 동작*을 한 뒤 원본을 호출.

비유 — **사장님 + 비서**:
- 사장님 = 원본 객체 (우리가 짠 `class OrderService`로 만든 인스턴스)
- 비서 = 프록시 (Spring이 부팅 시 메모리에 생성한 가짜)
- 손님(외부 호출) → 비서를 거쳐 사장님께 — 비서가 일정 체크(@Transactional advice) 수행
- 사장님이 *사무실 안에서* 자기 일 처리(self-invocation) → 비서 안 거침 → advice 미적용

---

## 2️⃣ 메모리에 객체가 *두 개* 만들어진다

우리 코드는 한 클래스지만, Spring 부팅 후 메모리엔:

```
[힙 메모리]
  ┌──────────────────────────┐
  │ OrderService 인스턴스 A    │   ← 우리 코드 그대로의 객체
  │ (주소 0x100)              │      "진짜 / target"
  └──────────────────────────┘

  ┌──────────────────────────┐
  │ OrderService$Proxy 인스턴스 B │ ← Spring이 런타임에 *새 클래스로* 만든 객체
  │ (주소 0x200)                 │     "프록시"
  │ 필드: target → 0x100         │ ← B는 A의 주소를 들고 있음(composition)
  └──────────────────────────┘
```

**핵심 사실:**
- 둘 다 *실제* 메모리에 존재. "진짜/가짜" 표현은 *우리 클래스 정의에서 나온 것* vs *Spring이 생성한 것* 의 구분일 뿐.
- 컨테이너 빈으로 등록된 건 **B(프록시)**. A는 B 안의 필드로만 접근 가능.
- 컨트롤러가 `@Autowired UserService` → 받는 건 B.

---

## 3️⃣ `this`의 정체 (self-invocation의 핵심)

### 규칙
> **`this` = 지금 이 메서드가 *호출된 객체*.** 어느 클래스에 정의됐는지가 아니라 *누구한테 호출됐는지*.

### 시나리오 ① 외부에서 `service.save()` 호출 (✅ advice 적용됨)
```
컨트롤러: service.save()                    ← service = B (프록시)
              │
              ▼
       B.save() 실행
       ┌──────────────────────┐
       │ BEGIN                │  ⭐ advice (트랜잭션 시작)
       │ target.save()        │  ← A에 위임
       │ COMMIT               │  ⭐ advice
       └──────────────────────┘
```

### 시나리오 ② 외부에서 `service.placeOrder()` 호출 (self-invocation ❌)
```
컨트롤러: service.placeOrder()              ← service = B (프록시)
              │
              ▼
       B.placeOrder() 실행 (advice 없으니 그냥 위임)
       │
       │  target.placeOrder() 호출
       ▼
       A.placeOrder() 실행 — 이제 JVM은 "A 안"에 있음
       ┌─────────────────────────────────┐
       │ 이 시점에 this = A (0x100)       │
       │                                 │
       │ validate()  = this.validate()    │
       │ save()      = this.save()        │  ⚠️
       │             = A.save() 직접      │
       │             (B.save 우회!)       │
       └─────────────────────────────────┘
              │
              ▼
       A.save() 실행 (BEGIN/COMMIT 없이)  ❌
```

### 두 시나리오 비교
| | 시나리오 ① 외부 호출 | 시나리오 ② self-invocation |
|---|---|---|
| save 호출 객체 | B (프록시) | A (target) |
| BEGIN/COMMIT | ✅ | ❌ |
| @Transactional 효과 | 적용됨 | 무시됨 |

---

## 4️⃣ 어노테이션 ≠ 동작

`@Transactional` 어노테이션은 **메서드에 박힌 표지판**일 뿐. 트랜잭션을 *실제로 시작하는 행위*는 프록시(B)가 함.
- 표지판만으론 동작 0. 표지판을 *읽어 집행하는 누군가*가 필요.
- 그 누군가 = 프록시.
- 프록시를 안 거치면 표지판은 그냥 글자 → @Transactional 무시.

**TS/NestJS 비유:** 데코레이터 `@Injectable()`, `@UseGuards()`는 메타데이터일 뿐. 실제 동작은 NestJS 런타임이 *그 메타데이터를 읽어* 처리. 같은 사고.

---

## 5️⃣ "왜 프록시?" — 결정적 오해 교정

흔한 오해: **"성능 이득을 위해 프록시 만든다"** ❌

진실: **프록시는 성능에 살짝 손해**(메서드 호출 한 단계 더). 그럼 왜?

> **답: 선언적 프로그래밍(declarative)의 편의.**

대안 비교:

| | A. 수동 | B. AspectJ | **C. Spring AOP (프록시)** |
|---|---|---|---|
| 방식 | 모든 메서드에 `BEGIN/COMMIT` 직접 작성 | 컴파일 타임 바이트코드 조작으로 advice 박음 | 런타임에 프록시 객체로 감쌈 |
| DRY | ❌ 100번 복붙 | ✅ | ✅ |
| 셋업 | 단순 | 복잡(설정·빌드 느림·디버깅 어려움) | **단순** |
| self-invocation | (해당 없음) | **문제 없음** | ⚠️ 문제 발생 |
| 성능 | 빠름 | 빠름 | 미세 손해 (~ns) |
| Spring 기본 | — | — | ✅ |

**Spring이 C를 고른 이유:**
- 90% 케이스에서 self-invocation 안 만남 (예외적 함정)
- 셋업·디버깅 압도적으로 쉬움
- 미세 성능 비용 < 개발 생산성 이득
- 함정은 *문서·교육*으로 안내

---

## 6️⃣ 프록시 만드는 두 기술 — CGLIB vs JDK Dynamic Proxy

| | JDK Dynamic Proxy | **CGLIB** |
|---|---|---|
| 방식 | 인터페이스 흉내내는 새 클래스 생성 | 클래스 자체를 *상속*한 자식 클래스 생성 |
| 조건 | 클래스가 *인터페이스 구현* 필요 | 클래스가 `final`만 아니면 됨 |
| 출시 | 자바 표준 (오래됨) | 별도 라이브러리 (Spring에 포함) |
| Spring Boot 2+ | — | **기본값** ✅ |
| Kotlin 함정 | — | Kotlin은 기본 `final` → CGLIB이 못 상속함 → **`kotlin("plugin.spring")`** 플러그인이 Spring 어노테이션 붙은 클래스를 자동 `open` 처리 |

> 우리 `build.gradle.kts`에 `kotlin("plugin.spring") version "..."` 한 줄이 있는 이유 = 정확히 이거. CGLIB이 우리 Kotlin 클래스를 감싸기 위함.

---

## 7️⃣ 보편적 패턴 — Spring만의 함정이 아니다

self-invocation 함정은 **프록시/데코레이터 기반 시스템의 공통 함정**:
- **NestJS** `@Injectable() + Guard/Interceptor` — 같은 문제. `this.method()` self-call은 데코레이터 우회.
- **Angular DI + Interceptor** — 같음.
- **Python `@decorator`** — 같음.

→ **한 번 박으면 언어 무관 자산.** 이거 알면 NestJS 회사 가도, Spring 회사 가도, Django 회사 가도 같은 함정 *바로* 알아챔.

---

## 8️⃣ 실무 해결 — Self-Invocation 회피 3종

### ① 클래스 분리 (실무 표준) ⭐
```kotlin
@Service class OrderFacade(private val saver: OrderSaver) {
    fun placeOrder(req: OrderRequest) {
        validate(req)
        saver.save(req)             // 다른 빈 호출 → 그 빈의 프록시 거침 ✅
    }
}
@Service class OrderSaver(private val repo: OrderRepository) {
    @Transactional fun save(req: OrderRequest) { repo.save(...) }
}
```

### ② 자기 자신을 빈으로 주입 (트릭)
```kotlin
@Service class OrderService(
    @Lazy private val self: OrderService     // 프록시를 self로 주입
) {
    fun placeOrder(req: OrderRequest) {
        validate(req)
        self.save(req)               // self = 프록시 → 거침 ✅
    }
    @Transactional fun save(req: OrderRequest) { repo.save(...) }
}
```

### ③ AspectJ 사용 (복잡, 거의 안 씀)
컴파일 타임 바이트코드 조작 → advice가 .class에 직접 박힘 → self-invocation도 작동. 대신 셋업 복잡.

→ **90%는 ①, 가끔 ②, ③은 정말 필요한 경우에만.**

---

## 9️⃣ 면접 Q&A 시뮬 (자가 점검)

### Q1. Spring AOP는 어떤 메커니즘으로 동작하나요?
A. 부팅 시 Spring이 대상 빈을 *프록시 객체로 감싸* 컨테이너에 등록. 외부 호출 시 프록시가 advice(예: 트랜잭션 BEGIN/COMMIT)를 적용 후 진짜 객체로 위임. CGLIB(클래스 상속)/JDK Dynamic Proxy(인터페이스 기반) 둘 중 하나로 프록시 생성. Spring Boot 2부터 CGLIB 기본.

### Q2. `@Transactional`을 붙였는데도 트랜잭션이 안 먹는 경우가 있다고 들었어요. 왜죠?
A. 가장 흔한 원인은 **self-invocation**. Spring AOP는 프록시 기반인데, 같은 클래스 안에서 `this.method()`로 호출 시 *진짜 객체에서 진짜 객체로 직접* 점프해 프록시를 우회 → advice 미적용. 또 다른 원인: 메서드가 `private`이거나 `final`인 경우(프록시가 가로채지 못함).

### Q3. self-invocation 어떻게 해결하나요?
A. ① 메서드를 *다른 빈으로 분리*(실무 표준). 다른 빈 호출 = 그 빈의 프록시 거침. ② `@Lazy`로 자기 자신을 주입받아 `self.method()`. ③ AspectJ로 갈아타기(컴파일 타임 weaving, self-invocation 자체 해결, 셋업 복잡).

### Q4. 그럼 왜 Spring은 처음부터 AspectJ를 안 썼나요?
A. AspectJ는 셋업·빌드·디버깅 복잡. 90% 케이스에서 self-invocation 안 만나니 프록시의 편의가 이김. 함정은 문서·교육으로 안내한다는 트레이드오프 결정.

### Q5. Kotlin Spring에서 `plugin.spring` 플러그인은 왜 필요?
A. Kotlin 클래스는 기본 `final` → CGLIB 상속 불가 → 프록시 못 만듦. 이 플러그인이 Spring 어노테이션(@Service, @Component 등) 붙은 클래스를 *자동으로 `open` 처리*해 CGLIB이 상속 가능하게.

---

## 🔑 한 줄 정리 — 다시

> **Spring AOP = 어노테이션 + 런타임 프록시 + 동적 호출.** 프록시는 *편의*를 위한 것(성능 X). 메모리에 진짜(A) + 프록시(B) 두 객체 공존. 외부 호출은 B 거치고, *같은 클래스 안 self-call은 A에서 A로 직행* → B 우회 → advice 무시 = self-invocation 함정.
> 회피: *다른 빈으로 분리*가 표준. NestJS/Angular/Django 등 다른 데코레이터 시스템도 동일 패턴.

---

## 참고 자료 (외부 학습)
- [Tecoble — "AOP에 대한 사실과 오해"](https://tecoble.techcourse.co.kr/post/2022-11-07-transaction-aop-fact-and-misconception/) (한국어, 입문)
- [gmoon92 — "Self Invocation은 왜 발생할까?"](https://gmoon92.github.io/spring/aop/2019/04/01/spring-aop-mechanism-with-self-invocation.html) (한국어, 메커니즘 깊이)
- [Spring 공식 — Using @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html)
- 본 노트의 형제: `2026-06-11-stage3-phase4e-aop.md` (Phase 4-E 본 노트)
