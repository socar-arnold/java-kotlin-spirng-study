import kotlin.reflect.full.memberProperties

data class Demo(val name: String, val count: Int)

fun main() {
    val d = Demo("kotlin", 42)
    println("클래스 ${d::class.simpleName}")
    d::class.memberProperties.forEach{
        println("  ${it.name} = ${it.getter.call(d)}")

    }
}