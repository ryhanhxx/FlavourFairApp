package com.ch.flavourfair.data.repository

import com.ch.flavourfair.data.dummy.DummyCategoryDataSource
import com.ch.flavourfair.data.local.database.datasource.ProductDataSource
import com.ch.flavourfair.data.local.database.mapper.toProductList
import com.ch.flavourfair.model.Category
import com.ch.flavourfair.model.Product
import com.ch.flavourfair.utils.ResultWrapper
import com.ch.flavourfair.utils.proceed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface ProductRepository {
    fun getCategories(): List<Category>
    fun getProducts(): Flow<ResultWrapper<List<Product>>>
}

class ProductRepositoryImpl(
    private val productDataSource: ProductDataSource,
    private val dummyCategoryDataSource: DummyCategoryDataSource
) : ProductRepository {

    override fun getCategories(): List<Category> {
        return dummyCategoryDataSource.getCategoryData()
    }

    override fun getProducts(): Flow<ResultWrapper<List<Product>>> {
        return productDataSource.getAllProducts().map {
            proceed { it.toProductList() //Product Entity to Product
            }
        }.onStart { //onStart is the first one of data running
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }
}