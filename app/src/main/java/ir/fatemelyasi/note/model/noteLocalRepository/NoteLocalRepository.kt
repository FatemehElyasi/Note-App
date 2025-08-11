package ir.fatemelyasi.note.model.noteLocalRepository

import ir.fatemelyasi.note.model.local.entity.CrossEntity
import ir.fatemelyasi.note.model.local.entity.LabelEntity
import ir.fatemelyasi.note.model.local.entity.NoteEntity
import ir.fatemelyasi.note.model.local.entity.NoteWithLabels
import kotlinx.coroutines.flow.Flow

interface NoteLocalRepository {

    fun getAllNotes(): Flow<List<NoteEntity>>
    fun getNoteById(id: Long): Flow<NoteEntity>
    suspend fun insertNote(note: NoteEntity): Long
    suspend fun updateNote(note: NoteEntity)
    suspend fun deleteNote(note: NoteEntity)
    suspend fun deleteAll()
    fun searchNotes(query: String): Flow<List<NoteEntity>>
    fun getFavoriteNotes(): Flow<List<NoteEntity>>
    suspend fun setFavorite(noteId: Long, isFavorite: Boolean)

    fun getByLabelName(label: String): Flow<List<LabelEntity>>
    suspend fun insertLabel(label: LabelEntity): Long
    suspend fun updateLabel(label: LabelEntity)
    suspend fun deleteLabel(label: LabelEntity)

    fun getNotesWithLabels(): Flow<List<NoteWithLabels>>
    suspend fun insertCrossRefs(crossList: List<CrossEntity>)
    suspend fun deleteCrossRefsForNote(noteId: Long)
    suspend fun getFavoriteNotesWithLabels(): List<NoteWithLabels>
    suspend fun replaceCrossRefsForNote(noteId: Long, newCrossRefs: List<CrossEntity>)
    suspend fun updateNoteWithLabels(note: NoteEntity, newCrossRefs: List<CrossEntity>)

}