import kotlinx.coroutines.runBlocking
import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import io.kotest.matchers.ints.shouldBeLessThan
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class CoroutineTest {
    @Test
    fun `순차 실행은 두 작업 시간 합만큼 걸린다` (): Unit= runBlocking {
        val elapsed = measureTimeMillis {
            val user = fetchUser() // 1초
            val posts = fetchPosts() // 1초
        }
        println("순차: ${elapsed}ms")
        elapsed.toInt() shouldBeGreaterThanOrEqual 2000 // ~2000ms
    }

    @Test
    fun `병렬 실행은 더 오래 걸리는 작업 하나만큼만`(): Unit = runBlocking {
        val elapsed = measureTimeMillis {
            loadParallel()
        }
        println("병렬: ${elapsed}ms")
        elapsed.toInt() shouldBeLessThan 1500 // ~1000ms(오버헤드 500ms 추가)
    }
}