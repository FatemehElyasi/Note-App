package ir.fatemelyasi.note.view.utils.states

import ir.fatemelyasi.note.view.viewEntity.NoteViewEntity

data class NoteListState(
    val isLoading: Boolean = false,
    val notes: List<NoteViewEntity> = emptyList(),
    val error: String? = null
)