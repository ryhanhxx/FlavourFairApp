package com.ch.flavourfair.presentation.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ch.flavourfair.core.ViewHolderBinder
import com.ch.flavourfair.databinding.ItemProductGridBinding
import com.ch.flavourfair.databinding.ItemProductListBinding
import com.ch.flavourfair.model.Product
import com.ch.flavourfair.utils.toCurrencyFormat

class ProductItemViewHolder {
}

class ProductLinearViewHolder(
    private val binding: ItemProductListBinding,
    private val onClickListener: (Product) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Product> {
    override fun bind(item: Product) {
        binding.ivImage.load(item.imgUrl) {
            crossfade(true)
        }
        binding.tvName.text = item.name
        binding.tvPrice.text = item.price.toCurrencyFormat()
        binding.root.setOnClickListener {
            onClickListener.invoke(item)
        }
    }
}

class ProductGridViewHolder(
    private val binding: ItemProductGridBinding,
    private val onClickListener: (Product) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Product> {
    override fun bind(item: Product) {
        binding.ivImage.load(item.imgUrl) {
            crossfade(true)
        }
        binding.tvName.text = item.name
        binding.tvPrice.text = item.price.toCurrencyFormat()
        binding.root.setOnClickListener {
            onClickListener.invoke(item)
        }
    }
}