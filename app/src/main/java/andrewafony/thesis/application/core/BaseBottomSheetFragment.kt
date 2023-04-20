package andrewafony.thesis.application.core

import andrewafony.thesis.application.MainViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetFragment<VB : ViewBinding> : BottomSheetDialogFragment() {

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
}