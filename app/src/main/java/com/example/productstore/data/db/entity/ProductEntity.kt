package com.example.productstore.data.db.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ProductEntity(
    var name: String? = null,
    var price: Float? = null,
    var pictureUri: String? = null,
    @PrimaryKey var id: Long? = 0
) : RealmObject()