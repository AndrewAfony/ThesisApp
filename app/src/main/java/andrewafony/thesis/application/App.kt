package andrewafony.thesis.application

import andrewafony.thesis.application.di.DependencyContainer
import andrewafony.thesis.application.di.ViewModelFactory
import andrewafony.thesis.application.feature_deadlines.data.local.DeadlinesDatabase
import android.app.Application
import androidx.room.Room

class App: Application(), ViewModelFactoryProvider {

    private lateinit var dependencyContainer: DependencyContainer

    override fun onCreate() {
        super.onCreate()

//        val database = Room.databaseBuilder(
//            this,
//            DeadlinesDatabase::class.java,
//            "deadlines_db"
//        ).build()

        val testDatabase = Room.inMemoryDatabaseBuilder(
            this,
            DeadlinesDatabase::class.java
        ).build()

        dependencyContainer = DependencyContainer.Base(database = testDatabase)
    }

    override fun provide(): ViewModelFactory = ViewModelFactory(dependencyContainer)
}

interface ViewModelFactoryProvider {

    fun provide(): ViewModelFactory
}