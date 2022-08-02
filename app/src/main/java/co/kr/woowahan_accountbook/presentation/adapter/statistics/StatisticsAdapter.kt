package co.kr.woowahan_accountbook.presentation.adapter.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.ItemStatisticsBinding
import co.kr.woowahan_accountbook.domain.entity.statistics.StatisticsItem
import kotlin.math.roundToInt

class StatisticsAdapter : RecyclerView.Adapter<StatisticsAdapter.StatisticsViewHolder>() {
    private val items = mutableListOf<StatisticsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsViewHolder {
        return StatisticsViewHolder.create(parent, items)
    }

    override fun onBindViewHolder(holder: StatisticsViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class StatisticsViewHolder(
        private val binding: ItemStatisticsBinding,
        private val items: List<StatisticsItem>
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: StatisticsItem) {
            binding.item = item
            binding.tvClassificationPercentage.text =
                "${(item.amount.toDouble() / items.sumOf { it.amount }.toDouble() * 100).roundToInt()}%"
        }

        companion object Factory {
            fun create(parent: ViewGroup, items: List<StatisticsItem>): StatisticsViewHolder {
                val binding = DataBindingUtil.inflate<ItemStatisticsBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_statistics,
                    parent,
                    false
                )
                return StatisticsViewHolder(binding, items)
            }
        }
    }

    fun updateItems(newItems: List<StatisticsItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}