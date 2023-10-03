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

    @Query("SELECT * FROM PRODUCTS")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM PRODUCTS WHERE id == :id")
    fun getProductById(id: Int): Flow<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(product: List<ProductEntity>)

    @Delete
    suspend fun deleteProduct(product: ProductEntity): Int

    @Update
    suspend fun updateProduct(product: ProductEntity): Int

}

class ProductDatabaseDataSource(private val dao : ProductDao) : ProductDataSource {
    override fun getAllProducts(): Flow<List<ProductEntity>> {
        return dao.getAllProducts()
    }

    override fun getProductById(id: Int): Flow<ProductEntity> {
        return dao.getProductById(id)
    }

    override suspend fun insertProducts(product: List<ProductEntity>) {
        return dao.insertProduct(product)
    }

    override suspend fun deleteProduct(product: ProductEntity): Int {
        return dao.deleteProduct(product)
    }

    override suspend fun updateProduct(product: ProductEntity): Int {
        return dao.updateProduct(product)
    }

}