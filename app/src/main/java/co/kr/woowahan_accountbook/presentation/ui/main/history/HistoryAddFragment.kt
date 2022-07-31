package co.kr.woowahan_accountbook.presentation.ui.main.history

import android.os.Bundle
import android.view.View
import android.widget.Spinner
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.FragmentHistoryAddBinding
import co.kr.woowahan_accountbook.domain.entity.dto.ClassificationDto
import co.kr.woowahan_accountbook.domain.entity.dto.PaymentDto
import co.kr.woowahan_accountbook.presentation.adapter.history.HistoryClassificationSpinnerAdapter
import co.kr.woowahan_accountbook.presentation.adapter.history.HistoryPaymentSpinnerAdapter
import co.kr.woowahan_accountbook.presentation.ui.base.BaseFragment
import co.kr.woowahan_accountbook.presentation.ui.widget.HistoryAddPaymentSpinner

class HistoryAddFragment : BaseFragment<FragmentHistoryAddBinding>(),
    HistoryAddPaymentSpinner.OnSpinnerEventsListener {
    override val TAG: String
        get() = HistoryAddFragment::class.java.simpleName
    override val layoutRes: Int
        get() = R.layout.fragment_history_add

    private val historyPaymentSpinnerAdapter by lazy {
        HistoryPaymentSpinnerAdapter(
            requireContext(),
            listOf(PaymentDto(1, "국민은행 체크카드"), PaymentDto(2, "카카오뱅크"))
        )
    }

    private val historyClassificationSpinnerAdapter by lazy {
        HistoryClassificationSpinnerAdapter(
            requireContext(),
            listOf(
                ClassificationDto(1, "식비", "#CB588F", false),
                ClassificationDto(2, "교육비", "#9BD182", true)
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        with(binding.tbHistoryAdd) {
            setNavigationIcon(co.kr.woowahan_accountbook.R.drawable.ic_back)
            setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }
        with(binding.spinnerPaymentValue) {
            setSpinnerEventsListener(this@HistoryAddFragment)
            adapter = historyPaymentSpinnerAdapter
            setSelection(3)
        }
        with(binding.spinnerClassificationValue) {
            setSpinnerEventsListener(this@HistoryAddFragment)
            adapter = historyClassificationSpinnerAdapter
            setSelection(3)
        }
    }

    override fun onPopupWindowOpened(spinner: Spinner?) {
        spinner?.let {
            it.background =
                resources.getDrawable(R.drawable.bg_spinner_history_add_up, null)
        }
    }

    override fun onPopupWindowClosed(spinner: Spinner?) {
        spinner?.let {
            it.background =
                resources.getDrawable(R.drawable.bg_spinner_history_add, null)
        }
    }
}