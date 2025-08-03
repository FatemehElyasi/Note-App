package ir.fatemelyasi.note.viewEntity

import kotlinx.serialization.Serializable

@Serializable
data class NoteViewEntity(
    val id: Int?,
    val title: String,
    val description: String?,
    val image: String?,
    val date: String,
    val isFavorite: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long,
    val label: String,
)