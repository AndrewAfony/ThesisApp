package andrewafony.thesis.application

import andrewafony.thesis.application.core.Dispatchers
import andrewafony.thesis.application.di.Component
import andrewafony.thesis.application.feature_home.data.BaseTimetableRepository
import andrewafony.thesis.application.feature_home.data.cloud.TimetableCloudDataSource
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
                    TimetableCloudDataSource.Base(FirestoreService.Base())
                )
            ),
            Dispatchers.Base()) as T
    }
}