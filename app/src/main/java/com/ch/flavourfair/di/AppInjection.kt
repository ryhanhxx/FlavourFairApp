package com.ch.flavourfair.di

import com.ch.flavourfair.data.local.database.AppDatabase
import com.ch.flavourfair.data.local.database.datasource.CartDataSource
import com.ch.flavourfair.data.local.database.datasource.CartDatabaseDataSource
import com.ch.flavourfair.data.local.datastore.UserPreferenceDataSource
import com.ch.flavourfair.data.local.datastore.UserPreferenceDataSourceImpl
import com.ch.flavourfair.data.local.datastore.appDataStore
import com.ch.flavourfair.data.network.api.datasource.FlavourfairApiDataSource
import com.ch.flavourfair.data.network.api.datasource.FlavourfairDataSource
import com.ch.flavourfair.data.network.api.service.FlavourfairApiService
import com.ch.flavourfair.data.network.firebase.auth.FirebaseAuthDataSource
import com.ch.flavourfair.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.ch.flavourfair.data.repository.CartRepository
import com.ch.flavourfair.data.repository.CartRepositoryImpl
import com.ch.flavourfair.data.repository.ProductRepository
import com.ch.flavourfair.data.repository.ProductRepositoryImpl
import com.ch.flavourfair.data.repository.UserRepository
import com.ch.flavourfair.data.repository.UserRepositoryImpl
import com.ch.flavourfair.presentation.cart.CartViewModel
import com.ch.flavourfair.presentation.checkout.CheckoutViewModel
import com.ch.flavourfair.presentation.home.HomeViewModel
import com.ch.flavourfair.presentation.splash.SplashViewModel
import com.ch.flavourfair.utils.PreferenceDataStoreHelper
import com.ch.flavourfair.utils.PreferenceDataStoreHelperImpl
import com.chuckerteam.chucker.api.ChuckerInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppInjection {
    private val localModule = module {
        single { AppDatabase.getInstance(androidContext()) }
        single { get<AppDatabase>().cartDao() }
        single { androidContext().appDataStore }
        single<PreferenceDataStoreHelper> { PreferenceDataStoreHelperImpl(get()) }
    }

    private val networkModule = module {
        single { ChuckerInterceptor(androidContext()) }
        single { FlavourfairApiService.invoke(get()) }
    }

    private val dataSourceModule = module {
        single<CartDataSource> { CartDatabaseDataSource(get()) }
        single<UserPreferenceDataSource> { UserPreferenceDataSourceImpl(get()) }
        single<FlavourfairDataSource> { FlavourfairApiDataSource(get()) }
        single<FirebaseAuthDataSource> { FirebaseAuthDataSourceImpl(get()) }
    }

    private val repositoryModule = module {
        single<CartRepository> { CartRepositoryImpl(get(), get()) }
        single<ProductRepository> { ProductRepositoryImpl(get()) }
        single<UserRepository> { UserRepositoryImpl(get()) }
    }

    private val viewModelModule = module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::CartViewModel)
        viewModelOf(::CheckoutViewModel)
        viewModelOf(::SplashViewModel)
    }

    val modules: List<Module> = listOf(
        localModule,
        networkModule,
        dataSourceModule,
        repositoryModule,
        viewModelModule
    )
}
