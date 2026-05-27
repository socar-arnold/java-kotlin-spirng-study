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