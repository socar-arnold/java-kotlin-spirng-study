import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class FpTest {
    @Test
    fun `제곱들의 합`() {
        sumOfSquares(listOf(1, 2, 3)) shouldBe 14   // 1 + 4 + 9
    }

    @Test
    fun `중복이 있으면 true`() {
        hasDuplicate(listOf(1, 2, 2)) shouldBe true
    }

    @Test
    fun `중복이 없으면 false`() {
        hasDuplicate(listOf(1, 2, 3)) shouldBe false
    }
}