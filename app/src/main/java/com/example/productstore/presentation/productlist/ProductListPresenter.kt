package com.example.productstore.presentation.productlist

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import com.example.productstore.App
import com.example.productstore.other.cicerone.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ProductListPresenter : MvpPresenter<ProductListView>() {
    private val uiScope = CoroutineScope(Dispatchers.Main)
    val adapter = ProductListAdapter()

    fun onViewCreated() {
        viewState.showFab(true)
        displayProductList()
    }

    fun onFabClick() {
        viewState.showFab(false)
    }

    fun getAnimatorListenerAdapter(onEnd: () -> Unit) = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            onEnd.invoke()
            App.router.navigateTo(Screens.ProductDetailsScreen())
        }
    }

    private fun displayProductList() {
        uiScope.launch {
            val productList = App.dataBase.productsDao.getAllProducts()
            if (adapter.data != productList) {
                adapter.data = productList as ArrayList
                adapter.notifyItemRangeChanged(0, productList.size)
            }
        }
    }
}