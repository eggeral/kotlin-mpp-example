package egger.software.kotlinmpp.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import egger.software.kotlinmpp.libgol.Board
import egger.software.kotlinmpp.libgol.cells
import egger.software.kotlinmpp.libgol.translatedTo
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.fixedRateTimer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val columns = 30
        val rows = 50
        val board = Board(columns, rows)
        board.setCells(
            """
                ***_*
                *____
                ___**
                _**_*
                *_*_*
                """.trimIndent().cells().translatedTo(columns / 2 - 2, rows / 2 - 2))


        boardView.cellSize = 14.0f * resources.displayMetrics.density
        boardView.board = board

        fixedRateTimer("GameLoop", false, period = 500L) {
            board.calculateNextGeneration()
            boardView.invalidate()
        }

    }
}
