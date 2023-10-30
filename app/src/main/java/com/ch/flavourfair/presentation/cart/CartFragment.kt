package com.ch.flavourfair.presentation.cart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ch.flavourfair.databinding.FragmentCartBinding
import com.ch.flavourfair.model.Cart
import com.ch.flavourfair.presentation.cart.adapter.CartListAdapter
import com.ch.flavourfair.presentation.cart.viewholder.CartListener
import com.ch.flavourfair.presentation.checkout.CheckoutProductActivity
import com.ch.flavourfair.utils.proceedWhen
import com.ch.flavourfair.utils.toCurrencyFormat
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    private val viewModel: CartViewModel by viewModel()

    private val adapter: CartListAdapter by lazy {
        CartListAdapter(object : CartListener {

            override fun onPlusTotalItemCartClicked(cart: Cart) {
                viewModel.increaseCart(cart)
            }

            override fun onMinusTotalItemCartClicked(cart: Cart) {
                viewModel.decreaseCart(cart)
            }

            override fun onRemoveCartClicked(cart: Cart) {
                viewModel.deleteCart(cart)
            }

            override fun onUserDoneEditingNotes(cart: Cart) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observeData()
        setClickListener()
    }

    private fun setClickListener() {
        binding.btnOrder.setOnClickListener {
            context?.startActivity(Intent(requireContext(), CheckoutProductActivity::class.java))
        }
    }

    private fun setupList() {
        binding.rvCart.itemAnimator = null
        binding.rvCart.adapter = adapter
    }

    private fun observeData() {
        viewModel.cartList.observe(viewLifecycleOwner) { result ->
            result.proceedWhen(doOnSuccess = {
                binding.rvCart.isVisible = true
                binding.layoutState.root.isVisible = false
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = false
                result.payload?.let { (carts, totalPrice) ->
                    adapter.submitData(carts)
                    binding.tvTotalprice.text = totalPrice.toCurrencyFormat()
                }
            }, doOnError = { err ->
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = err.exception?.message.orEmpty()
                    binding.layoutState.pbLoading.isVisible = false
                }, doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutState.pbLoading.isVisible = true
                    binding.rvCart.isVisible = false
                }, doOnEmpty = {
                    Log.d("Cart Fragment", "Cart is empty")
                })
        }
    }
}
