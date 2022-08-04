package co.kr.woowahan_accountbook.presentation.ui.main.statistics

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.FragmentStatisticsBinding
import co.kr.woowahan_accountbook.domain.entity.statistics.StatisticsItem
import co.kr.woowahan_accountbook.presentation.adapter.statistics.StatisticsAdapter
import co.kr.woowahan_accountbook.presentation.ui.base.BaseFragment
import co.kr.woowahan_accountbook.presentation.ui.widget.YearMonthPickerDialog
import co.kr.woowahan_accountbook.presentation.viewmodel.main.statistics.StatisticsViewModel
import co.kr.woowahan_accountbook.util.DateUtil
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>() {
    override val TAG: String
        get() = StatisticsFragment::class.java.simpleName
    override val layoutRes: Int
        get() = R.layout.fragment_statistics

    private val viewModel by viewModels<StatisticsViewModel>()
    private val statisticsAdapter by lazy { StatisticsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            binding.viewmodel = this
            setDate(
                DateUtil.getToday().split('.')[0].toInt(),
                DateUtil.getToday().split('.')[1].toInt()
            )
            getStatistics()
        }
        initView()
        initOnClickListener()
        observeData()
    }

    private fun initView() {
        binding.rvStatistics.adapter = statisticsAdapter
    }

    private fun initOnClickListener() {
        binding.ivLeft.setOnClickListener {
            if (viewModel.year.value == 2000 && viewModel.month.value == 1) {
                Toast.makeText(requireContext(), "2000년 1월 이전은 조회할 수 없습니다.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewModel.setPreviousMonth()
            }
        }
        binding.ivRight.setOnClickListener {
            if (viewModel.year.value == 2099 && viewModel.month.value == 12) {
                Toast.makeText(requireContext(), "2099년 12월 이후는 조회할 수 없습니다.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewModel.setNextMonth()
            }
        }
        binding.tvTitle.setOnClickListener {
            openDatePickerDialog()
        }
    }

    private fun openDatePickerDialog() {
        val todayArray = DateUtil.getToday().split('.')
        val yearMonthPickerDialog =
            YearMonthPickerDialog.newInstance(todayArray[0].toInt(), todayArray[1].toInt()).apply {
                this.setListener { _, year, month, _ ->
                    viewModel.setDate(year, month)
                }
            }
        yearMonthPickerDialog.show(childFragmentManager, null)
    }

    private fun observeData() {
        viewModel.statistics.observe(viewLifecycleOwner) { list ->
            statisticsAdapter.updateItems(list)
            binding.tvTotalAmountValue.text = String.format("%,2d", list.sumOf { it.amount })
            initChart(list)
        }
        viewModel.month.observe(viewLifecycleOwner) {
            viewModel.getStatistics()
        }
    }

    private fun initChart(items: List<StatisticsItem>) {
        if (items.isNotEmpty()) {
            binding.chartStatistics.isVisible = true
            val amounts = mutableListOf<PieEntry>()
            val colors = mutableListOf<Int>()
            items.forEachIndexed { index, item ->
                amounts.add(PieEntry(item.amount.toFloat()))
                colors.add(Color.parseColor(item.classificationColor))
            }
            val dataSet = PieDataSet(amounts, "").apply {
                this.colors = colors
                setDrawValues(false)
            }
            val data = PieData(dataSet)
            with(binding.chartStatistics) {
                description.isEnabled = false
                legend.isEnabled = false
                setTouchEnabled(false)
                this.data = data
                animateXY(1000, 1000)
            }
        } else {
            binding.chartStatistics.isVisible = false
        }
    }
}