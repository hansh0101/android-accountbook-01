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

class SettingPaymentAdapter(
    private val updatePayment: (SettingPayment) -> Unit,
    private val addPayment: () -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<SettingPayment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SettingPayment.Type.HEADER -> HeaderViewHolder.create(parent)
            SettingPayment.Type.BODY -> BodyViewHolder.create(parent, updatePayment)
            SettingPayment.Type.FOOTER -> FooterViewHolder.create(parent, addPayment)
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

    class BodyViewHolder(
        private val binding: ItemSettingBodyPaymentBinding,
        private val updatePayment: (SettingPayment) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(payment: SettingPayment) {
            binding.payment = payment
            binding.root.setOnClickListener {
                updatePayment(payment)
            }
        }

        companion object Factory {
            fun create(parent: ViewGroup, updatePayment: (SettingPayment) -> Unit): BodyViewHolder {
                val binding = DataBindingUtil.inflate<ItemSettingBodyPaymentBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_setting_body_payment,
                    parent,
                    false
                )
                return BodyViewHolder(binding, updatePayment)
            }
        }
    }

    class FooterViewHolder(
        private val binding: ItemSettingBodyAddBinding,
        private val addPayment: () -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            binding.tvBodyAdd.text = "결제수단 추가하기"
            binding.root.setOnClickListener {
                addPayment()
            }
        }

        companion object Factory {
            fun create(parent: ViewGroup, addPayment: () -> Unit): FooterViewHolder {
                val binding = ItemSettingBodyAddBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return FooterViewHolder(binding, addPayment)
            }
        }
    }

    fun updateItems(newItems: List<SettingPayment>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}