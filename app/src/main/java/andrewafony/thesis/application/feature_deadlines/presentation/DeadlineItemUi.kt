package andrewafony.thesis.application.feature_deadlines.presentation

import java.util.Date

data class DeadlineItemUi(
    val id: Int,
    val deadlineText: String,
    val isDone: Boolean,
    val description: String?,
    val date: Date?
)
