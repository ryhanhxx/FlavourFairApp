package com.ch.flavourfair.presentation.checkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ch.flavourfair.R
import com.ch.flavourfair.databinding.ActivityCheckoutProductBinding

class CheckoutProductActivity : AppCompatActivity() {

    private val binding: ActivityCheckoutProductBinding by lazy {
        ActivityCheckoutProductBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}