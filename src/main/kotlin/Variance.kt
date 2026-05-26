open class Animal(val name: String)
class Cat(name: String) : Animal(name)      // : Animal(name) 으로 부모 생성자 호출

class ReadBox<out T>(val value: T) {         // ← out 이 핵심
    fun get(): T = value
}