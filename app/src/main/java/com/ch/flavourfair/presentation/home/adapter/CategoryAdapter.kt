package com.ch.flavourfair.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ch.flavourfair.databinding.ItemCategoryListBinding
import com.ch.flavourfair.model.Category

class CategoryAdapter(private val onItemClick: (Category) -> Unit) :
    RecyclerView.Adapter<CategoryItemListViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemListViewHolder {
        return CategoryItemListViewHolder(
            binding = ItemCategoryListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onItemClick
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: CategoryItemListViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    fun setData(data : List<Category>){
        differ.submitList(data)
        notifyItemRangeChanged(0,data.size)
    }


}

class CategoryItemListViewHolder(
    private val binding: ItemCategoryListBinding,
    private val onItemClick: (Category) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Category) {
        binding.ivImage.load(item.imgUrl)
        binding.tvName.text = item.name
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
    }
}