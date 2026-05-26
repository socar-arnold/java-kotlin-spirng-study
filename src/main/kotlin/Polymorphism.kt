import kotlin.math.PI

interface Shape2 {
    fun area(): Double
}

class Circle2(val radius: Double): Shape2 {
    override fun area(): Double {
        return PI * radius * radius
    }
}


class Rectangle2(val width: Double, val height: Double ): Shape2 {
    override fun area():Double {
        return width * height
    }
}

class Triangle2(val base: Double, val height: Double): Shape2 {
    override fun area(): Double = 0.5 * base * height
}

fun totalArea(shapes: List<Shape2>): Double {
    return shapes.sumOf{ it.area()}
}