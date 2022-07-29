package co.kr.woowahan_accountbook.presentation.adapter.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.ItemHistoryBodyBinding
import co.kr.woowahan_accountbook.databinding.ItemHistoryHeaderBinding
import co.kr.woowahan_accountbook.domain.entity.history.HistoryItem
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<HistoryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HistoryItem.Type.HEADER -> HeaderViewHolder.create(parent)
            HistoryItem.Type.BODY -> BodyViewHolder.create(parent)
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.onBind(
                items[position],
                items.filter { items[position].day == it.day })
            is BodyViewHolder -> holder.onBind(items[position])
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].type

    class HeaderViewHolder(private val binding: ItemHistoryHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: HistoryItem, items: List<HistoryItem>) {
            binding.tvHeaderDate.text = "${item.month}월 ${item.day}일 ${setDayOfWeek(item)}"
            binding.tvIncomeValue.text =
                items.filter { it.isIncome }.sumOf { it.amount }.toString() + "원"
            binding.tvExpenditureValue.text =
                items.filterNot { it.isIncome }.sumOf { it.amount }.toString() + "원"
        }

        private fun setDayOfWeek(item: HistoryItem): String {
            val yearString = item.year.toString()
            val monthString = String.format("%02d", item.month)
            val dayString = String.format("%02d", item.day)
            val date = SimpleDateFormat("yyyyMMdd").parse(yearString + monthString + dayString)
            val calendar = Calendar.getInstance()
            calendar.time = date ?: throw IllegalArgumentException()
            return DayOfWeek.stringOf(calendar.get(Calendar.DAY_OF_WEEK))
        }

        companion object Factory {
            fun create(parent: ViewGroup): HeaderViewHolder {
                val binding = ItemHistoryHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return HeaderViewHolder(binding)
            }
        }
    }

    class BodyViewHolder(private val binding: ItemHistoryBodyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: HistoryItem) {
            binding.history = item
        }

        companion object Factory {
            fun create(parent: ViewGroup): BodyViewHolder {
                val binding = DataBindingUtil.inflate<ItemHistoryBodyBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_history_body,
                    parent,
                    false
                )
                return BodyViewHolder(binding)
            }
        }
    }

    fun updateItems(newItems: List<HistoryItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    enum class DayOfWeek(val index: Int, val dayOfWeek: String) {
        SUNDAY(0, "일요일"),
        MONDAY(1, "월요일"),
        TUESDAY(2, "화요일"),
        WEDNESDAY(3, "수요일"),
        THURSDAY(4, "목요일"),
        FRIDAY(5, "금요일"),
        SATURDAY(6, "토요일");

        companion object {
            fun stringOf(index: Int): String {
                return values().find { it.index == index }?.dayOfWeek
                    ?: throw IllegalArgumentException()
            }
        }
    }
}