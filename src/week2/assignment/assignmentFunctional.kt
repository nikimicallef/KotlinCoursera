package week2.assignment

data class EvaluationFunctional(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuessFunctional(secret: String, guess: String): EvaluationFunctional {

    val rightPositions = secret.zip(guess).count { (secret, guess) -> secret == guess}

    val commonLetters = "ABCDEF".sumBy { ch ->

        Math.min(secret.count { char -> ch == char }, guess.count { char -> ch == char })
    }
    return EvaluationFunctional(rightPositions, commonLetters - rightPositions)
}

fun main(args: Array<String>) {
    val result = EvaluationFunctional(rightPosition = 1, wrongPosition = 1)
    evaluateGuessFunctional("BCDF", "ACEB") eq result
    evaluateGuessFunctional("AAAF", "ABCA") eq result
    evaluateGuessFunctional("ABCA", "AAAF") eq result
}

infix fun <T> T.eq(other: T) {
    if (this == other) println("OK")
    else println("Error: $this != $other")
}
