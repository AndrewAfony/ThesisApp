package andrewafony.thesis.application.feature_deadlines.presentation

import andrewafony.thesis.application.R
import andrewafony.thesis.application.ViewModelFactoryProvider
import andrewafony.thesis.application.core.BaseFragment
import andrewafony.thesis.application.databinding.FragmentDeadlinesBinding
import andrewafony.thesis.application.feature_deadlines.presentation.adapter.DeadlinesAdapter
import andrewafony.thesis.application.feature_deadlines.presentation.adapter.DeadlinesViewHolderFabric
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetDialog

class FragmentDeadlines: BaseFragment<FragmentDeadlinesBinding>() {

    private val viewModel by activityViewModels<DeadlinesViewModel>(factoryProducer = { (activity as ViewModelFactoryProvider).provide() })

    override val bindingInflater: (LayoutInflater) -> FragmentDeadlinesBinding
        get() = FragmentDeadlinesBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deadlinesAdapter = DeadlinesAdapter(DeadlinesViewHolderFabric())

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
    }
}