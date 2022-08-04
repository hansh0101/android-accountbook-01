package co.kr.woowahan_accountbook.presentation.ui.main.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.FragmentSettingBinding
import co.kr.woowahan_accountbook.presentation.adapter.setting.SettingClassificationAdapter
import co.kr.woowahan_accountbook.presentation.adapter.setting.SettingPaymentAdapter
import co.kr.woowahan_accountbook.presentation.ui.base.BaseFragment
import co.kr.woowahan_accountbook.presentation.viewmodel.main.setting.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    override val TAG: String
        get() = SettingFragment::class.java.simpleName
    override val layoutRes: Int
        get() = R.layout.fragment_setting

    private val viewModel by viewModels<SettingViewModel>()
    private val settingPaymentAdapter by lazy {
        SettingPaymentAdapter({
            parentFragmentManager.commit {
                replace<PaymentAddFragment>(R.id.fcv_main, null, Bundle().apply {
                    putInt("_ID", it.id)
                    putString("PAYMENT_NAME", it.paymentName)
                })
                addToBackStack(PaymentAddFragment::class.java.simpleName)
            }
        }, {
            parentFragmentManager.commit {
                replace<PaymentAddFragment>(R.id.fcv_main)
                addToBackStack(PaymentAddFragment::class.java.simpleName)
            }
        })
    }
    private val settingIncomeClassificationAdapter by lazy {
        SettingClassificationAdapter({
            parentFragmentManager.commit {
                replace<ClassificationAddFragment>(R.id.fcv_main, null, Bundle().apply {
                    putInt("_ID", it.id)
                    putString("CLASSIFICATION_TYPE", it.classificationType)
                    putString("CLASSIFICATION_COLOR", it.classificationColor)
                    putInt("IS_INCOME", if (it.isIncome) 1 else 0)
                })
                addToBackStack((ClassificationAddFragment::class.java.simpleName))
            }
        }, {
            parentFragmentManager.commit {
                replace<ClassificationAddFragment>(R.id.fcv_main, null, Bundle().apply {
                    putInt("IS_INCOME", 1)
                })
                addToBackStack(ClassificationAddFragment::class.java.simpleName)
            }
        })
    }
    private val settingExpenditureClassificationAdapter by lazy {
        SettingClassificationAdapter({
            parentFragmentManager.commit {
                replace<ClassificationAddFragment>(R.id.fcv_main, null, Bundle().apply {
                    putInt("_ID", it.id)
                    putString("CLASSIFICATION_TYPE", it.classificationType)
                    putString("CLASSIFICATION_COLOR", it.classificationColor)
                    putInt("IS_INCOME", if (it.isIncome) 1 else 0)
                })
                addToBackStack((ClassificationAddFragment::class.java.simpleName))
            }
        }, {
            parentFragmentManager.commit {
                replace<ClassificationAddFragment>(R.id.fcv_main, null, Bundle().apply {
                    putInt("IS_INCOME", 0)
                })
                addToBackStack(ClassificationAddFragment::class.java.simpleName)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData()
        initView()
        observeData()
    }

    private fun initView() {
        binding.rvSetting.adapter = ConcatAdapter(
            settingPaymentAdapter,
            settingExpenditureClassificationAdapter,
            settingIncomeClassificationAdapter
        )
    }

    private fun observeData() {
        viewModel.payments.observe(viewLifecycleOwner) {
            settingPaymentAdapter.updateItems(it)
        }
        viewModel.expenditureClassification.observe(viewLifecycleOwner) {
            settingExpenditureClassificationAdapter.updateItems(it)
        }
        viewModel.incomeClassifications.observe(viewLifecycleOwner) {
            settingIncomeClassificationAdapter.updateItems(it)
        }
    }
}