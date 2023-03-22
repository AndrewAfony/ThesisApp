package andrewafony.thesis.application.feature_main.presentation

import andrewafony.thesis.application.BaseFragment
import andrewafony.thesis.application.databinding.FragmentNotificationsBinding
import android.view.LayoutInflater

class FragmentNotifications: BaseFragment<FragmentNotificationsBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentNotificationsBinding
        get() = FragmentNotificationsBinding::inflate

}