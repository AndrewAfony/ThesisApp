package andrewafony.thesis.application.feature_home.domain

import andrewafony.thesis.application.feature_home.presentation.TimetableItemUi
import com.google.firebase.firestore.GeoPoint
import java.text.SimpleDateFormat
import java.util.*

data class TimetableItemDomain(
    val id: String,
    val date: Date,
    val employee: String,
    val link: String,
    val name: String,
    val place: GeoPoint,
    val type: String,
) {

    interface Mapper<T> {

        fun map(
            id: String,
            date: Date,
            employee: String,
            link: String,
            name: String,
            place: GeoPoint,
            type: String,
        ): T

        object ToUi : Mapper<TimetableItemUi> {

            private val calendar = Calendar.getInstance()
            private var currentDate = ""

            override fun map(
                id: String,
                date: Date,
                employee: String,
                link: String,
                name: String,
                place: GeoPoint,
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

                // todo (order)
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
                    place,
                    type,
                    isFirstClass)
            }
        }
    }

    fun <T> map(mapper: Mapper<T>) =
        mapper.map(id, date, employee, link, name, place, type)
}