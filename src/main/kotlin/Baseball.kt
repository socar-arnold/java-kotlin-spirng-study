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