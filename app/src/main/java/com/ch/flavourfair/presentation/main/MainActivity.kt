package com.ch.flavourfair.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ch.flavourfair.R
import com.ch.flavourfair.data.dummy.DummyCategoryDataSourceImpl
import com.ch.flavourfair.data.dummy.DummyProductDataSourceImpl
import com.ch.flavourfair.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupBottomNav()
        val json = Gson().toJson(DummyProductDataSourceImpl().getProductData())
        val jsonca = Gson().toJson(DummyCategoryDataSourceImpl().getCategoryData())
        Log.d("Main", json)
    }

    private fun setupBottomNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)
    }
}
