package ir.fatemelyasi.note.model.noteLocalRepository

import ir.fatemelyasi.note.model.local.entity.NoteEntity
import ir.fatemelyasi.note.model.local.entity.CrossEntity
import ir.fatemelyasi.note.model.local.entity.LabelEntity
import ir.fatemelyasi.note.model.local.entity.NoteWithLabels
import kotlinx.coroutines.flow.Flow

interface NoteLocalRepository {

    fun getAllNotes(): Flow<List<NoteEntity>>
    fun getNoteById(noteId: Long): Flow<NoteEntity>
    suspend fun insertNote(note: NoteEntity): Long
    suspend fun updateNote(note: NoteEntity)
    suspend fun deleteNoteById(noteId: Long)
    suspend fun deleteNote(note: NoteEntity)
    suspend fun deleteAllNotes()
    suspend fun setFavoriteNote(noteId: Long, isFavorite: Boolean)
    fun getFavoriteNotes(): Flow<List<NoteEntity>>
    fun searchNotes(query: String): Flow<List<NoteEntity>>

    fun getAllLabels(): Flow<List<LabelEntity>>
    fun getLabelsByName(label: String): Flow<List<LabelEntity>>
    suspend fun insertLabel(label: LabelEntity): Long
    suspend fun insertAllLabels(labels: List<LabelEntity>)
    suspend fun updateLabel(label: LabelEntity)
    suspend fun deleteLabel(label: LabelEntity)
    suspend fun deleteLabelById(labelId: Long)
    suspend fun getLabelsCount(): Long

    fun getNotesWithLabels(): Flow<List<NoteWithLabels>>
    fun getNoteWithLabelsById(noteId: Long): Flow<NoteWithLabels>
    fun getFavoriteNotesWithLabels(): Flow<List<NoteWithLabels>>

    suspend fun insertOrUpdateNoteWithLabels(note: NoteEntity, crossRefs: List<CrossEntity> ): Long
    suspend fun replaceCrossRefs(noteId: Long, newCrossRefs: List<CrossEntity>)
}


