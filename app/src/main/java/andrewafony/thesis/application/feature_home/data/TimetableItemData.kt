package andrewafony.thesis.application.feature_home.data

import andrewafony.thesis.application.feature_home.domain.TimetableItemDomain
import com.google.firebase.firestore.GeoPoint
import java.util.*

data class TimetableItemData(
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
            data: Date,
            employee: String,
            link: String,
            name: String,
            place: GeoPoint,
            type: String,
        ): T

        class ToDomain : Mapper<TimetableItemDomain> {
            override fun map(
                id: String,
                data: Date,
                employee: String,
                link: String,
                name: String,
                place: GeoPoint,
                type: String,
            ): TimetableItemDomain {
                return TimetableItemDomain(id, data, employee, link, name, place, type)
            }
        }
    }

    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, date, employee, link, name, place, type)
}
