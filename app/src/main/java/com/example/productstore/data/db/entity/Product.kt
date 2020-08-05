package com.example.productstore.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products_table")
class Product(
    var name: String? = "",
    var price: Float? = 0F,
    var pictureUri: String? = "",
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
)
