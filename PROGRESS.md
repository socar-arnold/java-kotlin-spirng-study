# 학습 진척

> 매 세션 종료 시 갱신. 다음 대화에서 여기부터 이어간다.
> 스케줄은 [LEARNING_TRACK.md](LEARNING_TRACK.md) 참조.
> 처음 클론한 사람은 아래 "학습자 프로필"을 `<미설정>`으로 비우면 온보딩이 다시 돈다 ([CLAUDE.md](CLAUDE.md) 참조).

## 학습자 프로필
- **익숙한 언어/스택:** TypeScript (→ TS 매핑 비유 적극 활용)
- **주당 학습 시간:** 가볍게 (주 7~8시간)

## 현재 위치
- **Stage:** 1 (Kotlin 기초) — **Phase 1 개념(A~F) 전부 완료** 🎉
- **진행:** 누적 **12세션 완료** (Stage 0: 2 / Stage 1: 9 / Phase CS: 1). 세션 = 약 45~90분 학습 단위 ([LEARNING_TRACK.md](LEARNING_TRACK.md) 보정 기준)
- **Stage 1 잔여:** ~5세션 (미션 3개: 숫자야구/자동차경주/좌표계산기)
- **다음:** 🎯 **숫자야구 미션** (Phase 1 종합) — 랜덤 정답 생성 + 게임 루프
- **메모:** 숫자야구 재료 전부 완성 — judge/countStrikes/countBalls(1-D), hasDuplicate(1-E), validateGuess(1-F). src/main/kotlin/Baseball.kt
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

## 다음 세션 예고
- 🎯 **숫자야구 미션** (Phase 1 종합): 랜덤 정답 생성(1~9, 중복 없는 3자리) → 게임 루프(3스트라이크까지 반복) → 입력 처리. 이미 만든 judge/validateGuess/hasDuplicate 조립. 계속 TDD.
