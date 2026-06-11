# 학습 진척

> 매 세션 종료 시 갱신. 다음 대화에서 여기부터 이어간다.
> 스케줄은 [LEARNING_TRACK.md](LEARNING_TRACK.md) 참조.
> 처음 클론한 사람은 아래 "학습자 프로필"을 `<미설정>`으로 비우면 온보딩이 다시 돈다 ([CLAUDE.md](CLAUDE.md) 참조).

## 학습자 프로필
- **익숙한 언어/스택:** TypeScript (→ TS 매핑 비유 적극 활용)
- **주당 학습 시간:** 가볍게 (주 7~8시간)

## 현재 위치
- **Stage:** 3 (백엔드) — **Phase 4-E 졸업** (A/B/C/D/E 완료) 🎭
- **진행:** 누적 **29세션** (Stage 0:2 / Stage 1:12 / Stage 2:9 / Stage 3:6). 전체 ~78의 ~37%.
- **Stage 3 잔여:** ~12세션 (4-F~I + 미션 블랙잭/로또)
- **다음:** Phase 4-F Spring Security + JWT (Filter Chain으로 토큰 인증)
- **메모:** LoggingAspect 실측 — 컨트롤러 코드 안 건드리고 모든 호출에 자동 로그·시간. APM의 근본 원리 손으로 짠 셈. 미니 퀴즈 ②: **Self-invocation 함정 새로 인지** (Spring AOP 프록시 우회 = @Transactional 안 먹힘). Filter/Interceptor/AOP 비교 정리.
- **실측 페이스:** 5/22 4세션 + 5/25 2세션 + 5/26 2세션 → 개념은 계획比 ~3배, 미션이 시간 변수

## 완료 로그
| 날짜 | Stage·주차 | 주제 | 미션/결과 |
|---|---|---|---|
| 2026-05-22 | Stage 0 / 1주차 | JVM·생태계 + Java 문법 매핑 | ✅ 완료. 컴파일(javac/kotlinc=JDK) vs 실행(JVM⊂JRE) 분리 이해, WORA·바이트코드·바이너리 감각 확인. TS 파이프라인(tsc→node) 매핑 성공. 초기 오해(컴파일을 JVM이 한다)는 교정 완료 |
| 2026-05-22 | Stage 0 / 2주차 | Java OOP·관용구 + "Kotlin은 왜 바꿨나" | ✅ 완료(=Stage 0 졸업). 명목적 vs 구조적 타이핑, Java 관용구 3종(getter/setter·체크예외·제네릭소거)과 Kotlin 개선 이해. 교정: `throws`는 인자 아님(체크예외 처리 강제), Kotlin `override`는 필수 키워드. 20줄 Java→6줄 Kotlin 재작성 확인 |
| 2026-05-22 | Stage 1 / Phase 1-A | Introduction to Kotlin (첫 TDD) | ✅ 완료. `greet(name: String?)` 함수를 RED→GREEN→REFACTOR로 완주. 배운 것: val/var, 널 안전성(String vs String?), `?:`/`?.`/문자열템플릿, **최상위 함수**(클래스 불필요), **단일 식 함수**(`= 식`), 섀도잉. 교훈: Java 습관으로 class로 감쌌다가 unresolved → 최상위 함수로 교정. src/main/kotlin/Intro.kt, src/test/kotlin/IntroTest.kt |

| 2026-05-22 | Stage 1 / Phase 1-A ② | when 식·범위·data class | ✅ 완료. `grade(score)`(조건형 when·경계값 테스트), `data class Point`(값 동등성 `==`·`copy`). 실험으로 컴파일타임(copy 없음=Unresolved) vs 런타임(equals=주소비교 AssertionError) 체득. 교훈: 커밋 전 `./gradlew test`로 실제 초록 확인(저장 누락으로 BUILD FAILED 경험) |

| 2026-05-25 | Stage 1 / Phase 1-B | OOP & sealed class | ✅ 완료. `Shape`(sealed)+Circle/Rectangle/Triangle, `area()` 망라적 when, 스마트 캐스트. 실험: Triangle 추가 시 exhaustiveness 컴파일 에러로 누락 자동 검출. TDD 규율(가지 추가→테스트 추가), `kotlin.math.PI` 리팩터. src/main/kotlin/Shape.kt, src/test/kotlin/ShapeTest.kt |
| 2026-05-25 | Phase CS / CS-3 | 시간복잡도(Big-O) 맛보기 | ✅ 완료. O(1)~O(n²) 감각, 중첩루프=O(n²), List(O(n)) vs HashSet(O(1)) 조회, Redis=인메모리 해시 O(1) 연결. 면접 자가점검 2문항 정답 |
| 2026-05-26 | Stage 1 / Phase 1-B ② | 인터페이스·다형성 | ✅ 완료(=Phase 1-B 종료). `interface Shape2`+Circle2/Rectangle2/Triangle2, `totalArea`(다형성, when 없음). 핵심: 표(타입×동작)로 when vs 다형성 이해 — 묶은 방향으로 늘리면 쉬움(표현 문제). 실험: Triangle2 추가해도 totalArea 무수정(↔어제 sealed는 when 수정 필요). 노트에 표/동물원 비유 보존. src/main/kotlin/Polymorphism.kt, src/test/kotlin/PolymorphismTest.kt |
| 2026-05-26 | Stage 1 / Phase 1-C | Generics & 변성 | ✅ 완료. `fun <T> first`, `class Box<T>`(타입 추론), 타입 소거(=TS와 동일), 변성 `ReadBox<out T>`(공변). 실험: out 제거 시 type mismatch 컴파일 에러로 공변 ON/OFF 확인(PECS). 디버깅 교훈: 빈 Generics.kt가 원인이었음 — 에러가 가리키는 심볼을 먼저 보라. src/main/kotlin/{Generics,Variance}.kt |
| 2026-05-26 | Stage 1 / Phase 1-D | Collections + 야구 판정 | ✅ 완료. 불변 우선(listOf), 함수형 `count/filter/in`, `x in list`==contains. **숫자야구 두뇌 완성**: countStrikes·countBalls·judge(data class Result). 리뷰: 중복 `||` 제거, `it ->` 생략. src/main/kotlin/Baseball.kt, src/test/kotlin/BaseballTest.kt |
| 2026-05-26 | Stage 1 / Phase 1-E | 함수형(FP) | ✅ 완료. 고차함수·체이닝(filter→map→count), `fold(초기값){acc,x->}`=reduce 안전판, `sumOf`. `sumOfSquares`(fold), `hasDuplicate`(toSet 크기 비교, 야구 정답 검증용). src/main/kotlin/Fp.kt, src/test/kotlin/FpTest.kt. (Fp.kt를 test→main으로 위치 교정) |
| 2026-05-27 | Stage 1 / Phase 1-F | 예외 & 입력 검증 | ✅ 완료(=Phase 1 개념 A~F 종료). `require`/`check`/`error`(fail-fast), try-catch는 식, 체크예외 없음. `validateGuess`(3자리·1~9·중복 — all/hasDuplicate 재사용). 책임 분리(검증 입구 vs 판정). src/main/kotlin/Baseball.kt |
| 2026-05-27 | Stage 1 / 미션① | 숫자야구 ⚾ | ✅ **완성·플레이 성공**. generateAnswer(`(1..9).shuffled().take(3)`), Game(private val answer 은닉)+play, isWin, main(I/O 루프). 배움: 랜덤은 "규칙 충족"으로 테스트(repeat 100), I/O와 로직 분리, private≠val. src/main/kotlin/Baseball.kt, src/test/kotlin/BaseballTest.kt |
| 2026-05-27 | Stage 1 / 미션② | 자동차경주 🏎️ | ✅ **완성·플레이 성공(공동우승 정상)**. Car(copy 불변 전진), winners(maxOf→filter→map), playRound(zip), main((0..9).random()/repeat). 함정 교훈: position+1(value 아님), maxOf는 값 하나. 랜덤 주입=결정적 테스트. src/main/kotlin/CarRacing.kt, src/test/kotlin/CarRacingTest.kt |
| 2026-05-27 | Stage 1 / 미션③ | 좌표계산기 📐 | ✅ **완성·플레이 성공**(3-4-5 → 5.0). Coordinate(data class), distance(피타고라스, kotlin.math.sqrt), 구조 분해(`val (x,y) = list`). Point vs Coordinate 네이밍 충돌 회피. src/main/kotlin/Coordinate.kt |
| 2026-05-27 | **🏆 Stage 1 클로징** | 회고 | Phase 1 A~F 개념 + 미션 3개 + CS-3 + 디버깅/도구 교훈. 시니어 사고 5가지(테스트가능한설계/불변/책임분리/표현문제/컴파일타임vs런타임). 종합 노트: learning-notes/2026-05-27-stage1-COMPLETE.md |
| 2026-05-27 | Stage 2 / Phase 0+2 | Gradle & JVM 내부 | ✅ 완료. Phase 0 Gradle(tasks/dependencies/build, mavenCentral=npm registry, Wrapper). Phase 2 메모리(Stack/Heap/Metaspace)·GC(세대 가설 G1 기본, 실로그 Young 1~2ms vs Full 12.9ms STW)·JIT(워밍업, 티어드 C1/C2)·Reflection(`d::class.memberProperties`, Spring/Jackson/JPA의 마법 비결). 의존성 추가: kotlin-reflect. src/main/kotlin/Reflection.kt |
| 2026-06-02 | Stage 2 / Phase 3-A/B/C/D | 동시성 기초 | ✅ 완료. 3-A 스레드(thread{}, sleep, join, 비결정 실험), 3-B race condition 직관 실험(Unsafe 5/5 손실 12047~18775, Safe/Atomic 정확 20000), 3-C JMM 가시성(@Volatile·@Synchronized·Atomic 보장 차이), 3-D 동시성 컬렉션(ConcurrentHashMap 등). 핵심: 공유 상태 자체 줄이는 게 정답 → 코루틴 철학. src/main/kotlin/{ThreadDemo,Counter}.kt, src/test/kotlin/CounterTest.kt |
| 2026-06-03 | Stage 2 / Phase 3-E ① | 코루틴 입문 | ✅ 완료. suspend·delay·runBlocking·async/await. **실측: 순차 2013ms vs 병렬 1011ms (정확히 절반)** — 같은 스레드 위에서 동시 진행(delay가 스레드 안 막음). coroutineScope 안 async = 구조적 동시성 입문. 디버깅 교훈: JUnit+코루틴은 `(): Unit = runBlocking { }` 필수(아니면 No tests found로 조용히 무시). kotlinx-coroutines 의존성 추가. src/main/kotlin/Coroutine.kt, src/test/kotlin/CoroutineTest.kt |
| 2026-06-04 | Stage 2 / Phase 3-E ② | Dispatchers·구조적 동시성 | ✅ 완료(=Phase 3 개념 종료). Dispatchers Default/IO/Main, withContext 패턴, launch vs async. 실측 Default 519ms vs IO 110ms(~5배). 구조적 동시성 두 규칙: 부모가 자식 대기 + 자식 실패 시 형제 자동 취소. 실험: coroutineScope에서 한 자식 throw → 형제 도달 못함(✅), supervisorScope에선 격리(✅). 협력적 취소 개념. TS Promise.all과 차이(실제 취소 vs reject만). src/test/kotlin/{DispatcherTest,StructuredTest}.kt |
| 2026-06-04 | Stage 2 / 미션 ① | 코루틴 레이싱 🏎️ | ✅ **완성**. RaceCar/RaceResult/race(코루틴 응용). 3개 테스트 PASSED — 우승자, 완주 순서, **병렬 증명(직렬 1600ms→측정 812ms)**. cars.map{async{delay; car}}.awaitAll().sortedBy.map{name} 패턴. 디버깅 교훈: TS 습관 3개(객체 리터럴·람다 안 return·체이닝 후 .name 중복) 교정. src/main/kotlin/CoroutineRacing.kt, src/test/kotlin/CoroutineRacingTest.kt |
| 2026-06-05 | Stage 2 / 미션 ② | RSS리더 📰 | ✅ **완성·3 tests PASSED**. FeedClient 인터페이스(DI)+parseFeed(regex)+RssReader(supervisorScope+try/catch on await). 직렬 900ms→병렬 315ms. 한 피드 실패해도 형제 살아남는 격리 패턴 정착. 디버깅: settings.gradle.kts에 잘못된 `include(...)` 자동 삽입 → 제거. src/main/kotlin/RssReader.kt, src/test/kotlin/RssReaderTest.kt |
| 2026-06-05 | **🏆 Stage 2 클로징** | 회고 | Phase 0/2/3-A~E + 미션 2개. 시니어 사고 6가지(컴파일타임vs런타임/공유상태/변하는것밖으로/구조적동시성/Continuation=Heap/DI입문). LeetCode 트랙 LEARNING_TRACK 정식 편입 완료. 종합 노트: learning-notes/2026-06-05-stage2-COMPLETE.md |
| 2026-06-09 | Stage 2 / Phase 3-E ③ | 가상 스레드 vs 코루틴 (JDK 21 Loom) | 📝 심화 노트. 계기: 실무 레포 `test-drive-portal-api` JDK 20→21 업그레이드. 핵심: 가상 스레드 = "블로킹 코드 그대로 + 스레드만 가볍게"(suspend 전염 X). Continuation→스레드 양보를 JVM 레벨로 내린 것(↔Phase 3-E ① 연결). 신규 레포가 코루틴 대신 JDK 21 택하는 이유 = JPA/MVC 자산 안 버려도 됨. **트레이드오프: DB 커넥션 풀 병목이면 켜도 효과 없음 → 부하테스트 검증 필수. synchronized pinning, ThreadLocal 주의.** 둘은 대체재 아님(가상스레드=처리량, 코루틴=구조적 동시성/취소/Flow). 노트: learning-notes/2026-06-09-stage2-phase3-virtual-threads-vs-coroutines.md |
| 2026-06-09 | Stage 3 / Phase 4-A | REST·HTTP | ✅ 완료. 메서드·상태코드·멱등성·**PUT vs PATCH 교정**, 응답 패턴(201+Location, 409+구조화 ErrorResponse). CS-1 TCP 곁들임(HTTP 위 신뢰성). |
| 2026-06-09 | Stage 3 / Phase 4-B | Spring Boot 기초 | ✅ 완료. 의존성/플러그인 추가, HelloApplication, HelloController. **함정 ①: default package** → `com.example.baseball`로 전체 이동. Service 추출(GreetingService) + 싱글톤 빈 + AtomicInteger(VisitCounter/StatsController). 깊이: **Reflection·CAS·Spring DI 내부 흐름** 정리(누가 ctor 호출하나=Spring). |
| 2026-06-09 | Stage 3 / Phase 4-C ① | JPA CRUD 동작 | ✅ 절반(CRUD 동작). User Entity + JpaRepository + UserController, H2 인메모리, application.yml. **함정 ②: `user`는 H2 예약어 → `@Table(name="users")`**. SQL 로그(show-sql + jdbc.bind TRACE)로 INSERT/findByEmail 자동 생성 확인. 깊이: JpaRepository 두 마법(상속 CRUD + 메서드 이름 파싱 → 동적 프록시). |
| 2026-06-09 | Stage 3 / Phase 4-C ② | 영속성 컨텍스트·Dirty Checking | ✅ **완료(=Phase 4-C 졸업)**. UserService(@Transactional) 추출, User 필드 var화, UserController PATCH 엔드포인트, GlobalExceptionHandler(404). **하이라이트: PATCH 시 SQL 로그에 `update users set ...` 자동 발행 확인 — `repo.save()` 안 불렀는데도** = Dirty Checking 직접 증명. 3계층 구조 정착. OSIV 경고 인지(Phase 4-G 처리). 미세관찰: UPDATE가 전 컬럼(@DynamicUpdate는 4-G). |
| 2026-06-10 | Stage 3 / Phase 4-D | Validation & Exception | ✅ **완료**. Bean Validation(@NotBlank/@Email/@Size/@Pattern), @Valid 트리거, 전역 ExceptionHandler 확장(400 + ErrorResponse 표준 모양). 함정: Kotlin `@field:` 타깃, 우리 FieldError ↔ Spring FieldError 이름 충돌 → ValidationError로 개명. **🎯 첫 미니 퀴즈** 운영(NotEmpty 함정·여러 어노테이션 다 검증·AOP 예고 새로 인지). 학습 노트에 퀴즈 보존. |
| 2026-06-11 | Stage 3 / Phase 4-E | AOP + Filter/Interceptor | ✅ **완료**. spring-boot-starter-aop 추가, LoggingAspect(@Around로 모든 컨트롤러 자동 로그·시간 측정). JoinPoint API(signature/args/proceed/target vs this). Filter/Interceptor/AOP 비교. **🎯 두 번째 미니 퀴즈**: Self-invocation 함정 새로 인지(프록시 우회로 @Transactional 안 먹힘 → 시니어 면접 단골), JWT는 Filter 정답. APM 도구들의 근본 원리를 손으로 짠 셈. |
| 2026-06-11 | Stage 3 / Phase 4-E 보강 | AOP·Proxy Deep Dive | 📝 **면접 자산 노트** 작성. self-invocation/프록시 본질을 끝까지 캠 — A/B 두 객체, this의 정체, "왜 프록시?"(편의 vs 성능 오해 교정), 세 대안 비교(수동/AspectJ/Spring AOP), CGLIB vs JDK, NestJS 등 보편 패턴, 면접 Q&A 시뮬 5문항. learning-notes/2026-06-11-stage3-aop-proxy-deepdive.md |

## 다음 세션 예고
- **Phase 4-D**: 예외처리·Validation. `@Valid` + Jakarta Bean Validation, 전역 ErrorResponse 표준화, 4-C에서 미리 본 `@RestControllerAdvice` 본격.
- 이후 4-E AOP → 4-F Security/JWT → 4-G JPA 심화(연관관계·N+1·OSIV 끄기) → 4-H 테스트 → 4-I 설정·Flyway → 미션(블랙잭·로또) → Stage 3 졸업.
- 병행 LeetCode (하루 1문제 캡, Kotlin 관용구).

## TODO (Phase 4 졸업 시 처리)
- [ ] **Phase 1~4 통합 복습 세션** — Stage 3 졸업 직후 단독 세션으로 실시. 구성안:
  1. **Phase별 핵심 개념 인덱스** — 각 Phase의 "한 줄 요약" 압축본 + 잊으면 안 되는 함정 목록
  2. **시니어 사고 패턴 누적 정리** — 컴파일타임vs런타임 / 공유상태 / 변하는것 밖으로 / 책임분리(검증vs판정) / 구조적동시성 / Continuation=Heap / DI 입문 / Dirty Checking 등을 한 표로
  3. **TS↔Kotlin 매핑 표 종합** — 변수/타입/널/제네릭/async/DI/ORM 다 한 곳에
  4. **미니 퀴즈 10문항** — 면접 시뮬레이션 식으로 (멱등성, sealed exhaustiveness, AtomicInteger 왜, Dirty Checking, 등)
  5. **코드 자산 인덱스** — src/main/kotlin/ 안에 만든 거 한눈에 정리 (Intro/Shape/Polymorphism/Baseball/CarRacing/Coroutine/RssReader/User+Service+Controller 등)
  6. **Stage 4 진입 준비도 체크** — 빠진 개념 자가 진단
