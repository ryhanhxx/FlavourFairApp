package com.ch.flavourfair.presentation.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.ch.flavourfair.databinding.ActivityDetailProductBinding
import com.ch.flavourfair.model.Product
import com.ch.flavourfair.utils.proceedWhen
import com.ch.flavourfair.utils.toCurrencyFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailProductActivity : AppCompatActivity() {

    private val binding: ActivityDetailProductBinding by lazy {
        ActivityDetailProductBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailProductViewModel by viewModel { parametersOf(intent?.extras) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindProductData(viewModel.product)
        setOnClickLocation()
        setOnClickButton()
        observeData()
    }

    private fun setOnClickButton() {
        binding.ivBtnBack.setOnClickListener {
            onBackPressed()
        }
        binding.ivAdd.setOnClickListener {
            viewModel.add()
        }
        binding.ivMinus.setOnClickListener {
            viewModel.minus()
        }
        binding.btnAddtocart.setOnClickListener {
            viewModel.addToCart()
        }
    }

    private fun setOnClickLocation() {
        binding.tvTextLocation.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://maps.app.goo.gl/h4wQKqaBuXzftGK77")
            )
            startActivity(intent)
        }
    }

    private fun bindProductData(product: Product?) {
        product?.let { item ->
            binding.ivImage.load(item.imgUrl) {
                crossfade(true)
            }
            binding.tvName.text = item.name
            binding.tvDesc.text = item.desc
            binding.tvPrice.text = item.price.toCurrencyFormat()
        }
    }

    private fun observeData() {
        viewModel.priceLiveData.observe(this) {
            binding.tvPrice.text = it.toCurrencyFormat()
        }
        viewModel.productQuantityLiveData.observe(this) {
            binding.tvQuantity.text = it.toString()
        }

        viewModel.addToCartLiveData.observe(this) {
            it.proceedWhen(doOnSuccess = {
                Toast.makeText(this, "Product has been in cart", Toast.LENGTH_SHORT).show()
                finish()
            }, doOnError = {
                    Toast.makeText(this, "Failed add to cart", Toast.LENGTH_SHORT).show()
                })
        }
    }

    companion object {
        const val PRODUCT_KEY = "PRODUCT_KEY"
        fun startActivity(context: Context, product: Product?) {
            val intent = Intent(context, DetailProductActivity::class.java)
            intent.putExtra(PRODUCT_KEY, product)
            context.startActivity(intent)
        }
    }
}
