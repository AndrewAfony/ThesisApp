package andrewafony.thesis.application.feature_home

import andrewafony.thesis.application.core.Dispatchers
import andrewafony.thesis.application.di.Component
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
            Dispatchers.Base()
        ) as T
    }
}