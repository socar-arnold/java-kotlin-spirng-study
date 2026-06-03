import kotlinx.coroutines.*

suspend fun fetchUser():String {
    delay(1000)
    return "Arnold"
}

suspend fun fetchPosts():List<String> {
    delay(1000)
    return listOf("post1", "post2")
}

//병렬 실행: async + await

suspend fun loadParallel():Pair<String, List<String>> = coroutineScope {
    val user = async {fetchUser()}
    val posts = async{fetchPosts()}

    user.await() to posts.await()
}