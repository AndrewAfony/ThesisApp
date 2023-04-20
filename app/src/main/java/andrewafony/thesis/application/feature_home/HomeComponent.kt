package andrewafony.thesis.application.feature_home

import andrewafony.thesis.application.core.Dispatchers
import andrewafony.thesis.application.di.Component
import andrewafony.thesis.application.feature_home.data.BaseTimetableRepository
import andrewafony.thesis.application.feature_home.data.cloud.FirestoreService
import andrewafony.thesis.application.feature_home.data.cloud.TimetableCloudDataSource
import andrewafony.thesis.application.feature_home.domain.TimetableInteractor
import andrewafony.thesis.application.feature_home.presentation.ClassInfoCommunication
import andrewafony.thesis.application.feature_home.presentation.HomeViewModel
import andrewafony.thesis.application.feature_home.presentation.ProfessorInfoCommunication
import andrewafony.thesis.application.feature_home.presentation.ProfessorPageLoadingState
import androidx.lifecycle.ViewModel

class HomeComponent: Component {

    override fun <T : ViewModel> viewModel(): T {
        return HomeViewModel(
            ClassInfoCommunication.Base(),
            ProfessorInfoCommunication.Base(),
            ProfessorPageLoadingState.Base(),
            Dispatchers.Base(),
            TimetableInteractor.Base(
                BaseTimetableRepository(
                    TimetableCloudDataSource.Base(FirestoreService.Base())
                )
            ),
        ) as T
    }
}