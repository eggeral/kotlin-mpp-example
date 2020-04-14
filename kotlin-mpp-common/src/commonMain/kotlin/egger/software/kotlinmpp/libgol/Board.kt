package egger.software.kotlinmpp.libgol

data class Position2d(val column: Int, val row: Int)

class Board(val columns: Int, val rows: Int) {

    var cellSize = 15.0f
    var minCellSize = 7.0f
    var maxCellSize = 60.0f
    var cellPadding = 3.0f

    fun scale(scaleFactor: Float) {
        val newCellSize = cellSize * scaleFactor

        cellSize = when {
            newCellSize < minCellSize -> minCellSize
            newCellSize > maxCellSize -> maxCellSize
            else -> newCellSize
        }
    }

    private val cells: Array<Array<Cell>> = Array(rows) { Array(columns) { Cell(false) } }

    fun calculateNextGeneration() {

        for ((rowIdx, row) in cells.withIndex()) {
            for ((colIdx, cell) in row.withIndex()) {
                cell.livingNeighbours = countLivingNeighbours(colIdx, rowIdx)
            }
        }

        for (row in cells) {
            for (cell in row) {
                cell.calculateNextGeneration()
            }
        }

    }

    fun cellAt(column: Int, row: Int) = cells[row][column]
    private fun cellAt(position2d: Position2d) = cells[position2d.row][position2d.column]

    fun setCells(cellDefinitions: Map<Position2d, Boolean>) {
        for (cellDefinition in cellDefinitions.entries) {
            cellAt(cellDefinition.key).alive = cellDefinition.value
        }
    }

    private fun cellIsInsideBoardAndAlive(column: Int, row: Int) =
        column >= 0 && row >= 0 && column < columns && row < rows && cells[row][column].alive

    fun countLivingNeighbours(column: Int, row: Int): Int {
        var count = 0

        for (currentRow in row - 1..row + 1) { // fortunately the Kotlin compiler does not create Range objects in this case!
            for (currentColumn in column - 1..column + 1) {
                if (cellIsInsideBoardAndAlive(
                        currentColumn,
                        currentRow
                    ) && !(currentRow == row && currentColumn == column)
                ) // do not count the cell itself
                    count++
            }
        }
        return count

    }

}

fun String.cells(): Map<Position2d, Boolean> {
    val result = mutableMapOf<Position2d, Boolean>()
    for ((row, line) in this.lines().withIndex()) {
        for ((column, char) in line.withIndex()) {
            result[Position2d(column, row)] = (char == '*')
        }
    }
    return result
}

fun Map<Position2d, Boolean>.translatedTo(column: Int, row: Int): Map<Position2d, Boolean> {
    val result = mutableMapOf<Position2d, Boolean>()
    for (entry in this.entries) {
        result[Position2d(entry.key.column + column, entry.key.row + row)] = entry.value
    }
    return result
}

