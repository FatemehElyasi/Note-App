package ir.fatemelyasi.note.view.utils.states

import ir.fatemelyasi.note.view.viewEntity.NoteViewEntity

data class NoteDetailState(
    val isLoading: Boolean = false,
    val note: NoteViewEntity? = null,
    val error: String? = null
)