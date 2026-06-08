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

## Stage 순서 — 세션 기반 (실제 페이스 보정 · 2026-05-25 기준)

> **보정 근거:** 실측 결과 학습자는 계획(가벼운 페이스, 주 7~8h)보다 개념 학습이 약 3배 빠름
> (2일간 5세션 소화). TS 지식 덕에 개념은 빠르고, **시간이 걸리는 건 미션**(직접 설계+TDD).
> 그래서 "주차" 대신 **세션(약 45~90분 학습 단위)** 으로 재산정. 미션은 1개당 ~2세션 가중.

| Stage | 내용 | 세션(추정) | 비고 |
|---|---|---|---|
| 0. Java 온램프 | JVM·문법·OOP 개념 | ✅ 2세션 (완료) | 5/22 |
| 1. Kotlin 기초 | Phase 1(A~F) + 미션 3개 | ✅ 12세션 (완료) | 5/22~5/27 |
| 2. 빌드·JVM·동시성 | Phase 0/2/3 + 미션 2개 | ✅ 9세션 (완료) | 5/27~6/05 |
| 3. 백엔드 | Phase 4 (Spring·JPA·테스트) + 미션 | ~18세션 | **다음**, Stage 중 최대 |
| 4. 심화 | Phase 5~12 | ~24세션 | |
| 5. 분산·시스템디자인 | Phase 13 | ~8세션 | |
| 병렬 | CS + LeetCode | 세션당 곁들임 | 전 구간 (아래 ↓) |

> 현재 누적 **23세션 / ~78세션 (≈ 30%)**. 페이스 보정으로 Stage 3는 약간 길어질 수 있음 — Spring 매직과 JPA 함정이 진짜 새로운 영역.

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
5. (선택) CS 1꼭지(~10분) — 그 세션과 연관된 주제를 "말로 설명할 수준"으로
6. 마무리 요약 + 다음 예고 → PROGRESS.md 갱신

## CS 병렬 트랙 (면접·실전 빈출)
콘텐츠는 [CURRICULUM.md](CURRICULUM.md)의 **"Phase CS"** 섹션에 정리됨 (CS-1 네트워크 / CS-2 OS /
CS-3 자료구조·알고리즘 / CS-4 DB CS). 매 세션 끝에 1꼭지씩, 그 세션 메인 학습과 연관된 것을
"말로 설명할 수준"으로. 진척은 Phase CS의 체크박스로 표시.

## LeetCode 트랙 — CS-3의 실전 연습장 (Stage 2 졸업과 함께 가동)

> **LeetCode는 별도 트랙이 아니라 [Phase CS-3](CURRICULUM.md)의 *실전 연습장*이에요.**
> 알고리즘 자체보다 **Kotlin 관용구(stdlib) 익히기**가 목적. CS-3 체크박스 진척과 함께 운영.

### 운영 원칙
- **하루 1문제, 30~45분 캡.** 막히면 답 보고 다음 날 다시 풀기 (시간 낭비 금지).
- **Easy → Medium만.** Hard는 면접 임박 시기에. 지금은 *idiom 익히기*가 우선.
- **70(커리큘럼) / 30(LeetCode) 비율 유지.** Stage 3 Spring/JPA 진도를 LeetCode가 갉아먹지 않게.
- 무리한 날은 0문제도 OK — Spring 학습이 메인.

### 문제풀이 워크플로 (★ 중요)
1. **TS 사고로 풀지 말기.** `var counter`+for문 충동 거부 → Kotlin 컬렉션 함수 먼저 떠올리기.
2. 풀고 나서 **"Top Kotlin Solutions" 탭 열기** → 다른 사람들이 어떤 stdlib을 썼는지 흡수.
3. 한 번 더 **함수형 체이닝(map/filter/fold/groupBy)으로 다시 풀어보기** — 의도적 반복.
4. 입력이 크다면 **`asSequence()`** 로 지연 평가 시도(중간 컬렉션 절약).

### 추천 리스트 (큐레이션 > 무작위)
- **NeetCode 150** — 카테고리별 정리, 학습용 최고
- **LeetCode "Top Interview 150"** — 면접 빈출
- 둘 중 하나 정해서 순서대로 — 무작위 풀이는 효율 ↓

### Kotlin에서 자주 빛나는 도구 (이거 손에 익히기 목적)
- 컬렉션: `map`/`filter`/`fold`/`reduce`/`groupBy`/`partition`/`associateBy`/`zip`/`windowed`/`chunked`
- 시퀀스: `asSequence()` (대용량·체이닝 최적화)
- 인덱스: `withIndex()`/`indices`
- 정렬: `sortedBy`/`sortedByDescending`/`sortedWith(compareBy(...))`
- 카운트·집계: `count { }`/`sumOf { }`/`maxByOrNull { }`/`minByOrNull { }`
- 문자열: `groupingBy { }.eachCount()`, `toCharArray().sorted()`

### 어떻게 진척 추적하나
- 푼 문제는 [Phase CS-3](CURRICULUM.md) 영역에 한 줄로 기록(선택). 또는 별도 `learning-notes/leetcode-NN-제목.md`.
- 매주 한 번, "이번 주 N문제 / 손에 익힌 stdlib X 함수" 정도 자체 회고.
