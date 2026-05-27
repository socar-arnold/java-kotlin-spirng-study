data class Car(val name: String, val position: Int = 0) {
    fun move(value: Int): Car {
        if(value >= 4) return copy(position = position + 1)
        else return this
    }
}

fun winners(carList: List<Car>): List<String> {
    val max = carList.maxOf { it.position }
    return carList.filter{it.position == max}.map { it.name }
}

fun playRound(cars: List<Car>, values: List<Int>): List<Car> {
    return cars.zip(values) { car, value -> car.move(value)}
}

fun main() {
    print("자동차 이름을 쉼표(,)로 입력: ")
    val names = readln().split(",").map { it.trim() }
    print("시도 횟수: ")
    val rounds = readln().trim().toInt()

    var cars = names.map { Car(it) }                      // 이름들로 차 생성 (position=0)
    repeat(rounds) {
        val values = cars.map { (0..9).random() }         // 차마다 0~9 랜덤
        cars = playRound(cars, values)                    // 한 라운드 전진
        cars.forEach { println("${it.name} : ${"-".repeat(it.position)}") }  // 막대로 표시
        println()
    }
    println("🏆 우승자: ${winners(cars).joinToString(", ")}")
}