# Stage 2 · Phase 0 + Phase 2 학습 노트 — Gradle & JVM 내부

> 날짜: 2026-05-27
> 주제: Gradle(빌드), JVM 메모리·GC·JIT·Reflection
> 페르소나: TypeScript 개발자 → JS V8 모델과 매핑

---

## Phase 0 — Gradle (TS 매핑)

| 개념 | JVM/Gradle | TS/Node |
|---|---|---|
| 매니페스트 | `build.gradle.kts` | `package.json` |
| 명령 실행기 | `./gradlew <task>` | `npm run <script>` |
| 저장소 | `mavenCentral()` | npm registry |
| 캐시 | `~/.gradle/caches/` | `node_modules`/글로벌 캐시 |

핵심 4가지:
1. **플러그인**(`kotlin("jvm")`): 관련 태스크(compileKotlin/test/jar) 자동 등록
2. **저장소**: mavenCentral()이 사실상의 npm registry
3. **의존성 스코프**: implementation / testImplementation / compileOnly
4. **Gradle Wrapper**(`gradlew`): 로컬 Gradle 불필요, 정해진 버전 자동 다운로드

실습 명령: `./gradlew tasks`, `./gradlew dependencies`, `./gradlew build` → `build/libs/*.jar` 산출.

---

## Phase 2-A — 메모리 모델

```
Thread Stack(스레드마다)    Heap(공유)              Metaspace
┌─프레임─┐                  ┌─객체들─────┐         (클래스 메타)
│ 지역변수│                  │ Car, User..│
│ 참조   │ ──참조──▶        └────────────┘
└────────┘
```
- **Stack:** 스레드별, 메서드 프레임/지역변수. 메서드 끝 → 자동 정리.
- **Heap:** 모든 `new` 객체. **GC가 정리.** 모든 스레드 공유.
- **Metaspace:** 클래스 정의 메타데이터.

## Phase 2-B — GC (Garbage Collector)

**세대 가설:** "대부분 객체는 금방 죽는다" → Heap을 나눠 관리.
- **Young Gen(Eden+Survivor):** 새 객체. Minor GC=빠름.
- **Old Gen:** 오래 사는 객체. Major/Full GC=느림(Stop-The-World).
- **Stop The World(STW):** GC 중 앱 스레드 정지. 응답 지연 주범.

콜렉터:
| GC | 특징 | 용도 |
|---|---|---|
| **G1**(기본 Java 9+) | 균형, 동시 마킹 사이클 | 대부분 |
| ZGC | 초저지연(~수ms STW), 큰 힙 | 응답 민감 |
| Parallel | 처리량 | 배치 |

### 실제 GC 로그 해석 (실험 결과)
```
GC(0) Pause Young (Normal) (G1 Evacuation Pause) 24M->6M(64M) 1.622ms
```
→ Minor GC, 24MB→6MB(18MB 죽음, 가설 증명), STW 1.6ms.

```
GC(63) Pause Full (G1 Compaction Pause) 63M->34M(64M) 12.947ms
```
→ **Full GC** 12.9ms STW! Young GC(1~2ms)보다 5~10배 느림. 운영에서 P99 튀는 원인 → "Full GC 안 일어나게" 튜닝.
- `Evacuation Failure` = 옮길 공간 부족(힙 작아서) → Full로 떨어짐.
- `Concurrent Mark Cycle` + `Pause Remark/Cleanup` = G1이 백그라운드로 Old Gen 마킹, 짧게만 STW.

> JS V8도 같은 모델(Scavenge/Mark-Compact + 세대) — 그저 안 보일 뿐.

## Phase 2-C — JIT 컴파일

```
바이트코드 ─ Interpreter ─▶ 한 줄씩 해석(느리지만 즉시)
          └ JIT ─▶ 핫 메서드를 기계어로 컴파일·캐시(이후 네이티브 속도)
```
- **티어드(Tiered):** C1(빠른 컴파일/중간 최적화) → C2(느린/강한 최적화).
- **워밍업(Warm-up):** 시작 직후엔 Interpreter라 느리고, 핫 메서드들이 컴파일되면 빨라짐 → Spring 첫 요청 느린 이유, 부하테스트 워밍업 제외 이유.
- TS V8도 같은 모델(Ignition+TurboFan).
- Trade-off: AOT(Native Image)는 시작 빠르고 정점 낮음, JIT은 반대. 장시간 서버는 JIT 유리.

## Phase 2-D — Reflection

런타임에 클래스 정보 조회.
```kotlin
val d = Demo("kotlin", 42)
println(d::class.simpleName)                            // "Demo"
d::class.memberProperties.forEach {
    println("${it.name} = ${it.getter.call(d)}")        // name = kotlin / count = 42
}
```
- `something::class` = Kotlin reflection(KClass), `::class.java` = Java reflection.
- 풀 기능은 별도 `kotlin-reflect` 의존성 필요(`implementation(kotlin("reflect"))`) — 가벼운 stdlib 유지를 위해 분리.
- 1-C에서 본 `reified`로 타입 소거 일부 우회 가능.
- **프레임워크가 매일 쓰는 마법의 비결:** Spring DI(@Autowired), Jackson(JSON↔객체), JPA(@Entity), JUnit/Kotest(@Test 발견).
- Trade-off: 느리고 타입안전 우회 → 라이브러리는 OK, 비즈니스 코드는 피하기.

## 핵심 한 줄 요약
- Heap에 객체 살고 GC가 정리. **세대 가설 + G1**이 기본. **Full GC가 P99 튀게 하니 피하라.**
- JIT은 핫 메서드를 기계어로 캐시 → 워밍업 후 네이티브 속도.
- Reflection은 프레임워크의 마법 비결. 직접 쓸 일 드묾.

## 다음 — Phase 3 (동시성 · 코루틴)
**JS 이벤트 루프 ≠ JVM 스레드 모델** — 진짜 새로운 영역. 스레드/락/JMM/`@Volatile`/동시성 컬렉션 → **코루틴**(TS `Promise/async`이 매핑되는 자리, JVM에선 핵심 무기). Stage 2의 백미.
