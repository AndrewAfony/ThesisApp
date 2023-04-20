package andrewafony.thesis.application.feature_home.presentation

import andrewafony.thesis.application.core.BaseItem
import com.google.firebase.firestore.GeoPoint
import java.util.Date

data class TimetableItemUi(
    val id: String,
    val dateDay: String,
    val dateMonth: String,
    val dateWeekDay: String,
    val startTime: String,
    val endTime: String,
    val employee: String,
    val link: String,
    val name: String,
    val place: GeoPoint,
    val placeName: String,
    val type: String,
    val isFirstClass: Boolean,
    val order: String
) : BaseItem {

    override fun id(): String = id

    override fun content(): String = "$name$dateDay$dateMonth$dateWeekDay$startTime$endTime$employee$link$place$type$order$placeName"
}
