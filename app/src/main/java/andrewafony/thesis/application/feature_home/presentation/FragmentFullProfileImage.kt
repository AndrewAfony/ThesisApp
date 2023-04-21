package andrewafony.thesis.application.feature_home.presentation

import andrewafony.thesis.application.Navigation
import andrewafony.thesis.application.ViewModelFactoryProvider
import andrewafony.thesis.application.core.BaseFragment
import andrewafony.thesis.application.databinding.FragmentFullProfileImageBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide

class FragmentFullProfileImage: BaseFragment<FragmentFullProfileImageBinding>() {

    private val viewModel by activityViewModels<HomeViewModel>(factoryProducer = { (activity as ViewModelFactoryProvider).provide() })

    override val bindingInflater: (LayoutInflater) -> FragmentFullProfileImageBinding
        get() = FragmentFullProfileImageBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            mainViewModel.navigate(Navigation.Back)
        }

        viewModel.observeProfessorInfo(this) {
            Glide.with(binding.root)
                .load(it.photo)
                .into(binding.image)
        }
    }
}