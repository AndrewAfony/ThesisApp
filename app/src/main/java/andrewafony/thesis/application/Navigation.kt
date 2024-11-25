package andrewafony.thesis.application

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions

interface Navigation {

    fun navigate(navController: NavController)

    fun screen(): Screen

    abstract class Abstract(private val screen: Screen = Screen.Home) : Navigation {

        override fun screen(): Screen = screen
    }

    data class Open(private val screen: Screen) : Abstract(screen) {
        override fun navigate(navController: NavController) {
            navController.navigate(screen.fragment)
        }
    }

    data class Replace(private val screen: Screen) : Abstract(screen) {
        override fun navigate(navController: NavController) {
            navController.navigate(screen.fragment, null, navOptions {
//                popUpTo()
            })
        }
    }

    object Back : Abstract() {
        override fun navigate(navController: NavController) {
            navController.popBackStack()
        }
    }
}

interface Screen {

    val fragment: Int

    object Home : Screen {
        override val fragment: Int
            get() = R.id.fragmentHome
    }

    object UserProfile : Screen {
        override val fragment: Int
            get() = R.id.fragmentProfile
    }

//    object Search : Screen {
//        override val fragment: Int
//            get() = FragmentSearch::class.java
//    }
//
//    object News : Screen {
//        override val fragment: Class<out Fragment>
//            get() = FragmentNews::class.java
//    }
//
//    object Deadlines : Screen {
//        override val fragment: Class<out Fragment>
//            get() = FragmentDeadlines::class.java
//    }

    object Notifications : Screen {
        override val fragment: Int
            get() = R.id.fragmentNotifications
    }

    object DetailClassInfo : Screen {
        override val fragment: Int
            get() = R.id.fragmentDetailClassInfo
    }

    object FullProfileImage : Screen {
        override val fragment: Int
            get() = R.id.action_global_fragmentFullProfileImage
    }

//    object Login : Screen {
//        override val fragment: Class<out Fragment>
//            get() = FragmentLogin::class.java
//    }
}