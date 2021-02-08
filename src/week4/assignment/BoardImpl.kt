package board

import board.Direction.*

open class MySquareBoard(private val w: Int) : SquareBoard {
    final override val width: Int
        get() = w

    private val boardCoords: List<List<Cell>> = (1..width).map { i -> (1..width).map { j -> Cell(i, j) }.toList() }.toList()

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return if (i <= 0 || j <= 0 || i > width || j > width) null else boardCoords[i - 1][j - 1]
    }

    override fun getCell(i: Int, j: Int): Cell {
        return getCellOrNull(i, j) ?: throw IllegalArgumentException("Indices must be greater or equal to 1 and smaller or equal to $width")
    }

    override fun getAllCells(): Collection<Cell> {
        return (1..width).flatMap { i -> getRow(i, 1..width) }.toList()
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        return jRange.mapNotNull { index -> getCellOrNull(i, index) }.toList()
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        return iRange.mapNotNull { index -> getCellOrNull(index, j) }.toList()
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction) {
            UP -> getCellOrNull(i - 1, j)
            LEFT -> getCellOrNull(i, j - 1)
            DOWN -> getCellOrNull(i + 1, j)
            RIGHT -> getCellOrNull(i, j + 1)
        }
    }
}

class MyGameBoard<T>(w: Int) : GameBoard<T>, MySquareBoard(w) {

    private val board: MutableMap<Cell, T?> = (1..width)
        .flatMap { i -> (1..width).map { j -> this.getCell(i,j) }.toList() }
        .map { cell -> cell to null }
        .toMap()
        .toMutableMap()

    override fun get(cell: Cell): T? {
        return board[cell]
    }

    override fun set(cell: Cell, value: T?) {
        board[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return board.entries.filter { (_, value) -> predicate(value) }.map { entry -> entry.key }
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return board.entries.find { (_, value) -> predicate(value) }?.key
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return board.any { (_, value) -> predicate(value) }
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return board.all { (_, value) -> predicate(value) }
    }

}

fun createSquareBoard(width: Int): SquareBoard = MySquareBoard(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = MyGameBoard(width)

