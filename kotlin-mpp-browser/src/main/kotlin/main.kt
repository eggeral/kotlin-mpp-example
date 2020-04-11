import egger.software.kotlinmpp.libgol.Game
import egger.software.kotlinmpp.libgol.GolCanvas
import org.w3c.dom.*
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.browser.window
import kotlin.dom.appendElement

fun main() {

    val game = Game()
    val golCanvas = GolCanvas()
    val board = game.board
    val canvasWidth = window.innerWidth
    val canvasHeight = window.innerHeight - 50

    document.body?.appendElement("button") {
        this as HTMLButtonElement
        textContent = "Pause"
        onclick = {
            if (game.running) {
                textContent = "Resume"
                game.pause()
            } else {
                textContent = "Pause"
                game.resume()
            }
        }
    }

    document.body?.appendElement("canvas") {
        this as HTMLCanvasElement
        width = canvasWidth
        height = canvasHeight
        val context = getContext("2d") as CanvasRenderingContext2D

        fun drawBoard() {
            golCanvas.drawBoard(
                board = board,
                clear = {
                    context.fillStyle = "white"
                    context.fillRect(0.0, 0.0, canvasWidth.toDouble(), canvasHeight.toDouble())
                },
                drawRect = { left, top, size ->
                    context.beginPath()
                    context.fillStyle = "gray"
                    context.fillRect(left.toDouble(), top.toDouble(), size.toDouble(), size.toDouble())
                    context.stroke()
                }
            )
        }

        onwheel = { event ->
            val wheelMax = 100f
            val wheelDelta = event.asDynamic().wheelDelta as Float
            val windowedDelta = when {
                wheelDelta > wheelMax -> wheelMax
                wheelDelta < -wheelMax -> -wheelMax
                else -> wheelDelta
            }
            val zoom = (windowedDelta + wheelMax) / wheelMax
            golCanvas.zoom(board, zoom, event.clientX.toFloat(), event.clientY.toFloat())
            drawBoard()
            event.preventDefault()
        }

        addEventListener("touch", { event: Event ->
            event as TouchEvent
            console.log(event)
        })

        game.afterNextGenerationCalculated = { drawBoard() }
        game.resume()
    }
}



