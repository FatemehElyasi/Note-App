package ir.fatemelyasi.note.view.screens.addEditScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.fatemelyasi.note.model.local.entity.NoteEntity
import ir.fatemelyasi.note.model.noteLocalRepository.NoteLocalRepository
import ir.fatemelyasi.note.view.utils.states.AddEditNoteState
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

    fun loadNote(noteId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val note = repository.getNoteById(noteId).first()
                note.let {
                    currentNoteId = it.noteId

                    withContext(Dispatchers.Main) {
                        state = state.copy(
                            title = it.title ?: "",
                            description = it.description ?: "",
                            image = it.image,
                            isFavorite = it.isFavorite == true,
                            createdAt = it.createdAt
                        )
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    state = state.copy(
                        error = e.localizedMessage
                    )
                }
            }
        }
    }

    fun saveNote(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
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

                if (isEditing) {
                    repository.updateNote(note)
                } else {
                    repository.insertNote(note)
                }

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

