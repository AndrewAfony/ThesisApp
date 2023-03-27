package andrewafony.thesis.application

import andrewafony.thesis.application.di.DependencyContainer
import andrewafony.thesis.application.di.ViewModelFactory
import android.app.Application

class App: Application(), ViewModelFactoryProvider {

    private lateinit var dependencyContainer: DependencyContainer

    override fun onCreate() {
        super.onCreate()

        dependencyContainer = DependencyContainer.Base()
    }

    override fun provide(): ViewModelFactory = ViewModelFactory(dependencyContainer)
}

interface ViewModelFactoryProvider {

    fun provide(): ViewModelFactory
}