package com.example.productstore.presentation.productdetails

import android.net.Uri
import com.example.productstore.App
import com.example.productstore.R
import com.example.productstore.data.db.entity.Product
import kotlinx.coroutines.*
import moxy.InjectViewState
import moxy.MvpPresenter

const val REQUEST_IMAGE_GET = 1

@InjectViewState
class ProductDetailsPresenter : MvpPresenter<ProductDetailsView>() {
    private val uiScope = CoroutineScope(Dispatchers.Main)
    val editingMode = false
    var pictureUri: Uri? = null

    fun onAddProductClicked(productName: String, productPrice: String) {
        uiScope.launch {
            viewState.showLoader(true)
            withContext(Dispatchers.IO) {
                delay(1000)
                val parsedPrice = parsePrice(productPrice)
                val product = Product(productName, parsedPrice)
                if (pictureUri != null) product.pictureUri = pictureUri.toString()
                App.dataBase.productsDao.insert(product)
            }
            viewState.showLoader(false)
            viewState.showMessage(R.string.new_product_created)
        }
    }

    fun onAddImageClicked() {
        viewState.selectImageFromGallery()
    }

    private fun parsePrice(price: String): Float = try {
        price.toFloat()
    } catch (e: NumberFormatException) {
        0F
    }

    override fun onDestroy() {
        uiScope.cancel()
        super.onDestroy()
    }
}