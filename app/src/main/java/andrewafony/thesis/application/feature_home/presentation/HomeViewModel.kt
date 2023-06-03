package andrewafony.thesis.application.feature_home.presentation

import andrewafony.thesis.application.core.Communication
import andrewafony.thesis.application.core.Dispatchers
import andrewafony.thesis.application.databinding.FragmentHomeBinding
import andrewafony.thesis.application.feature_deadlines.domain.DeadlineItem
import andrewafony.thesis.application.feature_deadlines.domain.DeadlinesInteractor
import andrewafony.thesis.application.feature_deadlines.presentation.DeadlineItemUi
import andrewafony.thesis.application.feature_home.domain.TimetableInteractor
import andrewafony.thesis.application.feature_home.domain.TimetableItemDomain
import andrewafony.thesis.application.feature_home.presentation.professor_info.DetailProfessorInfo
import andrewafony.thesis.application.feature_home.presentation.professor_info.ProfessorInfo
import android.text.format.DateUtils
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.util.DataUtils
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first

class HomeViewModel(
    private val classInfoCommunication: ClassInfoCommunication,
    private val professorInfoCommunication: ProfessorInfoCommunication,
    private val loadingState: ProfessorPageLoadingState,
    private val lastDeadlineInfo: LastDeadlineInfoCommunication,
    private val deadlinesInteractor: DeadlinesInteractor,
    private val lastClassInfo: LastClassInfoCommunication,
    private val dispatchers: Dispatchers,
    private val interactor: TimetableInteractor
): ViewModel() {

    fun loadLastDeadlineInfo() {
        dispatchers.launchBackground(viewModelScope) {
            deadlinesInteractor.getDeadlines(false).collectLatest {
                if (it.isNotEmpty()) {
                    dispatchers.launchUi(this) {
                        lastDeadlineInfo.map(LastDeadline.Last(it.first()))
                    }
                } else {
                    dispatchers.launchUi(this) {
                        lastDeadlineInfo.map(LastDeadline.None())
                    }
                }
            }
        }
    }

    fun lastClassTime() {
        dispatchers.launchBackground(viewModelScope) {
            val last = interactor.getLastClass()
            dispatchers.launchUi(this) {
                if (last != null) {
                    lastClassInfo.map(LastClass.Last(last))
                } else
                    lastClassInfo.map(LastClass.None)
            }
        }
    }

    fun loadClassInfo(classDate: TimetableItemUi) {
        classInfoCommunication.map(classDate)
    }

    fun observeClassInfo(owner: LifecycleOwner, observer: Observer<TimetableItemUi>) {
        classInfoCommunication.observe(owner, observer)
    }

    fun loadProfessorInfo(professorRef: DocumentReference) {
        loadingState.map(true)
        dispatchers.launchBackground(viewModelScope) {
            delay(1000)
            val result = interactor.getProfessorInfo(professorRef)
            dispatchers.launchUi(this) {
                professorInfoCommunication.map(result)
                loadingState.map(false)
            }
        }
    }

    fun observeLastClassInfo(owner: LifecycleOwner, observer: Observer<LastClass>) {
        lastClassInfo.observe(owner, observer)
    }

    fun observeLastDeadlineInfo(owner: LifecycleOwner, observer: Observer<LastDeadline>) {
        lastDeadlineInfo.observe(owner, observer)
    }

    fun observeProfessorInfo(owner: LifecycleOwner, observer: Observer<DetailProfessorInfo>) {
        professorInfoCommunication.observe(owner, observer)
    }

    fun observeProfessorInfoLoadingState(owner: LifecycleOwner, observer: Observer<Boolean>) {
        loadingState.observe(owner, observer)
    }
}

interface LastClassInfoCommunication : Communication.Mutable<LastClass> {

    class Base : Communication.Ui<LastClass>(), LastClassInfoCommunication
}

interface LastClass {

    fun map(binding: FragmentHomeBinding)

    class Last(
        private val info: TimetableItemDomain
    ): LastClass {
        override fun map(binding: FragmentHomeBinding) {
            with(binding) {
                firstClassUntil.text = info.name
                firstClassUntilTime.text = DateUtils.getRelativeTimeSpanString(info.date.time)
                firstClassUntilTime.visibility = View.VISIBLE
                nextClassTitle.visibility = View.VISIBLE
            }
        }
    }

    object None : LastClass {
        override fun map(binding: FragmentHomeBinding) {
            with(binding) {
                firstClassUntil.text = "Нет занятий"
                firstClassUntilTime.visibility = View.GONE
                nextClassTitle.visibility = View.GONE
            }
        }
    }
}

interface LastDeadline {

    fun map(binding: FragmentHomeBinding)

    class Last(
        private val info: DeadlineItemUi
    ): LastDeadline {
        override fun map(binding: FragmentHomeBinding) {
            with(binding) {
                deadlineTitle.text = info.deadlineText
                deadlineDescription.text = info.description ?: ""
                if (info.date != null) {
                    deadlineDate.text = DateUtils.getRelativeTimeSpanString(info.date.time)
                    materialDivider.visibility = View.VISIBLE
                } else {
                    deadlineDate.text = ""
                    materialDivider.visibility = View.GONE
                }
            }
        }
    }

    class None: LastDeadline {
        override fun map(binding: FragmentHomeBinding) {
            binding.deadlineDescription.text = "Список напоминаний пуст"
            binding.materialDivider.visibility = View.GONE
        }
    }
}

interface ClassInfoCommunication : Communication.Mutable<TimetableItemUi> {

    class Base : Communication.Ui<TimetableItemUi>(), ClassInfoCommunication
}

interface LastDeadlineInfoCommunication : Communication.Mutable<LastDeadline> {

    class Base : Communication.Ui<LastDeadline>(), LastDeadlineInfoCommunication
}

interface ProfessorInfoCommunication : Communication.Mutable<DetailProfessorInfo> {

    class Base : Communication.Ui<DetailProfessorInfo>(), ProfessorInfoCommunication
}

interface ProfessorPageLoadingState: Communication.Mutable<Boolean> {

    class Base : Communication.Ui<Boolean>(), ProfessorPageLoadingState
}