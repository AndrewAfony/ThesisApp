package andrewafony.thesis.application.feature_deadlines.data.local

import andrewafony.thesis.application.feature_deadlines.domain.DeadlineItem
import kotlinx.coroutines.flow.Flow

interface DeadlinesCacheDataSource {

    suspend fun getDeadlines() : Flow<List<DeadlineItem>>

    suspend fun getDeadline(deadlineId: Int): DeadlineItem

    suspend fun addDeadline(deadline: DeadlineItem)

    suspend fun deleteDeadline(deadline: DeadlineItem)

    class Base(
        private val dao: DeadlinesDao
    ) : DeadlinesCacheDataSource {

        override suspend fun getDeadlines(): Flow<List<DeadlineItem>> = dao.allDeadlines()

        override suspend fun getDeadline(deadlineId: Int): DeadlineItem = dao.deadline(deadlineId)

        override suspend fun addDeadline(deadline: DeadlineItem) = dao.insert(deadline)

        override suspend fun deleteDeadline(deadline: DeadlineItem) = dao.delete(deadline)
    }
}