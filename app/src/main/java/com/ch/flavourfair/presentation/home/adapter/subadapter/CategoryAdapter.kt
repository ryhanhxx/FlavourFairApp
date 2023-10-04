package com.ch.flavourfair.presentation.home.adapter.subadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ch.flavourfair.databinding.ItemCategoryProductBinding
import com.ch.flavourfair.model.Category

class CategoryAdapter(private val onItemClick: (Category) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.CategoryItemListViewHolder>() {

    private var items: MutableList<Category> = mutableListOf()

    fun setItems(items: List<Category>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<Category>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemListViewHolder {
        val binding =
            ItemCategoryProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryItemListViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: CategoryItemListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class CategoryItemListViewHolder(
        private val binding: ItemCategoryProductBinding,
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
}



