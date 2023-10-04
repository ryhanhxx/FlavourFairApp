package com.ch.flavourfair.data.repository

import com.ch.flavourfair.data.local.database.datasource.CartDataSource
import com.ch.flavourfair.data.local.database.mapper.toCartProductList
import com.ch.flavourfair.model.CartProduct
import com.ch.flavourfair.utils.ResultWrapper
import com.ch.flavourfair.utils.proceed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface CartRepository {
    fun getCartList(): Flow<ResultWrapper<List<CartProduct>>>
}

class CartRepositoryImpl(
    private val dataSource: CartDataSource
) : CartRepository {
    override fun getCartList(): Flow<ResultWrapper<List<CartProduct>>> {
        return dataSource.getAllCarts().map { proceed { it.toCartProductList() } }.onStart {
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }
}