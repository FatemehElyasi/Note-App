package ir.fatemelyasi.note.model.local.mappers

import ir.fatemelyasi.note.model.local.entity.LabelEntity
import ir.fatemelyasi.note.model.local.entity.NoteEntity
import ir.fatemelyasi.note.model.local.entity.NoteWithLabels
import ir.fatemelyasi.note.viewEntity.NoteViewEntity

// ---------- NoteEntity -> NoteViewEntity (DB -> UI)
fun NoteEntity.toViewEntity(labels: List<LabelEntity> = emptyList()): NoteViewEntity {
    return NoteViewEntity(
        id = noteId,
        title = title.orEmpty(),
        description = description.orEmpty(),
        image = image,
        createdAt = createdAt ?: 0L,
        updatedAt = updatedAt ?: 0L,
        date = (updatedAt ?: 0L).toFormattedDate(),
        isFavorite = isFavorite,
        labels = labels.map { it.toViewEntity() }
    )
}

// ---------- NoteViewEntity -> NoteEntity (UI -> DB)
fun NoteViewEntity.toEntity(): NoteEntity {
    return NoteEntity(
        noteId = id ?: 0L,
        title = title ?: "",
        description = description ?: "",
        image = image,
        createdAt = createdAt ?: 0L,
        updatedAt = updatedAt ?: 0L,
        isFavorite = isFavorite ?: false
    )
}
