package andrewafony.thesis.application.feature_deadlines.presentation

import andrewafony.thesis.application.R
import andrewafony.thesis.application.ViewModelFactoryProvider
import andrewafony.thesis.application.core.BaseBottomSheetFragment
import andrewafony.thesis.application.databinding.FragmentAddDeadlineBottomSheetBinding
import andrewafony.thesis.application.feature_deadlines.domain.DeadlineItem
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat

class BottomSheetFragmentAddDeadline :
    BaseBottomSheetFragment<FragmentAddDeadlineBottomSheetBinding>() {

    private val viewModel by activityViewModels<DeadlinesViewModel>(factoryProducer = { (activity as ViewModelFactoryProvider).provide() })

    override val bindingInflater: (LayoutInflater) -> FragmentAddDeadlineBottomSheetBinding
        get() = FragmentAddDeadlineBottomSheetBinding::inflate

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // todo (schedule notification with WorkManager)
        val menu = DisciplinesPopupMenu(context, binding.buttonChooseDiscipline).apply {
            onItemSelectedListener {
                binding.textDiscipline.text = it
            }
        }

        binding.buttonCreate.isEnabled = false

        binding.deadlineEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.buttonCreate.isEnabled = !TextUtils.isEmpty(s)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.buttonChooseDate.setOnClickListener {
            val date =
                if (binding.textDate.text.contains(getString(R.string.no_date))) null else binding.textDate.text.toString()
                    .substringBefore(" ")
            val time =
                if (binding.textDate.text.contains(getString(R.string.no_date))) null else binding.textDate.text.toString()
                    .substringAfter(" ")
            BottomSheetDateAndTimePick.newInstance(
                date = date,
                time = time
            ).show(childFragmentManager, null)
        }

        binding.buttonCreate.setOnClickListener {
            val discipline =
                if (binding.textDiscipline.text.contains(getString(R.string.discipline))) null else binding.textDiscipline.text.toString()
            val date =
                if (binding.textDate.text.contains(getString(R.string.no_date)))
                    null
                else {
                    if (!binding.textDate.text.contains(" ")) {
                        SimpleDateFormat("dd/MM/yyyy").parse(binding.textDate.text.toString())
                    } else
                        SimpleDateFormat("dd/MM/yyyy HH:mm").parse(binding.textDate.text.toString())
                }
            val description =
                if (binding.deadlineDescriptionEditText.text.isNullOrEmpty()) null else binding.deadlineDescriptionEditText.text.toString()
            viewModel.addDeadline(
                DeadlineItem(
                    deadline = binding.deadlineEditText.text.toString(),
                    description = description,
                    discipline = discipline,
                    date = date
                )
            )
            dismiss()
        }

        binding.buttonChooseDiscipline.setOnClickListener { menu.show() }
    }

    override fun onResume() {
        super.onResume()
        binding.deadlineEditText.requestFocus()
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.deadlineEditText, InputMethodManager.SHOW_IMPLICIT)

        childFragmentManager.setFragmentResultListener("dateAndTime", this) { key, bundle ->
            val date = bundle.getString("date")
            val time = bundle.getString("time")

            if (date?.contains("Choose") == true) {
                binding.textDate.text = getString(R.string.no_date)
            } else if (time?.contains("Choose") == true) {
                binding.textDate.text = "$date"
            } else {
                binding.textDate.text = "$date $time"
            }
        }
    }

    companion object {
        fun newInstance(): BottomSheetFragmentAddDeadline {
            return BottomSheetFragmentAddDeadline()
        }
    }
}

class DisciplinesPopupMenu(
    private val context: Context?,
    private val anchorView: View,
) {

    private val menu by lazy { PopupMenu(context, anchorView) }

    init {
        // todo (load disciplines)
        menu.menu.add("Управление данными")
        menu.menu.add("Экономика")
        menu.menu.add("Математический анализ")
        menu.menu.add("Программирование на Java")
//        menu.menu.add("No disciplines").isEnabled = false
    }

    fun show() {
        menu.show()
    }

    fun onItemSelectedListener(onItemSelected: (String) -> Unit) {
        menu.setOnMenuItemClickListener {
            onItemSelected(it.title.toString())
            true
        }
    }
}