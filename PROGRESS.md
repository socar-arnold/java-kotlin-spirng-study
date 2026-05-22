# 학습 진척

> 매 세션 종료 시 갱신. 다음 대화에서 여기부터 이어간다.
> 스케줄은 [LEARNING_TRACK.md](LEARNING_TRACK.md) 참조.
> 처음 클론한 사람은 아래 "학습자 프로필"을 `<미설정>`으로 비우면 온보딩이 다시 돈다 ([CLAUDE.md](CLAUDE.md) 참조).

## 학습자 프로필
- **익숙한 언어/스택:** TypeScript (→ TS 매핑 비유 적극 활용)
- **주당 학습 시간:** 가볍게 (주 7~8시간)

## 현재 위치
- **Stage:** 1 (Kotlin 기초) — Phase 1 진입
- **주차:** 3주차(누적) — Kotlin Introduction (변수·표현식·널 안전성)
- **상태:** 시작 전

## 완료 로그
| 날짜 | Stage·주차 | 주제 | 미션/결과 |
|---|---|---|---|
| 2026-05-22 | Stage 0 / 1주차 | JVM·생태계 + Java 문법 매핑 | ✅ 완료. 컴파일(javac/kotlinc=JDK) vs 실행(JVM⊂JRE) 분리 이해, WORA·바이트코드·바이너리 감각 확인. TS 파이프라인(tsc→node) 매핑 성공. 초기 오해(컴파일을 JVM이 한다)는 교정 완료 |
| 2026-05-22 | Stage 0 / 2주차 | Java OOP·관용구 + "Kotlin은 왜 바꿨나" | ✅ 완료(=Stage 0 졸업). 명목적 vs 구조적 타이핑, Java 관용구 3종(getter/setter·체크예외·제네릭소거)과 Kotlin 개선 이해. 교정: `throws`는 인자 아님(체크예외 처리 강제), Kotlin `override`는 필수 키워드. 20줄 Java→6줄 Kotlin 재작성 확인 |

## 다음 세션 예고
- Stage 1 / Phase 1-A (Introduction to Kotlin): `val`/`var`, 타입 추론, 문자열 템플릿, `if/when`을 표현식으로, 널 안전성(`?.`/`?:`). 여기서부터 **직접 타이핑 + 실제 실행(RED/GREEN)** 으로 TDD 시작.
