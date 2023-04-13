package andrewafony.thesis.application.feature_main.data.cloud

import andrewafony.thesis.application.feature_main.data.TimetableItemData

interface CloudDataSource {

    fun allClasses() : List<TimetableItemData>
}