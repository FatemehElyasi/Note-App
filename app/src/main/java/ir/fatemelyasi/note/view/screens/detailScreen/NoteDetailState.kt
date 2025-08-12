package ir.fatemelyasi.note.view.screens.detailScreen

import ir.fatemelyasi.note.model.local.entity.NoteEntity

data class NoteDetailState(
    val isLoading: Boolean = false,
    val note: NoteEntity? = null,
    val error: String? = null
)
