import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.PI

class ShapeTest {
    @Test
    fun `직사각형 넓이는 가로x세로` () {
        area(Rectangle(3.0, 4.0)) shouldBe 12.0
    }

    @Test
    fun `원 넓이는 파이x반지름 제곱` () {
        area(Circle(2.0)) shouldBe PI*4.0
    }

    @Test
    fun `삼각형 넓이는 밑변x높이의 절반`() {
        area(Triangle(6.0, 4.0)) shouldBe 12.0
    }
}