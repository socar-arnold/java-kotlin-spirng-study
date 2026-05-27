# Stage 1 · 미션 ① 숫자야구 (Phase 1 종합) ⚾

> 날짜: 2026-05-27
> 의미: Phase 1 개념(A~F)을 조립한 첫 NEXTSTEP 미션. **완성·플레이 성공.**
> 페르소나: TypeScript 개발자

---

## 만든 것 (전부 TDD로, I/O만 제외)

| 함수/클래스 | 역할 | 사용한 Phase 개념 |
|---|---|---|
| `countStrikes(answer, guess)` | 같은 자리 같은 숫자 수 | 1-D 컬렉션(`count`/`indices`) |
| `countBalls(answer, guess)` | 숫자는 있고 자리 다른 수 | 1-D (`in`=contains) |
| `data class Result(strikes, balls)` | 판정 결과 | 1-A data class |
| `judge(answer, guess)` | 두 카운트 합성 | 조립 |
| `hasDuplicate(list)` | 중복 검사 | 1-E (`toSet().size`) |
| `validateGuess(guess)` | 3자리·1~9·중복없음 검증 | 1-F (`require`) + 1-E(`all`) |
| `generateAnswer()` | 1~9 중복없는 3자리 랜덤 | `(1..9).shuffled().take(3)` |
| `class Game(private val answer)` | 정답 은닉 + `play(guess)` | 캡슐화 |
| `isWin(result)` | strikes==3 | |
| `main()` | 콘솔 루프(I/O 글루) | 테스트 제외 |

## 핵심 배운 점
- **랜덤을 테스트하는 법:** 랜덤 결과 자체가 아니라 "결과가 규칙(3자리·1~9·중복없음)을 지키는지"를 `repeat(100)`으로 검증.
- **정답 은닉:** `private val answer` — `private`(외부에서 못 봄=치팅 방지) ≠ `val`(재할당 불가). 둘은 다른 것, 둘 다 필요.
- **I/O와 로직 분리:** 판정/검증/승리는 순수 함수·클래스로 빼서 테스트, `main`은 검증된 로직을 호출하는 얇은 껍데기. → "테스트 가능한 설계".
- **구조로 규칙 보장:** `(1..9).shuffled().take(3)`는 원본에 중복이 없으니 결과도 중복 불가 — 검증보다 좋은 "애초에 불가능하게 만들기".
- **조립의 힘:** judge/validateGuess/hasDuplicate 등 작게 만든 조각을 Game/main이 재사용.

## 실행
```bash
./gradlew run -q --console=plain
# ⚾ 숫자야구 시작! ... 입력: 321 → 1 스트라이크 0 볼 / 입력: 252 → 잘못된 입력: 중복 불가
```
플레이 확인 완료 ✅ (검증·판정·루프 정상).

## 핵심 한 줄 요약
- Phase 1 전 개념을 조립 → 동작하는 숫자야구. 랜덤은 "규칙 충족"으로 테스트, I/O는 로직과 분리, 정답은 private로 은닉.

## 다음
Stage 1 남은 미션: 자동차경주(랜덤 전진·우승자), 좌표계산기. 그 후 Stage 2(빌드·JVM·동시성).
