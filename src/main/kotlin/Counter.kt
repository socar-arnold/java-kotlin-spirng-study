import java.util.concurrent.atomic.AtomicInteger

class UnsafeCounter {
    var value = 0
    fun increment() { value++}
}

class SafeCounter {
    private var value = 0
    @Synchronized fun increment() { value++}
    @Synchronized fun get(): Int = value
}

class AtomicCounter {
    private val value = AtomicInteger(0)
    fun increment() { value.incrementAndGet()}
    fun get(): Int = value.get()
}