package com.ch.flavourfair.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ch.flavourfair.data.network.firebase.auth.FirebaseAuthDataSource
import com.ch.flavourfair.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.ch.flavourfair.data.repository.UserRepository
import com.ch.flavourfair.data.repository.UserRepositoryImpl
import com.ch.flavourfair.databinding.ActivitySplashBinding
import com.ch.flavourfair.presentation.login.LoginActivity
import com.ch.flavourfair.presentation.main.MainActivity
import com.ch.flavourfair.utils.GenericViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels {
        GenericViewModelFactory.create(createViewModel())
    }

    /*private val viewModel: SplashViewModel by viewModel()*/

    private fun createViewModel(): SplashViewModel {
        val firebaseAuth = FirebaseAuth.getInstance()
        val dataSource: FirebaseAuthDataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
        val repository: UserRepository = UserRepositoryImpl(dataSource)
        return SplashViewModel(repository)
    }

    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        checkIfUserLogin()
    }

    private fun checkIfUserLogin() {
        lifecycleScope.launch {
            delay(2000)
            if (viewModel.isUserLoggedIn()) {
                navigateToMain()
            } else {
                navigateToLogin()
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }
}
