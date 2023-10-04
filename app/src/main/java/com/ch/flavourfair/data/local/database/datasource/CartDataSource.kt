package com.ch.flavourfair.data.local.database.datasource

import com.ch.flavourfair.data.local.database.dao.CartDao
import com.ch.flavourfair.data.local.database.entity.CartEntity
import com.ch.flavourfair.data.local.database.relation.CartProductRelation
import kotlinx.coroutines.flow.Flow

interface CartDataSource {
    fun getAllCarts(): Flow<List<CartProductRelation>>
    fun getCartById(cartId: Int): Flow<CartProductRelation>
    suspend fun insertCart(cart: CartEntity)
    suspend fun insertCarts(carts: List<CartEntity>)
    suspend fun deleteProduct(cart: CartEntity): Int
    suspend fun updateProduct(cart: CartEntity): Int
}

class CartDatabaseDataSource(
    private val dao: CartDao
) : CartDataSource {
    override fun getAllCarts(): Flow<List<CartProductRelation>> {
        return dao.getAllCarts()
    }

    override fun getCartById(cartId: Int): Flow<CartProductRelation> {
        return dao.getCartById(cartId)
    }

    override suspend fun insertCart(cart: CartEntity) {
        return dao.insertCart(cart)
    }

    override suspend fun insertCarts(carts: List<CartEntity>) {
        return dao.insertCarts(carts)
    }

    override suspend fun deleteProduct(cart: CartEntity): Int {
        return dao.deleteProduct(cart)
    }

    override suspend fun updateProduct(cart: CartEntity): Int {
        return dao.updateProduct(cart)
    }


}