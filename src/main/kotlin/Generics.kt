// 1. 제네릭 함수: 아무 타입 리스트나 받아 첫 요소 반환
fun <T> first(list: List<T>): T {
    return list[0]
}

// 2. 제네릭 클래스: 아무 타입이나 담는 상자
class Box<T>(val value: T) {
    fun get(): T {
        return value
    }
}
