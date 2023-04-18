package andrewafony.thesis.application.feature_home.data.cloud

import andrewafony.thesis.application.feature_home.data.TimetableItemData

interface CloudDataSource {

    suspend fun allClasses() : List<TimetableItemData>

    suspend fun classInfo(time: String) : TimetableItemData

    class Base(
        private val firestoreService: FirestoreService
    ) : CloudDataSource {

        override suspend fun allClasses(): List<TimetableItemData> {
            return firestoreService.gelClasses()
        }

        override suspend fun classInfo(time: String): TimetableItemData {
            return firestoreService.getClassInfo(time)
        }
    }
}