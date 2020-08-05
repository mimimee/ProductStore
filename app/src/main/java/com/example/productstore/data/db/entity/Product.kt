package com.example.productstore.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products_table")
class Product(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = 0,
    var name: String? = "",
    var price: Float? = 0F,
    var pictureUri: String? = ""
)