package ir.fatemelyasi.note.model.local.mappers

import ir.fatemelyasi.note.model.local.entity.NoteWithLabels
import ir.fatemelyasi.note.viewEntity.NoteViewEntity

fun NoteWithLabels.toViewEntity(): NoteViewEntity {
    return note.toViewEntity(labels)
}