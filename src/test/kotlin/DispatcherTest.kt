import io.kotest.matchers.longs.shouldBeLessThan
import io.kotest.matchers.longs.shouldBeGreaterThanOrEqual
import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis


class DispatcherTest {
    private val WORK_COUNT = 50
    private val BLOCKING_MS = 100L

    @Test
    fun `Default 풀은 코어 수만큼만 동시 → 느림`():Unit = runBlocking {
        val elapsed = measureTimeMillis {
            coroutineScope {
                repeat(WORK_COUNT) {
                    launch(Dispatchers.Default) {
                        Thread.sleep(BLOCKING_MS) // 일부러 블로킹 -> 스레드 양보 안 함
                    }
                }
            }
        }

        println("Default: ${elapsed}ms")
        elapsed shouldBeGreaterThanOrEqual 300L
    }

    @Test
    fun `IO 풀은 충분히 많은 스레드 → 빠름`():Unit = runBlocking {
        val elapsed = measureTimeMillis {
            coroutineScope {
                repeat(WORK_COUNT) {
                    launch(Dispatchers.IO) {
                        Thread.sleep(BLOCKING_MS)
                    }
                }
            }
        }
        println("IO: ${elapsed}ms")
        elapsed shouldBeLessThan 400L
    }

}