package com.ch.flavourfair.presentation.checkout

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.ch.flavourfair.R
import com.ch.flavourfair.databinding.ActivityCheckoutProductBinding
import com.ch.flavourfair.presentation.cart.adapter.CartListAdapter
import com.ch.flavourfair.utils.proceedWhen
import com.ch.flavourfair.utils.toCurrencyFormat
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutProductActivity : AppCompatActivity() {

    private val binding: ActivityCheckoutProductBinding by lazy {
        ActivityCheckoutProductBinding.inflate(layoutInflater)
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter()
    }

    private val viewModel: CheckoutViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupList()
        observeData()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnOrder.setOnClickListener {
            viewModel.order()
        }
    }

    private fun setupList() {
        binding.layoutStateContent.rvCart.adapter = adapter
    }

    private fun observeData() {
        observeCartData()
        observeCheckoutResult()
    }

    private fun observeCheckoutResult() {
        viewModel.checkoutResult.observe(this) {
            it.proceedWhen(doOnSuccess = {
                binding.layoutState.root.isVisible = false
                binding.layoutState.pbLoading.isVisible = false
                showDialogCheckoutSuccess()
            }, doOnError = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    Toast.makeText(this, "Checkout Error", Toast.LENGTH_SHORT).show()
                }, doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                })
        }
    }

    private fun showDialogCheckoutSuccess() {
        AlertDialog.Builder(this).setMessage("Checkout Success")
            .setPositiveButton(getString(R.string.text_okay)) { _, _ ->
                viewModel.clearCart()
                finish()
            }.create().show()
    }

    private fun observeCartData() {
        viewModel.cartList.observe(this) {
            it.proceedWhen(doOnSuccess = { result ->
                binding.layoutState.root.isVisible = false
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = false
                binding.layoutStateContent.root.isVisible = true
                binding.layoutStateContent.rvCart.isVisible = true
                binding.clCheckout.isVisible = true
                result.payload?.let { (carts, totalPrice) ->
                    adapter.submitData(carts)
                    binding.tvTotalprice.text = totalPrice.toCurrencyFormat()
                }
            }, doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutStateContent.root.isVisible = false
                    binding.layoutStateContent.rvCart.isVisible = false
                    binding.clCheckout.isVisible = false
                }, doOnError = { err ->
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = err.exception?.message.orEmpty()
                    binding.layoutStateContent.root.isVisible = false
                    binding.layoutStateContent.rvCart.isVisible = false
                    binding.clCheckout.isVisible = false
                }, doOnEmpty = { data ->
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                    data.payload?.let { (_, totalPrice) ->
                        binding.tvTotalprice.text = totalPrice.toCurrencyFormat()
                    }
                    binding.layoutStateContent.root.isVisible = false
                    binding.layoutStateContent.rvCart.isVisible = false
                    binding.clCheckout.isVisible = false
                })
        }
    }
}
