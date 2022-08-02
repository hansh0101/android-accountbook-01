package co.kr.woowahan_accountbook.presentation.ui.main.statistics

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.FragmentStatisticsBinding
import co.kr.woowahan_accountbook.domain.entity.statistics.StatisticsItem
import co.kr.woowahan_accountbook.presentation.adapter.statistics.StatisticsAdapter
import co.kr.woowahan_accountbook.presentation.ui.base.BaseFragment
import co.kr.woowahan_accountbook.presentation.viewmodel.main.statistics.StatisticsViewModel
import co.kr.woowahan_accountbook.util.DateUtil
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

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
            setDate(DateUtil.date.split('.')[0].toInt(), DateUtil.date.split('.')[1].toInt())
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
            viewModel.setPreviousMonth()
        }
        binding.ivRight.setOnClickListener {
            viewModel.setNextMonth()
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
                setTransparentCircleColor(R.color.white_f7f6f3)
                this.data = data
                animateXY(1000, 1000)
            }
        } else {
            binding.chartStatistics.isVisible = false
        }
    }
}