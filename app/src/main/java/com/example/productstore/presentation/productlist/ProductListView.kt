package com.example.productstore.presentation.productlist

import com.example.productstore.presentation.base.BaseView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SingleStateStrategy::class)
interface ProductListView : BaseView {
    fun showFab(show: Boolean)
}