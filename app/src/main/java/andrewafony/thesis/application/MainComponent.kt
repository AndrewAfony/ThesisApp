package andrewafony.thesis.application

import andrewafony.thesis.application.di.Component
import androidx.lifecycle.ViewModel

class MainComponent: Component {

    override fun <T : ViewModel> viewModel(): T {
        return MainViewModel(NavigationCommunication.Base()) as T
    }
}