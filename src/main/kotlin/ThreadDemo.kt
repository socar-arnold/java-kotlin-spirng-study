import kotlin.concurrent.thread

fun main() {
    println("[메인] 시작 (${Thread.currentThread().name})")

    val workers = (1..3).map{id ->
        thread {
            repeat(3) {step ->
                println("[워커-$id] 단계 $step (${Thread.currentThread().name})")
                Thread.sleep(100)
            }
        }
    }

    println("[메인] 워커 3개 띄움, 끝나길 기다림")
    workers.forEach { it.join() }
    println("[메인] 모든 워커 종료")
}