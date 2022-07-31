package co.kr.woowahan_accountbook.presentation.adapter.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.view.isVisible
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.ItemHistoryAddSpinnerBinding
import co.kr.woowahan_accountbook.domain.entity.dto.ClassificationDto

class HistoryClassificationSpinnerAdapter(
    private val context: Context,
    private val list: List<ClassificationDto>
) :
    BaseAdapter() {
    override fun getCount(): Int = list.size + 1

    override fun getItem(position: Int): ClassificationDto? {
        return if (position == list.size) {
            null
        } else {
            list[position]
        }
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = ItemHistoryAddSpinnerBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )

        if (position == list.size + 1) {
            with(binding.tvLabel) {
                hint = "선택하세요"
                setHintTextColor(resources.getColor(R.color.light_purple_a79fcb, null))
            }
        } else if (position == list.size) {
            binding.tvLabel.text = "추가하기"
            binding.ivPlus.isVisible = true
        } else {
            binding.tvLabel.text = list[position].classificationType
        }
        return binding.root
    }
}