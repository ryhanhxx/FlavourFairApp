package com.ch.flavourfair.presentation.cart.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ch.flavourfair.R
import com.ch.flavourfair.core.ViewHolderBinder
import com.ch.flavourfair.databinding.ItemCartProductBinding
import com.ch.flavourfair.databinding.ItemCartProductCheckoutBinding
import com.ch.flavourfair.model.Cart
import com.ch.flavourfair.model.CartProduct
import com.ch.flavourfair.utils.doneEditing

class CartViewHolder(
    private val binding: ItemCartProductBinding,
    private val cartListener: CartListener?
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CartProduct> {
    override fun bind(item: CartProduct) {
        setCartData(item)
        setCartNotes(item)
        setClickListeners(item)
    }

    private fun setCartData(item: CartProduct) {
        with(binding) {
            binding.ivImage.load(item.product.imgUrl) {
                crossfade(true)
            }
            tvQuantity.text = item.cart.itemQuantity.toString()
            tvName.text = item.product.name
            tvPrice.text = (item.cart.itemQuantity * item.product.price).toString()
        }
    }

    private fun setCartNotes(item: CartProduct) {
        binding.etNote.setText(item.cart.itemNotes)
        binding.etNote.doneEditing {
            binding.etNote.clearFocus()
            val newItem = item.cart.copy().apply {
                itemNotes = binding.etNote.text.toString().trim()
            }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }

    private fun setClickListeners(item: CartProduct) {
        with(binding) {
            ivMinus.setOnClickListener { cartListener?.onMinusTotalItemCartClicked(item.cart) }
            ivPlus.setOnClickListener { cartListener?.onPlusTotalItemCartClicked(item.cart) }
            ivDelete.setOnClickListener { cartListener?.onRemoveCartClicked(item.cart) }
        }
    }
}

class CartCheckoutViewHolder(
    private val binding: ItemCartProductCheckoutBinding,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CartProduct> {
    override fun bind(item: CartProduct) {
        setCartData(item)
        setCartNotes(item)
    }

    private fun setCartData(item: CartProduct) {
        with(binding) {
            binding.ivImage.load(item.product.imgUrl) {
                crossfade(true)
            }
            tvQuantity.text =
                itemView.rootView.context.getString(
                    R.string.text_total_quantity,
                    item.cart.itemQuantity.toString()
                )
            tvName.text = item.product.name
            tvPrice.text = (item.cart.itemQuantity * item.product.price).toString()
        }
    }

    private fun setCartNotes(item: CartProduct) {
        binding.tvNotes.text = item.cart.itemNotes
    }
}


interface CartListener {
    fun onPlusTotalItemCartClicked(cart: Cart)
    fun onMinusTotalItemCartClicked(cart: Cart)
    fun onRemoveCartClicked(cart: Cart)
    fun onUserDoneEditingNotes(cart: Cart)
}