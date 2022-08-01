package co.kr.woowahan_accountbook.presentation.adapter.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.view.isVisible
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.ItemHistoryAddSpinnerBinding
import co.kr.woowahan_accountbook.domain.entity.dto.PaymentDto
import timber.log.Timber

class HistoryPaymentSpinnerAdapter : BaseAdapter() {
    private var items = listOf<PaymentDto>(
        PaymentDto(0, "추가하기"), PaymentDto(0, "선택하세요")
    )

    override fun getCount(): Int = if (items.isEmpty()) 0 else items.size

    override fun getItem(position: Int): PaymentDto = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = ItemHistoryAddSpinnerBinding.inflate(
            LayoutInflater.from(parent?.context),
            parent,
            false
        )

        Timber.tag("getDropDownView").i(position.toString())
        binding.tvLabel.text = getItem(position).paymentName
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
        Timber.tag("getView").i(position.toString())
        if (position == items.size - 1) {
            with(binding.tvLabel) {
                hint = items[position].paymentName
                setHintTextColor(resources.getColor(R.color.light_purple_a79fcb, null))
            }
        } else {
            binding.tvLabel.text = items[position].paymentName
        }
        return binding.root
    }

    override fun isEnabled(position: Int): Boolean {
        return position != count - 1
    }

    fun updateItems(newItems: List<PaymentDto>) {
        items = newItems
        notifyDataSetChanged()
    }
}