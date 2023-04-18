package andrewafony.thesis.application.feature_home

import andrewafony.thesis.application.core.Dispatchers
import andrewafony.thesis.application.di.Component
import andrewafony.thesis.application.feature_home.data.BaseTimetableRepository
import andrewafony.thesis.application.feature_home.data.cloud.CloudDataSource
import andrewafony.thesis.application.feature_home.data.cloud.FirestoreService
import andrewafony.thesis.application.feature_home.domain.TimetableInteractor
import andrewafony.thesis.application.feature_home.presentation.HomeViewModel
import andrewafony.thesis.application.feature_home.presentation.TimetableCommunication
import androidx.lifecycle.ViewModel

class HomeComponent: Component {

    override fun <T : ViewModel> viewModel(): T {
        return HomeViewModel(
            TimetableInteractor.Base(
                BaseTimetableRepository(
                    CloudDataSource.Base(FirestoreService.Base())
                )
            ),
            TimetableCommunication.Base(),
            Dispatchers.Base()
        ) as T
    }
}