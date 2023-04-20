package andrewafony.thesis.application.feature_home.data.cloud

import andrewafony.thesis.application.feature_home.data.TimetableItemData
import andrewafony.thesis.application.feature_home.presentation.professor_info.DetailProfessorInfo
import com.google.firebase.firestore.DocumentReference

interface TimetableCloudDataSource: ProfessorCloudDataSource {

    suspend fun allClasses() : List<TimetableItemData>

    suspend fun classInfo(classId: String) : TimetableItemData

    class Base(
        private val firestoreService: FirestoreService
    ) : TimetableCloudDataSource {

        override suspend fun allClasses(): List<TimetableItemData> {
            return firestoreService.gelClasses()
        }

        override suspend fun classInfo(classId: String): TimetableItemData {
            return firestoreService.getClassInfo(classId)
        }

        override suspend fun professorInfo(professorRef: DocumentReference): DetailProfessorInfo {
            return firestoreService.getProfessorInfo(professorRef)
        }
    }
}

interface ProfessorCloudDataSource {

    suspend fun professorInfo(professorRef: DocumentReference) : DetailProfessorInfo
}