package ir.fatemelyasi.note.model.local.mappers

import ir.fatemelyasi.note.model.local.entity.LabelEntity
import ir.fatemelyasi.note.model.local.entity.NoteEntity
import ir.fatemelyasi.note.model.local.entity.NoteWithLabels
import ir.fatemelyasi.note.view.utils.formatted.toFormattedDate
import ir.fatemelyasi.note.view.viewEntity.NoteViewEntity

//DB -> UI
fun NoteWithLabels.toViewEntity(): NoteViewEntity {
    return note.toViewEntity(labels)
}

//DB -> UI
fun NoteEntity.toViewEntity(labels: List<LabelEntity> = emptyList()): NoteViewEntity {
    return NoteViewEntity(
        id = noteId,
        title = title.orEmpty(),
        description = description.orEmpty(),
        image = image,
        isFavorite = isFavorite ?: false,
        createdAt = createdAt ?: 0L,
        updatedAt = updatedAt ?: 0L,
        date = (updatedAt ?: 0L).toFormattedDate(),
        labels = labels.map { it.toViewEntity() }
    )
}
//UI -> DB
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
