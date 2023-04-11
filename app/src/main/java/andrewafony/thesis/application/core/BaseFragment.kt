package andrewafony.thesis.application.core

import andrewafony.thesis.application.MainViewModel
import andrewafony.thesis.application.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected val mainViewModel by activityViewModels<MainViewModel>()

    protected lateinit var binding: VB

    abstract val bindingInflater: (LayoutInflater) -> VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = bindingInflater(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback {
            if (findNavController().currentDestination?.id.isStartDestination())
                requireActivity().finish()
            else
                mainViewModel.navigateBack()
        }
    }
}

fun Int?.isStartDestination(): Boolean =
    this == R.id.fragmentHome   ||
    this == R.id.fragmentSearch ||
    this == R.id.fragmentNews   ||
    this == R.id.fragmentDeadlines