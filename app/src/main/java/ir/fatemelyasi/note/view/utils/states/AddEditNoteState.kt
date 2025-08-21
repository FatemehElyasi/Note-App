package ir.fatemelyasi.note.view.utils.states

import ir.fatemelyasi.note.view.viewEntity.LabelViewEntity

data class AddEditNoteState(
    val noteId: Long? = null,
    val title: String = "",
    val description: String = "",
    val image: String? = null,
    val labels: List<LabelViewEntity> = emptyList(),
    val isFavorite: Boolean = false,
    val error: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long? = null,
    val isSaved: Boolean = false,
    val isAddLabelDialogOpen: Boolean = false,
    val newLabelName: String = ""
)