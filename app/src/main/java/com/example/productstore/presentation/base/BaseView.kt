package com.example.productstore.presentation.base

import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SingleStateStrategy::class)
interface BaseView : MvpView {
    fun showMessage(message: String)
    fun showMessage(resId: Int)
    fun showLoader(show: Boolean)
    fun showToolbar(show: Boolean)
}