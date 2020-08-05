package com.example.productstore.data.db.dao

import androidx.room.*
import com.example.productstore.data.db.entity.Product

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product)

    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product)

    @Query("select * from products_table")
    suspend fun getAllProducts(): List<Product>
}