package com.ch.flavourfair.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch.flavourfair.presentation.detail.DetailProductViewModel

object GenericViewModelFactory {
    fun create(vm: DetailProductViewModel) = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = vm as T
    }
}