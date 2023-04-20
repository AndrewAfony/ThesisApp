package andrewafony.thesis.application.feature_home.domain

import andrewafony.thesis.application.feature_home.presentation.professor_info.DetailProfessorInfo
import com.google.firebase.firestore.DocumentReference

interface TimetableRepository: ProfessorRepository {

    suspend fun timetable() : List<TimetableItemDomain>

    suspend fun lesson(classId: String): TimetableItemDomain
}

interface ProfessorRepository {

    suspend fun professorInfo(professorRef: DocumentReference): DetailProfessorInfo
}