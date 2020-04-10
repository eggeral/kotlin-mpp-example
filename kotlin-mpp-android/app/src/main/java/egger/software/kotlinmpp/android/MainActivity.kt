package egger.software.kotlinmpp.android

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import egger.software.kotlinmpp.libgol.Game
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val game = Game()
    private lateinit var playIcon: Drawable
    private lateinit var pauseIcon: Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        playIcon = resources.getDrawable(R.drawable.ic_play_circle_outline_black_24dp, null)
        pauseIcon = resources.getDrawable(R.drawable.ic_pause_circle_outline_black_24dp, null)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override
    fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_start_stop -> {
                if (game.running) {
                    item.icon = playIcon
                    game.stop()
                } else {
                    item.icon = pauseIcon
                    game.start()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }


}
