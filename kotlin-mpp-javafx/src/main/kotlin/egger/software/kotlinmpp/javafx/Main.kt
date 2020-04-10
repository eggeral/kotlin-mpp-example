package egger.software.kotlinmpp.javafx

import egger.software.kotlinmpp.libgol.Cell
import egger.software.kotlinmpp.libgol.Game
import javafx.application.Application
import javafx.application.Application.launch
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.control.Button
import javafx.scene.paint.Color
import javafx.stage.Stage

fun main() {
    launch(JavaFXExample::class.java)
}

class JavaFXExample : Application() {
    private val game = Game()
    private val board = game.board
    private val canvasWidth = 400.0
    private val canvasHeight = 700.0

    private var offsetX = 0.0
    private var offsetY = 0.0

    override fun start(primaryStage: Stage) {

        val canvas = Canvas(canvasWidth, canvasHeight)

        game.afterNextGenerationCalculated = {
            drawBoard(canvas)
        }

        canvas.onZoom = EventHandler { zoomEvent ->
            val oldCellSize = board.cellSize
            board.scale(zoomEvent.totalZoomFactor.toFloat())
            val realScaleFactor = board.cellSize / oldCellSize

            val distX = zoomEvent.x - offsetX
            val distY = zoomEvent.y - offsetY

            val corrX = distX - distX * realScaleFactor
            val corrY = distY - distY * realScaleFactor

            offsetX += corrX
            offsetY += corrY
            drawBoard(canvas)
        }

        val layout = VBox().apply {
            val toolbar = HBox().apply {
                val pauseResumeButton = Button("Pause").apply {
                    onAction = EventHandler {
                        if (game.running) {
                            this.text = "Resume"
                            game.pause()
                        } else {
                            this.text = "Pause"
                            game.resume()
                        }
                    }
                }
                children.add(pauseResumeButton)
            }
            children.add(toolbar)
            children.add(canvas)
        }

        primaryStage.run {
            scene = Scene(layout)
            show()
        }

        primaryStage.onCloseRequest = EventHandler {
            game.pause()
            Platform.exit()
        }

        game.resume()
    }

    private fun drawBoard(canvas: Canvas) {
        val gc: GraphicsContext = canvas.graphicsContext2D
        gc.clearRect(0.0, 0.0, canvasWidth, canvasHeight)
        for (rowIdx in 0 until board.rows) {
            for (columnIdx in 0 until board.columns) {
                val cell = board.cellAt(column = columnIdx, row = rowIdx)
                drawCell(canvas, cell, rowIdx, columnIdx)
            }
        }

    }

    private fun Canvas.drawCellAt(rowIdx: Int, columnIdx: Int) {
        val gc: GraphicsContext = graphicsContext2D
        val cellSize = board.cellSize.toDouble()
        val cellPadding = board.cellPadding
        val left = (columnIdx * cellSize) + cellPadding + offsetX
        val top = (rowIdx * cellSize) + cellPadding + offsetY
        gc.fill = Color.GRAY
        gc.fillRect(left, top, cellSize - cellPadding, cellSize - cellPadding)
    }

    private fun drawCell(canvas: Canvas, cell: Cell, rowIdx: Int, columnIdx: Int) {
        if (cell.alive) {
            canvas.drawCellAt(rowIdx, columnIdx)
        }
    }

}
