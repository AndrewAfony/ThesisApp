package andrewafony.thesis.application.feature_home.presentation.professor_info

import andrewafony.thesis.application.ViewModelFactoryProvider
import andrewafony.thesis.application.core.BaseBottomSheetFragment
import andrewafony.thesis.application.databinding.FragmentProfessorInfoBinding
import andrewafony.thesis.application.feature_home.presentation.HomeViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels

class BottomSheetFragmentProfessorInfo: BaseBottomSheetFragment<FragmentProfessorInfoBinding>() {

    private val viewModel by activityViewModels<HomeViewModel>(factoryProducer = { (activity as ViewModelFactoryProvider).provide() })

    override val bindingInflater: (LayoutInflater) -> FragmentProfessorInfoBinding
        get() = FragmentProfessorInfoBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeProfessorInfo(this) {
            // todo ()
        }
    }

    companion object {
        fun newInstance(): BottomSheetFragmentProfessorInfo {
            return BottomSheetFragmentProfessorInfo()
        }
    }
}