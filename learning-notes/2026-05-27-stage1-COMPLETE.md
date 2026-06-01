# 🏆 Stage 1 (Kotlin 기초) 완주 — 종합 회고

> 완주일: 2026-05-27
> 소요: 4개 학습일(5/22, 5/25, 5/26, 5/27), 누적 15세션
> 페르소나: TypeScript 개발자 → JVM/Kotlin 입문

---

## 정복한 것

### 개념 (Phase 1 A~F)
- **1-A:** val/var, 타입 추론, 문자열 템플릿, **널 안전성**(`?.`/`?:`), if/when 식, 최상위 함수, 단일 식 함수, data class
- **1-B:** sealed class(=TS 판별 유니온), 망라적 when, 스마트 캐스트, 인터페이스/추상클래스, **다형성** (표·동물원 비유로 표현 문제 정리)
- **1-C:** 제네릭 `<T>`, 타입 추론·소거, **변성**(out 공변, PECS)
- **1-D:** 컬렉션(불변 우선), `count/filter/map/in/all`, Set O(1) 활용
- **1-E:** 고차함수, 체이닝, `fold`(reduce 안전판), Set 중복검사
- **1-F:** `require`/`check`/`error` fail-fast, 검증/판정 책임 분리

### 미션 (NEXTSTEP)
- **⚾ 미션① 숫자야구:** generateAnswer/Game(private 정답)/judge/validateGuess/main. 랜덤은 "규칙 충족"으로 테스트.
- **🏎️ 미션② 자동차경주:** Car(copy 불변)/winners(maxOf→filter→map)/playRound(zip)/main. 랜덤 주입=결정적 테스트.
- **📐 미션③ 좌표계산기:** Coordinate(data class)/distance(피타고라스). 구조 분해 선언.

### 곁들임 (CS / 디버깅 / 도구)
- CS-3 Big-O (O(1)/O(n)/O(n²), Redis 해시 O(1) 연계)
- main vs test 소스셋 구분
- 에러가 가리키는 심볼 먼저 읽기(빈 Generics.kt 디버깅)
- 커밋 전 `./gradlew test`로 진짜 초록 확인(verification 습관)

---

## 통째로 박힌 시니어 사고

1. **테스트 가능한 설계**
   - 변하는 것(랜덤·I/O)은 경계 밖으로, 안쪽 로직은 순수하게.
   - "랜덤을 테스트하는 법" = 결과가 아니라 **규칙 충족**을 검증.

2. **불변 우선**
   - `val`/listOf 기본, 갱신은 `copy()`로 새 객체. `var`는 최소.

3. **책임 분리**
   - 검증(`validateGuess`)과 판정(`judge`)을 분리 → fail-fast + 재사용.
   - 정답 은닉(`private val`): private(외부 차단) ≠ val(재할당 차단). 둘 다 필요.

4. **표현 문제(Expression Problem)**
   - 종류↔동작 트레이드오프: **종류가 자주 늘면 다형성, 동작이 자주 늘면 sealed+when.**
   - "묶은 방향으로 늘리면 쉽고 직각이면 다 고쳐야 한다."

5. **컴파일타임 vs 런타임**
   - sealed exhaustiveness, 변성 type mismatch, copy unresolved → 다 컴파일 단계 차단.
   - JS에서 런타임에 터졌을 것들을 Kotlin은 컴파일 시점에 잡아줌.

---

## TS 개발자로서 얻은 자산
- Kotlin == TS 와 같은 사고가 **80%**. 차이 20% (널안전성 강제·sealed·변성·체크예외 없음·코루틴 예고) 가 핵심 무기.
- `Promise/async` → 코루틴(Phase 3에서), TS union → sealed(✅), TypeORM → JPA(Phase 4에서), NestJS DI → Spring DI(Phase 4) — 매핑 자산이 계속 쌓이는 중.

## 코드 자산 (전부 TDD + 콘솔 플레이 확인)
```
src/main/kotlin/
  Intro.kt          greet, grade
  Shape.kt          sealed Shape + area
  Polymorphism.kt   interface Shape2 + totalArea
  Generics.kt       first<T>, Box<T>
  Variance.kt       ReadBox<out T>
  Fp.kt             sumOfSquares, hasDuplicate
  Baseball.kt       ⚾ 숫자야구 풀구현
  CarRacing.kt      🏎️ 자동차경주 풀구현
  Coordinate.kt     📐 좌표계산기
```

---

## 다음 — Stage 2 (빌드·JVM·동시성, ~12세션)
- **Phase 0 빌드:** Gradle 본격(우리가 줄곧 쓴 그것의 정체), Version Catalog
- **Phase 2 JVM:** 컴파일러 파이프라인, 메모리·GC, JIT, **Reflection**
- **Phase 3 동시성:** 스레드/락/JMM/`@Volatile`/동시성 컬렉션 → **코루틴**(TS `Promise/async`의 그 자리, JVM에선 핵심)
- 페어링 CS: 네트워크/OS(스레드·데드락)
- 미션: RSS리더, 코루틴 레이싱

TS 사고 한 가지가 진짜 시험대에 오르는 단계예요 — **JS 이벤트 루프 ≠ JVM 스레드 모델**. 동시성이 진짜 새로운 영역.
