package ir.fatemelyasi.note.model.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class NoteEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "noteId")
    val noteId: Long? = 0L,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "image")
    val image: String?,

    @ColumnInfo(name = "createdAt")
    val createdAt: Long?,

    @ColumnInfo(name = "updatedAt")
    val updatedAt: Long?,

    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean? = false,

)