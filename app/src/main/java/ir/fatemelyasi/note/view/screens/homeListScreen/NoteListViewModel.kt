package ir.fatemelyasi.note.view.screens.homeListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.fatemelyasi.note.model.local.mapper.mapToNoteViewEntity
import ir.fatemelyasi.note.model.noteLocalRepository.NoteLocalRepository
import ir.fatemelyasi.note.view.utils.states.NoteListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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
        viewModelScope.launch (Dispatchers.IO){
            try {
                combine(
                    repository.getAllNotes(),
                    repository.getAllLabels(),
                    repository.getCrossRefs()
                ) { notes, labels, crossRefs ->
                    mapToNoteViewEntity(
                        notes = notes,
                        labels = labels,
                        crossEntities = crossRefs
                    )
                }.collect { mappedNotes ->
                    _state.value = NoteListState(
                        isLoading = false,
                        notes = mappedNotes
                    )
                }
            } catch (e: Exception) {
                _state.value = NoteListState(
                    isLoading = false,
                    error = e.localizedMessage ?: "error"
                )
            }
        }
    }
}
