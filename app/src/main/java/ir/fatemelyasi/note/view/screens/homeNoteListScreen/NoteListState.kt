package ir.fatemelyasi.note.view.screens.homeNoteListScreen

import ir.fatemelyasi.note.view.viewEntity.NoteViewEntity

data class NoteListState(
    val isLoading: Boolean = false,
    val notes: List<NoteViewEntity> = emptyList(),
    val error: String? = null
)
