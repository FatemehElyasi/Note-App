package ir.fatemelyasi.note.view.screens.addEditScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ir.fatemelyasi.note.view.ui.theme.LocalCustomColors
import ir.fatemelyasi.note.view.utils.components.LabelChipComponent
import ir.fatemelyasi.note.view.utils.components.MessageSnackBarHost
import ir.fatemelyasi.note.view.utils.saveImageToInternalStorage
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.io.File

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    noteId: Long?,
    onBack: () -> Unit,
    viewModel: AddEditNoteViewModel = koinViewModel(),
) {
    val state = viewModel.state
    val allLabels by viewModel.allLabels.collectAsState()
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

    LaunchedEffect(Unit) {
        if (noteId == null) {
            viewModel.clearForNewNote()
        } else {
            noteId.let { viewModel.loadNote(it) }
        }
    }

    Scaffold(
        snackbarHost = { MessageSnackBarHost(snackBarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(if (noteId == null) "Add Note" else "Edit Note") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
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
                                onError = { msg ->
                                    coroutineScope.launch {
                                        snackBarHostState.showSnackbar(msg)
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = state.title,
                onValueChange = viewModel::onTitleChange,
                label = { Text("Title") },
                isError = state.error?.contains("Title") == true,
                supportingText = { Text("${state.title.length + 1} / 200") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
            )

            OutlinedTextField(
                value = state.description,
                onValueChange = viewModel::onDescriptionChange,
                label = { Text("Description") },
                isError = state.error?.contains("Description") == true,
                supportingText = { Text("${state.description.length + 1} / 1000") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                shape = RoundedCornerShape(8.dp)
            )

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { imagePickerLauncher.launch("image/*") },
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    if (state.image.isNullOrEmpty()) {
                        "Add Image"
                    } else {
                        "Change Image"
                    }
                )
            }

            state.image
                ?.takeIf { it.isNotEmpty() }
                ?.let { imagePath ->
                    AsyncImage(
                        model = File(imagePath),
                        contentDescription = "Note Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                border = BorderStroke(
                                    1.dp,
                                    color = colors.primary
                                )
                            )
                    )
                }

            OutlinedButton(
                onClick = { viewModel.showAddLabelDialog() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                ) {
                Text("Add Label")
            }


            if (state.labels.isNotEmpty()) {
                Text(
                    text = "Selected Labels:",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 8.dp)
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.labels.forEach { label ->
                        LabelChipComponent(
                            label = label,
                        )
                    }
                }
            }

            if (state.isAddLabelDialogOpen) {
                AlertDialog(
                    onDismissRequest = { viewModel.closeAddLabelDialog() },
                    title = { Text("Manage Labels") },
                    text = {
                        Column {
                            // Add new label section
                            OutlinedTextField(
                                value = state.newLabelName,
                                onValueChange = viewModel::onNewLabelNameChange,
                                placeholder = { Text("New label name") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            TextButton(
                                onClick = { viewModel.addLabelToDb() },
                                modifier = Modifier.padding(vertical = 8.dp)
                            ) {
                                Text("Add New Label")
                            }

                            if (allLabels.isNotEmpty()) {
                                Text(
                                    text = "Select from existing labels:",
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )

                                Column {
                                    allLabels.forEach { label ->
                                        val isSelected =
                                            state.labels.any { it.labelId == label.labelId }
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .clickable {
                                                        viewModel.toggleLabelSelection(
                                                            label
                                                        )
                                                    },
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Checkbox(
                                                    checked = isSelected,
                                                    onCheckedChange = {
                                                        viewModel.toggleLabelSelection(label)
                                                    }
                                                )
                                                Text(
                                                    text = label.labelName ?: "",
                                                    modifier = Modifier.padding(start = 8.dp),
                                                )
                                            }

                                            IconButton(
                                                onClick = { viewModel.removeLabel(label) }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Clear,
                                                    contentDescription = "Delete Label",
                                                    tint = colors.onPrimary,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = { viewModel.closeAddLabelDialog() }
                        ) { Text("Done") }
                    }
                )
            }
        }
    }
}