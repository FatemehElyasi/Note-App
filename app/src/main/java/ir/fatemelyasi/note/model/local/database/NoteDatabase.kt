package ir.fatemelyasi.note.model.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.fatemelyasi.note.model.local.dao.LabelDao
import ir.fatemelyasi.note.model.local.entity.NoteEntity
import ir.fatemelyasi.note.model.local.dao.NoteDao
import ir.fatemelyasi.note.model.local.entity.CrossEntity
import ir.fatemelyasi.note.model.local.entity.LabelEntity

@Database(
    entities = [NoteEntity::class, LabelEntity::class, CrossEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun labelDao(): LabelDao
}