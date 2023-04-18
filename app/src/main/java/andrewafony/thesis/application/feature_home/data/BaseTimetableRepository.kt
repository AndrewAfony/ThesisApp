package andrewafony.thesis.application.feature_home.data

import andrewafony.thesis.application.feature_home.data.cloud.CloudDataSource
import andrewafony.thesis.application.feature_home.domain.TimetableRepository
import andrewafony.thesis.application.feature_home.domain.TimetableItemDomain

class BaseTimetableRepository(
    private val cloudDataSource: CloudDataSource,
    private val mapperToDomain: TimetableItemData.Mapper<TimetableItemDomain> = TimetableItemData.Mapper.ToDomain(),
) : TimetableRepository {

    override suspend fun timetable(): List<TimetableItemDomain> {
        val result = cloudDataSource.allClasses()
        return result.map { it.map(mapperToDomain) }
    }

    override suspend fun lesson(): TimetableItemDomain {
        val result = cloudDataSource.classInfo("")
        return result.map(mapperToDomain)
    }
}