package egger.software.kotlinmpp.libgol

class Game {

    var afterNextGenerationCalculated: () -> Unit = {}
    var running = false
    val board: Board
    private var timer: GolTimer? = null

    init {

        val columns = 30
        val rows = 50
        board = Board(columns, rows)
        board.setCells(
            """
                ***_*
                *____
                ___**
                _**_*
                *_*_*
                """.trimIndent().cells().translatedTo(columns / 2 - 2, rows / 2 - 2)
        )

    }

    fun resume() {
        if (timer != null) return
        timer = GolTimer {
            board.calculateNextGeneration()
            afterNextGenerationCalculated()
        }
        running = true
    }

    fun pause() {
        timer?.stop()
        timer = null
        running = false
    }

}
