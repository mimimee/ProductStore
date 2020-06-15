package com.example.productstore.presentation.productlist

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ProductItem(
    @PrimaryKey var id: Long = 0,
    var name: String = "",
    var price: Float = 0F
) : RealmObject()
