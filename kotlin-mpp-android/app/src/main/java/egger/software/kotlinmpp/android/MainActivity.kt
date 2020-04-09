package egger.software.kotlinmpp.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import egger.software.kotlinmpp.libgol.Game
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val game = Game()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        boardView.cellSize = 14.0f * resources.displayMetrics.density
        boardView.board = game.board

        game.afterNextGenerationCalculated = { boardView.invalidate() }
        game.start()

    }

    override fun onResume() {
        super.onResume()
        game.start()
    }

    override fun onPause() {
        super.onPause()
        game.stop()
    }

}
