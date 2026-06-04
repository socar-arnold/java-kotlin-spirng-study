import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

class StructuredTest {
    @Test
    fun `한 자식이 throw하면 형제도 자동 취소된다`():Unit = runBlocking {
        var siblingProgressed = false

        val exception = runCatching {
            coroutineScope {
                launch {
                    delay(50)
                    throw RuntimeException("의도된 실패")
                }

                launch {
                    delay(300)// 위에서 50ms 후 throw -> 250ms 더 가기전에 취소
                    siblingProgressed = true //도달 못할 예정
                }
            }
        }.exceptionOrNull()

        exception?.message shouldBe "의도된 실패"
        siblingProgressed shouldBe false //형제도 취소 = false 유지
    }

    @Test
    fun`supervisorScope에서는 형제가 살아남는다`():Unit = runBlocking{
        var siblingFinished = false

        supervisorScope {
            launch {
                try {
                    delay(50)
                    throw RuntimeException("의도된 실패")
                } catch(e:Exception) {
                    //격리
                }
            }

            launch {
                delay(200)
                siblingFinished = true //정상도달
            }
        }

        siblingFinished shouldBe true
    }
}