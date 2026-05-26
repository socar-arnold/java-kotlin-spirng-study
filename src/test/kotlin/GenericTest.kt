import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class GenericsTest {
    @Test
    fun `first는 타입을 유지한 채 첫 요소를 준다`() {
        first(listOf(10, 20, 30)) shouldBe 10
        first(listOf("a", "b")) shouldBe "a"
    }

    @Test
    fun `Box는 담은 값을 그대로 돌려준다`() {
        Box("hello").get() shouldBe "hello"
        Box(42).get() shouldBe 42
    }
}