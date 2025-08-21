package ir.fatemelyasi.note.view.viewEntity

import kotlinx.serialization.Serializable

@Serializable
data class NoteViewEntity(
    val id: Long?,
    val title: String,
    val description: String,
    val image: String?,
    val date: String,
    val isFavorite: Boolean,
    val createdAt: Long,
    val updatedAt: Long?,
    val labels: List<LabelViewEntity>,
)