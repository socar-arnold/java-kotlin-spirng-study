import io.kotest.matchers.shouldBe
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.longs.shouldBeLessThan
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class RssReaderTest {
    class FakeClient (
        private val responses: Map<String,String>,
        private val delayMs: Long = 0,
        private val failingUrls: Set<String> = emptySet(),
    ): FeedClient{
        override suspend fun fetch(url:String): String {
            delay(delayMs)
            if(url in failingUrls) throw RuntimeException("fetch failed: $url")
            return responses[url] ?: throw IllegalArgumentException("unknown: $url")
        }
    }

    private fun rss(feedTitle: String, vararg items: String): String = """
        <rss><channel>
            <title>$feedTitle</title>
            ${items.joinToString("") { "<item><title>$it</title></item>" }}
        </channel></rss>
    """.trimIndent()

    @Test
    fun `여러 피드를 병렬로 읽어 모든 아이템을 모은다`():Unit = runBlocking {
        val client = FakeClient(mapOf(
            "a" to rss("FeedA", "a1", "a2"),
            "b" to rss("FeedB", "b1"),
        ))
        val feeds = RssReader(client).readAll(listOf("a", "b"))

        feeds.flatMap { it.items } shouldContainExactlyInAnyOrder listOf("a1", "a2", "b1")
    }

    @Test
    fun `한 피드가 실패해도 나머지는 살아남는다 (supervisorScope)`(): Unit = runBlocking {
        val client = FakeClient(
            responses = mapOf("a" to rss("FeedA", "a1"), "c" to rss("FeedC", "c1")),
            failingUrls = setOf("b"),                   // b는 throw
        )
        val feeds = RssReader(client).readAll(listOf("a", "b", "c"))

        feeds.map { it.source } shouldContainExactlyInAnyOrder listOf("a", "c")
        // b는 빠지지만, 다른 둘은 살아남음 → 격리 증명
    }

    @Test
    fun `여러 피드를 병렬로 가져온다 (시간 측정)`(): Unit = runBlocking {
        val client = FakeClient(
            responses = mapOf("a" to rss("A","a1"), "b" to rss("B","b1"), "c" to rss("C","c1")),
            delayMs = 300,                              // 각 fetch 300ms
        )
        val elapsed = measureTimeMillis { RssReader(client).readAll(listOf("a","b","c")) }
        println("3개 피드 fetch: ${elapsed}ms (직렬이면 ~900ms, 병렬이면 ~300ms)")
        elapsed shouldBeLessThan 600L                   // 병렬 증명
    }
}