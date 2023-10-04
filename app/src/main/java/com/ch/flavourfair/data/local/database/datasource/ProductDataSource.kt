package com.ch.flavourfair.data.local.database.datasource

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ch.flavourfair.data.local.database.dao.ProductDao
import com.ch.flavourfair.data.local.database.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductDataSource {
    fun getAllProducts(): Flow<List<ProductEntity>>
    fun getProductById(id: Int): Flow<ProductEntity>
    suspend fun insertProducts(products: List<ProductEntity>)
    suspend fun deleteProduct(product: ProductEntity): Int
    suspend fun updateProduct(product: ProductEntity): Int

}

class ProductDatabaseDataSource(private val dao : ProductDao) : ProductDataSource {
    override fun getAllProducts(): Flow<List<ProductEntity>> {
        return dao.getAllProducts()
    }

    override fun getProductById(id: Int): Flow<ProductEntity> {
        return dao.getProductById(id)
    }

    override suspend fun insertProducts(products: List<ProductEntity>) {
        return dao.insertProducts(products)
    }

    override suspend fun deleteProduct(product: ProductEntity): Int {
        return dao.deleteProduct(product)
    }

    override suspend fun updateProduct(product: ProductEntity): Int {
        return dao.updateProduct(product)
    }

}