package andrewafony.thesis.application.feature_deadlines.presentation

import andrewafony.thesis.application.feature_deadlines.domain.DeadlinesInteractor
import androidx.lifecycle.ViewModel

class DeadlinesViewModel(
    private val interactor: DeadlinesInteractor
): ViewModel() {

    fun setDateAndTime(date: String, time: String) {

    }
}