package ir.fatemelyasi.note.di.databaseModule

import android.content.Context
import androidx.room.Room
import ir.fatemelyasi.note.local.NoteDao
import ir.fatemelyasi.note.local.NoteDatabase
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
            "news-db"
        ).build()
    }

    @Single
    fun provideDao(newsDatabase: NoteDatabase): NoteDao {
        return newsDatabase.noteDao()
    }
}