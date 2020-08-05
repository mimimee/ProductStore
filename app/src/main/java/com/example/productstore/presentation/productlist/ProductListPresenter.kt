package com.example.productstore.presentation.productlist

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import com.example.productstore.App
import com.example.productstore.other.cicerone.Screens
import moxy.InjectViewState
import moxy.MvpPresenter

const val REQUEST_IMAGE_GET = 1

@InjectViewState
class ProductListPresenter : MvpPresenter<ProductListView>() {
    val adapter = ProductListAdapter()

    fun onFabClick() {
        viewState.showFab(false)
    }

    fun getAnimatorListenerAdapter(onEnd: () -> Unit) = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            onEnd.invoke()
            App.router.navigateTo(Screens.ProductDetailsScreen())
        }
    }

    fun onViewCreated() {
        viewState.showFab(true)
    }
}