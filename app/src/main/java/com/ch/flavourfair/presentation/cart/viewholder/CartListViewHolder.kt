package com.ch.flavourfair.presentation.cart.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ch.flavourfair.R
import com.ch.flavourfair.core.ViewHolderBinder
import com.ch.flavourfair.databinding.ItemCartProductBinding
import com.ch.flavourfair.databinding.ItemCartProductCheckoutBinding
import com.ch.flavourfair.model.Cart
import com.ch.flavourfair.utils.doneEditing
import com.ch.flavourfair.utils.toCurrencyFormat

class CartViewHolder(
    private val binding: ItemCartProductBinding,
    private val cartListener: CartListener?
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
        setClickListeners(item)
    }

    private fun setCartData(item: Cart) {
        with(binding) {
            binding.ivImage.load(item.productImgUrl) {
                crossfade(true)
            }
            tvQuantity.text = item.itemQuantity.toString()
            tvName.text = item.productName
            tvPrice.text = (item.itemQuantity * item.productPrice).toCurrencyFormat()
        }
    }

    private fun setCartNotes(item: Cart) {
        binding.etNote.setText(item.itemNotes)
        binding.etNote.doneEditing {
            binding.etNote.clearFocus()
            val newItem = item.copy().apply {
                itemNotes = binding.etNote.text.toString().trim()
            }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }

    private fun setClickListeners(item: Cart) {
        binding.apply {
            ivPlus.setOnClickListener {
                cartListener?.onPlusTotalItemCartClicked(item)
            }
            ivMinus.setOnClickListener {
                cartListener?.onMinusTotalItemCartClicked(item)
            }
            ivDelete.setOnClickListener {
                cartListener?.onRemoveCartClicked(item)
            }
        }
    }
}

class CartCheckoutViewHolder(
    private val binding: ItemCartProductCheckoutBinding
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
    }

    private fun setCartData(item: Cart) {
        with(binding) {
            binding.ivImage.load(item.productImgUrl) {
                crossfade(true)
            }
            tvQuantity.text =
                itemView.rootView.context.getString(
                    R.string.text_total_price,
                    item.itemQuantity.toString()
                )
            tvName.text = item.productName
            tvPrice.text = (item.itemQuantity * item.productPrice).toCurrencyFormat()
        }
    }

    private fun setCartNotes(item: Cart) {
        binding.tvNotes.text = item.itemNotes
    }
}

interface CartListener {
    fun onPlusTotalItemCartClicked(cart: Cart)
    fun onMinusTotalItemCartClicked(cart: Cart)
    fun onRemoveCartClicked(cart: Cart)
    fun onUserDoneEditingNotes(cart: Cart)
}
