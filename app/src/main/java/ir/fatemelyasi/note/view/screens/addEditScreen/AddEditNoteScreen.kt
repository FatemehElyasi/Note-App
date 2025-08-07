package ir.fatemelyasi.note.view.screens.addEditScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ir.fatemelyasi.note.view.utils.LabelChip
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    noteId: Int?,
    onBack: () -> Unit,
    viewModel: AddEditNoteViewModel = koinViewModel(),
) {
    val state = viewModel.state

    LaunchedEffect(noteId) {
        noteId?.let { viewModel.loadNote(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (noteId == null) "Add Note" else "Edit Note") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.saveNote(); onBack() }) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = state.title,
                onValueChange = viewModel::onTitleChange,
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.description,
                onValueChange = viewModel::onDescriptionChange,
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            if (state.image != null) {
                AsyncImage(
                    model = state.image,
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
            } else {
                Button(onClick = {
                /* image picker */
                }) {
                    Text("Add Image")
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Favorite:", fontWeight = FontWeight.Bold)
                Switch(
                    checked = state.isFavorite,
                    onCheckedChange = { viewModel.onFavoriteToggle() }
                )
            }

            Text("Labels:", fontWeight = FontWeight.Bold)

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(state.labels) { label ->
                    LabelChip(
                        label = label,
                        onClick = { viewModel.onLabelToggle(label) },
                        isSelected = true
                    )
                }
            }
        }
    }
}


