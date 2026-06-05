import io.kotest.matchers.shouldBe
import io.kotest.matchers.longs.shouldBeLessThan
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class CoroutineRacingTest {
    @Test
    fun `가장 빠른 차가 우승한다`():Unit = runBlocking {
        val cars = listOf(
            RaceCar("느림이", 500),
            RaceCar("빨라요", 100),
            RaceCar("보통", 300),
        )
        val result = race(cars)
        result.winner shouldBe "빨라요"
    }

    @Test
    fun `완주 순서가 lap time 오름차순이다`():Unit = runBlocking {
        val cars = listOf(
            RaceCar("c", 300),
            RaceCar("a", 100),
            RaceCar("b", 200),
        )
        val result = race(cars)
        result.finishOrder shouldBe listOf("a", "b", "c")
    }

    @Test
    fun `총 소요시간은 가장 느린 차 시간과 비슷 (병렬 증명)`(): Unit = runBlocking {
        val cars = listOf(
            RaceCar("a", 300),
            RaceCar("b", 500),
            RaceCar("c", 800),
        )
        val elapsed = measureTimeMillis { race(cars) }
        println("총 시간: ${elapsed}ms (직렬이면 ~1600ms, 병렬이면 ~800ms)")
        elapsed shouldBeLessThan 1200L      // 800 + 오버헤드 < 1200
        //   ↑ 만약 직렬이었다면 1600ms 이상 나와야 함 → 이 테스트가 병렬을 보장
    }
}