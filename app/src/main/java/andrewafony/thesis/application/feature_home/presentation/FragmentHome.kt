package andrewafony.thesis.application.feature_home.presentation

import andrewafony.thesis.application.Navigation
import andrewafony.thesis.application.Screen
import andrewafony.thesis.application.ViewModelFactoryProvider
import andrewafony.thesis.application.core.BaseFragment
import andrewafony.thesis.application.databinding.FragmentHomeBinding
import andrewafony.thesis.application.feature_home.presentation.adapter.TimetableAdapter
import andrewafony.thesis.application.feature_home.presentation.adapter.TimetableClickHandler
import andrewafony.thesis.application.feature_home.presentation.adapter.TimetableViewHolderFabric
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.firestore.GeoPoint

class FragmentHome : BaseFragment<FragmentHomeBinding>() {

    private val viewModel by activityViewModels<HomeViewModel> (factoryProducer = { (activity as ViewModelFactoryProvider).provide() })

    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.notification.setOnClickListener {
            mainViewModel.navigate(Navigation.Open(Screen.Notifications))
        }

        binding.userPhoto.setOnClickListener {
            mainViewModel.navigate(Navigation.Open(Screen.UserProfile))
        }

        val timetableAdapter = TimetableAdapter(TimetableViewHolderFabric(object : TimetableClickHandler {

            // todo (вынести в отдельный класс)

            override fun onClassClick(classDate: TimetableItemUi) {
                viewModel.loadClassInfo(classDate)
                mainViewModel.navigate(Navigation.Open(Screen.DetailClassInfo))
            }

            override fun onPlaceClick(geoPoint: GeoPoint) {
                val geo = Uri.parse("geo:${geoPoint.latitude},${geoPoint.longitude}?z=15")
                val mapIntent = Intent(Intent.ACTION_VIEW, geo)
                try {
                    startActivity(mapIntent)
                } catch (e: Exception) {
                    Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onLinkClick(link: String) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                try {
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(context, "Error link: $link", Toast.LENGTH_SHORT).show()
                }
            }
        }))

        binding.rvTimetable.apply {
            adapter = timetableAdapter
            layoutManager = LinearLayoutManager(context)
        }

        mainViewModel.observeTimetable(this) {
            if (it.isEmpty()) binding.emptyTimetableText.visibility = View.VISIBLE
            binding.timetableLoadingAnimation.cancelAnimation()
            binding.timetableLoadingAnimation.visibility = View.GONE
            timetableAdapter.map(it)
        }

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetTimetable)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {}

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.backgroundFrame.alpha = slideOffset * 0.8f
                binding.pullIndicator.alpha = (0.6f - slideOffset)

                val shape = binding.bottomSheetTimetable.background as GradientDrawable
                val radius = (1 - slideOffset) * 32
                shape.cornerRadius = radius
            }
        })
    }
}