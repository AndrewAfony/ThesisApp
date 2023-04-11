package andrewafony.thesis.application.feature_deadlines.presentation

import andrewafony.thesis.application.core.BaseFragment
import andrewafony.thesis.application.databinding.FragmentDeadlinesBinding
import android.view.LayoutInflater

class FragmentDeadlines: BaseFragment<FragmentDeadlinesBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentDeadlinesBinding
        get() = FragmentDeadlinesBinding::inflate
}