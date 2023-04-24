package andrewafony.thesis.application.feature_deadlines.data.local

import andrewafony.thesis.application.feature_deadlines.domain.DeadlineItem
import kotlinx.coroutines.flow.Flow

interface DeadlinesCacheDataSource {

    fun getDeadlines() : Flow<List<DeadlineItem>>

    suspend fun getDeadline(deadlineId: Int): DeadlineItem

    suspend fun addDeadline(deadline: DeadlineItem)

    suspend fun deleteDeadline(deadline: DeadlineItem)

    suspend fun updateDeadline(deadlineItem: DeadlineItem)

    class Base(
        private val dao: DeadlinesDao
    ) : DeadlinesCacheDataSource {

        override fun getDeadlines(): Flow<List<DeadlineItem>> = dao.allDeadlines()

        override suspend fun getDeadline(deadlineId: Int): DeadlineItem = dao.deadline(deadlineId)

        override suspend fun addDeadline(deadline: DeadlineItem) = dao.insert(deadline)

        override suspend fun deleteDeadline(deadline: DeadlineItem) = dao.delete(deadline)

        override suspend fun updateDeadline(deadlineItem: DeadlineItem) = dao.updateDeadline(deadlineItem)
    }
}