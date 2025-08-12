package ir.fatemelyasi.note.view.screens.homeListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.fatemelyasi.note.model.local.mappers.toViewEntity
import ir.fatemelyasi.note.model.noteLocalRepository.NoteLocalRepository
import ir.fatemelyasi.note.view.utils.states.NoteListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class NoteListViewModel(
    private val repository: NoteLocalRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NoteListState(isLoading = true))
    val state = _state.asStateFlow()

    init {
        observeNotes()
    }

    private fun observeNotes() {
        viewModelScope.launch {
            repository.getAllNotes()
                .collect { notes ->
                    val viewNotes = notes.map { entity ->
                        entity.toViewEntity()
                    }
                    _state.value = NoteListState(
                        isLoading = false,
                        notes = viewNotes
                    )
                }
        }
    }
}

