package ir.fatemelyasi.note.model.local.mappers

import ir.fatemelyasi.note.model.local.entity.NoteEntity
import ir.fatemelyasi.note.view.utils.formatted.toFormattedDate
import ir.fatemelyasi.note.view.viewEntity.NoteViewEntity

// ---------- NoteEntity -> NoteViewEntity
fun NoteEntity.toViewEntity(labels: List<LabelEntity> = emptyList()): NoteViewEntity {
    return NoteViewEntity(
        id = noteId,
        title = title,
        description = description.orEmpty(),
        image = image,
        createdAt = createdAt,
        updatedAt = updatedAt,
        date = (updatedAt ?: createdAt).toFormattedDate(),
        isFavorite = isFavorite,
        labels = labels.map { it.toViewEntity() }
    )
}

// ---------- NoteViewEntity -> NoteEntity
fun NoteViewEntity.toEntity(): NoteEntity {
    return NoteEntity(
        noteId = id,
        title = title,
        description = description,
        image = image,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isFavorite = isFavorite
    )
}
