package com.ch.flavourfair.presentation.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import coil.load
import com.ch.flavourfair.R
import com.ch.flavourfair.databinding.ActivityDetailProductBinding
import com.ch.flavourfair.model.Product

class DetailProductActivity : AppCompatActivity() {

    private val binding: ActivityDetailProductBinding by lazy {
        ActivityDetailProductBinding.inflate(layoutInflater)
    }

    private val product: Product? by lazy {
        intent.getParcelableExtra<Product>(ARGS_PRODUCT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showProductData()
        setOnClickLocation()
    }

    private fun setOnClickLocation() {
        binding.tvTextLocation.setOnClickListener {
            navigateToMaps()
        }
    }

    private fun navigateToMaps() {
        val direct = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://maps.app.goo.gl/h4wQKqaBuXzftGK77")
        )
        startActivity(direct)
    }

    private fun showProductData() {
        if (product != null) {
            binding.ivImage.load(product?.imgUrl)
            binding.tvName.text = product?.name
            binding.tvPrice.text = product?.price
            binding.tvDesc.text = product?.desc
            binding.tvQuantity.text = product?.quantity.toString()
        } else {
            Toast.makeText(this, "Food Not Found", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val ARGS_PRODUCT = "PRODUCT_KEY"
        fun startActivity(context: Context, product: Product?) {
            val intent = Intent(context, DetailProductActivity::class.java)
            intent.putExtra(ARGS_PRODUCT, product)
            context.startActivity(intent)

        }
    }
}