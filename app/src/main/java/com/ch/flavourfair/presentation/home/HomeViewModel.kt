package com.ch.flavourfair.presentation.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ch.flavourfair.data.local.datastore.UserPreferenceDataSource
import com.ch.flavourfair.data.repository.ProductRepository
import com.ch.flavourfair.model.Product
import com.ch.flavourfair.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: ProductRepository) : ViewModel() {
    /*val userGridModeLiveData = userPreferenceDataSource.getUserGridModePrefFlow().asLiveData(
        Dispatchers.IO)

    fun setUserGridModePref(isUsingGridMode: Boolean) {
        viewModelScope.launch {
            userPreferenceDataSource.setUserGridModePref(isUsingGridMode)
        }
    }*/
    /*val homeData: LiveData<List<Product>>
        get() = repo.getProducts().map {
            it.payload ?: emptyList()
        }.asLiveData(Dispatchers.IO)*/

    val productData : LiveData<ResultWrapper<List<Product>>>
        get() = repo.getProducts().asLiveData(Dispatchers.IO)
}

