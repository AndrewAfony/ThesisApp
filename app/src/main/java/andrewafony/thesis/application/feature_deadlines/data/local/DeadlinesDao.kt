package andrewafony.thesis.application.feature_deadlines.data.local

import andrewafony.thesis.application.feature_deadlines.domain.DeadlineItem
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DeadlinesDao {

    @Query("SELECT * FROM deadlines")
    fun allDeadlines() : Flow<List<DeadlineItem>>

    @Query("SELECT * FROM deadlines WHERE id = :deadlineId")
    fun deadline(deadlineId: Int) : DeadlineItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(deadline: DeadlineItem)

    @Delete
    fun delete(deadline: DeadlineItem)
}