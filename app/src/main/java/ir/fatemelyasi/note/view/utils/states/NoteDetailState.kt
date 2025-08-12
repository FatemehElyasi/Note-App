package ir.fatemelyasi.note.view.utils.states

import ir.fatemelyasi.note.model.local.entity.NoteEntity

data class NoteDetailState(
    val isLoading: Boolean = false,
    val note: NoteEntity? = null,
    val error: String? = null
)