package andrewafony.thesis.application.feature_home.data

import andrewafony.thesis.application.feature_home.data.cloud.TimetableCloudDataSource
import andrewafony.thesis.application.feature_home.domain.TimetableRepository
import andrewafony.thesis.application.feature_home.domain.TimetableItemDomain
import andrewafony.thesis.application.feature_home.presentation.professor_info.DetailProfessorInfo
import com.google.firebase.firestore.DocumentReference

class BaseTimetableRepository(
    private val cloudDataSource: TimetableCloudDataSource,
    private val mapperToDomain: TimetableItemData.Mapper<TimetableItemDomain> = TimetableItemData.Mapper.ToDomain(),
) : TimetableRepository {

    override suspend fun timetable(): List<TimetableItemDomain> {
        val result = cloudDataSource.allClasses()
        return result.map { it.map(mapperToDomain) }
    }

    override suspend fun lesson(classId: String): TimetableItemDomain {
        val result = cloudDataSource.classInfo(classId)
        return result.map(mapperToDomain)
    }

    override suspend fun professorInfo(professorRef: DocumentReference): DetailProfessorInfo {
        return cloudDataSource.professorInfo(professorRef)
    }
}