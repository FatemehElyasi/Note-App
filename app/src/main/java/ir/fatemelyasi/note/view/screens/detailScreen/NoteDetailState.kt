package ir.fatemelyasi.note.view.screens.detailScreen

import ir.fatemelyasi.note.view.viewEntity.NoteViewEntity

data class NoteDetailState(
    val isLoading: Boolean = false,
    val note: NoteViewEntity? = null,
    val error: String? = null
)
