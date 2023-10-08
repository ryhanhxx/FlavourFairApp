package com.ch.flavourfair.presentation.detail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ch.flavourfair.data.repository.CartRepository
import com.ch.flavourfair.model.Cart
import com.ch.flavourfair.model.Product
import com.ch.flavourfair.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailProductViewModel(
    private val extras: Bundle?,
    private val repo: CartRepository
): ViewModel() {
    val product = extras?.getParcelable<Product>(DetailProductActivity.PRODUCT_KEY)

    val priceLiveData = MutableLiveData<Double>().apply {
        postValue(0.0)
    }
    val productQuantityLiveData = MutableLiveData<Int>().apply {
        postValue(0)
    }

    val addToCartLiveData = MutableLiveData<ResultWrapper<Boolean>>()

    fun add() {
        val count = (productQuantityLiveData.value ?: 0) + 1
        productQuantityLiveData.postValue(count)
        priceLiveData.postValue(product?.price?.times(count) ?: 0.0)
    }

    fun minus() {
        if((productQuantityLiveData.value ?: 0) > 0){
            val count = (productQuantityLiveData.value ?: 0) -1
            productQuantityLiveData.postValue(count)
            priceLiveData.postValue(product?.price?.times(count) ?: 0.0)
        }
    }

    fun addToCart() {
        viewModelScope.launch {
            val currentQuantity = productQuantityLiveData.value ?: 0
            val productQuantity = if (currentQuantity <= 0) 1 else currentQuantity //It cannot 0Q

            product?.let {
                repo.createCart(product, productQuantity).collect { result ->
                    addToCartLiveData.postValue(result)
                }
            }
        }
    }
}