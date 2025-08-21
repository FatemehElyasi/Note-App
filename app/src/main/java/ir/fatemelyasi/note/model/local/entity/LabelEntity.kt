package ir.fatemelyasi.note.model.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "label_table")
data class LabelEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("labelId")
    val labelId: Long? = null,

    @ColumnInfo("labelName")
    val labelName: String,

    @ColumnInfo("labelColor")
    val labelColor: String
)