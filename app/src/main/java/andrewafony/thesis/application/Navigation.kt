package andrewafony.thesis.application

import andrewafony.thesis.application.feature_login.presentation.FragmentLogin
import andrewafony.thesis.application.feature_main.presentation.FragmentHome
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

interface Navigation {

    fun navigate(manager: FragmentManager, container: Int = R.id.fragment_container)

    fun screen(): Screen

    abstract class Abstract(private val screen: Screen = Screen.Home) : Navigation {

        override fun screen(): Screen = screen
    }

    data class Add(private val screen: Screen) : Abstract(screen) {
        override fun navigate(manager: FragmentManager, container: Int) = manager.commit {
            setReorderingAllowed(true)
            add(container, screen.fragment, null)
            addToBackStack(screen.fragment.simpleName)
        }
    }

    data class Replace(private val screen: Screen) : Abstract(screen) {
        override fun navigate(manager: FragmentManager, container: Int) = manager.commit {
            replace(container, screen.fragment, null)
        }
    }

    object Back : Abstract() {
        override fun navigate(manager: FragmentManager, container: Int) {
            manager.popBackStack()
        }
    }
}

interface Screen {

    val fragment: Class<out Fragment>

    object Home : Screen {
        override val fragment: Class<out Fragment>
            get() = FragmentHome::class.java
    }

    object Login : Screen {
        override val fragment: Class<out Fragment>
            get() = FragmentLogin::class.java
    }
}