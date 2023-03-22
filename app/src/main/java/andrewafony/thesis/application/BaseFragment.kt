package andrewafony.thesis.application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment <VB: ViewBinding>: Fragment() {

    protected lateinit var binding: VB

    abstract val bindingInflater: (LayoutInflater) -> VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingInflater(layoutInflater)
        return binding.root
    }
}