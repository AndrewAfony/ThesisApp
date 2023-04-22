package andrewafony.thesis.application.feature_deadlines.presentation

import andrewafony.thesis.application.ViewModelFactoryProvider
import andrewafony.thesis.application.core.BaseBottomSheetFragment
import andrewafony.thesis.application.databinding.FragmentDeadlineDatePickerBinding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

class BottomSheetDateAndTimePick : BaseBottomSheetFragment<FragmentDeadlineDatePickerBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentDeadlineDatePickerBinding
        get() = FragmentDeadlineDatePickerBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC+3:00"))

        val today = SimpleDateFormat("d/MM/yyyy").format(calendar.time)
        val todayWord = SimpleDateFormat("EEE").format(calendar.time)
        calendar.add(Calendar.DATE, 1)
        val tomorrow = SimpleDateFormat("d/MM/yyyy").format(calendar.time)
        val tomorrowWord = SimpleDateFormat("EEE").format(calendar.time)
        calendar.timeInMillis = MaterialDatePicker.todayInUtcMilliseconds()
        calendar.add(Calendar.DATE, 7)
        val nextWeek = SimpleDateFormat("d/MM/yyyy").format(calendar.time)
        val nextWeekWord = SimpleDateFormat("EEE, dd").format(calendar.time)

        binding.run {
            textToday.text = todayWord
            textTomorrow.text = tomorrowWord
            textNextWeek.text = nextWeekWord
        }

        val calendarConstraints = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setCalendarConstraints(calendarConstraints.build()).build()
        val timePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()

        datePicker.addOnPositiveButtonClickListener {
            binding.textDate.text = SimpleDateFormat("d/MM/yyyy").format(Date(it))
        }
        timePicker.addOnPositiveButtonClickListener {
            val minutes =
                if (timePicker.minute < 10) "0${timePicker.minute}" else "${timePicker.minute}"
            val hours = if (timePicker.hour < 10) "0${timePicker.hour}" else "${timePicker.hour}"
            binding.textTime.text = "$hours:$minutes"
            if (binding.textDate.text.contains("Choose"))
                binding.textDate.text = today
        }

        binding.run {
            buttonChooseDateDatePicker.setOnClickListener {
                datePicker.show(childFragmentManager, null)
            }
            buttonChooseTime.setOnClickListener {
                timePicker.show(childFragmentManager, null)
            }
            buttonCancel.setOnClickListener { dismiss() }
            buttonDone.setOnClickListener {
                setFragmentResult("dateAndTime",
                    bundleOf(
                        "date" to binding.textDate.text.toString(),
                        "time" to binding.textTime.text.toString()
                    )
                )
                dismiss()
            }
            buttonToday.setOnClickListener {
                binding.textDate.text = today
            }
            buttonTomorrow.setOnClickListener {
                binding.textDate.text = tomorrow
            }
            buttonNextWeek.setOnClickListener {
                binding.textDate.text = nextWeek
            }
            buttonNoDate.setOnClickListener {
                binding.textDate.text = "Choose date"
                binding.textTime.text = "Choose time"
            }

            binding.run {
                textDate.text = arguments?.getString("date") ?: "Choose date"
                textTime.text = arguments?.getString("time") ?: "Choose time"
            }
        }
    }

    companion object {
        fun newInstance(date: String?, time: String?): BottomSheetDateAndTimePick {
            val fragment = BottomSheetDateAndTimePick().apply {
                arguments = bundleOf("date" to date, "time" to time)
            }
            return fragment
        }
    }
}