import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class CoordinateTest {
    @Test
    fun `같은 점 사이 거리는 0`() {
        distance(Coordinate(1, 1), Coordinate(1, 1)) shouldBe 0.0
    }

    @Test
    fun `3-4-5 직각삼각형 거리는 5`() {
        distance(Coordinate(0, 0), Coordinate(3, 4)) shouldBe 5.0
    }

    @Test
    fun `가로로 떨어진 거리`() {
        distance(Coordinate(2, 0), Coordinate(5, 0)) shouldBe 3.0
    }
}