package com.ch.flavourfair.presentation.splash

import androidx.lifecycle.ViewModel
import com.ch.flavourfair.data.repository.UserRepository

class SplashViewModel(private val repo: UserRepository) : ViewModel() {

    fun isUserLoggedIn() = repo.isLoggedIn()

}