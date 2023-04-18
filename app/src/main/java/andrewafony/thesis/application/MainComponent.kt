package andrewafony.thesis.application

import andrewafony.thesis.application.core.Dispatchers
import andrewafony.thesis.application.di.Component
import andrewafony.thesis.application.feature_home.data.BaseTimetableRepository
import andrewafony.thesis.application.feature_home.data.cloud.CloudDataSource
import andrewafony.thesis.application.feature_home.data.cloud.FirestoreService
import andrewafony.thesis.application.feature_home.domain.TimetableInteractor
import androidx.lifecycle.ViewModel

class MainComponent : Component {

    override fun <T : ViewModel> viewModel(): T {
        return MainViewModel(
            NavigationCommunication.Base(),
            TimetableCommunication.Base(),
            TimetableInteractor.Base(
                BaseTimetableRepository(
                    CloudDataSource.Base(FirestoreService.Base())
                )
            ),
            Dispatchers.Base()) as T
    }
}