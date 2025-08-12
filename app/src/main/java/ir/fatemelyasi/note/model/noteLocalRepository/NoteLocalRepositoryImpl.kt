package ir.fatemelyasi.note.model.noteLocalRepository

import ir.fatemelyasi.note.model.local.entity.CrossEntity
import ir.fatemelyasi.note.model.local.entity.LabelEntity
import ir.fatemelyasi.note.model.local.entity.NoteEntity
import ir.fatemelyasi.note.model.local.entity.NoteWithLabels
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
    override fun getNoteById(id: Long): Flow<NoteEntity> {
        return noteLocalDataSource.getNoteById(id=id)
    }
    override suspend fun insertNote(note: NoteEntity): Long {
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

    override fun getByLabelName(label: String): Flow<List<LabelEntity>> {
        return noteLocalDataSource.getByLabelName(label)
    }
    override suspend fun insertLabel(label: LabelEntity): Long {
        return noteLocalDataSource.insertLabel(label)
    }
    override suspend fun updateLabel(label: LabelEntity) {
        return noteLocalDataSource.updateLabel(label)
    }
    override suspend fun deleteLabel(label: LabelEntity) {
        return noteLocalDataSource.deleteLabel(label)
    }

    override fun getNotesWithLabels(): Flow<List<NoteWithLabels>> {
        return noteLocalDataSource.getNotesWithLabels()
    }
    override suspend fun insertCrossRefs(crossList: List<CrossEntity>) {
       return noteLocalDataSource.insertCrossRefs( crossList = crossList)
    }
    override suspend fun deleteCrossRefsForNote(noteId: Long) {
        return noteLocalDataSource.deleteCrossRefsForNote(noteId = noteId)
    }
    override suspend fun getFavoriteNotesWithLabels(): List<NoteWithLabels> {
        return noteLocalDataSource.getFavoriteNotesWithLabels()
    }
    override suspend fun replaceCrossRefsForNote(noteId: Long, newCrossRefs: List<CrossEntity>) {
       return noteLocalDataSource.replaceCrossRefsForNote(noteId = noteId, newCrossRefs = newCrossRefs)
    }
    override suspend fun updateNoteWithLabels(note: NoteEntity, newCrossRefs: List<CrossEntity>) {
       return noteLocalDataSource.updateNoteWithLabels(note = note, newCrossRefs = newCrossRefs)
    }

}