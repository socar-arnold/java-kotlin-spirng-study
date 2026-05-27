# Stage 1 · 미션② 자동차경주 🏎️

> 날짜: 2026-05-27
> 의미: 두 번째 미션. 여러 객체(자동차) 다루기 + 불변 갱신 + 공동 우승.
> 페르소나: TypeScript 개발자

---

## 만든 것 (로직은 TDD, 랜덤·I/O 분리)
```kotlin
data class Car(val name: String, val position: Int = 0) {
    fun move(value: Int): Car =
        if (value >= 4) copy(position = position + 1)   // 불변: copy로 새 차
        else this
}

fun winners(cars: List<Car>): List<String> {
    val max = cars.maxOf { it.position }                // 1) 최고 위치(값 하나)
    return cars.filter { it.position == max }           // 2) 그 위치 차들
               .map { it.name }                         // 3) 이름만 (동률=공동우승)
}

fun playRound(cars: List<Car>, values: List<Int>): List<Car> =
    cars.zip(values) { car, value -> car.move(value) }  // 두 리스트 짝지어 move

// main: (0..9).random()로 값 생성, "-".repeat(pos)로 시각화, var cars 갱신
```

## 헷갈렸던 함정 (교훈)
- **`copy(position = position + 1)`** — `value + 1`이 아님! 더할 건 주사위값(value)이 아니라 현재 위치(position). 한 칸 전진.
- **`maxOf`는 리스트가 아니라 값 하나를 반환** — 그래서 `.map`을 바로 못 붙임. 우승자 찾기는 "최고값 구하기 → filter → map" **두 단계**.
- **`zip`** — 두 리스트를 짝지어 묶음(`cars.zip(values){car,v->...}`). TS엔 없어서 index map 써야 하는 걸 한 줄로.

## 핵심 설계 교훈
- **랜덤 주입(injection):** `playRound`는 랜덤을 안 만들고 `values`를 받음 → 테스트가 **결정적**(고정값으로 검증 가능). 변하는 것(랜덤)은 경계 밖으로, 안쪽은 순수하게. (→ 나중에 DI/Spring으로 연결)
- **불변 갱신:** `move`는 `copy`로 새 객체. "현재 차들"을 가리키는 `var cars`만 매 라운드 갱신.
- **`(0..9).random()`**(범위 랜덤), **`"-".repeat(n)`**(문자열 반복) 신규.

## 핵심 한 줄 요약
- data class + copy로 불변 전진, zip으로 라운드 진행, maxOf→filter→map으로 (공동)우승자.
- 랜덤은 주입해 결정적 테스트, I/O는 main에 분리.

## 다음
미션③ 좌표계산기 (Stage 1 마지막) → 그 후 Stage 2.
