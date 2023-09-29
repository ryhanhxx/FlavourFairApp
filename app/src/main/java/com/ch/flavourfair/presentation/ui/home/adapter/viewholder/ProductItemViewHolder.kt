package com.ch.flavourfair.presentation.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ch.flavourfair.core.ViewHolderBinder
import com.ch.flavourfair.databinding.ProductItemGridBinding
import com.ch.flavourfair.databinding.ProductItemListBinding
import com.ch.flavourfair.model.Product

class ProductItemViewHolder {
}

class ProductLinearViewHolder(
    private val binding: ProductItemListBinding,
    private val onClickListener: (Product) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Product> {
    override fun bind(item: Product) {
        binding.ivImage.load(item.imgUrl) {
            crossfade(true)
        }
        binding.tvName.text = item.name
        binding.tvPrice.text = item.price
        binding.root.setOnClickListener {
            onClickListener.invoke(item)
        }
    }
}

class ProductGridViewHolder(
    private val binding: ProductItemGridBinding,
    private val onClickListener: (Product) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Product> {
    override fun bind(item: Product) {
        binding.ivImage.load(item.imgUrl) {
            crossfade(true)
        }
        binding.tvName.text = item.name
        binding.tvPrice.text = item.price
        binding.root.setOnClickListener {
            onClickListener.invoke(item)
        }
    }
}