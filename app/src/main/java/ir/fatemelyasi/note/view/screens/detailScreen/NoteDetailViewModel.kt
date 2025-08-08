package ir.fatemelyasi.note.view.screens.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.fatemelyasi.note.model.local.entity.NoteEntity
import ir.fatemelyasi.note.model.local.mapper.mapToNoteViewEntity
import ir.fatemelyasi.note.model.noteLocalRepository.NoteLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class NoteDetailViewModel(
    private val repository: NoteLocalRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NoteDetailState())
    val state: StateFlow<NoteDetailState> = _state

    fun loadNote(noteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = NoteDetailState(isLoading = true)

            try {
                val notes = repository.getAllNotes().first()
                val crossRefs = repository.getCrossRefs().first()
                val mappedNotes = mapToNoteViewEntity(
                    notes = notes,
                    labels = emptyList(),
                    crossEntities = crossRefs
                )

                val note = mappedNotes.find { it.id == noteId }

                if (note != null) {
                    _state.value = NoteDetailState(note = note)
                } else {
                    _state.value = NoteDetailState(error = "Note not found")
                }

            } catch (e: Exception) {
                _state.value = NoteDetailState(error = e.message)
            }
        }
    }

    fun toggleFavorite() {
        val current = _state.value.note ?: return

        val updatedEntity = NoteEntity(
            noteId = current.id,
            title = current.title,
            description = current.description,
            image = current.image,
            isFavorite = !current.isFavorite,
            createdAt = current.createdAt,
            updatedAt = System.currentTimeMillis()
        )

        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.updateNote(updatedEntity)
                _state.value = _state.value.copy(
                    note = current.copy(isFavorite = !current.isFavorite)
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    fun deleteNote(onSuccess: () -> Unit) {
        val noteId = _state.value.note?.id ?: return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteNoteById(noteId)
                onSuccess()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }
}




