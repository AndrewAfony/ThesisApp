package andrewafony.thesis.application.feature_deadlines.presentation

import andrewafony.thesis.application.R
import andrewafony.thesis.application.ViewModelFactoryProvider
import andrewafony.thesis.application.core.BaseFragment
import andrewafony.thesis.application.core.Vibration
import andrewafony.thesis.application.databinding.FragmentDeadlinesBinding
import andrewafony.thesis.application.feature_deadlines.presentation.adapter.DeadlinesAdapter
import andrewafony.thesis.application.feature_deadlines.presentation.adapter.DeadlinesViewHolderFabric
import android.content.Context
import android.os.*
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

class FragmentDeadlines: BaseFragment<FragmentDeadlinesBinding>() {

    private val viewModel by activityViewModels<DeadlinesViewModel>(factoryProducer = { (activity as ViewModelFactoryProvider).provide() })

    private lateinit var vibrator: Vibration

    override val bindingInflater: (LayoutInflater) -> FragmentDeadlinesBinding
        get() = FragmentDeadlinesBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vibrator = Vibration.Base(requireContext())

        val deadlinesAdapter = DeadlinesAdapter(DeadlinesViewHolderFabric(
            updateDoneState = { item ->
                viewModel.updateDoneState(item)
                vibrator.buttonClick()
            }
        ))

        viewModel.init(savedInstanceState == null)

        binding.run {
            rvDeadlines.apply {
                adapter = deadlinesAdapter
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            }
        }

        viewModel.observeDeadlines(this) {
            deadlinesAdapter.map(it)
        }

        binding.buttonAddDeadline.setOnClickListener {
            BottomSheetFragmentAddDeadline.newInstance().show(childFragmentManager, "fragment_add_deadline")
        }

        binding.toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.button_show_deadlines_filter -> {
                    BottomSheetDeadlinesListSettings.newInstance().show(childFragmentManager, null)
                    true
                }
                else -> false
            }
        }
    }
}