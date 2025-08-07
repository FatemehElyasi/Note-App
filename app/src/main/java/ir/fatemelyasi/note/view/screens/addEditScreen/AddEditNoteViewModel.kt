package ir.fatemelyasi.note.view.screens.addEditScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.fatemelyasi.note.model.local.entity.NoteEntity
import ir.fatemelyasi.note.model.noteLocalRepository.NoteLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AddEditNoteViewModel(
    private val repository: NoteLocalRepository
) : ViewModel() {

    var state by mutableStateOf(AddEditNoteState())
        private set

    private var currentNoteId: Int? = null

    fun onTitleChange(newTitle: String) {
        if (newTitle.length <= 200) {
            state = state.copy(title = newTitle, error = null)
        }
    }

    fun onDescriptionChange(newDesc: String) {
        state = state.copy(description = newDesc)
    }

    fun onImageChange(newImage: String?) {
        state = state.copy(image = newImage)
    }

    fun loadNote(noteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val note = repository.getAllNotes().first().find {
                    it.noteId == noteId
                }
                if (note != null) {

                    currentNoteId = note.noteId

                    state = state.copy(
                        title = note.title ?: "",
                        description = note.description ?: "",
                        image = note.image,
                        isFavorite = note.isFavorite ?: false,
                        createdAt = note.createdAt
                    )
                }
            } catch (e: Exception) {
                state = state.copy(
                    error = e.localizedMessage
                )
            }
        }
    }

    fun saveNote() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val now = System.currentTimeMillis()

                // Validate
                if (state.title.trim().isEmpty()) {
                    state = state.copy(error = "Title cannot be empty.")
                    return@launch
                }

                if (state.description.trim().isEmpty()) {
                    state = state.copy(error = "Description cannot be empty.")
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

                if (isEditing) {
                    repository.updateNote(note)
                } else {
                    repository.insertNote(note)
                }

                state = state.copy(isSaved = true)
            } catch (e: Exception) {
                state = state.copy(error = e.localizedMessage)
            }
        }
    }
}
