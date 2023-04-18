package andrewafony.thesis.application.feature_home.presentation

import andrewafony.thesis.application.core.Communication
import andrewafony.thesis.application.core.Dispatchers
import andrewafony.thesis.application.feature_home.domain.TimetableInteractor
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val interactor: TimetableInteractor,
    private val timetableCommunication: TimetableCommunication,
    private val dispatchers: Dispatchers
): ViewModel() {

    fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            dispatchers.launchBackground(viewModelScope) {
                val res = interactor.getAllClasses()
                dispatchers.launchUi(this) {
                    timetableCommunication.map(res)
                }
            }
        }
    }

    fun observe(owner: LifecycleOwner, observer: Observer<List<TimetableItemUi>>) {
        timetableCommunication.observe(owner, observer)
    }
}

interface TimetableCommunication : Communication.Mutable<List<TimetableItemUi>> {

    class Base : Communication.Ui<List<TimetableItemUi>>(), TimetableCommunication
}