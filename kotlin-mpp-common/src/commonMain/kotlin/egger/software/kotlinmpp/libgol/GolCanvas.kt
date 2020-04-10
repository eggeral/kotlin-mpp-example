package egger.software.kotlinmpp.libgol

class GolCanvas {
    private var offsetX = 0.0f
    private var offsetY = 0.0f

    fun drawBoard(
        board: Board,
        drawRect: (left: Float, top: Float, size: Float) -> Unit,
        clear: () -> Unit = {}
    ) {
        clear()
        for (rowIdx in 0 until board.rows) {
            for (columnIdx in 0 until board.columns) {
                val cell = board.cellAt(column = columnIdx, row = rowIdx)
                if (cell.alive) {
                    val cellSize = board.cellSize
                    val cellPadding = board.cellPadding
                    val left = (columnIdx * cellSize) + cellPadding + offsetX
                    val top = (rowIdx * cellSize) + cellPadding + offsetY
                    drawRect(left, top, cellSize - cellPadding)
                }
            }
        }
    }

    fun zoom(board: Board, zoomFactor: Float, xPosition: Float, yPosition: Float) {
        val oldCellSize = board.cellSize
        board.scale(zoomFactor)
        val realScaleFactor = board.cellSize / oldCellSize

        val distX = xPosition - offsetX
        val distY = yPosition - offsetY

        val corrX = distX - distX * realScaleFactor
        val corrY = distY - distY * realScaleFactor

        offsetX += corrX
        offsetY += corrY
    }


}
