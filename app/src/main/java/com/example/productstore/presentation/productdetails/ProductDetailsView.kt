package com.example.productstore.presentation.productdetails

import com.example.productstore.presentation.base.BaseView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface ProductDetailsView : BaseView {
    fun selectImageFromGallery()
}