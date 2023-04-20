package andrewafony.thesis.application.feature_home.domain

import andrewafony.thesis.application.feature_home.presentation.TimetableItemUi
import andrewafony.thesis.application.feature_home.presentation.professor_info.DetailProfessorInfo
import com.google.firebase.firestore.DocumentReference

interface TimetableInteractor : ProfessorInfoInteractor {

    suspend fun getAllClasses() : List<TimetableItemUi>

    suspend fun getClassInfo(classId: String): TimetableItemUi

    class Base(
        private val repository: TimetableRepository,
        private val mapperToUi: TimetableItemDomain.Mapper<TimetableItemUi> = TimetableItemDomain.Mapper.ToUi
    ) : TimetableInteractor {

        override suspend fun getAllClasses(): List<TimetableItemUi> {
            val result = repository.timetable()
            return result.map { it.map(mapperToUi) }
        }

        override suspend fun getClassInfo(classId: String): TimetableItemUi {
            val result = repository.lesson(classId)
            return result.map(mapperToUi)
        }

        override suspend fun getProfessorInfo(professorRef: DocumentReference): DetailProfessorInfo {
            return repository.professorInfo(professorRef)
        }
    }
}

interface ProfessorInfoInteractor {

    suspend fun getProfessorInfo(professorRef: DocumentReference) : DetailProfessorInfo
}