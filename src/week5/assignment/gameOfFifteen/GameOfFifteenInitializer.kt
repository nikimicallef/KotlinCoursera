package week5.assignment.gameOfFifteen

interface GameOfFifteenInitializer {
    /*
     * Even permutation of numbers 1..15
     * used to initialized the first 15 cells on a board.
     * The last cell is empty.
     */
    val initialPermutation: List<Int>
}

class RandomGameInitializer : GameOfFifteenInitializer {
    /*
     * Generate a random permutation from 1 to 15.
     * `shuffled()` function might be helpful.
     * If the permutation is not even, make it even (for instance,
     * by swapping two numbers).
     */
    override val initialPermutation by lazy {
        val shuffledListOfNumbers: MutableList<Int> = (1..15).shuffled().toMutableList()

        if(!isEven(shuffledListOfNumbers)) {
            val v0 = shuffledListOfNumbers[0]
            val v1 = shuffledListOfNumbers[1]

            shuffledListOfNumbers[0] = v1
            shuffledListOfNumbers[1] = v0
        }

        return@lazy shuffledListOfNumbers
    }
}

