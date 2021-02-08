package week5.assignment.gameOfFifteen

import board.Cell
import board.Direction
import board.Direction.*
import board.createGameBoard
import week5.assignment.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        val iterator: Iterator<Int> = initializer.initialPermutation.iterator()
        (1..board.width).forEach { iIndex -> (1..board.width).forEach { jIndex -> board[Cell(iIndex, jIndex)] = if (iterator.hasNext()) iterator.next() else null } }
    }

    override fun canMove(): Boolean = board.any { it == null }

    override fun hasWon(): Boolean {
        return (1..15)
            .map { index -> this.board[Cell(((index - 1) / 4) + 1, ((index - 1) % 4) + 1)] == index }
            .all { it }
    }

    override fun processMove(direction: Direction) {
        val nullCell = board.find { v -> v == null } ?: throw IllegalArgumentException("Could not find null value")

        when (direction) {
            UP -> {
                if (nullCell.i + 1 <= board.width) {
                    board[Cell(nullCell.i, nullCell.j)] = board[Cell(nullCell.i + 1, nullCell.j)]
                    board[Cell(nullCell.i + 1, nullCell.j)] = null
                }
            }
            DOWN -> {
                if (nullCell.i - 1 >= 1) {
                    board[Cell(nullCell.i, nullCell.j)] = board[Cell(nullCell.i - 1, nullCell.j)]
                    board[Cell(nullCell.i - 1, nullCell.j)] = null
                }
            }
            LEFT -> {
                if (nullCell.j + 1 <= board.width) {
                    board[Cell(nullCell.i, nullCell.j)] = board[Cell(nullCell.i, nullCell.j + 1)]
                    board[Cell(nullCell.i, nullCell.j + 1)] = null
                }
            }
            RIGHT -> {
                if (nullCell.j - 1 >= 1) {
                    board[Cell(nullCell.i, nullCell.j)] = board[Cell(nullCell.i, nullCell.j - 1)]
                    board[Cell(nullCell.i, nullCell.j - 1)] = null
                }
            }
        }
    }

    override fun get(i: Int, j: Int): Int? = board[Cell(i, j)]

}
