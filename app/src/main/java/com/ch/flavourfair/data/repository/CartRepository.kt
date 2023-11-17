package com.ch.flavourfair.data.repository

import com.ch.flavourfair.data.local.database.datasource.CartDataSource
import com.ch.flavourfair.data.local.database.entity.CartEntity
import com.ch.flavourfair.data.local.database.mapper.toCartEntity
import com.ch.flavourfair.data.local.database.mapper.toCartList
import com.ch.flavourfair.data.network.api.datasource.FlavourfairDataSource
import com.ch.flavourfair.data.network.api.model.order.OrderItemRequest
import com.ch.flavourfair.data.network.api.model.order.OrderRequest
import com.ch.flavourfair.model.Cart
import com.ch.flavourfair.model.Product
import com.ch.flavourfair.utils.ResultWrapper
import com.ch.flavourfair.utils.proceed
import com.ch.flavourfair.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.lang.IllegalStateException

interface CartRepository {
    fun getUserCartData(): Flow<ResultWrapper<Pair<List<Cart>, Double>>>
    suspend fun createCart(product: Product, totalQuantity: Int): Flow<ResultWrapper<Boolean>>
    suspend fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun setCartNotes(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun order(items: List<Cart>): Flow<ResultWrapper<Boolean>>
    suspend fun deleteAll()
}

class CartRepositoryImpl(
    private val dataSource: CartDataSource,
    private val FlavourfairDataSource: FlavourfairDataSource
) : CartRepository {

    override fun getUserCartData(): Flow<ResultWrapper<Pair<List<Cart>, Double>>> {
        return dataSource.getAllCarts()
            .map {
                proceed {
                    val result = it.toCartList()
                    val totalPrice = result.sumOf {
                        val pricePerItem = it.productPrice
                        val quantity = it.itemQuantity
                        pricePerItem * quantity
                    }
                    Pair(result, totalPrice)
                }
            }.map {
                if (it.payload?.first?.isEmpty() == true) {
                    ResultWrapper.Empty(it.payload)
                } else {
                    it
                }
            }.catch {
                emit(ResultWrapper.Error(Exception(it)))
            }
            .onStart {
                emit(ResultWrapper.Loading())
                delay(2000)
            }
    }

    override suspend fun createCart(
        product: Product,
        totalQuantity: Int
    ): Flow<ResultWrapper<Boolean>> {
        return product.id?.let { productId ->
            proceedFlow {
                val affectedRow = dataSource.insertCart(
                    CartEntity(
                        productId = productId,
                        itemQuantity = totalQuantity,
                        productImgUrl = product.imgUrl,
                        productName = product.name,
                        productPrice = product.price
                    )
                )
                affectedRow > 0
            }
        } ?: flow {
            emit(ResultWrapper.Error(IllegalStateException("Product ID not found")))
        }
    }

    override suspend fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart = item.copy().apply {
            itemQuantity -= 1
        }
        return if (modifiedCart.itemQuantity <= 0) {
            proceedFlow { dataSource.deleteCart(modifiedCart.toCartEntity()) > 0 }
        } else {
            proceedFlow { dataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
        }
    }

    override suspend fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart = item.copy().apply {
            itemQuantity += 1
        }
        return proceedFlow { dataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
    }

    override suspend fun setCartNotes(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.updateCart(item.toCartEntity()) > 0 }
    }

    override suspend fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.deleteCart(item.toCartEntity()) > 0 }
    }

    override suspend fun order(items: List<Cart>): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val orderItems = items.map {
                OrderItemRequest(it.itemNotes, it.productId, it.itemQuantity)
            } // xxx -> ppp
            val orderRequest = OrderRequest(orderItems)
            FlavourfairDataSource.createOrder(orderRequest).status == true
        }
    }

    override suspend fun deleteAll() {
        dataSource.deleteAll()
    }
}
