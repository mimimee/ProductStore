package com.example.productstore.presentation.productlist

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import com.example.productstore.App
import com.example.productstore.other.cicerone.Screens
import com.example.productstore.presentation.productdetails.EDIT_PRODUCT_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ProductListPresenter : MvpPresenter<ProductListView>() {
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private var lastClickedItemPosition = -1
    val adapter = ProductListAdapter(
        onItemClickListener = ::openEditingScreen
    )

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
            if (adapter.data != productList && productList.isNotEmpty()) {
                adapter.data = productList as ArrayList
                if (lastClickedItemPosition >= 0) adapter.notifyItemRemoved(lastClickedItemPosition) else adapter.notifyDataSetChanged()
            } else if (productList.isEmpty()) {
                adapter.data = productList as ArrayList
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun openEditingScreen(itemId: Long, itemPosition: Int) {
        lastClickedItemPosition = itemPosition
        val bundle = Bundle().apply { putLong(EDIT_PRODUCT_ID, itemId) }
        App.router.navigateTo(Screens.ProductDetailsScreen(bundle))
    }
}