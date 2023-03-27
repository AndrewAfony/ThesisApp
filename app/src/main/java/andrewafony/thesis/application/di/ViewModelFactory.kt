package andrewafony.thesis.application.di

import andrewafony.thesis.application.MainComponent
import andrewafony.thesis.application.MainViewModel
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
        private val dependencyContainer: DependencyContainer = Error()
    ) : DependencyContainer {

        override fun <T> component(clazz: Class<T>): Component = when (clazz) {
            MainViewModel::class.java -> MainComponent()
            else -> dependencyContainer.component(clazz)
        }
    }
}