package ir.fatemelyasi.note.view.screens.addEditScreen

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

private const val TITLE_MAX_LIMIT = 200

@KoinViewModel
class AddEditNoteViewModel(
    private val repository: NoteLocalRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(AddEditNoteState())
    val state: StateFlow<AddEditNoteState> = _state


    init {
        loadLabels()
    }

    fun loadLabels() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllLabels().map {
                it.map { label ->
                    label.toViewEntity()
                }
            }
                .collect { labels ->
                    _state.update {
                        it.copy(
                            labels = labels
                        )
                    }
                }
        }
    }

    fun clearForNewNote() {
        _state.value = AddEditNoteState()
    }

    fun onTitleChange(newTitle: String) {
        if (newTitle.length <= TITLE_MAX_LIMIT) {
            _state.update {
                it.copy(
                    title = newTitle,
                    error = null
                )
            }
        }
    }

    fun onDescriptionChange(newDesc: String) {
        _state.update {
            it.copy(
                description = newDesc
            )
        }
    }

    fun onImageChange(newImage: String?) {
        _state.update {
            it.copy(
                image = newImage
            )
        }
    }

    fun removeLabel(label: LabelViewEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteLabel(
                label.toEntity()
            )
            _state.update {
                it.copy(
                    labels = it.labels
                        .filterNot { it ->
                            it.labelId == label.labelId
                        }
                )
            }
        }
    }

    fun loadNote(noteId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNoteWithLabelsById(noteId).collect { noteWithLabels ->
                _state.update {
                    it.copy(
                        noteId = noteWithLabels.note.noteId,
                        title = noteWithLabels.note.title,
                        description = noteWithLabels.note.description ?: "",
                        image = noteWithLabels.note.image,
                        isFavorite = noteWithLabels.note.isFavorite,
                        createdAt = noteWithLabels.note.createdAt,
                        labels = noteWithLabels.labels.map { label -> label.toViewEntity() }
                    )
                }
            }
        }
    }

    fun saveNote(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentState = _state.value
                when {
                    currentState.title.isBlank() -> {
                        _state.update {
                            it.copy(
                                error = "Title cannot be empty."
                            )
                        }
                        return@launch
                    }

                    currentState.description.isBlank() -> {
                        _state.update {
                            it.copy(
                                error = "Description cannot be empty."
                            )
                        }
                        return@launch
                    }
                }

                val currentDate = System.currentTimeMillis()
                val note = NoteEntity(
                    noteId = currentState.noteId,
                    title = currentState.title,
                    description = currentState.description,
                    image = currentState.image,
                    createdAt = currentState.createdAt,
                    updatedAt = currentDate,
                    isFavorite = currentState.isFavorite
                )

                val finalNoteId = repository.insertOrUpdateNoteWithLabels(note, emptyList())

                val crossRefs = currentState.labels.mapNotNull { label ->
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

                _state.update {
                    it.copy(
                        isSaved = true
                    )
                }
                onSuccess()
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = e.localizedMessage ?: "Unknown error"
                    )
                }
                onError(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun showAddLabelDialog() {
        _state.update {
            it.copy(
                isAddLabelDialogOpen = true
            )
        }
    }

    fun closeAddLabelDialog() {
        _state.update {
            it.copy(
                isAddLabelDialogOpen = false,
                newLabelName = ""
            )
        }
    }

    fun onNewLabelNameChange(name: String) {
        _state.update {
            it.copy(
                newLabelName = name
            )
        }
    }

    fun addLabelToDb() {
        val name = _state.value.newLabelName.trim()

        if (name.isBlank()) return
        if (_state.value.labels.any {
                it.labelName.equals(
                    name,
                    ignoreCase = true
                )
            }
        )
            return

        val newLabel = LabelEntity(
            labelId = null,
            labelName = name,
            labelColor = randomColorHex()
        )

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val insertedLabelId = repository.insertLabel(newLabel)

                val newLabelView = newLabel.copy(labelId = insertedLabelId).toViewEntity()
                _state.update {
                    it.copy(
                        labels = it.labels + newLabelView,
                        isAddLabelDialogOpen = false,
                        newLabelName = ""
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = "Failed to add label: ${e.localizedMessage}"
                    )
                }
            }
        }
    }

    fun toggleLabelSelection(label: LabelViewEntity) {
        _state.update {
            it.copy(
                labels = if (
                    it.labels.any { l -> l.labelId == label.labelId }
                )
                    it.labels.filterNot { l -> l.labelId == label.labelId }
                else
                    it.labels + label
            )
        }
    }
}