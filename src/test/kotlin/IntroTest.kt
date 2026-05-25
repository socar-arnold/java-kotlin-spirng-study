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

    @Test
    fun `90점 이상은 A`() {
        grade(95) shouldBe "A"
    }

    @Test
    fun `60점 미만은 F`() {
        grade(50) shouldBe "F"
    }

    @Test
    fun `경계값 90은 A, 89는 B`() {
        grade(90) shouldBe "A"
        grade(89) shouldBe "B"
    }

    @Test
    fun `내용이 같은 두 Point는 동등하다`() {
        Point(1, 2) shouldBe Point(1, 2)
    }

    @Test
    fun `copy로 일부만 바꿀 수 있다`() {
        val p = Point(1, 2)
        p.copy(y = 9) shouldBe Point(1, 9)
    }
}