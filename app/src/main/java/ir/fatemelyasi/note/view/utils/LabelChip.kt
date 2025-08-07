package ir.fatemelyasi.note.view.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.fatemelyasi.note.view.ui.theme.LocalCustomTypography
import ir.fatemelyasi.note.view.viewEntity.LabelViewEntity

@Composable
fun LabelChip(
    label: LabelViewEntity,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val backgroundColor = when {
        isSelected -> androidx.compose.ui.graphics.Color.Gray
        else -> (label.labelColor?.safeHexColor() ?: "#CCCCCC").toComposeColorOr()
    }
    val contentColor = contentColorFor(backgroundColor)

    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 2.dp,
        modifier = if (onClick != null) modifier.clickable { onClick() } else modifier
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = contentColor
                )
                Spacer(modifier = Modifier.width(6.dp))
            }

            Text(
                text = label.labelName ?: "",
                style = LocalCustomTypography.current.bodySmall,
                color = contentColor
            )
        }
    }
}
