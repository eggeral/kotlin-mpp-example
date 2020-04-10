package egger.software.kotlinmpp.libgol

class GolCanvas(
    val board: Board,
    val clear: () -> Unit,
    val drawRect: (left: Float, top: Float, size: Float) -> Unit
) {
    private var offsetX = 0.0f
    private var offsetY = 0.0f

    fun drawBoard() {
        clear()
        for (rowIdx in 0 until board.rows) {
            for (columnIdx in 0 until board.columns) {
                val cell = board.cellAt(column = columnIdx, row = rowIdx)
                if (cell.alive) {
                    drawCellAt(rowIdx, columnIdx)
                }
            }
        }
    }

    fun zoom(zoomFactor: Float, xPosition: Float, yPosition: Float) {
        val oldCellSize = board.cellSize
        board.scale(zoomFactor)
        val realScaleFactor = board.cellSize / oldCellSize

        val distX = xPosition - offsetX
        val distY = yPosition - offsetY

        val corrX = distX - distX * realScaleFactor
        val corrY = distY - distY * realScaleFactor

        offsetX += corrX
        offsetY += corrY
        drawBoard()
    }

    private fun drawCellAt(rowIdx: Int, columnIdx: Int) {
        val cellSize = board.cellSize
        val cellPadding = board.cellPadding
        val left = (columnIdx * cellSize) + cellPadding + offsetX
        val top = (rowIdx * cellSize) + cellPadding + offsetY
        drawRect(left, top, cellSize - cellPadding)
    }


}
