package com.ch.flavourfair.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ch.flavourfair.data.local.datastore.UserPreferenceDataSource
import com.ch.flavourfair.data.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeDataStore(private val userPreferenceDataSource: UserPreferenceDataSource): ViewModel() {
    val userGridModeLiveData = userPreferenceDataSource.getUserGridModePrefFlow().asLiveData(
        Dispatchers.IO)

    fun setUserGridModePref(isUsingGridMode: Boolean) {
        viewModelScope.launch {
            userPreferenceDataSource.setUserGridModePref(isUsingGridMode)
        }
    }
}