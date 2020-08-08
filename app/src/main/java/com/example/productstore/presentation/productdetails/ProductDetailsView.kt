package com.example.productstore.presentation.productdetails

import com.example.productstore.data.db.entity.Product
import com.example.productstore.presentation.base.BaseView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface ProductDetailsView : BaseView {
    fun selectImageFromGallery()
    fun fillScreenForEditing(product: Product?)
    fun setNameError(text: String)
    fun setPriceError(text: String)
    fun goBack()
}