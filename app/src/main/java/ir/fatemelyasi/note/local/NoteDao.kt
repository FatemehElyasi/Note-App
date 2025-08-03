package ir.fatemelyasi.note.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table ORDER BY updatedAt  Asc")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note_table WHERE id = :id")
    fun getNoteById(id: Int): Flow<NoteEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

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

    @Query("UPDATE note_table SET isFavorite = :isFavorite WHERE id = :noteId")
    suspend fun setFavorite(noteId: Long, isFavorite: Boolean)

    @Query("SELECT * FROM note_table WHERE label = :label")
    fun getNotesByLabel(label: String): Flow<List<NoteEntity>>

}