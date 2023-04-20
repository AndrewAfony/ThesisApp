package andrewafony.thesis.application.feature_home.data.cloud

import andrewafony.thesis.application.feature_home.data.Place
import andrewafony.thesis.application.feature_home.data.TimetableItemData
import andrewafony.thesis.application.feature_home.presentation.professor_info.DetailProfessorInfo
import andrewafony.thesis.application.feature_home.presentation.professor_info.ProfessorInfo
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

    suspend fun getProfessorInfo(professorRef: DocumentReference): DetailProfessorInfo

    class Base : FirestoreService {

        private val group =
            "19_bi_1" //todo(получать группу в зависимости от авторизованного пользователя)

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
                val resultPlace = Place(placeArray[1] as GeoPoint, placeArray[0] as String)

                val employee = it.get("employee") as Map<*, *>
                val teacher = ProfessorInfo(
                    photo = employee.get("photo") as String,
                    info = employee.get("info") as DocumentReference
                )

                resultList.add(TimetableItemData(
                    id = it.id,
                    date = (it.get("date") as Timestamp).toDate(),
                    employee = teacher,
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

            val place = first.get("place") as? Map<*, *>
            val link = place?.get("link").toString()

            val placeArray = (place?.get("place") as? Array<*>)
            val resultPlace = Place(placeArray?.get(1) as GeoPoint, placeArray[0] as String)

            val employee = first.get("employee") as Map<*, *>
            val teacher = ProfessorInfo(
                photo = employee.get("photo") as String,
                info = employee.get("info") as DocumentReference
            )

            return TimetableItemData(
                id = classId,
                date = (first.get("date") as Timestamp).toDate(),
                employee = teacher,
                link = link,
                name = first.get("name") as String,
                place = resultPlace,
                type = first.get("type") as String
            )
        }

        override suspend fun getProfessorInfo(professorRef: DocumentReference): DetailProfessorInfo {
            val result = professorRef
                .get()
                .await()

            return DetailProfessorInfo(
                name = result.get("name") as String,
                photo = result.get("photo") as String,
                birthday = result.get("birthday") as String,
                email = result.get("email") as String,
                phone_number = result.get("phone_number") as String,
                position = result.get("position") as String
            )
        }
    }
}