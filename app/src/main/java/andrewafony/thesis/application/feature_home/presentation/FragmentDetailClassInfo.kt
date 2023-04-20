package andrewafony.thesis.application.feature_home.presentation

import andrewafony.thesis.application.ViewModelFactoryProvider
import andrewafony.thesis.application.core.BaseFragment
import andrewafony.thesis.application.databinding.FragmentDetailClassInfoBinding
import andrewafony.thesis.application.databinding.FragmentDetailClassInfoFirstTabBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator

class FragmentDetailClassInfo: BaseFragment<FragmentDetailClassInfoBinding>() {

    private val viewModel by activityViewModels<HomeViewModel>(factoryProducer = { (activity as ViewModelFactoryProvider).provide() })

    private lateinit var viewPager: ViewPager2

    override val bindingInflater: (LayoutInflater) -> FragmentDetailClassInfoBinding
        get() = FragmentDetailClassInfoBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = TestAdapter(this)
        viewPager = binding.viewPager
        viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            if (position == 0) tab.text = "Info"
            else tab.text = "Deadlines"
        }.attach()

        binding.toolbar.setNavigationOnClickListener {
            mainViewModel.navigateBack()
        }

        viewModel.observeClassInfo(this) {
            binding.run {
                toolbar.title = "${it.order} class"

            }
        }
    }
}

class TestAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return FragmentDetailClassInfoFirstTab()
    }
}

class FragmentDetailClassInfoFirstTab : BaseFragment<FragmentDetailClassInfoFirstTabBinding>() {

    private val viewModel by activityViewModels<HomeViewModel>(factoryProducer = { (activity as ViewModelFactoryProvider).provide() })

    override val bindingInflater: (LayoutInflater) -> FragmentDetailClassInfoFirstTabBinding
        get() = FragmentDetailClassInfoFirstTabBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeClassInfo(this) { classInfo ->
            binding.run {
                classTitle.text = classInfo.name
                classType.text = classInfo.type.replaceFirstChar { it.uppercase() }
                classPlace.text = classInfo.place.toString()
                classTime.text = "${classInfo.dateWeekDay}, ${classInfo.dateDay} ${classInfo.dateMonth} from ${classInfo.startTime} to ${classInfo.endTime}"
                professorCard.setOnClickListener {  }
                professorName.text = classInfo.employee
            }
        }
    }
}