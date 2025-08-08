package ir.fatemelyasi.note.model.local.mapper

import ir.fatemelyasi.note.model.local.entity.CrossEntity
import ir.fatemelyasi.note.model.local.entity.LabelEntity
import ir.fatemelyasi.note.model.local.entity.NoteEntity
import ir.fatemelyasi.note.view.viewEntity.NoteViewEntity

fun mapToNoteViewEntity(
    notes: List<NoteEntity>,
    labels: List<LabelEntity>,
    crossEntities: List<CrossEntity>
): List<NoteViewEntity> {

    val noteIdToLabelIds = crossEntities.groupBy { it.noteId }
        .mapValues { entry -> entry.value.map { it.labelId } }

    val labelIdToLabel = labels.associateBy { it.labelId }

    return notes.map { note ->
        val relatedLabelIds = noteIdToLabelIds[note.noteId] ?: emptyList()
        val relatedLabels = relatedLabelIds.mapNotNull { labelIdToLabel[it] }

        note.toViewEntity(relatedLabels)
    }
}

fun NoteViewEntity.toCrossEntities(): List<CrossEntity> {
    return this.labels.mapNotNull { label ->
        label.labelId?.let { labelId ->
            this.id?.let { noteId ->
                CrossEntity(noteId = noteId, labelId = labelId)
            }
        }
    }
}


