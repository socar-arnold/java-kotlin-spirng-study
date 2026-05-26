import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class VarianceTest {
    @Test
    fun `Cat 상자를 Animal 상자로 쓸 수 있다`() {
        val catBox: ReadBox<Cat> = ReadBox(Cat("나비"))
        val animalBox: ReadBox<Animal> = catBox   // 여기가 핵심! out이라야 통과
        animalBox.get().name shouldBe "나비"
    }
}