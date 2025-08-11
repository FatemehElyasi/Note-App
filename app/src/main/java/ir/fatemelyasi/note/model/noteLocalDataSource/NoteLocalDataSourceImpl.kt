package ir.fatemelyasi.note.model.noteLocalDataSource

import ir.fatemelyasi.note.model.local.dao.LabelDao
import ir.fatemelyasi.note.model.local.dao.NoteDao
import ir.fatemelyasi.note.model.local.entity.CrossEntity
import ir.fatemelyasi.note.model.local.entity.LabelEntity
import ir.fatemelyasi.note.model.local.entity.NoteEntity
import ir.fatemelyasi.note.model.local.entity.NoteWithLabels
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class NoteLocalDataSourceImpl(
    private val noteDao: NoteDao,
    private val labelDao: LabelDao
) : NoteLocalDataSource {

    override fun getAllNotes(): Flow<List<NoteEntity>> {
        return noteDao.getAllNotes()
    }
    override fun getNoteById(id: Long ): Flow<NoteEntity> {
        return noteDao.getNoteById(id)
    }
    override suspend fun insertNote(note: NoteEntity): Long {
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

    override fun getByLabelName(label: String): Flow<List<LabelEntity>> {
        return labelDao.getByLabelName(label = label)
    }
    override suspend fun insertLabel(label: LabelEntity) {
        return labelDao.insertLabel(label = label)
    }
    override suspend fun updateLabel(label: LabelEntity) {
        return labelDao.updateLabel(label = label)
    }
    override suspend fun deleteLabel(label: LabelEntity) {
        return labelDao.deleteLabel(label = label)
    }

    override fun getNotesWithLabels(): Flow<List<NoteWithLabels>> {
        return noteDao.getNotesWithLabels()
    }
    override suspend fun insertCrossRefs(crossList: List<CrossEntity>) {
        return noteDao.insertCrossRefs(crossList = crossList)
    }
    override suspend fun deleteCrossRefsForNote(noteId: Long) {
       return noteDao.deleteCrossRefsForNote(noteId = noteId)
    }
    override suspend fun getFavoriteNotesWithLabels(): List<NoteWithLabels> {
        return noteDao.getFavoriteNotesWithLabels()
    }
    override suspend fun replaceCrossRefsForNote(noteId: Long, newCrossRefs: List<CrossEntity>) {
       return noteDao.replaceCrossRefsForNote(noteId= noteId, newCrossRefs = newCrossRefs)
    }
    override suspend fun updateNoteWithLabels(note: NoteEntity, newCrossRefs: List<CrossEntity>) {
        return noteDao.updateNoteWithLabels(note = note, newCrossRefs = newCrossRefs)
    }
}