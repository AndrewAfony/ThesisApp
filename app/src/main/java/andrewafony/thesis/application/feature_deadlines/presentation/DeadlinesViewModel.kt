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
import java.util.logging.Filter

class DeadlinesViewModel(
    private val interactor: DeadlinesInteractor,
    private val dispatchers: Dispatchers,
    private val deadlinesCommunication: DeadlinesCommunication,
    private val filterCommunication: FilterCommunication,
    private val mapperToDatabaseEntity: DeadlineItemUi.Mapper.ToDatabaseEntity = DeadlineItemUi.Mapper.ToDatabaseEntity()
): ViewModel() {

    fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            loadDeadlines(false)
            filterCommunication.map(false)
        }
    }

    fun addDeadline(deadline: DeadlineItem) {
        dispatchers.launchBackground(viewModelScope) {
            interactor.addDeadline(deadline)
        }
    }

    fun loadDeadlines(filterByDone: Boolean) {
        dispatchers.launchBackground(viewModelScope) {
            interactor.getDeadlines(filterByDone).collectLatest { deadlines ->
                dispatchers.launchUi(this) {
                    deadlinesCommunication.map(deadlines)
                }
            }
        }
    }

    fun filterDeadlines() {
        val value = filterCommunication.value ?: false
        filterCommunication.map(!value)
        loadDeadlines(!value)
    }

    fun updateDoneState(deadline: DeadlineItemUi) {
        dispatchers.launchBackground(viewModelScope) {
            interactor.updateDeadline(deadline.map(mapperToDatabaseEntity))
        }
    }

    fun observeDeadlines(owner: LifecycleOwner, observer: Observer<List<DeadlineItemUi>>) {
        deadlinesCommunication.observe(owner, observer)
    }

    fun observeFilterByDone(owner: LifecycleOwner, observer: Observer<Boolean>) {
        filterCommunication.observe(owner, observer)
    }
}

interface DeadlinesCommunication : Communication.Mutable<List<DeadlineItemUi>> {

    class Base : Communication.Ui<List<DeadlineItemUi>>(), DeadlinesCommunication
}

interface FilterCommunication : Communication.Mutable<Boolean>, Communication.Getter<Boolean> {

    class Base : Communication.Get<Boolean>(), FilterCommunication
}