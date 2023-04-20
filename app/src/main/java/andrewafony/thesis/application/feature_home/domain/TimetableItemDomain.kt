package andrewafony.thesis.application.feature_home.domain

import andrewafony.thesis.application.feature_home.data.Place
import andrewafony.thesis.application.feature_home.presentation.TimetableItemUi
import andrewafony.thesis.application.feature_home.presentation.professor_info.ProfessorInfo
import com.google.firebase.firestore.GeoPoint
import java.text.SimpleDateFormat
import java.util.*

data class TimetableItemDomain(
    val id: String,
    val date: Date,
    val employee: ProfessorInfo,
    val link: String,
    val name: String,
    val place: Place,
    val type: String,
) {

    interface Mapper<T> {

        fun map(
            id: String,
            date: Date,
            employee: ProfessorInfo,
            link: String,
            name: String,
            place: Place,
            type: String,
        ): T

        object ToUi : Mapper<TimetableItemUi> {

            private val calendar = Calendar.getInstance()
            private var currentDate = ""

            override fun map(
                id: String,
                date: Date,
                employee: ProfessorInfo,
                link: String,
                name: String,
                place: Place,
                type: String,
            ): TimetableItemUi {

                calendar.time = date
                calendar.add(Calendar.SECOND, 4800)

                val dateDay = SimpleDateFormat("d", Locale.getDefault()).format(date)
                val month = SimpleDateFormat("MMM", Locale.getDefault()).format(date)
                val weekDay = SimpleDateFormat("EEE", Locale.getDefault()).format(date)

                val startTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(date)
                val endTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)

                var isFirstClass: Boolean

                if (dateDay != currentDate) {
                    currentDate = dateDay
                    isFirstClass = true
                } else {
                    isFirstClass = false
                }

                val order = startTime.order()

                return TimetableItemUi(
                    id,
                    dateDay,
                    month,
                    weekDay,
                    startTime,
                    endTime,
                    employee,
                    link,
                    name,
                    place.coordinates,
                    place.placeName,
                    type,
                    isFirstClass,
                    order
                )
            }

            private fun String.order(): String {
                return when(this) {
                    "08:00" -> "1"
                    "09:30" -> "2"
                    "11:10" -> "3"
                    "13:00" -> "4"
                    "14:40" -> "5"
                    "16:20" -> "6"
                    "18:10" -> "7"
                    else -> "1"
                }
            }
        }
    }

    fun <T> map(mapper: Mapper<T>) =
        mapper.map(id, date, employee, link, name, place, type)
}