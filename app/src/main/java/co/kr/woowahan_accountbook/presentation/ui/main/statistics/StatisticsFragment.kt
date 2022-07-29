package co.kr.woowahan_accountbook.presentation.ui.main.statistics

import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.FragmentStatisticsBinding
import co.kr.woowahan_accountbook.presentation.ui.base.BaseFragment

class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>() {
    override val TAG: String
        get() = StatisticsFragment::class.java.simpleName
    override val layoutRes: Int
        get() = R.layout.fragment_statistics
}