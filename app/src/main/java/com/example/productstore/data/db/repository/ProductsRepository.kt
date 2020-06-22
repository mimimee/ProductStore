package com.example.productstore.data.db.repository

import android.util.Log
import com.example.productstore.data.db.entity.ProductEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.kotlin.where

object ProductsRepository {
    fun insertItem(productItem: ProductEntity): Observable<ProductEntity> {
        return Observable.just(productItem)
            .doOnNext { nextItem ->
                Log.d("kek", Thread.currentThread().name + " onNext")
                val realm = Realm.getDefaultInstance()
                val maxValue = realm.where<ProductEntity>().max("id")
                val index = if (maxValue == null) 0 else maxValue.toLong() + 1
                nextItem.id = index
                realm.executeTransaction { it.copyToRealm(nextItem) }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}