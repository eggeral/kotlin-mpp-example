package egger.software.kotlinmpp.libgol

import kotlin.browser.window

actual class GolTimer actual constructor(action: () -> Unit) {

    private val timer = window.setInterval({ action() }, 500)

    actual fun stop() {
        window.clearInterval(timer)
    }
}
