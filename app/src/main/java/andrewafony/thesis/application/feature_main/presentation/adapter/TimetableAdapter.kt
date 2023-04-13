package andrewafony.thesis.application.feature_main.presentation.adapter

import andrewafony.thesis.application.core.adapter.BaseAdapter
import andrewafony.thesis.application.feature_main.presentation.TimetableItemUi

class TimetableAdapter(
    private val viewHolderFabric: TimetableViewHolderFabric
): BaseAdapter<TimetableItemUi>(viewHolderFabric)