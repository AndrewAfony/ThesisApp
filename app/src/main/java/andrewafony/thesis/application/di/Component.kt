package andrewafony.thesis.application.di

import androidx.lifecycle.ViewModel

interface Component {

    fun <T: ViewModel> viewModel() : T
}