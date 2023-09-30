package com.ch.flavourfair.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ch.flavourfair.databinding.ItemCategoryListBinding
import com.ch.flavourfair.model.Category


class CategoryAdapter(private val onItemClick: (Category) -> Unit) :
    RecyclerView.Adapter<CategoryItemListViewHolder>() {

    private var items: MutableList<Category> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemListViewHolder {
        return CategoryItemListViewHolder(
            binding = ItemCategoryListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onItemClick
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CategoryItemListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setData(items: List<Category>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
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