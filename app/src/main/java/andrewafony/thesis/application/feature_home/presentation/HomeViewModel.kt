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
    private val classInfoCommunication: ClassInfoCommunication,
): ViewModel() {

    fun loadClassInfo(classDate: TimetableItemUi) {
        classInfoCommunication.map(classDate)
    }

    fun observeClassInfo(owner: LifecycleOwner, observer: Observer<TimetableItemUi>) {
        classInfoCommunication.observe(owner, observer)
    }
}

interface ClassInfoCommunication : Communication.Mutable<TimetableItemUi> {

    class Base : Communication.Ui<TimetableItemUi>(), ClassInfoCommunication
}