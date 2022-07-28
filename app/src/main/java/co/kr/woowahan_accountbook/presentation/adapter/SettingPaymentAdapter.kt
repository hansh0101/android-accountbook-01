package co.kr.woowahan_accountbook.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.ItemSettingBodyAddBinding
import co.kr.woowahan_accountbook.databinding.ItemSettingBodyPaymentBinding
import co.kr.woowahan_accountbook.databinding.ItemSettingHeaderBinding
import co.kr.woowahan_accountbook.domain.entity.setting.SettingPayment

class SettingPaymentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<SettingPayment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SettingPayment.Type.HEADER -> HeaderViewHolder.create(parent)
            SettingPayment.Type.BODY -> BodyViewHolder.create(parent)
            SettingPayment.Type.FOOTER -> FooterViewHolder.create(parent)
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.onBind()
            is BodyViewHolder -> holder.onBind(items[position])
            is FooterViewHolder -> holder.onBind()
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].type

    class HeaderViewHolder(private val binding: ItemSettingHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            binding.tvHeaderTitle.text = "결제수단"
        }

        companion object Factory {
            fun create(parent: ViewGroup): HeaderViewHolder {
                val binding = ItemSettingHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return HeaderViewHolder(binding)
            }
        }
    }

    class BodyViewHolder(private val binding: ItemSettingBodyPaymentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(payment: SettingPayment) {
            binding.payment = payment
            binding.root.setOnClickListener {
                TODO("결제수단 수정하기 화면으로 이동")
            }
        }

        companion object Factory {
            fun create(parent: ViewGroup): BodyViewHolder {
                val binding = DataBindingUtil.inflate<ItemSettingBodyPaymentBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_setting_body_payment,
                    parent,
                    false
                )
                return BodyViewHolder(binding)
            }
        }
    }

    class FooterViewHolder(private val binding: ItemSettingBodyAddBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            binding.tvBodyAdd.text = "결제수단 추가하기"
            binding.ivPlus.setOnClickListener {
                TODO("결제수단 추가 화면으로 이동시키기")
            }
        }

        companion object Factory {
            fun create(parent: ViewGroup): FooterViewHolder {
                val binding = ItemSettingBodyAddBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return FooterViewHolder(binding)
            }
        }
    }

    fun updateItems(newItems: List<SettingPayment>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}