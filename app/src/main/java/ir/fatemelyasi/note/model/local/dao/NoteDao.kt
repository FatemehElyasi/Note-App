package ir.fatemelyasi.note.model.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import ir.fatemelyasi.note.model.local.entity.CrossEntity
import ir.fatemelyasi.note.model.local.entity.NoteEntity
import ir.fatemelyasi.note.model.local.entity.NoteWithLabels
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table ORDER BY updatedAt Desc")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note_table WHERE noteId = :noteId")
    fun getNoteById(noteId: Long): Flow<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM note_table WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchNotes(query: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note_table WHERE isFavorite = 1 ORDER BY updatedAt DESC")
    fun getFavoriteNotes(): Flow<List<NoteEntity>>

    @Query("UPDATE note_table SET isFavorite = :isFavorite WHERE noteId = :noteId")
    suspend fun setFavorite(noteId: Long, isFavorite: Boolean)

    @Transaction
    @Query("SELECT * FROM note_table")
    fun getNotesWithLabels(): Flow<List<NoteWithLabels>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRefs(crossList: List<CrossEntity>)

    @Transaction
    suspend fun deleteCrossRefsForNote(noteId: Long) = deleteCrossRefsForNoteInternal(noteId)


    @Query("DELETE FROM note_label_join WHERE noteId = :noteId")
    suspend fun deleteCrossRefsForNoteInternal(noteId: Long)

    @Transaction
    @Query("SELECT * FROM note_table WHERE isFavorite = 1")
    suspend fun getFavoriteNotesWithLabels(): List<NoteWithLabels>

    @Transaction
    suspend fun replaceCrossRefsForNote(noteId: Long, newCrossRefs: List<CrossEntity>) {
        deleteCrossRefsForNoteInternal(noteId)
        if (newCrossRefs.isNotEmpty()) {
            insertCrossRefs(newCrossRefs)
        }
    }

    @Transaction
    suspend fun updateNoteWithLabels(
        note: NoteEntity, newCrossRefs: List<CrossEntity>
    ) {
        updateNote(note)
        deleteCrossRefsForNoteInternal(note.noteId?.toLong() ?: 0)
        if (newCrossRefs.isNotEmpty()) {
            insertCrossRefs(newCrossRefs)
        }
    }
}