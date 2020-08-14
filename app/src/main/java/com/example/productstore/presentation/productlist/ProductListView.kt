package com.example.productstore.presentation.productlist

import com.example.productstore.presentation.base.BaseView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ProductListView : BaseView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showFab(show: Boolean)
}