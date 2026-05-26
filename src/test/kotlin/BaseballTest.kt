import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class BaseballTest {
    @Test
    fun `같은 자리 같은 숫자는 스트라이크`() {
        countStrikes(listOf(1, 2, 3), listOf(1, 5, 3)) shouldBe 2
    }

    @Test
    fun `완전히 같으면 3스트라이크`() {
        countStrikes(listOf(1, 2, 3), listOf(1, 2, 3)) shouldBe 3
    }

    @Test
    fun `하나도 안 맞으면 0`() {
        countStrikes(listOf(1, 2, 3), listOf(4, 5, 6)) shouldBe 0
    }

    @Test
    fun `숫자는 있지만 자리가 다르면 볼`() {
        countBalls(listOf(1, 2, 3), listOf(3, 1, 2)) shouldBe 3
    }

    @Test
    fun `자리까지 같으면 볼이 아니다`() {
        countBalls(listOf(1, 2, 3), listOf(1, 2, 5)) shouldBe 0
        // 1,2는 같은 자리(스트라이크)라 볼 아님. 5는 answer에 없음. → 0볼
    }

    @Test
    fun `스트라이크와 볼을 합쳐 판정한다`() {
        judge(listOf(1, 2, 3), listOf(1, 5, 3)) shouldBe Result(strikes = 2, balls = 0)
        judge(listOf(1, 2, 3), listOf(3, 1, 2)) shouldBe Result(strikes = 0, balls = 3)
        judge(listOf(1, 2, 3), listOf(4, 5, 6)) shouldBe Result(strikes = 0, balls = 0)
    }
}