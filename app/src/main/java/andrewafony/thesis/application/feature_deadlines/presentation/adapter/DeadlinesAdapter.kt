package andrewafony.thesis.application.feature_deadlines.presentation.adapter

import andrewafony.thesis.application.core.adapter.BaseAdapter
import andrewafony.thesis.application.feature_deadlines.presentation.DeadlineItemUi

class DeadlinesAdapter(
    private val deadlinesViewHolderFabric: DeadlinesViewHolderFabric
): BaseAdapter<DeadlineItemUi>(deadlinesViewHolderFabric)