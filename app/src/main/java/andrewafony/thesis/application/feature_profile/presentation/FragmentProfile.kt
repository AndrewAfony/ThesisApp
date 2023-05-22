package andrewafony.thesis.application.feature_profile.presentation

import andrewafony.thesis.application.R
import andrewafony.thesis.application.core.BaseFragment
import andrewafony.thesis.application.databinding.FragmentProfileBinding
import andrewafony.thesis.application.databinding.FragmentTabEduFunctionsBinding
import andrewafony.thesis.application.databinding.FragmentTabInformationBinding
import andrewafony.thesis.application.databinding.FragmentTabSettingsBinding
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
                    tab.text = getString(R.string.info)
                }
                1 -> {
                    tab.text = getString(R.string.options)
                }
                2 -> {
                    tab.text = getString(R.string.settings)
                }
            }
        }.attach()
    }

    override fun onStart() {
        super.onStart()
        Glide.with(requireActivity())
            .load("https://www.clipartma1x.com/png/full/267-2671061_yükle-youssefdibeyoussefdibe-profile-123picture-user-male111.png")
            .placeholder(R.drawable.user_placeholder)
            .error(R.drawable.user_placeholder)
            .into(binding.userPhoto)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSystemSettings.setOnClickListener {
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", this@TabSettings.requireActivity().packageName, null)
            }.also {
                startActivity(it)
            }
        }
    }
}

class TabInformation : BaseFragment<FragmentTabInformationBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentTabInformationBinding
        get() = FragmentTabInformationBinding::inflate
}

class TabEduFunctions : BaseFragment<FragmentTabEduFunctionsBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentTabEduFunctionsBinding
        get() = FragmentTabEduFunctionsBinding::inflate
}