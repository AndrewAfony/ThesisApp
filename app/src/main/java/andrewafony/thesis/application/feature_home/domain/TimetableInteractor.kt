package andrewafony.thesis.application.feature_home.domain

import andrewafony.thesis.application.feature_home.presentation.TimetableItemUi

interface TimetableInteractor {

    suspend fun getAllClasses() : List<TimetableItemUi>

    class Base(
        private val repository: TimetableRepository,
        private val mapperToUi: TimetableItemDomain.Mapper<TimetableItemUi> = TimetableItemDomain.Mapper.ToUi
    ) : TimetableInteractor {

        override suspend fun getAllClasses(): List<TimetableItemUi> {
            val result = repository.timetable()
            return result.map { it.map(mapperToUi) }
        }
    }
}