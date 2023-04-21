package andrewafony.thesis.application.feature_deadlines.presentation

import andrewafony.thesis.application.R
import andrewafony.thesis.application.core.BaseFragment
import andrewafony.thesis.application.databinding.FragmentDeadlinesBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetDialog

class FragmentDeadlines: BaseFragment<FragmentDeadlinesBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentDeadlinesBinding
        get() = FragmentDeadlinesBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAddDeadline.setOnClickListener {
            BottomSheetFragmentAddDeadline.newInstance().show(childFragmentManager, "fragment_add_deadline")
        }
    }
}