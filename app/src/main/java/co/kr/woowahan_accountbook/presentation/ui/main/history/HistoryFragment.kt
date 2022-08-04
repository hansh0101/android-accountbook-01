package co.kr.woowahan_accountbook.presentation.ui.main.history

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.FragmentHistoryBinding
import co.kr.woowahan_accountbook.presentation.adapter.history.HistoryAdapter
import co.kr.woowahan_accountbook.presentation.ui.base.BaseFragment
import co.kr.woowahan_accountbook.presentation.viewmodel.main.history.HistoryViewModel
import co.kr.woowahan_accountbook.util.DateUtil
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {
    override val TAG: String
        get() = HistoryFragment::class.java.simpleName
    override val layoutRes: Int
        get() = R.layout.fragment_history

    private val viewModel by viewModels<HistoryViewModel>()
    private val historyAdapter by lazy {
        HistoryAdapter({
            viewModel.updateSelectedItems(it)
        }, {
            parentFragmentManager.commit {
                replace<HistoryAddFragment>(R.id.fcv_main, null, Bundle().apply {
                    putSerializable("ITEM", it)
                })
                addToBackStack(HistoryAddFragment::class.java.simpleName)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        with(viewModel) {
            setDate(
                DateUtil.getToday().split('.')[0].toInt(),
                DateUtil.getToday().split('.')[1].toInt()
            )
            getHistories()
            getTotalAmountByType(true)
            getTotalAmountByType(false)
        }
        initView()
        initOnClickListener()
        observeData()
    }

    private fun initView() {
        binding.rvHistory.adapter = historyAdapter
    }

    private fun initOnClickListener() {
        binding.fabAdd.setOnClickListener {
            if (viewModel.isIncomeSelected.value == true ||
                (viewModel.isIncomeSelected.value == false && viewModel.isExpenditureSelected.value == false)
            ) {
                parentFragmentManager.commit {
                    replace<HistoryAddFragment>(
                        R.id.fcv_main,
                        null,
                        Bundle().apply { putBoolean("IS_INCOME", true) })
                    addToBackStack(HistoryAddFragment::class.java.simpleName)
                }
            } else {
                parentFragmentManager.commit {
                    replace<HistoryAddFragment>(
                        R.id.fcv_main,
                        null,
                        Bundle().apply { putBoolean("IS_INCOME", false) })
                    addToBackStack(HistoryAddFragment::class.java.simpleName)
                }
            }
        }
        binding.layoutIncome.setOnClickListener {
            viewModel.onClickIncomeButton()
        }
        binding.layoutExpenditure.setOnClickListener {
            viewModel.onClickExpenditure()
        }
        binding.ivLeft.setOnClickListener {
            if (binding.fabAdd.isVisible) {
                viewModel.setPreviousMonth()
            } else {
                viewModel.clearSelectedItems()
                historyAdapter.notifyDataSetChanged()
            }
        }
        binding.ivRight.setOnClickListener {
            if (binding.fabAdd.isVisible) {
                viewModel.setNextMonth()
            } else {
                viewModel.deleteSelectedItems()
            }
        }
        binding.tvTitle.setOnClickListener {
            openDatePickerDialog()
        }
    }

    private fun openDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, _ ->
                viewModel.setDate(year, month + 1)
            },
            requireNotNull(viewModel.year.value),
            requireNotNull(viewModel.month.value) - 1,
            1,
        )
        val dateString = "20000101"
        val simpleDateFormat = SimpleDateFormat("yyyyMMdd")
        val date: Date = simpleDateFormat.parse(dateString) as Date
        val startDate = date.time
        with(datePickerDialog) {
            datePicker.minDate = startDate
            datePicker.maxDate = System.currentTimeMillis()
            setCanceledOnTouchOutside(false)
            show()
        }
    }

    private fun observeData() {
        viewModel.histories.observe(viewLifecycleOwner) {
            historyAdapter.updateItems(it)
            binding.ivRight.setImageResource(R.drawable.ic_right)
            binding.fabAdd.isVisible = true
        }
        viewModel.isIncomeSelected.observe(viewLifecycleOwner) {
            viewModel.getHistories()
        }
        viewModel.isExpenditureSelected.observe(viewLifecycleOwner) {
            with(viewModel) {
                getHistories()
                getTotalAmountByType(true)
                getTotalAmountByType(false)
            }
        }
        viewModel.month.observe(viewLifecycleOwner) {
            with(viewModel) {
                getHistories()
                getTotalAmountByType(true)
                getTotalAmountByType(false)
            }
        }
        viewModel.historiesSelected.observe(viewLifecycleOwner) {
            historyAdapter.updateSelectedItems(it)
        }
    }
}