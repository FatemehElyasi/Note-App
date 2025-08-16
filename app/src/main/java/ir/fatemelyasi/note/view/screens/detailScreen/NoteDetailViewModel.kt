package ir.fatemelyasi.note.view.screens.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.fatemelyasi.note.model.local.mappers.toEntity
import ir.fatemelyasi.note.model.local.mappers.toViewEntity
import ir.fatemelyasi.note.model.noteLocalRepository.NoteLocalRepository
import ir.fatemelyasi.note.view.utils.states.NoteDetailState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class NoteDetailViewModel(
    private val repository: NoteLocalRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(NoteDetailState())
    val state: StateFlow<NoteDetailState> = _state

    fun loadNote(noteId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = NoteDetailState(isLoading = true)
            try {
                val noteWithLabels = repository.getNoteWithLabelsById(noteId).firstOrNull()

                if (noteWithLabels?.note != null) {
                    _state.value = NoteDetailState(
                        note = noteWithLabels.note.toViewEntity(),
                        labels = noteWithLabels.labels.map { it.toViewEntity() },
                        isLoading = false
                    )
                } else {
                    _state.value = NoteDetailState(
                        error = "Note not found or has been deleted",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.value = NoteDetailState(
                    error = e.localizedMessage ?: "Unknown error",
                    isLoading = false
                )
            }
        }
    }

    fun deleteNote(onSuccess: () -> Unit) {
        val noteId = _state.value.note?.id ?: return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteNoteById(noteId)

                withContext(Dispatchers.Main) {
                    onSuccess()
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _state.value = _state.value.copy(
                        error = e.localizedMessage ?: "Failed to delete note"
                    )
                }
            }
        }
    }

    fun toggleFavorite() {
        val current = _state.value.note ?: return

        val updatedNote = current.copy(
            isFavorite = current.isFavorite != true,
            updatedAt = System.currentTimeMillis()
        )

        viewModelScope.launch(
            Dispatchers.IO
        ) {
            try {
                repository.updateNote(updatedNote.toEntity())
                _state.value = _state.value.copy(
                    note = updatedNote
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.localizedMessage ?: "Failed to update favorite"
                )
            }
        }
    }
}

