package com.ch.flavourfair.data.repository

import com.ch.flavourfair.data.network.api.datasource.FlavourfairDataSource
import com.ch.flavourfair.data.network.api.model.category.toCategoryList
import com.ch.flavourfair.data.network.api.model.product.toProductList
import com.ch.flavourfair.model.Category
import com.ch.flavourfair.model.Product
import com.ch.flavourfair.utils.ResultWrapper
import com.ch.flavourfair.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getCategories(): Flow<ResultWrapper<List<Category>>>
    fun getProducts(category: String? = null): Flow<ResultWrapper<List<Product>>>
}

class ProductRepositoryImpl(
    private val apiDataSource: FlavourfairDataSource
) : ProductRepository {

    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow {
            apiDataSource.getCategories().data?.toCategoryList() ?: emptyList()
        }
    }

    override fun getProducts(category: String?): Flow<ResultWrapper<List<Product>>> {
        return proceedFlow {
            apiDataSource.getProducts(category).data?.toProductList() ?: emptyList()
        }
    }
}
