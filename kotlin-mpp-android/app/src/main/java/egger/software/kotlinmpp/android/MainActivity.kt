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
    private lateinit var resumeIcon: Drawable
    private lateinit var pauseIcon: Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        resumeIcon = resources.getDrawable(R.drawable.ic_resume_circle_outline_black_24dp, null)
        pauseIcon = resources.getDrawable(R.drawable.ic_pause_circle_outline_black_24dp, null)

        boardView.cellSize = 14.0f * resources.displayMetrics.density
        boardView.board = game.board

        game.afterNextGenerationCalculated = { boardView.invalidate() }
        game.resume()

    }

    override fun onResume() {
        super.onResume()
        game.resume()
    }

    override fun onPause() {
        super.onPause()
        game.pause()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override
    fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_pause_resume -> {
                if (game.running) {
                    item.icon = resumeIcon
                    game.pause()
                } else {
                    item.icon = pauseIcon
                    game.resume()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }


}
