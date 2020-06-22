package com.example.productstore.presentation.productlist

import android.animation.Animator
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

    fun getFabAnimatorListener(onEnd: () -> Unit): Animator.AnimatorListener = object : Animator.AnimatorListener {
        override fun onAnimationEnd(animation: Animator?) {
            onEnd.invoke()
            App.router.navigateTo(Screens.ProductDetailsScreen())
        }

        override fun onAnimationRepeat(animation: Animator?) {}
        override fun onAnimationCancel(animation: Animator?) {}
        override fun onAnimationStart(animation: Animator?) {}
    }

    fun onViewCreated() {
        viewState.showFab(true)
    }
}