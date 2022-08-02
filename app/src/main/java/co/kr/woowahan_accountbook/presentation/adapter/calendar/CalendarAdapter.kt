package co.kr.woowahan_accountbook.presentation.adapter.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.view.isVisible
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.ItemCalendarBinding
import co.kr.woowahan_accountbook.domain.entity.calendar.CalendarItem
import co.kr.woowahan_accountbook.util.DateUtil

class CalendarAdapter : BaseAdapter() {
    private val items = mutableListOf<CalendarItem>()
    private lateinit var firstDay: CalendarItem
    private lateinit var lastDay: CalendarItem
    private var beforeFirstDayCount = 0
    private var afterLastDayCount = 0
    private val todayArray = DateUtil.getToday().split('.')
    private var previousDayCount = 0

    override fun getCount(): Int {
        return if (items.isEmpty()) {
            0
        } else {
            return items.size + beforeFirstDayCount + afterLastDayCount
        }
    }

    override fun getItem(position: Int): CalendarItem? {
        return if (position < beforeFirstDayCount) {
            null
        } else if (position >= beforeFirstDayCount + items.size) {
            items[position - beforeFirstDayCount]
        } else {
            null
        }
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = ItemCalendarBinding.inflate(
            LayoutInflater.from(parent?.context),
            parent,
            false
        )
        // 1, 3, 5, 7, 8, 10, 12
        // 4, 6, 9, 11
        // 2
        if (position >= beforeFirstDayCount && position < beforeFirstDayCount + items.size) {
            with(items[position - beforeFirstDayCount]) {
                binding.tvDay.text = this.day.toString()
                binding.tvIncomeValue.text = String.format("%,2d", this.income)
                binding.tvExpenditureValue.text = String.format("%,2d", this.expenditure)
                binding.tvTotalValue.text = String.format("%,2d", this.total)
                if (this.income == 0) binding.tvIncomeValue.isVisible = false
                if (this.expenditure == 0) binding.tvExpenditureValue.isVisible = false
                if (this.total == 0) binding.tvTotalValue.isVisible = false
                if (todayArray[2].toInt() == this.day && todayArray[1].toInt() == this.month && todayArray[0].toInt() == this.year) {
                    binding.root.backgroundTintList =
                        binding.root.context.resources.getColorStateList(R.color.white_ffffff, null)
                }
            }
        } else if (position < beforeFirstDayCount) {
            with(binding.tvDay) {
                text = (previousDayCount - beforeFirstDayCount + position + 1).toString()
                setTextColor(this.resources.getColor(R.color.purple_66a79fcb, null))
            }
        } else {
            with(binding.tvDay) {
                text = (position - beforeFirstDayCount - items.size + 1).toString()
                setTextColor(this.resources.getColor(R.color.purple_66a79fcb, null))
            }
        }
        return binding.root
    }

    fun updateItems(newItems: List<CalendarItem>) {
        items.clear()
        items.addAll(newItems)
        if (newItems.isNotEmpty()) {
            previousDayCount =
                DateUtil.getPreviousMonthDayCount(newItems.first().year, newItems.first().month)
            firstDay = newItems.first()
            lastDay = newItems.last()
            beforeFirstDayCount =
                DateUtil.getDayOfWeekIndex(firstDay.year, firstDay.month, firstDay.day)
            afterLastDayCount =
                6 - DateUtil.getDayOfWeekIndex(lastDay.year, lastDay.month, lastDay.day)
        }
        notifyDataSetChanged()
    }
}