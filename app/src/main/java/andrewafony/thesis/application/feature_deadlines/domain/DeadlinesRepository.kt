package andrewafony.thesis.application.feature_deadlines.domain

import kotlinx.coroutines.flow.Flow

interface DeadlinesRepository {

    fun getDeadlines() : Flow<List<DeadlineItem>>

    suspend fun getDeadline(deadlineId: Int) : DeadlineItem

    suspend fun addDeadline(deadlineItem: DeadlineItem)

    suspend fun deleteDeadline(deadline: DeadlineItem)
}