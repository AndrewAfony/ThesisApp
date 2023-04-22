package andrewafony.thesis.application.feature_deadlines.domain

import andrewafony.thesis.application.feature_deadlines.presentation.DeadlineItemUi
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "deadlines")
data class DeadlineItem(
    @PrimaryKey(true) val id: Int = 0,
    val deadline: String,
    val isDone: Boolean = false,
    val discipline: String? = null,
    val description: String? = null,
    val date: Date? = null
) {

    interface Mapper<T> {

        fun map(
            id: Int,
            deadline: String,
            isDone: Boolean,
            discipline: String?,
            description: String?,
            date: Date?,
        ): T

        class ToUi : Mapper<DeadlineItemUi> {

            override fun map(
                id: Int,
                deadline: String,
                isDone: Boolean,
                discipline: String?,
                description: String?,
                date: Date?,
            ): DeadlineItemUi {
                return DeadlineItemUi(id, deadline, isDone, discipline, description, date)
            }
        }
    }

    fun <T> map(mapper: Mapper<T>): T =
        mapper.map(id, deadline, isDone, discipline, description, date)
}