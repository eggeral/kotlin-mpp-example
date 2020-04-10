package egger.software.kotlinmpp.javafx

import egger.software.kotlinmpp.libgol.Cell
import egger.software.kotlinmpp.libgol.Game
import egger.software.kotlinmpp.libgol.GolCanvas
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
        val gc = canvas.graphicsContext2D

        val golCanvas = GolCanvas(
            board = board,
            clear = {
                gc.clearRect(0.0, 0.0, canvasWidth, canvasHeight)
            },
            drawRect = { left, top, size ->
                gc.fill = Color.GRAY
                gc.fillRect(left.toDouble(), top.toDouble(), size.toDouble(), size.toDouble())
            }
        )
        game.afterNextGenerationCalculated = {
            golCanvas.drawBoard()
        }

        canvas.onZoom = EventHandler { zoomEvent ->
            golCanvas.zoom(
                zoomFactor = zoomEvent.totalZoomFactor.toFloat(),
                xPosition = zoomEvent.x.toFloat(),
                yPosition = zoomEvent.y.toFloat()
            )
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

}
