package ir.fatemelyasi.note.view.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    onRemove: (() -> Unit)? = null,
) {
    val typography = LocalCustomTypography.current
    val colors = LocalCustomColors.current

    val backgroundColor = (label.labelColor?.safeHexColor() ?: "#CCCCCC").toComposeColorOr()
    val contentColor = contentColorFor(backgroundColor)

    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 2.dp,
        border = BorderStroke(1.dp, colors.outline),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .border(1.dp, colors.outline, CircleShape)
                    .background(
                        color = (label.labelColor?.safeHexColor() ?: "#CCCCCC").toComposeColorOr(),
                        shape = CircleShape
                    )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = label.labelName ?: "",
                style = typography.titleSmall,
                color = contentColor
            )

            onRemove?.let {
                Spacer(modifier = Modifier.width(8.dp))
                androidx.compose.material3.Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Close,
                    contentDescription = "Remove Label",
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { it() },
                    tint = contentColor
                )
            }
        }
    }
}
