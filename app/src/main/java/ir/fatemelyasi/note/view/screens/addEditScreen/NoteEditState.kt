package ir.fatemelyasi.note.view.screens.addEditScreen

import ir.fatemelyasi.note.view.viewEntity.LabelViewEntity

data class NoteEditState(
    val title: String = "",
    val description: String = "",
    val image: String? = null,
    val labels: List<LabelViewEntity> = emptyList(),
    val isFavorite: Boolean = false,
    val error: String? = null,
    val isSaved: Boolean = false
)
