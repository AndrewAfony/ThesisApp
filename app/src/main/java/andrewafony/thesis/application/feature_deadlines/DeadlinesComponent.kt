package andrewafony.thesis.application.feature_deadlines

import andrewafony.thesis.application.core.Dispatchers
import andrewafony.thesis.application.di.Component
import andrewafony.thesis.application.feature_deadlines.data.BaseDeadlinesRepository
import andrewafony.thesis.application.feature_deadlines.data.local.DeadlinesCacheDataSource
import andrewafony.thesis.application.feature_deadlines.data.local.DeadlinesDao
import andrewafony.thesis.application.feature_deadlines.domain.DeadlinesInteractor
import andrewafony.thesis.application.feature_deadlines.presentation.DeadlinesCommunication
import andrewafony.thesis.application.feature_deadlines.presentation.DeadlinesViewModel
import andrewafony.thesis.application.feature_deadlines.presentation.FilterCommunication
import androidx.lifecycle.ViewModel

class DeadlinesComponent(
    private val deadlinesDatabaseDao: DeadlinesDao
): Component {

    override fun <T : ViewModel> viewModel(): T {
        val dispatchers = Dispatchers.Base()
        return DeadlinesViewModel(
            interactor = DeadlinesInteractor.Base(
                repository = BaseDeadlinesRepository(
                    cacheDataSource = DeadlinesCacheDataSource.Base(deadlinesDatabaseDao)
                ),
                dispatchers = dispatchers
            ),
            dispatchers = dispatchers,
            deadlinesCommunication = DeadlinesCommunication.Base(),
            filterCommunication = FilterCommunication.Base()
        ) as T
    }
}