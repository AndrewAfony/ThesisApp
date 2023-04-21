package andrewafony.thesis.application.feature_deadlines.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "deadlines")
data class DeadlineItem(
    @PrimaryKey(true) val id: Int,
    val deadline: String,
    val description: String?,
    val date: Date?
)