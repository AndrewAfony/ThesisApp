package andrewafony.thesis.application.feature_deadlines.domain

import andrewafony.thesis.application.feature_deadlines.presentation.DeadlineItemUi
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "deadlines")
data class DeadlineItem(
    @PrimaryKey(true) val id: Int,
    val deadline: String,
    val isDone: Boolean,
    val description: String?,
    val date: Date?
) {

    interface Mapper<T> {

        fun map(id: Int, deadline: String, isDone: Boolean, description: String?, date: Date?): T

        class ToUi : Mapper<DeadlineItemUi> {

            override fun map(
                id: Int,
                deadline: String,
                isDone: Boolean,
                description: String?,
                date: Date?
            ): DeadlineItemUi {
                return DeadlineItemUi(id, deadline, isDone, description, date)
            }
        }
    }

    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, deadline, isDone, description, date)
}