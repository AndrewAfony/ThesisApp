package andrewafony.thesis.application.feature_home.presentation.profile

import andrewafony.thesis.application.core.BaseFragment
import andrewafony.thesis.application.databinding.FragmentProfileBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View

class FragmentProfile: BaseFragment<FragmentProfileBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}