package ir.fatemelyasi.note.view.screens.addEditScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ir.fatemelyasi.note.view.ui.theme.LocalCustomColors
import ir.fatemelyasi.note.view.utils.LabelChip
import ir.fatemelyasi.note.view.utils.MessageSnackBarHost
import ir.fatemelyasi.note.view.utils.saveImageToInternalStorage
import ir.fatemelyasi.note.view.viewEntity.LabelViewEntity
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.io.File


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    noteId: Long?,
    onBack: () -> Unit,
    availableLabels: List<LabelViewEntity>,
    viewModel: AddEditNoteViewModel = koinViewModel(),
) {
    val state = viewModel.state
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current
    val colors = LocalCustomColors.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val localPath = saveImageToInternalStorage(context, it)
            localPath?.let { path -> viewModel.onImageChange(path) }
        }
    }

    LaunchedEffect(noteId) {
        noteId?.let {
            viewModel.loadNote(noteId)
        }
    }

    Scaffold(
        snackbarHost = { MessageSnackBarHost(snackBarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(if (noteId == null) "Add Note" else "Edit Note")
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onBack() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.saveNote(
                                onSuccess = { onBack() },
                                onError = { message ->
                                    coroutineScope.launch {
                                        snackBarHostState.showSnackbar(message)
                                    }
                                }
                            )
                        }

                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save"
                        )
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
                isError = state.error?.contains("Title") == true,
                supportingText = { Text("${state.title.length + 1} / 200") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.description,
                onValueChange = viewModel::onDescriptionChange,
                label = { Text("Description") },
                isError = state.error?.contains("Description") == true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            if (!state.error.isNullOrBlank()) {
                Text(
                    text = state.error,
                    color = colors.error,
                    style =typography.bodySmall
                )
            }

            if (state.image != null) {
                AsyncImage(
                    model = File(state.image),
                    contentDescription = "Selected Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            2.dp,
                            colors.outline,
                            RoundedCornerShape(12.dp)
                        )
                )
            } else {
                OutlinedButton(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = colors.background,
                        contentColor = colors.onBackground
                    ),
                    border = BorderStroke(1.dp, colors.outline)
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Add Image")
                }}

            Text(
                text = "Select Labels:",
                style = typography.titleMedium
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                availableLabels.forEach { label ->
                    val isSelected = state.labels.contains(label)

                    LabelChip(
                        label = label,
                        isSelected = isSelected,
                        onClick = { viewModel.onLabelToggle(label) }
                    )
                }
            }
        }
    }
}


