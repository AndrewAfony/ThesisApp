package andrewafony.thesis.application.feature_home.presentation

import andrewafony.thesis.application.core.Communication
import andrewafony.thesis.application.core.Dispatchers
import andrewafony.thesis.application.feature_home.presentation.professor_info.ProfessorInfo
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay

class HomeViewModel(
    private val classInfoCommunication: ClassInfoCommunication,
    private val professorInfoCommunication: ProfessorInfoCommunication,
    private val loadingState: ProfessorPageLoadingState,
    private val dispatchers: Dispatchers
): ViewModel() {

    fun loadClassInfo(classDate: TimetableItemUi) {
        classInfoCommunication.map(classDate)
    }

    fun observeClassInfo(owner: LifecycleOwner, observer: Observer<TimetableItemUi>) {
        classInfoCommunication.observe(owner, observer)
    }

    fun loadProfessorInfo(professorId: String) {
        loadingState.map(true)
        dispatchers.launchBackground(viewModelScope) {
//            val result = interactor.professorInfo(professorId)
            delay(2000)
            dispatchers.launchUi(this) {
                loadingState.map(false)
//                professorInfoCommunication.map(result)
            }
        }
    }

    fun observeProfessorInfo(owner: LifecycleOwner, observer: Observer<ProfessorInfo>) {
        professorInfoCommunication.observe(owner, observer)
    }

    fun observeProfessorInfoLoadingState(owner: LifecycleOwner, observer: Observer<Boolean>) {
        loadingState.observe(owner, observer)
    }
}

interface ClassInfoCommunication : Communication.Mutable<TimetableItemUi> {

    class Base : Communication.Ui<TimetableItemUi>(), ClassInfoCommunication
}

interface ProfessorInfoCommunication : Communication.Mutable<ProfessorInfo> {

    class Base : Communication.Background<ProfessorInfo>(), ProfessorInfoCommunication
}

interface ProfessorPageLoadingState: Communication.Mutable<Boolean> {

    class Base : Communication.Ui<Boolean>(), ProfessorPageLoadingState
}