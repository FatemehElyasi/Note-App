package ir.fatemelyasi.note.view.screens.homeListScreen

import ir.fatemelyasi.note.view.viewEntity.NoteViewEntity

data class NoteListState(
    val isLoading: Boolean = false,
    val notes: List<NoteViewEntity> = emptyList(),
    val error: String? = null
)
