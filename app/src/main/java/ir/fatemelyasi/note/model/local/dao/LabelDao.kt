package ir.fatemelyasi.note.model.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ir.fatemelyasi.note.model.local.entity.LabelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LabelDao {
    @Insert
    suspend fun insertLabel(label: LabelEntity)

    @Query("SELECT * FROM label_table")
    fun getAllLabels(): Flow<List<LabelEntity>>

    @Query("SELECT * FROM label_table WHERE labelName = :label")
    fun getByLabelName(label: String): Flow<List<LabelEntity>>

    @Update
    suspend fun updateLabel(label: LabelEntity)

    @Delete
    suspend fun deleteLabel(label: LabelEntity)
}