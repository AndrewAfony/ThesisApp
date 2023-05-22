package andrewafony.thesis.application.feature_home.presentation.professor_info

import andrewafony.thesis.application.Navigation
import andrewafony.thesis.application.Screen
import andrewafony.thesis.application.ViewModelFactoryProvider
import andrewafony.thesis.application.core.BaseBottomSheetFragment
import andrewafony.thesis.application.databinding.FragmentProfessorInfoBinding
import andrewafony.thesis.application.feature_home.presentation.HomeViewModel
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide

class BottomSheetFragmentProfessorInfo: BaseBottomSheetFragment<FragmentProfessorInfoBinding>() {

    private val viewModel by activityViewModels<HomeViewModel>(factoryProducer = { (activity as ViewModelFactoryProvider).provide() })

    override val bindingInflater: (LayoutInflater) -> FragmentProfessorInfoBinding
        get() = FragmentProfessorInfoBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val clipboard by lazy { requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager }

        viewModel.observeProfessorInfo(this) { info ->
            binding.run {
                professorName.text = info.name
                professorPosition.text = info.position
                Glide.with(root)
                    .load(info.photo)
                    .into(binding.professorPhoto)
                professorPhoto.setOnClickListener {
                    mainViewModel.navigate(Navigation.Open(Screen.FullProfileImage))
                }

                professorEmail.text = info.email
                professorBirthday.text = info.birthday
                professorPhone.text = info.phone_number

                emailSection.setOnClickListener {
                    Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
                    val email = ClipData.newPlainText("email", info.email)
                    clipboard.setPrimaryClip(email)
                }

                buttonMessage.setOnClickListener {
                    Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:${info.email}")
                    }.also {
                        startActivity(it)
                    }
                }
                buttonCall.setOnClickListener {
                    Intent(Intent.ACTION_DIAL, Uri.parse("tel:${info.phone_number}")).also {
                        startActivity(it)
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance(): BottomSheetFragmentProfessorInfo {
            return BottomSheetFragmentProfessorInfo()
        }
    }
}