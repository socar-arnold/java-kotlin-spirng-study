# 🏆 Stage 2 (빌드·JVM·동시성) 완주 — 종합 회고

> 완주일: 2026-06-05
> 소요: 6 학습일 (5/27 → 6/05), 누적 9세션 (전체 23세션)
> 페르소나: TypeScript 개발자 → JVM의 진짜 멀티스레드 세계 입성

---

## 정복한 것

### Phase 0 — Gradle 빌드
- 매니페스트(`build.gradle.kts`)·플러그인·저장소(mavenCentral)·의존성 스코프·Wrapper 이해
- `./gradlew tasks/dependencies/build` 자유롭게 사용
- ↔ npm/package.json 매핑 완료

### Phase 2 — JVM 내부 ⚡
- **메모리 모델:** Thread Stack vs Heap vs Metaspace
- **GC (Garbage Collector):** 세대 가설, G1(기본), STW, **실로그 관찰**: Young 1~2ms vs Full 12.9ms
- **JIT:** Interpreter + 핫 메서드 컴파일, 워밍업, 티어드 (C1/C2)
- **Reflection:** `KClass.memberProperties`로 객체가 자기 자신 조회 — Spring/Jackson 마법의 비결
- 의존성 추가: `kotlin-reflect`

### Phase 3 — 동시성 🧵 (Stage 2 백미)
- **스레드 기초:** `thread { }`/`join()`, 비결정 실험으로 진짜 병렬 확인
- **Race Condition:** Unsafe 5/5 손실(12047~18775) → `@Synchronized`/`AtomicInteger`로 정확히 20000
- **JMM 가시성:** `@Volatile`, 캐시·메모리 동기화 — 원자성과 별개
- **동시성 컬렉션:** `ConcurrentHashMap` 등 (Spring 내부 단골)
- **코루틴 ①:** `suspend`·`delay`·`runBlocking`·`async`/`await`. **순차 2013ms vs 병렬 1011ms (절반!)**
- **코루틴 ②:** Dispatchers (Default 519ms vs IO 110ms = ~5배), 구조적 동시성, supervisorScope 격리

### 미션 ✅
- **🏎️ 코루틴 레이싱:** `cars.map{async{...}}.awaitAll().sortedBy{...}` — 300+500+800 직렬 1600 → 병렬 812
- **📰 RSS리더:** `supervisorScope`+`try/catch on await` 부분 실패 격리. DI(`FeedClient` 인터페이스)로 테스트 가능. 직렬 900 → 병렬 315

---

## 통째로 박힌 시니어 사고 (Stage 2 추가분)

1. **컴파일타임 vs 런타임 (재확인)**
   - sealed exhaustiveness, 변성, copy unresolved에 이어 `class Point` 없는 메서드 호출까지: Kotlin은 가능한 한 일찍 잡음.

2. **공유 상태가 모든 문제의 근원**
   - race condition은 코드가 아니라 *공유*가 만든다 → 락/Atomic은 대증요법, 진짜 해법은 *공유 자체를 줄이기*.

3. **변하는 것(랜덤·시간·I/O)은 경계 밖으로**
   - 코루틴 레이싱(시간), RSS(외부 I/O) 모두 *주입*해 결정적 테스트.
   - Stage 1의 "랜덤 주입" 사고가 코루틴/외부 의존성으로 확장.

4. **구조적 동시성 = 누락 방지의 언어 차원 해결**
   - TS는 Promise leak 위험 / Kotlin은 *언어가 보장*.
   - 한 자식 실패 → 형제 자동 취소 또는 (supervisor)격리. 누수 없음.

5. **Continuation = Heap 객체**
   - 스택은 스레드 전용이라 못 옮김 / Heap에 두니 *어느 스레드든* 이어 실행 → "가벼움"의 본질.
   - Phase 2-A 메모리 모델 ↔ Phase 3-E 코루틴이 같은 그림에서 만남.

6. **DI 입문**
   - RSS의 `FeedClient` 인터페이스 = "*어떻게* 가져오는지 분리". Phase 4 Spring DI의 예고편.

---

## TS 개발자로서 얻은 새 자산
- **`Promise/async-await` ↔ 코루틴** 직접 매핑 완료. 단 *진짜 멀티스레드*라는 차이 체득.
- **`Promise.all` ↔ `coroutineScope { async{} async{} }`** — Kotlin은 실패 시 *실제 취소*.
- **JS 싱글 스레드 사고에서 → JVM 풀+Dispatcher 사고로** 확장 (Phase 4 Spring 진입 준비).

## 코드 자산 (전부 TDD)
```
src/main/kotlin/
  Reflection.kt        Demo + memberProperties
  ThreadDemo.kt        비결정 출력 실험
  Counter.kt           Unsafe/Safe(@Sync)/Atomic 3종
  Coroutine.kt         fetchUser/fetchPosts/loadParallel
  CoroutineRacing.kt   🏎️ RaceCar/RaceResult/race
  RssReader.kt         📰 FeedClient/Feed/parseFeed/RssReader
```

---

## 페이스 점검
- **계획 ~12세션 → 실제 9세션.** 페이스 약간 빨라짐.
- TS 지식이 코루틴에서 큰 가속(`Promise/async` 사전 이해), 다만 메모리/GC·JMM은 새로 배움.
- Stage 1+2 누적 **23세션 / ~78 (≈ 30%)**.

## 다음 — Stage 3 (백엔드, ~18세션) 🚀
**진짜 산.** 여기서 *실무 백엔드*가 머리에 박힘:
- **Phase 4-A** HTTP/REST 기초 (CS-1 페어링 적기)
- **Phase 4-B** Spring Boot 기초 — `@SpringBootApplication`, 의존성 주입(RSS의 `FeedClient`를 Spring이 *진짜로* 주입)
- **Phase 4-C** Spring Data JPA — TS의 TypeORM/Prisma에 해당, 다만 영속성 컨텍스트라는 함정 영역
- **Phase 4-D** 예외 처리 & Validation
- **Phase 4-E** Filter/Interceptor/AOP
- **Phase 4-F** Spring Security & JWT (CS-1 토큰 페어링)
- **Phase 4-G** JPA 심화
- **Phase 4-H** Spring 테스트 전략 (`@SpringBootTest`/`@DataJpaTest`/MockMvc)
- **Phase 4-I** 설정 관리 & Flyway
- 미션: 블랙잭, 로또

병행: **LeetCode 트랙 정식 가동** (LEARNING_TRACK 참고). 하루 1문제 캡, Kotlin 관용구 익히기 목적.

→ Stage 3 마치면 **"Kotlin으로 진짜 서버 만들 줄 안다"** 가 됨.
