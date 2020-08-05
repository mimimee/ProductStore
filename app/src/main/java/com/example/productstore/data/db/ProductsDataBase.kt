package com.example.productstore.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.productstore.App
import com.example.productstore.data.db.dao.ProductsDao
import com.example.productstore.data.db.entity.Product

private const val DB_NAME = "products.db"

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class ProductsDataBase : RoomDatabase() {
    abstract val productsDao: ProductsDao

    companion object {
        private lateinit var INSTANCE: ProductsDataBase
        fun getInstance(): ProductsDataBase {
            synchronized(this) {
                if (!::INSTANCE.isInitialized)
                    INSTANCE = Room.databaseBuilder(App.context, ProductsDataBase::class.java, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return INSTANCE
        }
    }
}