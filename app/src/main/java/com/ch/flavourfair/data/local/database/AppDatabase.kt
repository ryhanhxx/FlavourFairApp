package com.ch.flavourfair.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ch.flavourfair.data.dummy.DummyProductDataSourceImpl
import com.ch.flavourfair.data.local.database.AppDatabase.Companion.getInstance
import com.ch.flavourfair.data.local.database.dao.CartDao
import com.ch.flavourfair.data.local.database.dao.ProductDao
import com.ch.flavourfair.data.local.database.entity.CartEntity
import com.ch.flavourfair.data.local.database.entity.ProductEntity
import com.ch.flavourfair.data.local.database.mapper.toProductEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Database(
    entities = [CartEntity::class, ProductEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun productDao(): ProductDao

    companion object {
        private const val DB_NAME = "FlavourFair.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseSeederCallback(context))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

class DatabaseSeederCallback(private val context: Context) : RoomDatabase.Callback() {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        scope.launch {
            getInstance(context).productDao().insertProduct(prepopulateProducts())
            getInstance(context).cartDao().insertCarts(prepopulateCarts())
        }
    }

    private fun prepopulateProducts(): List<ProductEntity> {
        return DummyProductDataSourceImpl().getProductData().toProductEntity()
    }

    private fun prepopulateCarts(): List<CartEntity> {
        return mutableListOf(
            CartEntity(
                id = 1,
                productId = 1,
                itemNotes = "Barang yang fresh ya",
                itemQuantity = 3
            ),
            CartEntity(
                id = 2,
                productId = 2,
                itemNotes = "Barang yang fresh yaaaaaa",
                itemQuantity = 6
            ),
        )
    }
}