package com.example.productstore.data.db.dao

import androidx.room.*
import com.example.productstore.data.db.entity.Product

@Dao
interface ProductsDao {
    @Query("select * from products_table where id == :productId")
    suspend fun getProductById(productId: Long): Product

    @Query("select * from products_table")
    suspend fun getAllProducts(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Update
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(product: Product)
}