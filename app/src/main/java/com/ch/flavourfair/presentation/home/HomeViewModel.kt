package com.ch.flavourfair.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch.flavourfair.data.repository.ProductRepository
import com.ch.flavourfair.model.Product
import com.ch.flavourfair.utils.ResultWrapper
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: ProductRepository) : ViewModel() {

    private val _homeData = MutableLiveData<List<Product>>()
    val homeData: LiveData<List<Product>>
        get() = _homeData

    fun fetchHomeData() {
        viewModelScope.launch {
            repo.getProducts().map {
                mapToHomeData(it)
            }.collect {
                _homeData.postValue(it)
            }
        }
    }

    private fun mapToHomeData(productResult: ResultWrapper<List<Product>>): List<Product> {
        // Di sini, Anda dapat melakukan transformasi atau pemrosesan tambahan jika diperlukan
        return productResult.payload ?: emptyList()
    }
}

