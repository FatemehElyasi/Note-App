package ir.fatemelyasi.note.model.di.databaseModule

import android.content.Context
import androidx.room.Dao
import androidx.room.Room
import ir.fatemelyasi.note.model.local.dao.LabelDao
import ir.fatemelyasi.note.model.local.dao.NoteDao
import ir.fatemelyasi.note.model.local.database.NoteDatabase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import kotlin.jvm.java

@Module
class DatabaseModule {
    @Single
    fun provideDatabase(context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "note-db"
        ).build()
    }

    @Single
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao {
        return noteDatabase.noteDao()
    }

    @Single
    fun provideLabelDao(noteDatabase: NoteDatabase): LabelDao {
        return noteDatabase.labelDao()
    }

}