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
    suspend fun insertProducts(product: List<ProductEntity>)
    suspend fun deleteProduct(product: ProductEntity): Int
    suspend fun updateProduct(product: ProductEntity): Int

}

class ProductDatabaseDataSource(private val productDao : ProductDao) : ProductDataSource {
    override fun getAllProducts(): Flow<List<ProductEntity>> {
        return productDao.getAllProducts()
    }

    override fun getProductById(id: Int): Flow<ProductEntity> {
        return productDao.getProductById(id)
    }

    override suspend fun insertProducts(product: List<ProductEntity>) {
        return productDao.insertProduct(product)
    }

    override suspend fun deleteProduct(product: ProductEntity): Int {
        return productDao.deleteProduct(product)
    }

    override suspend fun updateProduct(product: ProductEntity): Int {
        return productDao.updateProduct(product)
    }

}