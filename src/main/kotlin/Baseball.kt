fun countStrikes (answer: List<Int>, guess: List<Int>): Int {
    return answer.indices.count {
        answer[it] == guess[it]
    }
}

fun countBalls(answer: List<Int>, guess: List<Int>):Int {
    return guess.indices.count {
        i -> guess[i] in answer && answer[i] != guess[i]
    }
}

data class Result(val strikes: Int, val balls: Int)

fun judge(answer: List<Int>, guess: List<Int>): Result {
    return Result(countStrikes(answer, guess), countBalls(answer, guess))
}

fun validateGuess(guess :List<Int>) {
    require(guess.size == 3) { "3자리여야 함"}
    require(guess.all {it in 1..9}) {"1~9만"}
    require(!hasDuplicate(guess)) {"중복 불가"}
}

fun generateAnswer(): List<Int> {
    return (1..9).shuffled().take(3)
}

class Game(private val answer: List<Int>) {
    fun play(guess: List<Int>): Result {
        validateGuess(guess)
        return judge(answer, guess)
    }
}

fun isWin(result: Result): Boolean {
    return result.strikes  == 3
}

fun main() {
    val game = Game(generateAnswer())

    println("⚾ 숫자야구 시작! 1~9, 중복 없는 3자리를 입력하세요.")
    while(true) {
        print("입력: ")
        val input = readln().trim()
        val guess = try {
            input.map { it.digitToInt()}
        } catch(e: Exception) {
            println("숫자만 입력하세요."); continue
        }

        val result = try {
            game.play(guess)
        } catch(e: IllegalArgumentException) {
            println("잘못된 입력: ${e.message}"); continue
        }

        when {
            isWin(result) -> { println("🎉 정답입니다!"); break }
            result.strikes == 0 && result.balls == 0 -> println("낫싱")
            else -> println("${result.strikes} 스트라이크 ${result.balls} 볼")

        }
    }
}