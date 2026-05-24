import org.junit.jupiter.api.Test
import io.kotest.matchers.shouldBe


class IntroTest {
    @Test
    fun `이름이 있으면 인사한다`() {
        greet("Arnold") shouldBe "Hello, Arnold!"
    }

    @Test
    fun `이름이 null이면 Guest로 인사한다`() {
        greet(null) shouldBe "Hello, Guest!"
    }
}