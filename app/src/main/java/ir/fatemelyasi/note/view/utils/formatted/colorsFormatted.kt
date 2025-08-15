package ir.fatemelyasi.note.view.utils.formatted

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

fun String.safeHexColor(): String {
    return if (this.startsWith("#")) this else "#$this"
}

fun String.toComposeColorOr(default: Color = Color(0xFFCCCCCC)): Color {
    return try {
        Color(this.toColorInt())
    } catch (_: Exception) {
        default
    }
}
fun Color.toHex(): String {
    return String.format("#%02X%02X%02X",
        (red * 255).toInt(),
        (green * 255).toInt(),
        (blue * 255).toInt()
    )
}
