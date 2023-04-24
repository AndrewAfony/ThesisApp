package andrewafony.thesis.application.feature_home.presentation.profile

import andrewafony.thesis.application.core.BaseFragment
import andrewafony.thesis.application.databinding.FragmentProfileBinding
import andrewafony.thesis.application.databinding.FragmentTabEduFunctionsBinding
import andrewafony.thesis.application.databinding.FragmentTabInformationBinding
import andrewafony.thesis.application.databinding.FragmentTabSettingsBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator

class FragmentProfile: BaseFragment<FragmentProfileBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            mainViewModel.navigateBack()
        }

        val adapter = FragmentProfileTabAdapter(this)
        binding.profileViewPager.adapter = adapter
        TabLayoutMediator(binding.profileTabs, binding.profileViewPager) { tab, position ->
            when(position) {
                0 -> {
                    tab.text = "Info"
                }
                1 -> {
                    tab.text = "Options"
                }
                2 -> {
                    tab.text = "Settings"
                }
            }
        }.attach()
    }
}

class FragmentProfileTabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> TabInformation()
            1 -> TabEduFunctions()
            else -> TabSettings()
        }
    }
}

class TabSettings : BaseFragment<FragmentTabSettingsBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentTabSettingsBinding
        get() = FragmentTabSettingsBinding::inflate
}

class TabInformation : BaseFragment<FragmentTabInformationBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentTabInformationBinding
        get() = FragmentTabInformationBinding::inflate
}

class TabEduFunctions : BaseFragment<FragmentTabEduFunctionsBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentTabEduFunctionsBinding
        get() = FragmentTabEduFunctionsBinding::inflate
}