package andrewafony.thesis.application.feature_home

import andrewafony.thesis.application.di.Component
import andrewafony.thesis.application.feature_home.presentation.ClassInfoCommunication
import andrewafony.thesis.application.feature_home.presentation.HomeViewModel
import androidx.lifecycle.ViewModel

class HomeComponent: Component {

    override fun <T : ViewModel> viewModel(): T {
        return HomeViewModel(
            ClassInfoCommunication.Base(),
        ) as T
    }
}