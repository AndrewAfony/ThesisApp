package andrewafony.thesis.application.feature_deadlines.domain

import andrewafony.thesis.application.core.DispatchersName
import andrewafony.thesis.application.feature_deadlines.presentation.DeadlineItemUi
import kotlinx.coroutines.flow.*

interface DeadlinesInteractor {

    fun getDeadlines(filterByDone: Boolean): Flow<List<DeadlineItemUi>>

    suspend fun deadlinesByDiscipline(discipline: String) : List<DeadlineItemUi>

    suspend fun getDeadline(deadlineId: Int): DeadlineItemUi

    suspend fun addDeadline(deadlineItem: DeadlineItem)

    suspend fun deleteDeadline(deadline: DeadlineItem)

    suspend fun updateDeadline(deadline: DeadlineItem)

    class Base(
        private val repository: DeadlinesRepository,
        private val mapperToUi: DeadlineItem.Mapper<DeadlineItemUi> = DeadlineItem.Mapper.ToUi(),
        private val dispatchers: DispatchersName
    ) : DeadlinesInteractor {

        override fun getDeadlines(filterByDone: Boolean): Flow<List<DeadlineItemUi>> =
            repository
                .getDeadlines()
                .map { deadlines ->
                    deadlines
                        .sortedBy { it.date }
                        .map { it.map(mapperToUi) }
                        .filter {
                            if (filterByDone) it.isDone
                            else !it.isDone
                        }
                }
                .flowOn(dispatchers.backgroundName)

        override suspend fun deadlinesByDiscipline(discipline: String): List<DeadlineItemUi> {
            return repository.deadlinesByDiscipline(discipline).map { it.map(mapperToUi) }
        }

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