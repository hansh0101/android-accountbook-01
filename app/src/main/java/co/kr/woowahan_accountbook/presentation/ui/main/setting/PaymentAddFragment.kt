package co.kr.woowahan_accountbook.presentation.ui.main.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.FragmentPaymentAddBinding
import co.kr.woowahan_accountbook.presentation.ui.base.BaseFragment
import co.kr.woowahan_accountbook.presentation.viewmodel.main.setting.PaymentAddViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentAddFragment : BaseFragment<FragmentPaymentAddBinding>() {
    override val TAG: String
        get() = PaymentAddFragment::class.java.simpleName
    override val layoutRes: Int
        get() = R.layout.fragment_payment_add

    private var id: Int? = null
    private var name: String? = null
    private val viewModel by viewModels<PaymentAddViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt("_ID")
            name = it.getString("PAYMENT_NAME")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        initView()
        observeData()
    }

    private fun initView() {
        with(binding.tbPaymentAdd) {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }
        name?.let { viewModel.name.value = it }
        binding.btnAdd.setOnClickListener {
            id?.let {
                viewModel.updatePayment(it)
            } ?: viewModel.addPayment()
        }
    }

    private fun observeData() {
        viewModel.isSuccess.observe(viewLifecycleOwner) {
            if (it) requireActivity().onBackPressed()
        }
    }
}