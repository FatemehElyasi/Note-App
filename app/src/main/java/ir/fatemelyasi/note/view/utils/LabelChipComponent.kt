package ir.fatemelyasi.note.view.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ir.fatemelyasi.note.view.ui.theme.LocalCustomColors
import ir.fatemelyasi.note.view.ui.theme.LocalCustomTypography
import ir.fatemelyasi.note.view.utils.formatted.safeHexColor
import ir.fatemelyasi.note.view.utils.formatted.toComposeColorOr
import ir.fatemelyasi.note.view.viewEntity.LabelViewEntity

@Composable
fun LabelChipComponent(
    label: LabelViewEntity,
    modifier: Modifier = Modifier,
) {
    val typography = LocalCustomTypography.current
    val colors = LocalCustomColors.current

    val backgroundColor = (label.labelColor?.safeHexColor() ?: "#CCCCCC").toComposeColorOr()
    val contentColor = contentColorFor(backgroundColor)

    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 2.dp,
        border = BorderStroke(
            width = 1.dp,
            color = colors.outline
        ),
        modifier = modifier
    ) {
        Text(
            text = label.labelName ?: "",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                ),
            style = typography.labelMedium
        )
    }
}
