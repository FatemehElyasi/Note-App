package ir.fatemelyasi.note.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class NoteEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int?,

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
    val isFavorite: Boolean?,

    @ColumnInfo(name = "label")
    val label: String?,
)

