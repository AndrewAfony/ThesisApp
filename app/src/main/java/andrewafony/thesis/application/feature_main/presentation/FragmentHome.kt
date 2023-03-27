package andrewafony.thesis.application.feature_main.presentation

import andrewafony.thesis.application.core.BaseFragment
import andrewafony.thesis.application.databinding.FragmentHomeBinding
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

class FragmentHome : BaseFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetTimetable)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {}

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.backgroundFrame.alpha = slideOffset * 0.8f

                val shape = binding.bottomSheetTimetable.background as GradientDrawable
                val radius = (1 - slideOffset) * 32
                shape.cornerRadius = radius
            }
        })
    }
}