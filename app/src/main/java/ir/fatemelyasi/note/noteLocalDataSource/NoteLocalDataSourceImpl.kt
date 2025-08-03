package ir.fatemelyasi.note.noteLocalDataSource

import ir.fatemelyasi.note.local.NoteDao
import ir.fatemelyasi.note.local.NoteEntity
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class NoteLocalDataSourceImpl(
    private val noteDao: NoteDao
) : NoteLocalDataSource {
    override fun getAllNotes(): Flow<List<NoteEntity>> {
        return noteDao.getAllNotes()
    }

    override fun getNoteById(id: Int): Flow<NoteEntity>? {
        return noteDao.getNoteById(id = id)
    }

    override suspend fun insertNote(note: NoteEntity) {
        return noteDao.insertNote(note = note)
    }

    override suspend fun updateNote(note: NoteEntity) {
        return noteDao.updateNote(note = note)
    }

    override suspend fun deleteNote(note: NoteEntity) {
        return noteDao.deleteNote(note = note)
    }

    override suspend fun deleteAll() {
        return noteDao.deleteAll()
    }

    override fun searchNotes(query: String): Flow<List<NoteEntity>> {
        return noteDao.searchNotes(query = query)
    }

    override fun getFavoriteNotes(): Flow<List<NoteEntity>> {
        return noteDao.getFavoriteNotes()
    }

    override suspend fun setFavorite(noteId: Long, isFavorite: Boolean) {
        return noteDao.setFavorite(noteId = noteId, isFavorite = isFavorite)
    }

    override fun getNotesByLabel(label: String): Flow<List<NoteEntity>> {
        return noteDao.getNotesByLabel(label = label)
    }

}