package co.kr.woowahan_accountbook.presentation.ui.main.history

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.FragmentHistoryAddBinding
import co.kr.woowahan_accountbook.presentation.adapter.history.HistoryClassificationSpinnerAdapter
import co.kr.woowahan_accountbook.presentation.adapter.history.HistoryPaymentSpinnerAdapter
import co.kr.woowahan_accountbook.presentation.ui.base.BaseFragment
import co.kr.woowahan_accountbook.presentation.ui.main.setting.ClassificationAddFragment
import co.kr.woowahan_accountbook.presentation.ui.main.setting.PaymentAddFragment
import co.kr.woowahan_accountbook.presentation.ui.widget.HistoryAddPaymentSpinner
import co.kr.woowahan_accountbook.presentation.viewmodel.main.history.HistoryAddViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class HistoryAddFragment : BaseFragment<FragmentHistoryAddBinding>(),
    HistoryAddPaymentSpinner.OnSpinnerEventsListener {
    override val TAG: String
        get() = HistoryAddFragment::class.java.simpleName
    override val layoutRes: Int
        get() = R.layout.fragment_history_add

    private val historyPaymentSpinnerAdapter by lazy { HistoryPaymentSpinnerAdapter() }

    private val historyClassificationSpinnerAdapter by lazy {
        HistoryClassificationSpinnerAdapter(isIncome = true)
    }

    private val viewModel by viewModels<HistoryAddViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        initView()
        initOnClickListener()
        observeData()
    }

    override fun onStart() {
        super.onStart()
        initData()
    }

    private fun initView() {
        initToolbar()
        initPaymentSpinner()
        initClassificationSpinner()
        initDateValue()
    }

    private fun initOnClickListener() {
        binding.tvIncome.setOnClickListener {
            viewModel.onClickHistoryType(true)
        }
        binding.tvExpenditure.setOnClickListener {
            viewModel.onClickHistoryType(false)
        }
        binding.btnAdd.setOnClickListener {
            viewModel.insertHistory()
        }
    }

    private fun initData() {
        with(viewModel) {
            getPayments()
            getClassifications()
        }
    }

    private fun initToolbar() {
        with(binding.tbHistoryAdd) {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun initPaymentSpinner() {
        with(binding.spinnerPaymentValue) {
            setSpinnerEventsListener(this@HistoryAddFragment)
            adapter = historyPaymentSpinnerAdapter
            setSelection(historyPaymentSpinnerAdapter.count - 1)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == historyPaymentSpinnerAdapter.count - 2) {
                        parentFragmentManager.commit {
                            replace<PaymentAddFragment>(R.id.fcv_main)
                            addToBackStack(PaymentAddFragment::class.java.simpleName)
                        }
                    } else {
                        viewModel.onSelectedPaymentItem(
                            historyPaymentSpinnerAdapter.getItem(position).id
                        )
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }
    }

    private fun initClassificationSpinner() {
        with(binding.spinnerClassificationValue) {
            setSpinnerEventsListener(this@HistoryAddFragment)
            adapter = historyClassificationSpinnerAdapter
            setSelection(historyClassificationSpinnerAdapter.count - 1)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == historyClassificationSpinnerAdapter.count - 2) {
                        parentFragmentManager.commit {
                            replace<ClassificationAddFragment>(
                                R.id.fcv_main,
                                null,
                                Bundle().apply {
                                    putInt(
                                        "IS_INCOME",
                                        if (requireNotNull(viewModel.isIncome.value)) 1 else 0
                                    )
                                })
                            addToBackStack(ClassificationAddFragment::class.java.simpleName)
                        }
                    } else {
                        viewModel.onSelectedClassificationItem(
                            historyClassificationSpinnerAdapter.getItem(position).id
                        )
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }
    }

    private fun initDateValue() {
        val calendar = Calendar.getInstance()
        val time = calendar.time
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd.E요일")
        val date = simpleDateFormat.format(time)
        viewModel.setDate(date)
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

    private fun observeData() {
        addTextChangedListener()

        viewModel.isIncome.observe(viewLifecycleOwner) {
            viewModel.getClassifications()
        }

        viewModel.payments.observe(viewLifecycleOwner) {
            Timber.tag("payments size").i(it.size.toString())
            historyPaymentSpinnerAdapter.updateItems(it)
            binding.spinnerPaymentValue.setSelection(it.size)
        }

        viewModel.classifications.observe(viewLifecycleOwner) {
            Timber.tag("classifications size").i(it.size.toString())
            historyClassificationSpinnerAdapter.updateItems(it)
            binding.spinnerClassificationValue.setSelection(it.size)
        }

        viewModel.amount.observe(viewLifecycleOwner) {
            Timber.tag("amount observe").i(it.toString())
        }

        viewModel.date.observe(viewLifecycleOwner) {
            Timber.tag("date observe").i(it)
        }

        viewModel.payment.observe(viewLifecycleOwner) {
            Timber.tag("payment observe").i(it.toString())
        }

        viewModel.isSuccess.observe(viewLifecycleOwner) {
            requireActivity().onBackPressed()
        }
    }

    private fun addTextChangedListener() {
        binding.etAmountValue.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (binding.etAmountValue.text == null || binding.etAmountValue.text.toString() == "") {
                    return
                }
                binding.etAmountValue.removeTextChangedListener(this)

                val value = binding.etAmountValue.text.toString().replace(",", "")
                val parsed: String = "%,d".format(value.toInt())

                binding.etAmountValue.setText(parsed)
                binding.etAmountValue.setSelection(parsed.length)
                binding.etAmountValue.addTextChangedListener(this)

                if (binding.etAmountValue.text == null || binding.etAmountValue.text.toString() == "") {
                    viewModel.amount.value = 0
                } else {
                    viewModel.amount.value =
                        binding.etAmountValue.text.toString().replace(",", "").toInt()
                }
            }
        })
    }
}