package ir.fatemelyasi.note.model.noteLocalDataSource

import ir.fatemelyasi.note.model.local.entity.LabelEntity
import ir.fatemelyasi.note.model.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteLocalDataSource {

    fun getAllNotes(): Flow<List<NoteEntity>>
    fun getNoteById(id: Int): Flow<NoteEntity>?
    suspend fun insertNote(note: NoteEntity)
    suspend fun updateNote(note: NoteEntity)
    suspend fun deleteNote(note: NoteEntity)
    suspend fun deleteAll()
    fun searchNotes(query: String): Flow<List<NoteEntity>>
    fun getFavoriteNotes(): Flow<List<NoteEntity>>
    suspend fun setFavorite(noteId: Long, isFavorite: Boolean)

    fun getAllLabels(): Flow<List<LabelEntity>>
    fun getByLabelName(label: String): Flow<List<LabelEntity>>
    suspend fun insertLabel(label: LabelEntity)
    suspend fun updateLabel(label: LabelEntity)
    suspend fun deleteLabel(label: LabelEntity)

}