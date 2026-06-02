import io.kotest.matchers.shouldBe
import io.kotest.matchers.ints.shouldBeLessThan
import org.junit.jupiter.api.Test
import kotlin.concurrent.thread

class CounterTest {

    @Test
    fun `UnSafeCounter는 race condition으로 자주 20000 미만이 된다`() {
        var lostCountSeen = false

        repeat(5) {
            val c = UnsafeCounter()
            val t1 = thread { repeat(10_000) { c.increment() } }
            val t2 = thread { repeat(10_000) { c.increment() } }
            t1.join(); t2.join()
            println("Unsafe결과: ${c.value}")
            if(c.value < 20_000) lostCountSeen = true
        }
        lostCountSeen shouldBe true          // 적어도 한 번은 손실 발생해야 race condition 증명

    }

    @Test
    fun `SafeCounter는 항상 정확히 20000`() {
        val c = SafeCounter()
        val t1 = thread { repeat(10_000) { c.increment() } }
        val t2 = thread { repeat(10_000) { c.increment() } }
        t1.join(); t2.join()
        c.get() shouldBe 20_000
    }

    @Test
    fun `AtomicCounter도 항상 정확히 20000`() {
        val c = AtomicCounter()
        val t1 = thread { repeat(10_000) { c.increment() } }
        val t2 = thread { repeat(10_000) { c.increment() } }
        t1.join(); t2.join()
        c.get() shouldBe 20_000
    }
}