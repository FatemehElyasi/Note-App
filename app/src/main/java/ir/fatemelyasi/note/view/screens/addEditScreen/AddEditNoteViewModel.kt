package ir.fatemelyasi.note.view.screens.addEditScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.fatemelyasi.note.model.local.entity.CrossEntity
import ir.fatemelyasi.note.model.local.entity.LabelEntity
import ir.fatemelyasi.note.model.local.entity.NoteEntity
import ir.fatemelyasi.note.model.local.mappers.toEntity
import ir.fatemelyasi.note.model.local.mappers.toViewEntity
import ir.fatemelyasi.note.model.noteLocalRepository.NoteLocalRepository
import ir.fatemelyasi.note.view.utils.randomColorHex
import ir.fatemelyasi.note.view.utils.states.AddEditNoteState
import ir.fatemelyasi.note.view.viewEntity.LabelViewEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AddEditNoteViewModel(
    private val repository: NoteLocalRepository,
) : ViewModel() {

    var state by mutableStateOf(AddEditNoteState())
        private set

    val allLabels: StateFlow<List<LabelViewEntity>> =
        repository.getAllLabels()
            .map { databaseLabels -> databaseLabels.map { it.toViewEntity() } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun clearForNewNote() {
        state = AddEditNoteState()
    }

    fun onTitleChange(newTitle: String) {
        if (newTitle.length <= 200) {
            state = state.copy(
                title = newTitle,
                error = null
            )
        }
    }

    fun onDescriptionChange(newDesc: String) {
        state = state.copy(
            description = newDesc
        )
    }

    fun onImageChange(newImage: String?) {
        state = state.copy(
            image = newImage
        )
    }

    fun removeLabel(label: LabelViewEntity) {
        viewModelScope.launch {
            repository.deleteLabel(label.toEntity())
            state = state.copy(
                labels = state.labels.filterNot {
                    it.labelId == label.labelId
                }
            )
        }
    }

    fun loadNote(noteId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNoteWithLabelsById(noteId).collect { noteWithLabels ->

                state = state.copy(
                    noteId = noteWithLabels.note.noteId,
                    title = noteWithLabels.note.title,
                    description = noteWithLabels.note.description ?: "",
                    image = noteWithLabels.note.image,
                    isFavorite = noteWithLabels.note.isFavorite,
                    createdAt = noteWithLabels.note.createdAt,
                    labels = noteWithLabels.labels.map { it.toViewEntity() }
                )
            }
        }
    }

    fun saveNote(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                if (state.title.isBlank()) {
                    state = state.copy(
                        error = "Title cannot be empty."
                    )
                    return@launch
                }
                if (state.description.isBlank()) {
                    state = state.copy(
                        error = "Description cannot be empty."
                    )
                    return@launch
                }

                val now = System.currentTimeMillis()
                val note = NoteEntity(
                    noteId = state.noteId,
                    title = state.title,
                    description = state.description,
                    image = state.image,
                    createdAt = state.createdAt,
                    updatedAt = now,
                    isFavorite = state.isFavorite
                )

                val finalNoteId = repository.insertOrUpdateNoteWithLabels(note, emptyList())

                val crossRefs = state.labels.mapNotNull { label ->
                    label.labelId?.let { labelId ->
                        CrossEntity(
                            noteId = finalNoteId,
                            labelId = labelId
                        )
                    }
                }

                if (crossRefs.isNotEmpty()) {
                    repository.replaceCrossRefs(finalNoteId, crossRefs)
                }

                onSuccess()
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun showAddLabelDialog() {
        state = state.copy(
            isAddLabelDialogOpen = true
        )
    }

    fun closeAddLabelDialog() {
        state = state.copy(
            isAddLabelDialogOpen = false,
            newLabelName = ""
        )
    }

    fun onNewLabelNameChange(name: String) {
        state = state.copy(
            newLabelName = name
        )
    }

    fun addLabelToDb() {
        val name = state.newLabelName.trim()
        if (name.isBlank()) return

        if (state.labels.any {
                it.labelName.equals(name, ignoreCase = true)
            }) {
            return
        }

        val newLabel = LabelEntity(
            labelId = null,
            labelName = name,
            labelColor = randomColorHex()
        )

        viewModelScope.launch {
            try {
                val insertedLabelId = repository.insertLabel(newLabel)

                val newLabelView = newLabel.copy(labelId = insertedLabelId).toViewEntity()
                state = state.copy(
                    labels = state.labels + newLabelView,
                    isAddLabelDialogOpen = false,
                    newLabelName = ""
                )
            } catch (e: Exception) {
                state = state.copy(
                    error = "Failed to add label: ${e.localizedMessage}"
                )
            }
        }
    }

    fun toggleLabelSelection(label: LabelViewEntity) {
        val isSelected = state.labels.any {
            it.labelId == label.labelId
        }

        state = if (isSelected) {
            state.copy(
                labels = state.labels.filterNot {
                    it.labelId == label.labelId
                }
            )
        } else {
            state.copy(
                labels = state.labels + label
            )
        }
    }
}