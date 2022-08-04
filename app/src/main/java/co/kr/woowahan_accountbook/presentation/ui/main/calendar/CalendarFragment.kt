package co.kr.woowahan_accountbook.presentation.ui.main.calendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.FragmentCalendarBinding
import co.kr.woowahan_accountbook.presentation.adapter.calendar.CalendarAdapter
import co.kr.woowahan_accountbook.presentation.ui.base.BaseFragment
import co.kr.woowahan_accountbook.presentation.ui.widget.YearMonthPickerDialog
import co.kr.woowahan_accountbook.presentation.viewmodel.main.calendar.CalendarViewModel
import co.kr.woowahan_accountbook.util.DateUtil
import dagger.hilt.android.AndroidEntryPoint

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
        val todayArray = DateUtil.getToday().split('.')
        val yearMonthPickerDialog =
            YearMonthPickerDialog.newInstance(todayArray[0].toInt(), todayArray[1].toInt()).apply {
                this.setListener { _, year, month, _ ->
                    viewModel.setDate(year, month)
                }
            }
        yearMonthPickerDialog.show(childFragmentManager, null)
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