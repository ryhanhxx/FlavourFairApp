package com.ch.flavourfair.data.local.database.datasource

import com.ch.flavourfair.data.local.database.dao.CartDao
import com.ch.flavourfair.data.local.database.entity.CartEntity
import com.ch.flavourfair.data.local.database.relation.CartProductRelation
import kotlinx.coroutines.flow.Flow

interface CartDataSource {
    fun getAllCarts(): Flow<List<CartProductRelation>>
    fun getCartById(cartId: Int): Flow<CartProductRelation>
    suspend fun insertCart(cart: CartEntity)
    suspend fun deleteProduct(cart: CartEntity): Int
    suspend fun updateProduct(cart: CartEntity): Int
}

class CartDatabaseDataSource(private val cartDao: CartDao) : CartDataSource {
    override fun getAllCarts(): Flow<List<CartProductRelation>> {
        return cartDao.getAllCarts()
    }

    override fun getCartById(cartId: Int): Flow<CartProductRelation> {
        return cartDao.getCartById(cartId)
    }

    override suspend fun insertCart(cart: CartEntity) {
        return cartDao.insertCart(cart)
    }

    override suspend fun deleteProduct(cart: CartEntity): Int {
        return cartDao.deleteProduct(cart)
    }

    override suspend fun updateProduct(cart: CartEntity): Int {
        return cartDao.updateProduct(cart)
    }

}