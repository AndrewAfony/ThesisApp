package andrewafony.thesis.application

import andrewafony.thesis.application.core.Communication
import andrewafony.thesis.application.core.Dispatchers
import andrewafony.thesis.application.feature_home.domain.TimetableInteractor
import andrewafony.thesis.application.feature_home.presentation.TimetableItemUi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class MainViewModel(
    private val navigationCommunication: NavigationCommunication,
    private val timetableCommunication: TimetableCommunication,
    private val interactor: TimetableInteractor,
    private val dispatchers: Dispatchers
): ViewModel(), MainNavigation, ObserveTimetable {

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

    override fun navigate(navigation: Navigation) {
        navigationCommunication.map(navigation)
    }

    override fun navigateBack() {
        navigationCommunication.map(Navigation.Back)
    }

    override fun observeNavigation(owner: LifecycleOwner, observer: Observer<Navigation>) {
        navigationCommunication.observe(owner, observer)
    }

    override fun observeTimetable(owner: LifecycleOwner, observer: Observer<List<TimetableItemUi>>) {
        timetableCommunication.observe(owner, observer)
    }
}

interface MainNavigation {

    fun navigate(navigation: Navigation)

    fun navigateBack()

    fun observeNavigation(owner: LifecycleOwner, observer: Observer<Navigation>)
}

interface ObserveTimetable {

    fun observeTimetable(owner: LifecycleOwner, observer: Observer<List<TimetableItemUi>>)
}

interface NavigationCommunication: Communication.Mutable<Navigation> {

    class Base : Communication.Ui<Navigation>(), NavigationCommunication
}

interface TimetableCommunication : Communication.Mutable<List<TimetableItemUi>> {

    class Base : Communication.Ui<List<TimetableItemUi>>(), TimetableCommunication
}