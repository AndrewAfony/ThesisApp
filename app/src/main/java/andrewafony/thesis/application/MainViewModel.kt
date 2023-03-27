package andrewafony.thesis.application

import andrewafony.thesis.application.core.Communication
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class MainViewModel(
    private val navigationCommunication: NavigationCommunication
): ViewModel(), MainNavigation {

    fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            navigationCommunication.map(Navigation.Replace(Screen.Home))
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
}

interface MainNavigation {

    fun navigate(navigation: Navigation)

    fun navigateBack()

    fun observeNavigation(owner: LifecycleOwner, observer: Observer<Navigation>)
}

interface NavigationCommunication: Communication.Mutable<Navigation> {

    class Base : Communication.Ui<Navigation>(), NavigationCommunication
}