package ir.fatemelyasi.note.view.screens.addEditScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.fatemelyasi.note.model.local.entity.CrossEntity
import ir.fatemelyasi.note.model.local.entity.NoteEntity
import ir.fatemelyasi.note.model.local.mappers.toViewEntity
import ir.fatemelyasi.note.model.noteLocalRepository.NoteLocalRepository
import ir.fatemelyasi.note.view.utils.states.AddEditNoteState
import ir.fatemelyasi.note.view.viewEntity.LabelViewEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AddEditNoteViewModel(
    private val repository: NoteLocalRepository
) : ViewModel() {

    var state by mutableStateOf(AddEditNoteState())
        private set

    private var currentNoteId: Long? = null

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

    fun onLabelToggle(label: LabelViewEntity) {
        val current = state.labels.toMutableList()
        if (current.contains(label)) {
            current.remove(label)
        } else {
            current.add(label)
        }
        state = state.copy(labels = current)
    }

    fun loadNote(noteId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val noteWithLabels = repository.getNotesWithLabels()
                    .first()
                    .firstOrNull { it.note.noteId == noteId }

                noteWithLabels?.let { it ->
                    currentNoteId = it.note.noteId

                    withContext(Dispatchers.Main) {
                        state = state.copy(
                            title = it.note.title ?: "",
                            description = it.note.description ?: "",
                            image = it.note.image,
                            isFavorite = it.note.isFavorite == true,
                            createdAt = it.note.createdAt,
                            labels = it.labels.map { it.toViewEntity() }
                        )
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    state = state.copy(error = e.localizedMessage)
                }
            }
        }
    }

    fun saveNote(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val now = System.currentTimeMillis()

                if (state.title.trim().isEmpty()) {
                    withContext(Dispatchers.Main) {
                        state = state.copy(
                            error = "Title cannot be empty."
                        )
                    }
                    return@launch
                }

                if (state.description.trim().isEmpty()) {
                    withContext(Dispatchers.Main) {
                        state = state.copy(
                            error = "Description cannot be empty."
                        )
                    }
                    return@launch
                }

                val isEditing = currentNoteId != null
                val note = NoteEntity(
                    noteId = currentNoteId,
                    title = state.title,
                    description = state.description,
                    image = state.image,
                    isFavorite = state.isFavorite,
                    createdAt = if (isEditing) state.createdAt ?: now else now,
                    updatedAt = now
                )

                val noteId = if (isEditing) {
                    repository.updateNote(note)
                    currentNoteId!!
                } else {
                    repository.insertNote(note)
                }


                repository.replaceCrossRefsForNote(
                    noteId = noteId,
                    newCrossRefs = state.labels.map {
                        CrossEntity(noteId.toInt(), it.labelId ?: 0)
                    }
                )

                withContext(Dispatchers.Main) {
                    state = state.copy(isSaved = true)
                    onSuccess()
                }
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}

