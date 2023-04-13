package andrewafony.thesis.application.feature_main.presentation

import andrewafony.thesis.application.core.BaseItem

data class TimetableItemUi(
    val id: Int,
    val name: String,
    val time: String,
) : BaseItem {

    override fun id(): Int = id

    override fun content(): String = "$name$time"
}
