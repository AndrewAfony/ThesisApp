package andrewafony.thesis.application.feature_home.data.cloud

import andrewafony.thesis.application.feature_home.data.Place
import andrewafony.thesis.application.feature_home.data.TimetableItemData
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface FirestoreService {

    suspend fun gelClasses(): List<TimetableItemData>

    suspend fun getClassInfo(classId: String): TimetableItemData

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
                .addOnSuccessListener {
                    Log.d("MyHelper", "gelClasses success: ${it.documents}")
                }
                .addOnFailureListener {
                    Log.d("MyHelper", "gelClasses error: ${it.localizedMessage}")
                }
                .await()

            ref.documents.forEach {

                val place = it.get("place") as? Map<*, *>
                val link = place?.get("link").toString()

                val placeArray = (place?.get("place_info") as List<*>)
                val resultPlace = Place(placeArray.get(1) as GeoPoint, placeArray[0] as String)

                val teacherRef = it.get("employee") as DocumentReference
                val teacherName = teacherRef.get().await().get("name") as String

                resultList.add(TimetableItemData(
                    id = it.id,
                    date = (it.get("date") as Timestamp).toDate(),
                    employee = teacherName,
                    link = link,
                    name = it.get("name") as String,
                    place = resultPlace,
                    type = it.get("type") as String
                ))
            }

            return resultList
        }

        override suspend fun getClassInfo(classId: String): TimetableItemData {
            val result = classes
                .whereEqualTo(FieldPath.documentId(), classId)
                .get()
                .await()

            val first = result.documents[0]

            val teacherRef = first.get("employee") as DocumentReference
            val teacherName = teacherRef.get().await().get("name") as String

            val place = first.get("place") as? Map<*, *>
            val link = place?.get("link").toString()

            val placeArray = (place?.get("place") as? Array<*>)
            val resultPlace = Place(placeArray?.get(1) as GeoPoint, placeArray[0] as String)

            return TimetableItemData(
                id = classId,
                date = (first.get("date") as Timestamp).toDate(),
                employee = teacherName,
                link = link,
                name = first.get("name") as String,
                place = resultPlace,
                type = first.get("type") as String
            )
        }
    }
}