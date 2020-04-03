package egger.software.kotlinmpp

fun add(inputA: String, inputB: String): String {

    val numberA = inputA.toDoubleOrNull() ?: 0.0
    val numberB = inputB.toDoubleOrNull() ?: 0.0
    val resultValue = numberA + numberB
    return "The result is: ${resultValue}"

}
