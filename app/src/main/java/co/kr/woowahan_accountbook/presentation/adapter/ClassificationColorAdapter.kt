package co.kr.woowahan_accountbook.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.kr.woowahan_accountbook.databinding.ItemClassificationColorBinding
import co.kr.woowahan_accountbook.presentation.viewmodel.main.setting.ClassificationAddViewModel

class ClassificationColorAdapter(private val onColorClick: (Int) -> Unit) :
    RecyclerView.Adapter<ClassificationColorAdapter.ClassificationColorViewHolder>() {
    private val items = mutableListOf<ClassificationAddViewModel.ColorInfo>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassificationColorViewHolder {
        return ClassificationColorViewHolder.create(parent, onColorClick)
    }

    override fun onBindViewHolder(holder: ClassificationColorViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ClassificationColorViewHolder(
        private val binding: ItemClassificationColorBinding,
        private val onColorClick: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(colorInfo: ClassificationAddViewModel.ColorInfo) {
            binding.root.setBackgroundColor(colorInfo.color)
            binding.root.setOnClickListener { onColorClick(layoutPosition) }
            if (colorInfo.isSelected) {
                binding.root.apply {
                    scaleX = 1f
                    scaleY = 1f
                }
            } else {
                binding.root.apply {
                    scaleX = 0.7f
                    scaleY = 0.7f
                }
            }
        }

        companion object Factory {
            fun create(
                parent: ViewGroup,
                onColorClick: (Int) -> Unit
            ): ClassificationColorViewHolder {
                val binding = ItemClassificationColorBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ClassificationColorViewHolder(binding, onColorClick)
            }
        }
    }

    fun updateItems(newItems: List<ClassificationAddViewModel.ColorInfo>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}