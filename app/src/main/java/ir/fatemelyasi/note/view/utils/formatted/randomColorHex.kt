package ir.fatemelyasi.note.view.utils.formatted

fun randomColorHex(): String {
    val r = (150..255).random()
    val g = (150..255).random()
    val b = (150..255).random()
    return String.format("#%02X%02X%02X", r, g, b)
}
