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
import ir.fatemelyasi.note.view.utils.formatted.randomColorHex
import ir.fatemelyasi.note.view.utils.states.AddEditNoteState
import ir.fatemelyasi.note.view.viewEntity.LabelViewEntity
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
    private var currentNoteId: Long? = null

    val allLabels: StateFlow<List<LabelViewEntity>> =
        repository.getAllLabels()
            .map { databaseLabels -> databaseLabels.map { it.toViewEntity() } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

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
        viewModelScope.launch {
            repository.getNoteWithLabelsById(noteId).collect { noteWithLabels ->
                currentNoteId = noteWithLabels.note.noteId

                state = state.copy(
                    title = noteWithLabels.note.title,
                    description = noteWithLabels.note.description,
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
                    state = state.copy(error = "Title cannot be empty.")
                    return@launch
                }
                if (state.description.isBlank()) {
                    state = state.copy(error = "Description cannot be empty.")
                    return@launch
                }

                val now = System.currentTimeMillis()
                val note = NoteEntity(
                    noteId = currentNoteId,
                    title = state.title,
                    description = state.description,
                    image = state.image ?: "",
                    createdAt = state.createdAt ?: now,
                    updatedAt = now,
                    isFavorite = state.isFavorite
                )

                val labelEntities = state.labels.map {
                    it.toEntity()
                }

                repository.insertOrUpdateNoteWithLabels(
                    note = note,
                    crossRefs = labelEntities.map { label ->
                        CrossEntity(
                            noteId = note.noteId ?: 0L,
                            labelId = label.labelId ?: 0L
                        )
                    }
                )

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
            repository.insertLabel(newLabel)
        }
        closeAddLabelDialog()
    }

}


