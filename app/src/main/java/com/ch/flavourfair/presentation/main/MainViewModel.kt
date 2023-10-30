package com.ch.flavourfair.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ch.flavourfair.data.local.datastore.UserPreferenceDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repo: UserPreferenceDataSource) : ViewModel() {
    val userGridModeLiveData = repo.getUserGridModePrefFlow().asLiveData(Dispatchers.IO)

    fun setGridModePref(isUsingGridMode: Boolean) {
        viewModelScope.launch {
            repo.setUserGridModePref(isUsingGridMode)
        }
    }
}
