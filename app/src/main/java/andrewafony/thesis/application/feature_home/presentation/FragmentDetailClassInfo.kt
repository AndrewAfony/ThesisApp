package andrewafony.thesis.application.feature_home.presentation

import andrewafony.thesis.application.core.BaseFragment
import andrewafony.thesis.application.databinding.FragmentDetailClassInfoBinding
import andrewafony.thesis.application.databinding.FragmentDetailClassInfoFirstTabBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator

class FragmentDetailClassInfo: BaseFragment<FragmentDetailClassInfoBinding>() {

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
    }
}

class TestAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return Test1Fragment()
    }
}

class Test1Fragment : BaseFragment<FragmentDetailClassInfoFirstTabBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentDetailClassInfoFirstTabBinding
        get() = FragmentDetailClassInfoFirstTabBinding::inflate
}