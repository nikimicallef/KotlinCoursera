package week5.assignment.game2048

import board.Cell
import board.Direction
import board.Direction.*
import board.GameBoard
import board.createGameBoard
import week5.assignment.game.Game

/*
 * Your task is to implement the game 2048 https://en.wikipedia.org/wiki/2048_(video_game).
 * Implement the utility methods below.
 *
 * After implementing it you can try to play the game running 'PlayGame2048'.
 */
fun newGame2048(initializer: Game2048Initializer<Int> = RandomGame2048Initializer): Game =
    Game2048(initializer)

class Game2048(private val initializer: Game2048Initializer<Int>) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        repeat(2) {
            board.addNewValue(initializer)
        }
    }

    override fun canMove() = board.any { it == null }

    override fun hasWon() = board.any { it == 2048 }

    override fun processMove(direction: Direction) {
        if (board.moveValues(direction)) {
            board.addNewValue(initializer)
        }
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }
}

/*
 * Add a new value produced by 'initializer' to a specified cell in a board.
 */
fun GameBoard<Int?>.addNewValue(initializer: Game2048Initializer<Int>) =
    initializer.nextValue(this)?.let {
        this[it.first] = it.second
    }

/*
 * Update the values stored in a board,
 * so that the values were "moved" in a specified rowOrColumn only.
 * Use the helper function 'moveAndMergeEqual' (in Game2048Helper.kt).
 * The values should be moved to the beginning of the row (or column),
 * in the same manner as in the function 'moveAndMergeEqual'.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValuesInRowOrColumn(rowOrColumn: List<Cell>): Boolean {
    val originalRowOrColumn: List<Int?> = rowOrColumn
        .map { this[it] }
        .toList()

    val itemsToUpdate: List<Pair<Int?, Cell>> = originalRowOrColumn
        .moveAndMergeEqual { item -> item + item }
        .zipLongest(rowOrColumn)
        .toList()



    itemsToUpdate
        .forEach { (value, cell) -> this[cell] = value }

    return itemsToUpdate.map { it.first }.toList() != originalRowOrColumn
}

// Works only for this example when we know that R is always longer than T
fun <T, R> List<T>.zipLongest(other: List<R>): List<Pair<T?, R>> {
    val first = iterator()
    val second = other.iterator()

    return (other.indices)
        .map { (if (first.hasNext()) first.next() else null) to second.next() }
        .toList()
}

/*
 * Update the values stored in a board,
 * so that the values were "moved" to the specified direction
 * following the rules of the 2048 game .
 * Use the 'moveValuesInRowOrColumn' function above.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValues(direction: Direction): Boolean {
    return when (direction) {
        UP -> moveAllRows(1..this.width, 1..this.width)
        DOWN -> moveAllRows(this.width downTo 1, 1..this.width)
        LEFT -> moveAllColumns(1..this.width, 1..this.width)
        RIGHT -> moveAllColumns(1..this.width, this.width downTo 1)
    }
}

private fun GameBoard<Int?>.moveAllColumns(iRange : IntProgression, jRange : IntProgression): Boolean {
    return (iRange)
        .map { i -> (jRange).map { Cell(i, it) }.toList() }
        .map { moveValuesInRowOrColumn(it) }
        .any { it }
}

private fun GameBoard<Int?>.moveAllRows(iRange : IntProgression, jRange : IntProgression): Boolean {
    return (jRange)
        .map { j -> (iRange).map { Cell(it, j) }.toList() }
        .map { moveValuesInRowOrColumn(it) }
        .any { it }
}
