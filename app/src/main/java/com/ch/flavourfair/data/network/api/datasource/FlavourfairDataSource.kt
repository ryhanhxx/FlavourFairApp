package com.ch.flavourfair.data.network.api.datasource

import com.ch.flavourfair.data.network.api.model.category.CategoriesResponse
import com.ch.flavourfair.data.network.api.model.order.OrderRequest
import com.ch.flavourfair.data.network.api.model.order.OrderResponse
import com.ch.flavourfair.data.network.api.model.product.ProductsResponse
import com.ch.flavourfair.data.network.api.service.FlavourfairApiService


interface FlavourfairDataSource {
    suspend fun getProducts(category: String? = null): ProductsResponse
    suspend fun getCategories(): CategoriesResponse
    suspend fun createOrder(orderRequest: OrderRequest): OrderResponse
}

class FlavourfairApiDataSource(private val service: FlavourfairApiService) : FlavourfairDataSource {
    override suspend fun getProducts(category: String?): ProductsResponse {
        return service.getProducts(category)
    }

    override suspend fun getCategories(): CategoriesResponse {
        return service.getCategories()
    }

    override suspend fun createOrder(orderRequest: OrderRequest): OrderResponse {
        return service.createOrder(orderRequest)
    }

}