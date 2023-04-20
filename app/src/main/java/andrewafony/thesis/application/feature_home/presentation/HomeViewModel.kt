package andrewafony.thesis.application.feature_home.presentation

import andrewafony.thesis.application.core.Communication
import andrewafony.thesis.application.core.Dispatchers
import andrewafony.thesis.application.feature_home.domain.TimetableInteractor
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class HomeViewModel(
    private val interactor: TimetableInteractor,
    private val classInfoCommunication: ClassInfoCommunication,
    private val dispatchers: Dispatchers
): ViewModel() {

    fun loadClassInfo(classDate: TimetableItemUi) {
        classInfoCommunication.map(classDate)
//        dispatchers.launchBackground(viewModelScope) {
//            val result = interactor.getClassInfo(classId)
//            Log.d("MyHelper", "loadClassInfo: $result")
//            dispatchers.launchUi(this) {
//                Log.d("MyHelper", "ui")
//                classInfoCommunication.map(result)
//            }
//        }
    }

    fun observeClassInfo(owner: LifecycleOwner, observer: Observer<TimetableItemUi>) {
        classInfoCommunication.observe(owner, observer)
    }
}

interface ClassInfoCommunication : Communication.Mutable<TimetableItemUi> {

    class Base : Communication.Ui<TimetableItemUi>(), ClassInfoCommunication
}