package com.example.productstore.presentation.productlist

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import com.example.productstore.App
import com.example.productstore.R
import com.example.productstore.data.db.entity.Product
import com.example.productstore.other.cicerone.Screens
import com.example.productstore.presentation.productdetails.EDIT_PRODUCT_ID
import com.example.productstore.presentation.productlist.EDITING_EVENT.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ProductListPresenter : MvpPresenter<ProductListView>() {
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private var lastClickedItemPosition = -1
    private val fullResults = arrayListOf<Product>()
    private var editingEvent: EDITING_EVENT? = null
    val searchQueryListener = getSearchQueryWatcher()
    val adapter = ProductListAdapter(
        onItemClickListener = ::openEditingScreen,
        onMapMarkerClickListener = ::openMapWithStorage
    )

    fun onViewCreated() {
        viewState.showFab(true)
        displayProductList()
    }

    fun onFabClick() {
        viewState.showFab(false)
    }

    fun getAnimatorListenerAdapter() = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            App.router.navigateTo(Screens.ProductDetailsScreen())
        }
    }

    private fun displayProductList() {
        uiScope.launch {
            val productList = App.dataBase.productsDao.getAllProducts()
            fullResults.clear()
            fullResults.addAll(productList)
            editingEvent = when {
                adapter.data == productList -> NONE
                adapter.data.size == productList.size -> EDIT
                adapter.data.size > productList.size -> DELETE
                adapter.data.size < productList.size -> ADD
                else -> NONE
            }

            if (editingEvent == NONE) return@launch
            adapter.data = productList as ArrayList

            when (editingEvent) {
                EDIT -> adapter.notifyItemChanged(lastClickedItemPosition)
                DELETE -> adapter.notifyItemRemoved(lastClickedItemPosition)
                ADD -> adapter.notifyItemInserted(adapter.itemCount - 1)
            }
        }
    }

    private fun openEditingScreen(itemId: Long, itemPosition: Int) {
        lastClickedItemPosition = itemPosition
        val bundle = Bundle().apply { putLong(EDIT_PRODUCT_ID, itemId) }
        App.router.navigateTo(Screens.ProductDetailsScreen(bundle))
    }

    private fun openMapWithStorage(address: String?) {
        if (address.isNullOrEmpty()) {
            viewState.showMessage(R.string.no_address_found)
            return
        }
        viewState.showLoader(true)
        uiScope.launch {
            var addressList = listOf<Address>()
            withContext(Dispatchers.IO) {
                val geoCoder = Geocoder(App.context)
                addressList = geoCoder.getFromLocationName(address, 1)
            }
            if (addressList.isEmpty())
                viewState.showMessage(R.string.no_address_found)
            else {
                val locationUri = Uri.parse("geo:0,0?q=$address")
                val intent = Intent(Intent.ACTION_VIEW, locationUri)
                if (intent.resolveActivity(App.context.packageManager) != null)
                    App.context.startActivity(intent)
                else
                    viewState.showMessage(R.string.map_app_is_not_fount)
            }
            viewState.showLoader(false)
        }
    }

    private fun getSearchQueryWatcher() = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String) = false
        override fun onQueryTextChange(newText: String): Boolean {
            if (newText.isEmpty()) {
                adapter.data = fullResults
            } else {
                val filtered = fullResults.filter { product -> product.name?.contains(newText.toLowerCase().trim()) == true } as ArrayList
                filtered.sortBy { product -> product.name?.indexOf(newText.toLowerCase().trim()) }
                adapter.data = filtered
            }
            adapter.notifyDataSetChanged()
            return false
        }
    }
}