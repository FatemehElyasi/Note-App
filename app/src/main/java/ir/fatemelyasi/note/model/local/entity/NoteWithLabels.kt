package ir.fatemelyasi.note.model.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class NoteWithLabels(
    @Embedded val note: NoteEntity,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "labelId",
        associateBy = Junction(
            value = CrossEntity::class,
            parentColumn = "noteId",
            entityColumn = "labelId"
        )
    )
    val labels: List<LabelEntity>
)
