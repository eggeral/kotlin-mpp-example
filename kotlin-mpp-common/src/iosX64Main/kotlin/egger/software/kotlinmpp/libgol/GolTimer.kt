package egger.software.kotlinmpp.libgol

import platform.Foundation.NSTimer

actual class GolTimer actual constructor(action: () -> Unit) {

    private val nativeTimer: NSTimer = NSTimer.scheduledTimerWithTimeInterval(1.0, true) {
        action()
    }

    actual fun stop() {
        nativeTimer.invalidate()
    }

}
