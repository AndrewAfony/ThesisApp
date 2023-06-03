package andrewafony.thesis.application.feature_home

import andrewafony.thesis.application.core.Dispatchers
import andrewafony.thesis.application.di.Component
import andrewafony.thesis.application.feature_deadlines.data.BaseDeadlinesRepository
import andrewafony.thesis.application.feature_deadlines.data.local.DeadlinesCacheDataSource
import andrewafony.thesis.application.feature_deadlines.data.local.DeadlinesDao
import andrewafony.thesis.application.feature_deadlines.domain.DeadlinesInteractor
import andrewafony.thesis.application.feature_deadlines.domain.DeadlinesRepository
import andrewafony.thesis.application.feature_home.data.BaseTimetableRepository
import andrewafony.thesis.application.feature_home.data.cloud.FirestoreService
import andrewafony.thesis.application.feature_home.data.cloud.TimetableCloudDataSource
import andrewafony.thesis.application.feature_home.domain.TimetableInteractor
import andrewafony.thesis.application.feature_home.presentation.*
import androidx.lifecycle.ViewModel

class HomeComponent(
    private val deadlinesDatabaseDao: DeadlinesDao
): Component {

    override fun <T : ViewModel> viewModel(): T {
        return HomeViewModel(
            ClassInfoCommunication.Base(),
            ProfessorInfoCommunication.Base(),
            ProfessorPageLoadingState.Base(),
            LastDeadlineInfoCommunication.Base(),
            DeadlinesInteractor.Base(
                BaseDeadlinesRepository(DeadlinesCacheDataSource.Base(deadlinesDatabaseDao)),
                dispatchers = Dispatchers.Base()
            ),
            LastClassInfoCommunication.Base(),
            Dispatchers.Base(),
            TimetableInteractor.Base(
                BaseTimetableRepository(
                    TimetableCloudDataSource.Base(FirestoreService.Base())
                )
            ),
        ) as T
    }
}