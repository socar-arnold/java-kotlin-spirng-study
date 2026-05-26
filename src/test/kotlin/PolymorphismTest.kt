import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class PolymorphismTest {
    @Test
    fun `서로 다른 도형들의 넓이 합을 구한다`() {
        val shapes = listOf(Rectangle2(2.0, 3.0), Rectangle2(4.0, 5.0))
        totalArea(shapes) shouldBe 26.0     // 6 + 20
    }

    @Test
    fun `원과 사각형이 섞여 있어도 각자 넓이로 합산`() {
        val shapes = listOf(Circle2(1.0), Rectangle2(2.0, 2.0))
        totalArea(shapes) shouldBe (Math.PI * 1.0 + 4.0)
    }

    @Test
    fun `삼각형이 섞여도 합산된다`() {
        totalArea(listOf(Triangle2(6.0, 4.0), Rectangle2(2.0, 2.0))) shouldBe 16.0 //12 + 4

    }
}
