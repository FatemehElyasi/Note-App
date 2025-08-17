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

