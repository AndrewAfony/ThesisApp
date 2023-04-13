package andrewafony.thesis.application.feature_main.data

import andrewafony.thesis.application.feature_main.data.cloud.CloudDataSource
import andrewafony.thesis.application.feature_main.domain.TimetableRepository
import andrewafony.thesis.application.feature_main.domain.model.TimetableItemDomain

class BaseTimetableRepository(
    private val cloudDataSource: CloudDataSource,
) : TimetableRepository {

    override fun timetable(): List<TimetableItemDomain> {
        val result = cloudDataSource.allClasses()
        return result.map { it.map() }
    }

    override fun lesson(): TimetableItemDomain {
        return TimetableItemDomain("")
    }
}