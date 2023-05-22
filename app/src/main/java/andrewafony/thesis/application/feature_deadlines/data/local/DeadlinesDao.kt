package andrewafony.thesis.application.feature_deadlines.data.local

import andrewafony.thesis.application.feature_deadlines.domain.DeadlineItem
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DeadlinesDao {

    @Query("SELECT * FROM deadlines")
    fun allDeadlines() : Flow<List<DeadlineItem>>

    @Query("SELECT * FROM deadlines WHERE discipline = :discipline")
    suspend fun deadlinesByDiscipline(discipline: String): List<DeadlineItem>

    @Query("SELECT * FROM deadlines WHERE id = :deadlineId")
    suspend fun deadline(deadlineId: Int) : DeadlineItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(deadline: DeadlineItem)

    @Delete
    suspend fun delete(deadline: DeadlineItem)

    @Update(entity = DeadlineItem::class)
    suspend fun updateDeadline(deadline: DeadlineItem)
}