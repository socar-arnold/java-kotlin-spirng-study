import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class CarRacingTest {
    @Test
    fun `4이상이면 전진한다` () {
        Car("pobi").move(4) shouldBe Car("pobi", 1)
    }

    @Test
    fun `3이하면 멈춘다` () {
        Car("pobi").move(3) shouldBe Car("pobi", 0)
    }

    @Test
    fun `가장 멀리 간 차가 우승자` () {
        val cars = listOf(Car("a", 3), Car("b", 5), Car("c", 5))
        winners(cars) shouldBe listOf("b", "c")
    }

    @Test
    fun `우승자가 한 명일 수도` () {
        winners(listOf(Car("a", 1), Car("b", 3))) shouldBe listOf("b")
    }
    @Test
    fun `한 라운드 - 값에 따라 각 차가 전진하거나 멈춘다`() {
        val cars = listOf(Car("a"), Car("b"), Car("c"))
        val result = playRound(cars, listOf(4, 3, 9))   // a전진, b멈춤, c전진
        result shouldBe listOf(Car("a", 1), Car("b", 0), Car("c", 1))
    }
}