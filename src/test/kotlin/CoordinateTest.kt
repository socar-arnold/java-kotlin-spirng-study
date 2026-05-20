import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class CoordinateTest: StringSpec({
    "Points는 x,y 좌표를 가진다" {
        val point = Point(3,4)
        point.x shouldBe 3
        point.y shouldBe 4
    }
    "Point 좌표가 0-24  범위를 벗어나면 예외가 발생한다" {
        shouldThrow<IllegalArgumentException> {
            Point(-1,0)
        }
    }
    "Line은 두 점 사이의 거리를 반환한다." {
        val line = Line(Point(0,0), Point(3,4))
        line.measure() shouldBe 5.0
    }
    "Triangle은 세 점으로 넓이를 반환하다" {
        val triangle = Triangle(Point(0,0), Point(4,0), Point(0,3))
        triangle.measure() shouldBe 6.0
    }
    "Rectangle은 네 점으로 넓이를 반환하다" {
        val rectangle = Rectangle(Point(0,0), Point(4,0), Point(4,3), Point(0,3))
        rectangle.measure() shouldBe 12.0
    }
    "직사각형이 아닌 네 점이면 예외가 발생한다" {
        shouldThrow<IllegalArgumentException> {
            Rectangle(Point(0, 0), Point(4, 0), Point(4, 3), Point(1, 3))
        }
    }
})