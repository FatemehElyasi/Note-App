package ir.fatemelyasi.note.model.noteLocalRepository

import ir.fatemelyasi.note.model.local.entity.NoteEntity
import ir.fatemelyasi.note.model.noteLocalDataSource.NoteLocalDataSource
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class NoteLocalRepositoryImpl(
    private val noteLocalDataSource: NoteLocalDataSource
) : NoteLocalRepository {
    override fun getAllNotes(): Flow<List<NoteEntity>> {
        return noteLocalDataSource.getAllNotes()
    }

    override fun getNoteById(id: Int): Flow<NoteEntity>? {
        return noteLocalDataSource.getNoteById(id)
    }

    override suspend fun insertNote(note: NoteEntity) {
        return noteLocalDataSource.insertNote(note)
    }

    override suspend fun updateNote(note: NoteEntity) {
        return noteLocalDataSource.updateNote(note)
    }

    override suspend fun deleteNote(note: NoteEntity) {
        return noteLocalDataSource.deleteNote(note)
    }

    override suspend fun deleteAll() {
        return noteLocalDataSource.deleteAll()
    }

    override fun searchNotes(query: String): Flow<List<NoteEntity>> {
        return noteLocalDataSource.searchNotes(query)
    }

    override fun getFavoriteNotes(): Flow<List<NoteEntity>> {
        return noteLocalDataSource.getFavoriteNotes()
    }

    override suspend fun setFavorite(noteId: Long, isFavorite: Boolean) {
        return noteLocalDataSource.setFavorite(noteId, isFavorite)
    }
}