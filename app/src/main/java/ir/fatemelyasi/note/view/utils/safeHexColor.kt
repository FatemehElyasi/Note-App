package ir.fatemelyasi.note.view.utils

import androidx.compose.ui.graphics.Color

fun String.safeHexColor(): String {
    return if (this.startsWith("#")) this else "#$this"
}

fun String.toComposeColorOr(default: Color = Color(0xFFCCCCCC)): Color {
    return try {
        Color(android.graphics.Color.parseColor(this))
    } catch (e: Exception) {
        default
    }
}
