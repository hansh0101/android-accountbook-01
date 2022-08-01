package co.kr.woowahan_accountbook.presentation.adapter.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.view.isVisible
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.ItemHistoryAddSpinnerBinding
import co.kr.woowahan_accountbook.domain.entity.dto.ClassificationDto

class HistoryClassificationSpinnerAdapter(isIncome: Boolean) : BaseAdapter() {
    private var items = listOf<ClassificationDto>(
        ClassificationDto(0, "추가하기", "", isIncome),
        ClassificationDto(0, "선택하세요", "", isIncome)
    )

    override fun getCount(): Int = if (items.isEmpty()) 0 else items.size

    override fun getItem(position: Int): ClassificationDto = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = ItemHistoryAddSpinnerBinding.inflate(
            LayoutInflater.from(parent?.context),
            parent,
            false
        )

        binding.tvLabel.text = getItem(position).classificationType
        if (position == count - 1) {
            binding.root.layoutParams.height = 1
        } else if (position == count - 2) {
            binding.ivPlus.isVisible = true
        }
        return binding.root
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = ItemHistoryAddSpinnerBinding.inflate(
            LayoutInflater.from(parent?.context),
            parent,
            false
        )

        if (position == items.size - 1) {
            with(binding.tvLabel) {
                hint = items[position].classificationType
                setHintTextColor(resources.getColor(R.color.light_purple_a79fcb, null))
            }
        } else {
            binding.tvLabel.text = items[position].classificationType
        }
        return binding.root
    }

    override fun isEnabled(position: Int): Boolean {
        return position != count - 1
    }

    fun updateItems(newItems: List<ClassificationDto>) {
        items = newItems
        notifyDataSetChanged()
    }
}