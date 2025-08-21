package ir.fatemelyasi.note.model.noteLocalRepository

import ir.fatemelyasi.note.model.local.entity.NoteEntity
import ir.fatemelyasi.note.model.local.entity.CrossEntity
import ir.fatemelyasi.note.model.local.entity.LabelEntity
import ir.fatemelyasi.note.model.local.entity.NoteWithLabels
import ir.fatemelyasi.note.model.noteLocalDataSource.NoteLocalDataSource
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class NoteLocalRepositoryImpl(
    private val noteLocalDataSource: NoteLocalDataSource,
) : NoteLocalRepository {

    override fun getAllNotes(): Flow<List<NoteEntity>> {
        return noteLocalDataSource.getAllNotes()
    }
    override fun getNoteById(noteId: Long): Flow<NoteEntity> {
        return noteLocalDataSource.getNoteById(noteId = noteId)
    }
    override suspend fun insertNote(note: NoteEntity): Long {
        return noteLocalDataSource.insertNote(note = note)
    }
    override suspend fun updateNote(note: NoteEntity) {
        return noteLocalDataSource.updateNote(note = note)
    }
    override suspend fun deleteNoteById(noteId: Long) {
        return noteLocalDataSource.deleteNoteById(noteId = noteId)
    }
    override suspend fun deleteNote(note: NoteEntity) {
        return noteLocalDataSource.deleteNote(note = note)
    }
    override suspend fun deleteAllNotes() {
        return noteLocalDataSource.deleteAllNotes()
    }
    override suspend fun setFavoriteNote(noteId: Long, isFavorite: Boolean) {
        return noteLocalDataSource.setFavoriteNote(noteId = noteId, isFavorite = isFavorite)
    }
    override fun searchNotes(query: String): Flow<List<NoteEntity>> {
        return noteLocalDataSource.searchNotes(query = query)
    }

    override fun getAllLabels(): Flow<List<LabelEntity>> {
        return noteLocalDataSource.getAllLabels()
    }
    override fun getLabelsByName(label: String): Flow<List<LabelEntity>> {
        return noteLocalDataSource.getLabelsByName(label = label)
    }
    override suspend fun insertLabel(label: LabelEntity): Long {
        return noteLocalDataSource.insertLabel(label = label)
    }
    override fun getFavoriteNotes(): Flow<List<NoteEntity>> {
        return noteLocalDataSource.getFavoriteNotes()
    }

    override suspend fun insertAllLabels(labels: List<LabelEntity>) {
        return noteLocalDataSource.insertAllLabels(labels = labels)
    }
    override suspend fun updateLabel(label: LabelEntity) {
        return noteLocalDataSource.updateLabel(label = label)
    }
    override suspend fun deleteLabel(label: LabelEntity) {
        return noteLocalDataSource.deleteLabel(label = label)
    }
    override suspend fun deleteLabelById(labelId: Long) {
        return noteLocalDataSource.deleteLabelById(labelId = labelId)
    }
    override suspend fun getLabelsCount(): Long {
        return noteLocalDataSource.getLabelsCount()
    }

    override fun getNotesWithLabels(): Flow<List<NoteWithLabels>> {
        return noteLocalDataSource.getNotesWithLabels()
    }
    override fun getNoteWithLabelsById(noteId: Long): Flow<NoteWithLabels> {
        return noteLocalDataSource.getNoteWithLabelsById(noteId = noteId)
    }
    override fun getFavoriteNotesWithLabels(): Flow<List<NoteWithLabels>> {
        return noteLocalDataSource.getFavoriteNotesWithLabels()
    }

    override suspend fun insertOrUpdateNoteWithLabels(note: NoteEntity, crossRefs: List<CrossEntity> ): Long {
        return noteLocalDataSource.insertOrUpdateNoteWithLabels(note = note, crossRefs = crossRefs)
    }
    override suspend fun replaceCrossRefs(noteId: Long, newCrossRefs: List<CrossEntity>) {
        return noteLocalDataSource.replaceCrossRefs(noteId = noteId, newCrossRefs = newCrossRefs)
    }
}