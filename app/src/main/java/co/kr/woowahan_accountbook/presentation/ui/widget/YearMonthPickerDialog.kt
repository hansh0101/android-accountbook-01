package co.kr.woowahan_accountbook.presentation.ui.widget

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import co.kr.woowahan_accountbook.databinding.DialogYearMonthPickerBinding
import co.kr.woowahan_accountbook.util.DateUtil

class YearMonthPickerDialog : DialogFragment() {
    private val todayArray = DateUtil.getToday().split('.')
    private val minYear = 2000

    private lateinit var listener: DatePickerDialog.OnDateSetListener

    fun setListener(listener: DatePickerDialog.OnDateSetListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val binding = DialogYearMonthPickerBinding.inflate(
            requireActivity().layoutInflater,
            null,
            false
        )

        with(binding.numberPickerMonth) {
            minValue = 1
            maxValue = 12
            value = todayArray[1].toInt()
            wrapSelectorWheel = false
        }
        with(binding.numberPickerYear) {
            minValue = 2000
            maxValue = 2099
            value = todayArray[0].toInt()
            wrapSelectorWheel = false
        }

        return builder.setView(binding.root)
            .setPositiveButton("확인") { _, _ ->
                listener.onDateSet(
                    null,
                    binding.numberPickerYear.value,
                    binding.numberPickerMonth.value,
                    0
                )
            }
            .setNegativeButton("취소") { _, _ ->
                this.dismiss()
            }
            .create().apply {
                setCanceledOnTouchOutside(false)
            }
    }

    companion object {
        fun newInstance(year: Int, month: Int): YearMonthPickerDialog {
            return YearMonthPickerDialog().apply {
                arguments = Bundle().apply {
                    putInt("YEAR", year)
                    putInt("MONTH", month)
                }
            }
        }
    }
}