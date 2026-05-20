import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class FuelTest: StringSpec({
    "휘발유 차량에 휘발유를 주유하면 tank가 증가한다" {
        val car = GasolineCar("소나타")
        car.fillUp(30, Fuel.Gasoline)
        car.tank shouldBe 30
    }
    "휘발유 차량에 경유를 넣으면 예외가 발생한다" {
        val car = GasolineCar("소나타")
        shouldThrow<IllegalArgumentException> {
            car.fillUp(30, Fuel.Diesel)
        }
    }
    "주유량이 0 이하면 예외가 발생한다" {
        val car = GasolineCar("소나타")
        shouldThrow<IllegalArgumentException> {
            car.fillUp(0, Fuel.Gasoline)
        }
    }
    "주유소는 차량 종류에 맞는 연료로 주유한다" {
        val station = GasStation()
        val car = GasolineCar("소나타")
        station.refuel(car, 30)
        car.tank shouldBe 30
    }
    "휘발유 30리터 주유 시 요금은 51000원이다" {
        val station = GasStation()
        val car = GasolineCar("소나타")
        station.refuel(car, 30) shouldBe 51000
    }
})