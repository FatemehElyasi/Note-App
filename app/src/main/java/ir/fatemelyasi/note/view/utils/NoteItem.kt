package ir.fatemelyasi.note.view.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import ir.fatemelyasi.note.view.ui.theme.LocalCustomColors
import ir.fatemelyasi.note.view.ui.theme.LocalCustomTypography
import ir.fatemelyasi.note.view.viewEntity.NoteViewEntity

@Composable
fun NoteItem(
    note: NoteViewEntity,
    onClick: () -> Unit
) {
    val colors = LocalCustomColors.current
    val typography = LocalCustomTypography.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
        ) {
            // Note Image (if available)
            if (note.image != null) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = note.image
                    ),
                    contentDescription = "Note Image",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = note.title,
                    style = typography.headlineSmall
                )

                Text(
                    text = note.description ?: "",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = typography.displaySmall
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Updated: ${note.updatedAt}",
                    style = typography.titleSmall
                )
            }
        }
    }
}