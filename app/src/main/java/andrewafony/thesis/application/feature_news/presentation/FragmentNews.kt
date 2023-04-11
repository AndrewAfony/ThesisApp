package andrewafony.thesis.application.feature_news.presentation

import andrewafony.thesis.application.core.BaseFragment
import andrewafony.thesis.application.databinding.FragmentNewsBinding
import android.view.LayoutInflater

class FragmentNews: BaseFragment<FragmentNewsBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentNewsBinding
        get() = FragmentNewsBinding::inflate
}