package egger.software.kotlinmpp.libgol

expect class GolTimer(action: () -> Unit) {
    fun stop()
}
