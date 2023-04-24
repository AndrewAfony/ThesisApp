package andrewafony.thesis.application.feature_deadlines.presentation

import andrewafony.thesis.application.Navigation
import andrewafony.thesis.application.Screen
import andrewafony.thesis.application.ViewModelFactoryProvider
import andrewafony.thesis.application.core.BaseBottomSheetFragment
import andrewafony.thesis.application.databinding.FragmentDeadlinesSettingsBottomSheetBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels

class BottomSheetDeadlinesListSettings: BaseBottomSheetFragment<FragmentDeadlinesSettingsBottomSheetBinding>() {

    private val viewModel by activityViewModels<DeadlinesViewModel>(factoryProducer = { (activity as ViewModelFactoryProvider).provide() })

    override val bindingInflater: (LayoutInflater) -> FragmentDeadlinesSettingsBottomSheetBinding
        get() = FragmentDeadlinesSettingsBottomSheetBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeFilterByDone(this) { isFiltered ->
            binding.buttonDeadlinesFilterByDone.text = if (isFiltered) "Show all deadlines" else "Show completed deadlines"
        }

        binding.buttonDeadlinesFilterByDone.setOnClickListener {
            viewModel.filterDeadlines()
            dismiss()
        }
    }

    companion object {
        fun newInstance(): BottomSheetDeadlinesListSettings {
            return BottomSheetDeadlinesListSettings()
        }
    }
}