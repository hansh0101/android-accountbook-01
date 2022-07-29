package co.kr.woowahan_accountbook.presentation.ui.main.history

import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.FragmentHistoryBinding
import co.kr.woowahan_accountbook.presentation.ui.base.BaseFragment

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {
    override val TAG: String
        get() = HistoryFragment::class.java.simpleName
    override val layoutRes: Int
        get() = R.layout.fragment_history
}