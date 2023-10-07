package com.ch.flavourfair.presentation.cart


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ch.flavourfair.data.repository.CartRepository
import com.ch.flavourfair.model.Cart
import kotlinx.coroutines.Dispatchers

class CartViewModel(private val repo: CartRepository) : ViewModel() {

    val cartList = repo.getUserCartData().asLiveData(Dispatchers.IO)

    fun decreaseCart(item: Cart) {/*
        viewModelScope.launch {repo.decreaseCart(item).collect()}*/
    }
}