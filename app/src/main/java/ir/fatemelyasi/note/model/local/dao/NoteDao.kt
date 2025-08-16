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

    @Query("DELETE FROM note_table WHERE noteId = :noteId")
    suspend fun deleteNoteById(noteId: Long)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("DELETE FROM note_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM note_table WHERE isFavorite = 1 ORDER BY updatedAt DESC")
    fun getFavoriteNotes(): Flow<List<NoteEntity>>

    @Query("UPDATE note_table SET isFavorite = :isFavorite WHERE noteId = :noteId")
    suspend fun setFavoriteNote(noteId: Long, isFavorite: Boolean)

    @Query("SELECT * FROM note_table WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchNotes(query: String): Flow<List<NoteEntity>>

    // ------------------------------------- Notes with Labels(view)
    @Transaction
    @Query("SELECT * FROM note_table")
    fun getNotesWithLabels(): Flow<List<NoteWithLabels>>

    @Transaction
    @Query("SELECT * FROM note_table WHERE noteId = :noteId")
    fun getNoteWithLabelsById(noteId: Long): Flow<NoteWithLabels>

    @Transaction
    @Query("SELECT * FROM note_table WHERE isFavorite = 1")
    fun getFavoriteNotesWithLabels(): Flow<List<NoteWithLabels>>

    // ------------------------------------- Cross References(many-to-many)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRefs(crossList: List<CrossEntity>)

    @Query("DELETE FROM note_label_join WHERE noteId = :noteId")
    suspend fun deleteCrossRefs(noteId: Long)

    @Transaction
    suspend fun insertOrUpdateNoteWithLabels(note: NoteEntity, crossRefs: List<CrossEntity>): Long {
        val noteId = if (note.noteId != null && note.noteId != 0L) {
            updateNote(note)
            note.noteId
        } else {
            insertNote(note)
        }
        deleteCrossRefs(noteId)

        if (crossRefs.isNotEmpty()) {
            val updatedCrossRefs = crossRefs.map { crossRef ->
                crossRef.copy(noteId = noteId)
            }
            insertCrossRefs(updatedCrossRefs)
        }
        return noteId
    }

    @Transaction
    suspend fun replaceCrossRefs(noteId: Long, newCrossRefs: List<CrossEntity>) {
        deleteCrossRefs(noteId)
        if (newCrossRefs.isNotEmpty()) {
            insertCrossRefs(newCrossRefs)
        }
    }

}