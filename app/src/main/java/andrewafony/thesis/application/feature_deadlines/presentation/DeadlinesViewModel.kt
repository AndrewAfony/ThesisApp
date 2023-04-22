package andrewafony.thesis.application.feature_deadlines.presentation

import andrewafony.thesis.application.core.Communication
import andrewafony.thesis.application.core.Dispatchers
import andrewafony.thesis.application.feature_deadlines.domain.DeadlineItem
import andrewafony.thesis.application.feature_deadlines.domain.DeadlinesInteractor
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DeadlinesViewModel(
    private val interactor: DeadlinesInteractor,
    private val dispatchers: Dispatchers,
    private val deadlinesCommunication: DeadlinesCommunication
): ViewModel() {

    fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            loadDeadlines()
        }
    }

    fun addDeadline(deadline: DeadlineItem) {
        dispatchers.launchBackground(viewModelScope) {
            interactor.addDeadline(deadline)
        }
    }

    fun loadDeadlines() {
        dispatchers.launchBackground(viewModelScope) {
            interactor.getDeadlines().collectLatest { deadlines ->
                dispatchers.launchUi(this) {
                    deadlinesCommunication.map(deadlines)
                }
            }
        }
    }

    fun observeDeadlines(owner: LifecycleOwner, observer: Observer<List<DeadlineItemUi>>) {
        deadlinesCommunication.observe(owner, observer)
    }
}

interface DeadlinesCommunication : Communication.Mutable<List<DeadlineItemUi>> {

    class Base : Communication.Ui<List<DeadlineItemUi>>(), DeadlinesCommunication
}