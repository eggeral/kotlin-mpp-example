package egger.software.kotlinmpp.libgol

import java.util.*
import kotlin.concurrent.fixedRateTimer

actual class GolTimer actual constructor(action: () -> Unit) {

    private val timer: Timer =
        fixedRateTimer("GolTimer", false, period = 500L) {
            action()
        }

    actual fun stop() {
        timer.cancel()
    }

}
