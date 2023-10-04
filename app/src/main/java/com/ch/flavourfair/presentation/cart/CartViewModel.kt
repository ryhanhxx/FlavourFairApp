package com.ch.flavourfair.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ch.flavourfair.data.repository.CartRepository
import kotlinx.coroutines.Dispatchers

class CartViewModel(private val repo : CartRepository) : ViewModel() {

    val cartList = repo.getCartList().asLiveData(Dispatchers.IO)
}