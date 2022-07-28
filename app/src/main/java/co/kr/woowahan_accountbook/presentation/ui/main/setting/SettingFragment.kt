package co.kr.woowahan_accountbook.presentation.ui.main.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.FragmentSettingBinding
import co.kr.woowahan_accountbook.presentation.adapter.SettingClassificationAdapter
import co.kr.woowahan_accountbook.presentation.adapter.SettingPaymentAdapter
import co.kr.woowahan_accountbook.presentation.ui.base.BaseFragment
import co.kr.woowahan_accountbook.presentation.viewmodel.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    override val TAG: String
        get() = SettingFragment::class.java.simpleName
    override val layoutRes: Int
        get() = R.layout.fragment_setting

    private val viewModel by viewModels<SettingViewModel>()
    private val settingPaymentAdapter by lazy { SettingPaymentAdapter() }
    private val settingIncomeClassificationAdapter by lazy { SettingClassificationAdapter() }
    private val settingExpenditureClassificationAdapter by lazy { SettingClassificationAdapter() }
    private lateinit var adapter: ConcatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    private fun initView() {
        binding.rvSetting.adapter = ConcatAdapter(
            settingPaymentAdapter,
            settingIncomeClassificationAdapter,
            settingExpenditureClassificationAdapter
        )
    }

    private fun observeData() {
        viewModel.payments.observe(viewLifecycleOwner) {
            settingPaymentAdapter.updateItems(it)
        }
        viewModel.incomeClassifications.observe(viewLifecycleOwner) {
            settingIncomeClassificationAdapter.updateItems(it)
        }
        viewModel.expenditureClassification.observe(viewLifecycleOwner) {
            settingExpenditureClassificationAdapter.updateItems(it)
        }
    }
}