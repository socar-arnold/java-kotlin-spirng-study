import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope

interface FeedClient {
    suspend fun fetch(url: String): String
}

data class Feed(val source: String, val items: List<String>)

fun parseFeed(source: String, xml:String): Feed {
    val titles = Regex("<title>(.*?)</title>", RegexOption.DOT_MATCHES_ALL).findAll(xml).map{it.groupValues[1]}.toList()
    return Feed(source = source, items = titles.drop(1))
}

class RssReader (
    private val client: FeedClient
) {
    suspend fun readAll(urls: List<String>): List<Feed> = supervisorScope {
        urls.map {url -> async {parseFeed(url, client.fetch(url))}}
    }.mapNotNull { deferred ->
        try {deferred.await()}
        catch(e: Exception){null}
    }
}