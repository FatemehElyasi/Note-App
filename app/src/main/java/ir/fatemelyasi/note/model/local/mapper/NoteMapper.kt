package ir.fatemelyasi.note.model.local.mapper

import ir.fatemelyasi.note.model.local.entity.LabelEntity
import ir.fatemelyasi.note.model.local.entity.NoteEntity
import ir.fatemelyasi.note.view.utils.toFormattedDate
import ir.fatemelyasi.note.view.viewEntity.NoteViewEntity

fun NoteEntity.toViewEntity(labelEntities: List<LabelEntity>): NoteViewEntity {
    return NoteViewEntity(
        id = this.noteId,
        title = this.title ?: "",
        description = this.description,
        image = this.image,
        isFavorite = this.isFavorite ?: false,
        createdAt = this.createdAt ?: 0L,
        updatedAt =( this.updatedAt ?: 0L),
        date = (this.updatedAt ?: 0L).toFormattedDate(),
        labels = labelEntities.map { it.toViewEntity() }
    )
}
