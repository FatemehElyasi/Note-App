package ir.fatemelyasi.note.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

data class CustomNoteColors(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val error: Color,
    val onError: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val outline: Color,
    val onSurfaceVariant: Color
)


private val DarkColorScheme = CustomNoteColors(
    primary = Teal200,
    onPrimary = White,
    primaryContainer = black,
    secondary = Teal200,
    onSecondary = black,
    secondaryContainer = Teal200,
    background = DarkGrayBackground,
    onBackground = White,
    surface = black,
    onSurface = White,
    error = Red,
    onError = black,
    outline = GrayText,
    onSurfaceVariant = GrayText,
    onSecondaryContainer = black


)

private val LightColorScheme = CustomNoteColors(
    primary = Teal200,
    onPrimary = black,
    primaryContainer = White,
    secondary = Teal200,
    onSecondary = black,
    secondaryContainer = Teal700,
    background = LightGrayBackground,
    onBackground = black,
    surface = White,
    onSurface = black,
    error = Red,
    onError = White,
    outline = GrayText,
    onSurfaceVariant = GrayText,
    onSecondaryContainer = White
)
val LocalCustomColors = staticCompositionLocalOf<CustomNoteColors> {
    LightColorScheme
}

val LocalCustomTypography = staticCompositionLocalOf<AppTypography> {
    NoteTypography
}

@Composable
fun NoteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(
        LocalCustomColors provides colors,
        LocalCustomTypography provides NoteTypography
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
        ) {
            content()
        }
    }
}