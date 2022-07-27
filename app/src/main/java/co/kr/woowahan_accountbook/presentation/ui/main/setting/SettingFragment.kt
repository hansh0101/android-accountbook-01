package co.kr.woowahan_accountbook.presentation.ui.main.setting

import android.os.Bundle
import android.view.View
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.FragmentSettingBinding
import co.kr.woowahan_accountbook.presentation.ui.base.BaseFragment

class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    override val TAG: String
        get() = SettingFragment::class.java.simpleName
    override val layoutRes: Int
        get() = R.layout.fragment_setting

    companion object {
        fun newInstance() = SettingFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
    }
}