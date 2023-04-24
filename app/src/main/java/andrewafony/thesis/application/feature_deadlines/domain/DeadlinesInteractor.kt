package andrewafony.thesis.application.feature_deadlines.domain

import andrewafony.thesis.application.core.Dispatchers
import andrewafony.thesis.application.core.DispatchersName
import andrewafony.thesis.application.feature_deadlines.presentation.DeadlineItemUi
import kotlinx.coroutines.flow.*

interface DeadlinesInteractor {

    suspend fun getDeadlines(): Flow<List<DeadlineItemUi>>

    suspend fun getDeadline(deadlineId: Int): DeadlineItemUi

    suspend fun addDeadline(deadlineItem: DeadlineItem)

    suspend fun deleteDeadline(deadline: DeadlineItem)

    suspend fun updateDeadline(deadline: DeadlineItem)

    class Base(
        private val repository: DeadlinesRepository,
        private val mapperToUi: DeadlineItem.Mapper<DeadlineItemUi> = DeadlineItem.Mapper.ToUi(),
        private val dispatchers: DispatchersName
    ) : DeadlinesInteractor {

        override suspend fun getDeadlines(): Flow<List<DeadlineItemUi>> =
            // todo (return map<Date, List<DeadlineItemUi>>)
            repository
                .getDeadlines()
                .map { deadlines ->
                    deadlines
                        .sortedBy { it.date }
                        .map { it.map(mapperToUi) }
                        .filter { !it.isDone }
                }
                .flowOn(dispatchers.backgroundName)

        override suspend fun getDeadline(deadlineId: Int): DeadlineItemUi {
            val result = repository.getDeadline(deadlineId)
            return result.map(mapperToUi)
        }

        override suspend fun addDeadline(deadlineItem: DeadlineItem) {
            repository.addDeadline(deadlineItem)
        }

        override suspend fun deleteDeadline(deadline: DeadlineItem) {
            repository.deleteDeadline(deadline)
        }

        override suspend fun updateDeadline(deadline: DeadlineItem) {
            repository.updateDeadline(deadline)
        }
    }
}