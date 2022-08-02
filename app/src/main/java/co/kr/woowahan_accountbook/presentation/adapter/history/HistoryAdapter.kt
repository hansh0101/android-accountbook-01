package co.kr.woowahan_accountbook.presentation.adapter.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.ItemHistoryBodyBinding
import co.kr.woowahan_accountbook.databinding.ItemHistoryHeaderBinding
import co.kr.woowahan_accountbook.domain.entity.history.HistoryItem
import co.kr.woowahan_accountbook.util.DateUtil

class HistoryAdapter(private val onItemClick: (HistoryItem) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<HistoryItem>()
    private val selectedItems = mutableListOf<HistoryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HistoryItem.Type.HEADER -> HeaderViewHolder.create(parent)
            HistoryItem.Type.BODY -> BodyViewHolder.create(parent, onItemClick, selectedItems)
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
            binding.tvHeaderDate.text = "${item.month}월 ${item.day}일 ${getDayOfWeek(item)}"

            with(items.filter { it.isIncome }.sumOf { it.amount }) {
                if (this == 0) {
                    binding.tvIncomeLabel.visibility = View.GONE
                    binding.tvIncomeValue.visibility = View.GONE
                } else {
                    binding.tvIncomeLabel.visibility = View.VISIBLE
                    binding.tvIncomeValue.visibility = View.VISIBLE
                    binding.tvIncomeValue.text = String.format("%,2d", this) + "원"
                }
            }
            with(items.filterNot { it.isIncome }.sumOf { it.amount }) {
                if (this == 0) {
                    binding.tvExpenditureLabel.visibility = View.GONE
                    binding.tvExpenditureValue.visibility = View.GONE
                } else {
                    binding.tvExpenditureLabel.visibility = View.VISIBLE
                    binding.tvExpenditureValue.visibility = View.VISIBLE
                    binding.tvExpenditureValue.text = String.format("%,2d", this) + "원"
                }
            }
        }

        private fun getDayOfWeek(item: HistoryItem): String {
            return DateUtil.getDayOfWeek(item.year, item.month, item.day)
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

    class BodyViewHolder(
        private val binding: ItemHistoryBodyBinding,
        private val onItemClick: (HistoryItem) -> Unit,
        private val selectedItems: List<HistoryItem>
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: HistoryItem) {
            binding.history = item
            binding.root.setBackgroundResource(R.color.white_f7f6f3)
            binding.root.setOnLongClickListener {
                if (selectedItems.isEmpty()) {
                    onItemClick(item)
                    binding.ivChecked.isVisible = true
                    binding.root.setBackgroundResource(R.color.white_ffffff)
                }
                true
            }
            binding.root.setOnClickListener { view ->
                if (selectedItems.isNotEmpty()) {
                    onItemClick(item)
                    binding.ivChecked.isVisible = selectedItems.find { it == item } != null
                    if (selectedItems.find { it == item } != null) {
                        binding.root.setBackgroundResource(R.color.white_ffffff)
                    } else {
                        binding.root.setBackgroundResource(R.color.white_f7f6f3)
                    }
                } else {
                    Toast.makeText(view.context, "해야지?", Toast.LENGTH_SHORT).show()
                }
            }
            if (selectedItems.isEmpty()) {
                binding.ivChecked.isVisible = false
            }
        }

        companion object Factory {
            fun create(
                parent: ViewGroup,
                onItemClick: (HistoryItem) -> Unit,
                selectedItems: List<HistoryItem>
            ): BodyViewHolder {
                val binding = DataBindingUtil.inflate<ItemHistoryBodyBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_history_body,
                    parent,
                    false
                )
                return BodyViewHolder(binding, onItemClick, selectedItems)
            }
        }
    }

    fun updateItems(newItems: List<HistoryItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun updateSelectedItems(newSelectedItems: List<HistoryItem>) {
        selectedItems.clear()
        selectedItems.addAll(newSelectedItems)
    }
}