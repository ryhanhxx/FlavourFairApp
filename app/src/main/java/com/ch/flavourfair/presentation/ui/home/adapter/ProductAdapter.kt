package com.ch.flavourfair.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ch.flavourfair.core.ViewHolderBinder
import com.ch.flavourfair.databinding.ProductItemGridBinding
import com.ch.flavourfair.databinding.ProductItemListBinding
import com.ch.flavourfair.model.Product
import com.ch.flavourfair.presentation.ui.home.adapter.viewholder.ProductGridViewHolder
import com.ch.flavourfair.presentation.ui.home.adapter.viewholder.ProductLinearViewHolder

class ProductAdapter(
    var adapterLayout: AdapterLayout,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            AdapterLayout.GRID.ordinal -> (ProductGridViewHolder(
                binding = ProductItemGridBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), onItemClick
            ))

            else -> {
                ProductLinearViewHolder(
                    binding = ProductItemListBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ), onItemClick
                )
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderBinder<Product>).bind(differ.currentList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return adapterLayout.ordinal
    }

    fun setData(data: List<Product>) {
        differ.submitList(data)
    }

    fun refreshList() {
        notifyItemRangeChanged(0, differ.currentList.size)
    }
}