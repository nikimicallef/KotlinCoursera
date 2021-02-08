package week2.assignment

import kotlin.math.min

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var rightPosition = 0;
    var wrongPosition = 0;

    val unmatchedSecretCharacters = mutableMapOf<Char, Int>();
    val unmatchedGuessCharacters = mutableMapOf<Char, Int>();

    for(index in guess.indices) {
        val guessedChar : Char = guess[index]
        val secretChar : Char = secret[index]

        if(secretChar == guessedChar) {
            rightPosition += 1;
        } else {
            if(unmatchedSecretCharacters.containsKey(secretChar)){
                unmatchedSecretCharacters.replace(secretChar, unmatchedSecretCharacters.getValue(secretChar)+1)
            } else {
                unmatchedSecretCharacters[secretChar] = 1
            }

            if(unmatchedGuessCharacters.containsKey(guessedChar)){
                unmatchedGuessCharacters.replace(guessedChar, unmatchedGuessCharacters.getValue(guessedChar)+1)
            } else {
                unmatchedGuessCharacters[guessedChar] = 1
            }
        }

    }

    for((char, times) in unmatchedGuessCharacters) {
        val charOccurrencesInSecret = unmatchedSecretCharacters[char]

        if(charOccurrencesInSecret != null) {
            wrongPosition += min(charOccurrencesInSecret, times)
        }
    }

    return Evaluation(rightPosition, wrongPosition)
}
