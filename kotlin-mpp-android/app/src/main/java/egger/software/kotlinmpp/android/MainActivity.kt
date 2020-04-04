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

        val size = 15
        val board = Board(size, size)
        board.setCells(
            """
                ***_*
                *____
                ___**
                _**_*
                *_*_*
                """.trimIndent().cells().translatedTo(size / 2 - 2, size / 2 - 2))


        boardView.cellSize = 40.0f * resources.displayMetrics.density
        boardView.board = board

        fixedRateTimer("GameLoop", false, period = 500L) {
            board.calculateNextGeneration()
            boardView.invalidate()
        }

    }
}
