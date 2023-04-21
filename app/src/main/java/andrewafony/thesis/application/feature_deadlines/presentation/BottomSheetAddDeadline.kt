package andrewafony.thesis.application.feature_deadlines.presentation

import andrewafony.thesis.application.core.BaseBottomSheetFragment
import andrewafony.thesis.application.databinding.FragmentAddDeadlineBottomSheetBinding
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

class BottomSheetFragmentAddDeadline: BaseBottomSheetFragment<FragmentAddDeadlineBottomSheetBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentAddDeadlineBottomSheetBinding
        get() = FragmentAddDeadlineBottomSheetBinding::inflate

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonChooseDate.setOnClickListener {
            Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show()
        }

        binding.buttonCreate.setOnClickListener {
            Toast.makeText(context, "Created", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.deadlineEditText.requestFocus()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.deadlineEditText, InputMethodManager.SHOW_IMPLICIT)
    }

    companion object {
        fun newInstance(): BottomSheetFragmentAddDeadline {
            return BottomSheetFragmentAddDeadline()
        }
    }
}