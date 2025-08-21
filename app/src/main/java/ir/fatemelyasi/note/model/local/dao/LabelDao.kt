package ir.fatemelyasi.note.model.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ir.fatemelyasi.note.model.local.entity.LabelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LabelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLabel(label: LabelEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLabels(labels: List<LabelEntity>)

    @Query("SELECT * FROM label_table ORDER BY labelName ASC")
    fun getAllLabels(): Flow<List<LabelEntity>>

    @Query("SELECT * FROM label_table WHERE labelName = :label")
    fun getLabelsByName(label: String): Flow<List<LabelEntity>>

    @Update
    suspend fun updateLabel(label: LabelEntity)

    @Delete
    suspend fun deleteLabel(label: LabelEntity)

    @Query("DELETE FROM label_table WHERE labelId = :labelId")
    suspend fun deleteLabelById(labelId: Long)

    @Query("SELECT COUNT(*) FROM label_table")
    suspend fun getLabelsCount(): Long
}
