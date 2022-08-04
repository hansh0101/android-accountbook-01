package co.kr.woowahan_accountbook.presentation.adapter.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.ItemSettingBodyAddBinding
import co.kr.woowahan_accountbook.databinding.ItemSettingBodyClassificationBinding
import co.kr.woowahan_accountbook.databinding.ItemSettingHeaderBinding
import co.kr.woowahan_accountbook.domain.entity.setting.SettingClassification

class SettingClassificationAdapter(
    private val onClassificationClick: (SettingClassification) -> Unit,
    private val onAddButtonClick: () -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<SettingClassification>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SettingClassification.Type.HEADER -> HeaderViewHolder.create(parent)
            SettingClassification.Type.BODY -> BodyViewHolder.create(parent, onClassificationClick)
            SettingClassification.Type.FOOTER -> FooterViewHolder.create(parent, onAddButtonClick)
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.onBind(items[position].isIncome)
            is BodyViewHolder -> holder.onBind(items[position])
            is FooterViewHolder -> holder.onBind(items[position].isIncome)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].type

    class HeaderViewHolder(private val binding: ItemSettingHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(type: Boolean) {
            binding.tvHeaderTitle.text = if (type) "수입 카테고리" else "지출 카테고리"
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
        private val binding: ItemSettingBodyClassificationBinding,
        private val onClassificationClick: (SettingClassification) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(classification: SettingClassification) {
            binding.classification = classification
            binding.root.setOnClickListener {
                if (classification.id != 3 && classification.id != 10) {
                    onClassificationClick(classification)
                }
            }
        }

        companion object Factory {
            fun create(
                parent: ViewGroup,
                onClassificationClick: (SettingClassification) -> Unit
            ): BodyViewHolder {
                val binding = DataBindingUtil.inflate<ItemSettingBodyClassificationBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_setting_body_classification,
                    parent,
                    false
                )
                return BodyViewHolder(binding, onClassificationClick)
            }
        }
    }

    class FooterViewHolder(
        private val binding: ItemSettingBodyAddBinding,
        private val onAddButtonClick: () -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(type: Boolean) {
            binding.tvBodyAdd.text = if (type) "수입 카테고리 추가하기" else "지출 카테고리 추가하기"
            binding.root.setOnClickListener {
                onAddButtonClick()
            }
        }

        companion object Factory {
            fun create(parent: ViewGroup, onAddButtonClick: () -> Unit): FooterViewHolder {
                val binding = ItemSettingBodyAddBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return FooterViewHolder(binding, onAddButtonClick)
            }
        }
    }

    fun updateItems(newItems: List<SettingClassification>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}