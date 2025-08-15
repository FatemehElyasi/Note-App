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
    private val labelDao: LabelDao,
) : NoteLocalDataSource {

    override fun getAllNotes(): Flow<List<NoteEntity>> {
        return noteDao.getAllNotes()
    }
    override fun getNoteById(noteId: Long): Flow<NoteEntity> {
        return noteDao.getNoteById(noteId = noteId)
    }
    override suspend fun insertNote(note: NoteEntity): Long {
        return noteDao.insertNote(note = note)
    }
    override suspend fun updateNote(note: NoteEntity) {
        return noteDao.updateNote(note = note)
    }
    override suspend fun deleteNoteById(noteId: Long) {
        return noteDao.deleteNoteById(noteId = noteId)
    }
    override suspend fun deleteNote(note: NoteEntity) {
        return noteDao.deleteNote(note = note)
    }
    override suspend fun deleteAllNotes() {
        return noteDao.deleteAllNotes()
    }
    override suspend fun setFavoriteNote(noteId: Long, isFavorite: Boolean) {
        return noteDao.setFavoriteNote(noteId = noteId, isFavorite = isFavorite)
    }
    override fun getFavoriteNotes(): Flow<List<NoteEntity>> {
        return noteDao.getFavoriteNotes()
    }
    override fun searchNotes(query: String): Flow<List<NoteEntity>> {
        return noteDao.searchNotes(query = query)
    }

    // ------------------------------------- Labels
    override fun getAllLabels(): Flow<List<LabelEntity>> {
        return labelDao.getAllLabels()
    }
    override fun getLabelsByName(label: String): Flow<List<LabelEntity>> {
        return labelDao.getLabelsByName(label = label)
    }
    override suspend fun insertLabel(label: LabelEntity): Long {
        return labelDao.insertLabel(label = label)
    }
    override suspend fun insertAllLabels(labels: List<LabelEntity>) {
        return labelDao.insertAllLabels(labels = labels)
    }
    override suspend fun updateLabel(label: LabelEntity) {
        return labelDao.updateLabel(label = label)
    }
    override suspend fun deleteLabel(label: LabelEntity) {
        return labelDao.deleteLabel(label = label)
    }
    override suspend fun deleteLabelById(labelId: Long) {
        return labelDao.deleteLabelById(labelId = labelId)
    }
    override suspend fun getLabelsCount(): Long {
        return labelDao.getLabelsCount()
    }

    // ------------------------------------- Notes with Labels
    override fun getNotesWithLabels(): Flow<List<NoteWithLabels>> {
        return noteDao.getNotesWithLabels()
    }
    override fun getNoteWithLabelsById(noteId: Long): Flow<NoteWithLabels> {
        return noteDao.getNoteWithLabelsById(noteId = noteId)
    }
    override fun getFavoriteNotesWithLabels(): Flow<List<NoteWithLabels>> {
        return noteDao.getFavoriteNotesWithLabels()
    }

    // ------------------------------------- Cross References
    override suspend fun insertOrUpdateNoteWithLabels(note: NoteEntity, crossRefs: List<CrossEntity> ): Long {
        return noteDao.insertOrUpdateNoteWithLabels(note = note, crossRefs = crossRefs)
    }
    override suspend fun replaceCrossRefs(noteId: Long, newCrossRefs: List<CrossEntity>) {
        return noteDao.replaceCrossRefs(noteId = noteId, newCrossRefs = newCrossRefs)
    }

}
