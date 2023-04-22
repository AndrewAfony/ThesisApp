package andrewafony.thesis.application.di

import andrewafony.thesis.application.MainComponent
import andrewafony.thesis.application.MainViewModel
import andrewafony.thesis.application.feature_deadlines.DeadlinesComponent
import andrewafony.thesis.application.feature_deadlines.data.local.DeadlinesDatabase
import andrewafony.thesis.application.feature_deadlines.presentation.DeadlinesViewModel
import andrewafony.thesis.application.feature_home.HomeComponent
import andrewafony.thesis.application.feature_home.presentation.HomeViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(
    private val dependencyContainer: DependencyContainer
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        dependencyContainer.component(modelClass).viewModel()
}

interface DependencyContainer {

    fun <T> component(clazz: Class<T>) : Component

    class Error: DependencyContainer {
        override fun <T> component(clazz: Class<T>): Component {
            throw IllegalStateException("No module found for $clazz")
        }
    }

    class Base(
        private val dependencyContainer: DependencyContainer = Error(),
        private val database: DeadlinesDatabase
    ) : DependencyContainer {

        override fun <T> component(clazz: Class<T>): Component = when (clazz) {
            MainViewModel::class.java -> MainComponent()
            HomeViewModel::class.java -> HomeComponent()
            DeadlinesViewModel::class.java -> DeadlinesComponent(database.dao())
            else -> dependencyContainer.component(clazz)
        }
    }
}