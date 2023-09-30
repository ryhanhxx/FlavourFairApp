package com.ch.flavourfair.presentation.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import coil.load
import com.ch.flavourfair.databinding.ActivityDetailProductBinding
import com.ch.flavourfair.model.Product
import com.ch.flavourfair.utils.GenericViewModelFactory
import com.ch.flavourfair.utils.toCurrencyFormat

class DetailProductActivity : AppCompatActivity() {

    private val binding: ActivityDetailProductBinding by lazy {
        ActivityDetailProductBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailProductViewModel by viewModels {
        GenericViewModelFactory.create(DetailProductViewModel(intent?.extras))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showProductData(viewModel.product)
        setOnClickLocation()
        setOnClickQuantity()
        observeData()
    }

    private fun setOnClickQuantity() {
        binding.ivBtnadd.setOnClickListener {
            viewModel.add()
        }
        binding.ivBtnremove.setOnClickListener {
            viewModel.minus()
        }
    }

    private fun setOnClickLocation() {
        binding.tvTextLocation.setOnClickListener {
            navigateToMaps()
        }
    }

    private fun navigateToMaps() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://maps.app.goo.gl/h4wQKqaBuXzftGK77")
        )
        startActivity(intent)
    }

    private fun showProductData(product: Product?) {
        product?.apply {
            binding.ivImage.load(this.imgUrl) {
                crossfade(true)
            }
            binding.tvName.text = this.name
            binding.tvDesc.text = this.desc
            binding.tvPrice.text = this.price.toCurrencyFormat()
        }
    }

    private fun observeData() {
        viewModel.priceLiveData.observe(this) {
            binding.tvPrice.text = it.toCurrencyFormat()
        }
        viewModel.productCountLiveData.observe(this) {
            binding.tvQuantity.text = it.toString()
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
