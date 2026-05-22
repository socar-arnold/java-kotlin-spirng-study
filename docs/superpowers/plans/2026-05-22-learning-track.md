# 학습 트랙 스캐폴딩 + Stage 0 1주차 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** AI 튜터 주도 학습 트랙의 인프라 문서(LEARNING_TRACK.md, PROGRESS.md)와 CURRICULUM.md의 Java 온램프 섹션을 만들고, Stage 0 1주차 레슨까지 준비해 첫 세션을 바로 시작할 수 있는 상태로 만든다.

**Architecture:** 문서 3종 — `LEARNING_TRACK.md`(진입점: 스케줄+세션 프로토콜), `PROGRESS.md`(진척 추적), `CURRICULUM.md`(콘텐츠 본체에 "Phase Pre: Java 온램프" 섹션 신설). 60주치를 일괄 집필하지 않고(적시 집필), Stage 0의 주차 스케줄과 1주차 레슨만 채운다.

**Tech Stack:** Markdown 문서. 코드 예시는 Java/Kotlin/TypeScript (페르소나 매핑용).

**참조 스펙:** [docs/superpowers/specs/2026-05-22-learning-track-design.md](../specs/2026-05-22-learning-track-design.md)

---

## File Structure

- `LEARNING_TRACK.md` (신규, 루트): 진입점. 페르소나·파라미터·Stage 표·Stage 0 주차 스케줄·세션 프로토콜·진척 링크.
- `PROGRESS.md` (신규, 루트): 현재 Stage/주차/주제/완료 미션 기록. 세션마다 갱신.
- `CURRICULUM.md` (수정, 루트): line 83(`---`)과 85(`## Phase 0`) 사이에 "Phase Pre: Java 온램프" 섹션 삽입.

---

## Task 1: LEARNING_TRACK.md 진입점 문서 생성

**Files:**
- Create: `LEARNING_TRACK.md`

- [ ] **Step 1: 파일 작성**

아래 전체 내용으로 `LEARNING_TRACK.md`를 생성한다.

```markdown
# 학습 트랙 — TypeScript 개발자를 위한 Kotlin 백엔드 양성

> **이 문서는 진입점입니다.** "언제·어떻게" 공부할지를 담습니다.
> 콘텐츠 본체는 [CURRICULUM.md](CURRICULUM.md), 진척은 [PROGRESS.md](PROGRESS.md),
> 설계 근거는 [스펙](docs/superpowers/specs/2026-05-22-learning-track-design.md).

## 대상 페르소나
**TypeScript에 익숙하지만 JVM은 처음인 개발자.**
- 이미 아는 것: 타입, async/await, OOP·함수형 기초, 모듈/패키지
- 새로운 것: JVM·바이트코드·GC, 스레드 모델(이벤트 루프 ≠ 스레드), Kotlin/Java 문법, Spring DI, JPA
- 학습 방식: 모든 개념을 TS에 매핑 → "새로 배우기"보다 "아는 것과 연결"

## 학습 파라미터
| 항목 | 값 |
|---|---|
| 페이스 | 가볍게 = 주 7~8시간 (평일 1h + 주말 3h) |
| 세션 형태 | 인터랙티브 튜터 (1세션 = 1주제) |
| 집필 방식 | 적시 집필 — 뼈대 먼저, 상세 레슨은 매주 |

## Stage 순서 (누적 주차, TS 숙련 반영)
| Stage | 내용 | 누적 |
|---|---|---|
| 0. Java 온램프 | JVM·생태계·문법·OOP 개념 (남 코드 읽기용) | ~2주 |
| 1. Kotlin 기초 | Phase 1 + 비기너 쿠션 + OOP 사고 | ~10주 |
| 2. 빌드·JVM·동시성 | Phase 0/2/3 + 미션 | ~20주 |
| 3. 백엔드 | Phase 4 (Spring·JPA·테스트) + 미션 | ~32주 |
| 4. 심화 | Phase 5~12 | 이후 |
| 5. 분산·시스템디자인 | Phase 13 신설 | 이후 |
| 병렬 | CS (면접·실전 빈출), 매주 1개 | 전 구간 |

## Stage 0 주차 스케줄 (Java 온램프)
| 주차 | 주제 | 목표 | 완료 기준 |
|---|---|---|---|
| 1 | JVM·생태계 + Java 문법 매핑 | JVM/JDK/JRE 구분, Java 코드 읽기 | Java 코드를 읽고 TS로 의미 설명 가능 |
| 2 | Java OOP·관용구 + Kotlin은 왜 바꿨나 | 클래스/상속/인터페이스, 체크예외·getter/setter·제네릭 소거 | "Kotlin이 개선한 점" 3가지 설명 가능 |

## 세션 프로토콜 (1세션 = 1주제)
1. 지난 내용 1분 복습 체크
2. **WHY** — 나쁜 코드/문제 상황 먼저
3. **WHAT** — 개념 + trade-off + TS 매핑
4. **HOW** — 미션 제시 → 직접 타이핑(복붙 금지) → TDD Red→Green→Refactor → 시니어 코드리뷰처럼 피드백
5. 마무리 요약 + 다음 예고 → PROGRESS.md 갱신

## CS 병렬 트랙 (면접·실전 빈출)
네트워크(TCP/UDP·HTTP·TLS·쿠키/세션/토큰·CORS) / OS(프로세스vs스레드·컨텍스트스위칭·가상메모리·데드락) /
자료구조·알고리즘(해시·트리·정렬·시간복잡도) / DB CS(인덱스 B-tree·트랜잭션·격리수준·정규화).
매주 그 주 메인 학습과 연관된 1개를 "말로 설명할 수준"으로.
```

- [ ] **Step 2: 구조 검증**

Run: `grep -c "^## " LEARNING_TRACK.md`
Expected: `6` (대상 페르소나 / 학습 파라미터 / Stage 순서 / Stage 0 주차 스케줄 / 세션 프로토콜 / CS 병렬 트랙)

- [ ] **Step 3: 커밋**

```bash
git add LEARNING_TRACK.md
git commit -m "docs: 학습 트랙 진입점 LEARNING_TRACK.md 추가"
```

---

## Task 2: PROGRESS.md 진척 추적 문서 생성

**Files:**
- Create: `PROGRESS.md`

- [ ] **Step 1: 파일 작성**

아래 전체 내용으로 `PROGRESS.md`를 생성한다.

```markdown
# 학습 진척

> 매 세션 종료 시 갱신. 다음 대화에서 여기부터 이어간다.
> 스케줄은 [LEARNING_TRACK.md](LEARNING_TRACK.md) 참조.

## 현재 위치
- **Stage:** 0 (Java 온램프)
- **주차:** 1주차 — JVM·생태계 + Java 문법 매핑
- **상태:** 시작 전

## 완료 로그
| 날짜 | Stage·주차 | 주제 | 미션/결과 |
|---|---|---|---|
| - | - | (아직 없음) | - |

## 다음 세션 예고
- Stage 0 / 1주차: JVM이 뭔지(JS 런타임과 대비), JDK/JRE/JVM 구분, Java 기본 문법을 TS와 매핑하며 읽기.
```

- [ ] **Step 2: 구조 검증**

Run: `grep -E "현재 위치|완료 로그|다음 세션" PROGRESS.md | wc -l`
Expected: `3`

- [ ] **Step 3: 커밋**

```bash
git add PROGRESS.md
git commit -m "docs: 학습 진척 추적 PROGRESS.md 추가"
```

---

## Task 3: CURRICULUM.md에 "Phase Pre: Java 온램프" 섹션 신설

**Files:**
- Modify: `CURRICULUM.md` (line 83 `---` 다음, line 85 `## Phase 0` 앞에 삽입)

- [ ] **Step 1: 삽입 위치 확인**

Run: `grep -n "^## Phase 0: 빌드 시스템" CURRICULUM.md`
Expected: `85:## Phase 0: 빌드 시스템`

- [ ] **Step 2: 섹션 삽입**

`## Phase 0: 빌드 시스템` 바로 앞에 아래 블록을 삽입한다 (Edit 도구로 `## Phase 0: 빌드 시스템`을 아래 + 원본으로 치환).

```markdown
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

```

치환 대상(old_string): `## Phase 0: 빌드 시스템`
치환 결과(new_string): 위 블록 전체 + `## Phase 0: 빌드 시스템`

- [ ] **Step 3: 삽입 검증**

Run: `grep -n "^## Phase Pre: Java 온램프" CURRICULUM.md`
Expected: `84:## Phase Pre: Java 온램프 (TypeScript 개발자용)` (라인 번호는 근사)

Run: `grep -c "^### Pre-" CURRICULUM.md`
Expected: `4`

- [ ] **Step 4: 커밋**

```bash
git add CURRICULUM.md
git commit -m "docs: CURRICULUM에 Phase Pre Java 온램프 섹션 신설 (TS 개발자용)"
```

---

## Task 4: 첫 세션 진입 준비 확인

**Files:** (없음 — 상태 확인만)

- [ ] **Step 1: 세 문서가 모두 존재하고 서로 링크되는지 확인**

Run: `ls LEARNING_TRACK.md PROGRESS.md && grep -l "Phase Pre" CURRICULUM.md`
Expected: 세 경로 모두 출력, 에러 없음

- [ ] **Step 2: git 상태 확인**

Run: `git log --oneline -4`
Expected: Task 1~3의 커밋 3개 + 직전 스펙 커밋이 보임

- [ ] **Step 3: 첫 세션 시작 안내**

별도 커밋 없음. 이 시점에서 사용자에게 "Stage 0 1주차 세션을 시작할까요?"를 묻고,
세션 프로토콜(WHY→WHAT→HOW→리뷰→PROGRESS 갱신)에 따라 인터랙티브 학습을 시작한다.

---

## Self-Review

- **Spec coverage:** §4 산출물(LEARNING_TRACK/PROGRESS/CURRICULUM 보강) → Task 1/2/3. §2 페르소나·§5 Stage·§8 프로토콜 → Task 1에 반영. §6 Java 온램프 → Task 3. 갭 보강(§7)·Stage 1~5 상세는 적시 집필이라 본 계획 범위 밖(스펙 §10 비목표와 일치).
- **Placeholder scan:** TBD/TODO 없음. 모든 문서 전체 내용 기재.
- **Type consistency:** 문서 작업이라 시그니처 없음. 파일명·헤더명 일관 확인 완료(LEARNING_TRACK.md / PROGRESS.md / "Phase Pre: Java 온램프").
