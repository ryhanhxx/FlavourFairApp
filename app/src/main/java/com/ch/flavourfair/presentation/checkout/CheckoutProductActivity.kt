package com.ch.flavourfair.presentation.checkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.ch.flavourfair.R
import com.ch.flavourfair.data.local.database.AppDatabase
import com.ch.flavourfair.data.local.database.datasource.CartDataSource
import com.ch.flavourfair.data.local.database.datasource.CartDatabaseDataSource
import com.ch.flavourfair.data.network.api.datasource.FlavourfairApiDataSource
import com.ch.flavourfair.data.network.api.service.FlavourfairApiService
import com.ch.flavourfair.data.repository.CartRepository
import com.ch.flavourfair.data.repository.CartRepositoryImpl
import com.ch.flavourfair.databinding.ActivityCheckoutProductBinding
import com.ch.flavourfair.presentation.cart.adapter.CartListAdapter
import com.ch.flavourfair.utils.GenericViewModelFactory
import com.ch.flavourfair.utils.proceedWhen
import com.ch.flavourfair.utils.toCurrencyFormat

class CheckoutProductActivity : AppCompatActivity() {

    private val binding: ActivityCheckoutProductBinding by lazy {
        ActivityCheckoutProductBinding.inflate(layoutInflater)
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter()
    }

    private val viewModel: CheckoutViewModel by viewModels {
        val database = AppDatabase.getInstance(this)
        val cartDao = database.cartDao()
        val cartDataSource: CartDataSource = CartDatabaseDataSource(cartDao)
        val service = FlavourfairApiService.invoke()
        val apiDataSource = FlavourfairApiDataSource(service)
        val repo: CartRepository = CartRepositoryImpl(cartDataSource, apiDataSource)
        GenericViewModelFactory.create(CheckoutViewModel(repo))
    }

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
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    showDialogCheckoutSuccess()
                },
                doOnError = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    Toast.makeText(this, "Checkout Error", Toast.LENGTH_SHORT).show()
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                }
            )
        }
    }

    private fun showDialogCheckoutSuccess() {
        AlertDialog.Builder(this)
            .setMessage("Checkout Success")
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