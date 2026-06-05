import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

data class RaceCar(val name: String, val lapTimeMs: Long)
data class RaceResult(val winner:String, val finishOrder: List<String>)

suspend fun race(cars: List<RaceCar>):RaceResult = coroutineScope {
    // 1. 각 차를 async로 동시 출발 → delay(car.lapTimeMs) 후 car 반환
    // 2. awaitAll()로 다 끝나길 기다림
    // 3. lapTimeMs 오름차순 정렬 → 이름만 추출 → finishOrder
    // 4. 첫 번째가 winner
    val result = cars.map { car -> async { delay(car.lapTimeMs); car } }.awaitAll().sortedBy { car -> car.lapTimeMs }.map{it -> it.name}

    RaceResult(winner = result[0], finishOrder = result)
}