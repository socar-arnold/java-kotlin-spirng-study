sealed class Fuel {
    abstract val pricePerLiter: Int

    object Gasoline : Fuel() {
        override val pricePerLiter = 1700
    }

    object Diesel : Fuel() {
        override val pricePerLiter = 1500
    }

    object LPG : Fuel() {
        override val pricePerLiter = 900
    }
}

abstract class Car(val name: String, val fuelType: Fuel) {
    var tank: Int = 0
        private set

    fun fillUp(liters:Int, fuel: Fuel) {
        require(liters > 0) { "주유량은 0보다 커야 합니다."}
        require(fuel == fuelType) {"잘못된 연료 타입입니다: $fuel"}

        tank += liters
    }
}

class GasolineCar(name:String) : Car(name, Fuel.Gasoline)
class DieselCar(name:String) : Car(name, Fuel.Diesel)
class LPGCar(name:String) : Car(name, Fuel.LPG)
