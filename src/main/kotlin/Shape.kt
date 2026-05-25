import kotlin.math.PI

sealed class Shape
data class Circle(val radius: Double): Shape()
data class Rectangle(val width: Double, val height: Double): Shape()
data class Triangle(val base: Double, val height: Double): Shape()
fun area(shape: Shape):Double = when(shape) {
    is Circle -> PI * shape.radius * shape.radius
    is Rectangle -> shape.height * shape.width
    is Triangle -> 0.5 * shape.base * shape.height
}