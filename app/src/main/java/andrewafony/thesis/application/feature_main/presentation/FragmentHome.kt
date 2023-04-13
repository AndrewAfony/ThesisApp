package andrewafony.thesis.application.feature_main.presentation

import andrewafony.thesis.application.Navigation
import andrewafony.thesis.application.Screen
import andrewafony.thesis.application.core.BaseFragment
import andrewafony.thesis.application.databinding.FragmentHomeBinding
import andrewafony.thesis.application.feature_main.presentation.adapter.TimetableAdapter
import andrewafony.thesis.application.feature_main.presentation.adapter.TimetableViewHolderFabric
import andrewafony.thesis.application.feature_main.presentation.adapter.TopPullIndicatorDecorator
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior

class FragmentHome : BaseFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.notification.setOnClickListener {
            mainViewModel.navigate(Navigation.Open(Screen.Notifications))
        }

        val timetableAdapter = TimetableAdapter(TimetableViewHolderFabric())

        binding.rvTimetable.apply {
            adapter = timetableAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
            addItemDecoration(TopPullIndicatorDecorator())
        }

        timetableAdapter.map(
            listOf(
                TimetableItemUi(1, "Test1", "12:00-13:00"),
                TimetableItemUi(2, "Test2", "13:00-14:00"),
                TimetableItemUi(2, "Test2", "13:00-14:00"),
                TimetableItemUi(2, "Test2", "13:00-14:00"),
                TimetableItemUi(2, "Test2", "13:00-14:00"),
                TimetableItemUi(2, "Test2", "13:00-14:00"),
                TimetableItemUi(2, "Test2", "13:00-14:00"),
                TimetableItemUi(2, "Test2", "13:00-14:00"),
                TimetableItemUi(2, "Test2", "13:00-14:00"),
            )
        )

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