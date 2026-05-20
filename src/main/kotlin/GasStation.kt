class GasStation {
    fun refuel(car: Car, liters: Int): Int {
        car.fillUp(liters, car.fuelType)
        return car.fuelType.pricePerLiter * liters
    }
}