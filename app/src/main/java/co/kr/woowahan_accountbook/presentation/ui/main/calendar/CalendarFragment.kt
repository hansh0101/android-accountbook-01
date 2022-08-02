package co.kr.woowahan_accountbook.presentation.ui.main.calendar

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.FragmentCalendarBinding
import co.kr.woowahan_accountbook.presentation.adapter.calendar.CalendarAdapter
import co.kr.woowahan_accountbook.presentation.ui.base.BaseFragment
import co.kr.woowahan_accountbook.presentation.viewmodel.main.calendar.CalendarViewModel
import co.kr.woowahan_accountbook.util.DateUtil
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CalendarFragment : BaseFragment<FragmentCalendarBinding>() {
    override val TAG: String
        get() = CalendarFragment::class.java.simpleName
    override val layoutRes: Int
        get() = R.layout.fragment_calendar

    private val calendarAdapter by lazy { CalendarAdapter() }
    private val viewModel by viewModels<CalendarViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            binding.viewmodel = this
            setDate(
                DateUtil.getToday().split('.')[0].toInt(),
                DateUtil.getToday().split('.')[1].toInt()
            )
            getCalendarItems()
        }
        initView()
        initOnClickListener()
        observeData()
    }

    private fun initView() {
        binding.calendar.adapter = calendarAdapter
    }

    private fun initOnClickListener() {
        binding.ivLeft.setOnClickListener {
            viewModel.setPreviousMonth()
        }
        binding.ivRight.setOnClickListener {
            viewModel.setNextMonth()
        }
        binding.tvTitle.setOnClickListener {
            openDatePickerDialog()
        }
    }

    private fun openDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, _ ->
                viewModel.setDate(year, month + 1)
            },
            requireNotNull(viewModel.year.value),
            requireNotNull(viewModel.month.value) - 1,
            1,
        )
        val dateString = "20000101"
        val simpleDateFormat = SimpleDateFormat("yyyyMMdd")
        val date: Date = simpleDateFormat.parse(dateString) as Date
        val startDate = date.time
        with(datePickerDialog) {
            datePicker.minDate = startDate
            datePicker.maxDate = System.currentTimeMillis()
            setCanceledOnTouchOutside(false)
            show()
        }
    }

    private fun observeData() {
        viewModel.calendarItems.observe(viewLifecycleOwner) {
            calendarAdapter.updateItems(it)
        }
        viewModel.month.observe(viewLifecycleOwner) {
            viewModel.getCalendarItems()
        }
    }
}