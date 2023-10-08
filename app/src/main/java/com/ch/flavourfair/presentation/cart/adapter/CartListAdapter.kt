package com.ch.flavourfair.presentation.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ch.flavourfair.core.ViewHolderBinder
import com.ch.flavourfair.databinding.ItemCartProductBinding
import com.ch.flavourfair.databinding.ItemCartProductCheckoutBinding
import com.ch.flavourfair.model.CartProduct
import com.ch.flavourfair.presentation.cart.viewholder.CartCheckoutViewHolder
import com.ch.flavourfair.presentation.cart.viewholder.CartListener
import com.ch.flavourfair.presentation.cart.viewholder.CartViewHolder

class CartListAdapter(
    private val cartListener: CartListener? = null
) : RecyclerView.Adapter<ViewHolder>() {
    private val dataDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<CartProduct>() {
        override fun areItemsTheSame(
            oldItem: CartProduct, newItem: CartProduct
        ): Boolean {
            return oldItem.cart.id == newItem.cart.id && oldItem.product.id == newItem.product.id
        }

        override fun areContentsTheSame(
            oldItem: CartProduct, newItem: CartProduct
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return if (cartListener != null) CartViewHolder(
            ItemCartProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), cartListener
        ) else CartCheckoutViewHolder(
            ItemCartProductCheckoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    fun submitData(data: List<CartProduct>) {
        dataDiffer.submitList(data)
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolderBinder<CartProduct>).bind(dataDiffer.currentList[position])
    }
}



