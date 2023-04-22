package andrewafony.thesis.application.feature_deadlines.presentation

import andrewafony.thesis.application.core.BaseItem
import java.util.Date

data class DeadlineItemUi(
    val id: Int,
    val deadlineText: String,
    val isDone: Boolean,
    val discipline: String?,
    val description: String?,
    val date: Date?
): BaseItem {

    override fun id(): String = id.toString()

    override fun content(): String = "$deadlineText$isDone$discipline$description$date"
}
