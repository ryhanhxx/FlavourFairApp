package com.ch.flavourfair.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ch.flavourfair.data.local.database.entity.CartEntity
import com.ch.flavourfair.data.local.database.relation.CartProductRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM CARTS")
    fun getAllCarts(): Flow<List<CartProductRelation>>

    @Query("SELECT * FROM CARTS WHERE id == :cartId")
    fun getCartById(cartId: Int): Flow<CartProductRelation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(carts: CartEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCarts(carts: List<CartEntity>)

    @Delete
    suspend fun deleteProduct(cart: CartEntity): Int

    @Update
    suspend fun updateProduct(cart: CartEntity): Int
}