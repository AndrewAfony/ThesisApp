package andrewafony.thesis.application.feature_home.presentation

import andrewafony.thesis.application.core.BaseFragment
import andrewafony.thesis.application.databinding.FragmentNotificationsBinding
import android.view.LayoutInflater

class FragmentNotifications: BaseFragment<FragmentNotificationsBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentNotificationsBinding
        get() = FragmentNotificationsBinding::inflate

}