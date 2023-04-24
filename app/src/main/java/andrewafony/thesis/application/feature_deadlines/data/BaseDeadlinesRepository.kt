package andrewafony.thesis.application.feature_deadlines.data

import andrewafony.thesis.application.feature_deadlines.data.local.DeadlinesCacheDataSource
import andrewafony.thesis.application.feature_deadlines.domain.DeadlineItem
import andrewafony.thesis.application.feature_deadlines.domain.DeadlinesRepository
import kotlinx.coroutines.flow.Flow

class BaseDeadlinesRepository(
    private val cacheDataSource: DeadlinesCacheDataSource
): DeadlinesRepository {

    override fun getDeadlines(): Flow<List<DeadlineItem>> {
        return cacheDataSource.getDeadlines()
    }

    override suspend fun getDeadline(deadlineId: Int): DeadlineItem {
        return cacheDataSource.getDeadline(deadlineId)
    }

    override suspend fun addDeadline(deadlineItem: DeadlineItem) {
        return cacheDataSource.addDeadline(deadlineItem)
    }

    override suspend fun deleteDeadline(deadline: DeadlineItem) {
        return cacheDataSource.deleteDeadline(deadline)
    }

    override suspend fun updateDeadline(deadline: DeadlineItem) {
        return cacheDataSource.updateDeadline(deadline)
    }
}