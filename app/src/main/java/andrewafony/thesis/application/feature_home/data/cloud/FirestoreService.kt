package andrewafony.thesis.application.feature_home.data.cloud

import andrewafony.thesis.application.feature_home.data.TimetableItemData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.Date

interface FirestoreService {

    suspend fun gelClasses(): List<TimetableItemData>

    suspend fun getClassInfo(classTime: String): TimetableItemData

    class Base : FirestoreService {

        private val group = "19_bi_1" //todo(получать группу в зависимости от авторизованного пользователя)

        private val database = Firebase.firestore
        private val classes = database.collection("classes")
            .document("group_$group")
            .collection("group_classes")

        override suspend fun gelClasses(): List<TimetableItemData> {

            val resultList = mutableListOf<TimetableItemData>()

            val ref = classes
                .whereGreaterThan("date", Timestamp.now())
                .orderBy("date")
                .get()
                .await()

            ref.documents.forEach {
                val teacherRef = it.get("employee") as DocumentReference
                val teacherName = teacherRef.get().await().get("name") as String
                resultList.add(TimetableItemData(
                    id = it.id,
                    date = (it.get("date") as Timestamp).toDate(),
                    employee = teacherName,
                    link = it.get("link") as String,
                    name = it.get("name") as String,
                    place = it.get("place") as GeoPoint,
                    type = it.get("type") as String
                ))
            }

            return resultList
        }

        override suspend fun getClassInfo(classTime: String): TimetableItemData {
            return TimetableItemData("",Date(), "", "", "", GeoPoint(12.3, 23.3), "")
        }
    }
}

fun String.createDate() : String {
    return this.substringAfter("(").substringBefore(",")
}