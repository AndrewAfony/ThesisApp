package andrewafony.thesis.application.feature_login.presentation

import andrewafony.thesis.application.core.BaseFragment
import andrewafony.thesis.application.databinding.FragmentLoginBinding
import android.view.LayoutInflater

class FragmentLogin: BaseFragment<FragmentLoginBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate
}