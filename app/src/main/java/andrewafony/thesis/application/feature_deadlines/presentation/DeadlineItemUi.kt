package andrewafony.thesis.application.feature_deadlines.presentation

import andrewafony.thesis.application.core.BaseItem
import andrewafony.thesis.application.feature_deadlines.domain.DeadlineItem
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

    interface Mapper <T> {

        fun map(id: Int, deadlineText: String, isDone: Boolean, discipline: String?, description: String?, date: Date?) : T

        class ToDatabaseEntity : Mapper<DeadlineItem> {

            override fun map(
                id: Int,
                deadlineText: String,
                isDone: Boolean,
                discipline: String?,
                description: String?,
                date: Date?,
            ): DeadlineItem {
                return DeadlineItem(id, deadlineText, isDone, discipline, description, date)
            }
        }
    }

    fun <T> map(mapper: Mapper<T>) : T = mapper.map(id, deadlineText, isDone, discipline, description, date)
}
