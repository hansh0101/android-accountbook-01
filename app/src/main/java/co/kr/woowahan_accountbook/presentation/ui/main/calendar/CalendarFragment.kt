package co.kr.woowahan_accountbook.presentation.ui.main.calendar

import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.FragmentCalendarBinding
import co.kr.woowahan_accountbook.presentation.ui.base.BaseFragment

class CalendarFragment : BaseFragment<FragmentCalendarBinding>() {
    override val TAG: String
        get() = CalendarFragment::class.java.simpleName
    override val layoutRes: Int
        get() = R.layout.fragment_calendar
}