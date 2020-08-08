package com.example.productstore.presentation.productdetails

import android.net.Uri
import com.example.productstore.App
import com.example.productstore.R
import com.example.productstore.data.db.entity.Product
import kotlinx.coroutines.*
import moxy.InjectViewState
import moxy.MvpPresenter

private const val DEFAULT_PRICE = 0F
const val REQUEST_IMAGE_GET = 1
const val EDIT_PRODUCT_ID = "edit_product_id"

@InjectViewState
class ProductDetailsPresenter : MvpPresenter<ProductDetailsView>() {
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private var changingProduct: Product? = null
    var changedItemId: Long? = null
    var editingMode = false
    var selectedPictureUri: Uri? = null

    override fun onFirstViewAttach() {
        changedItemId?.let { if (editingMode) displayItemForEditing(it) }
    }

    fun onFinalClick(productName: String, productPrice: String) {
        if (editingMode)
            saveChanges(productName, productPrice)
        else
            addProduct(productName, productPrice)
    }

    fun onCancelClicked() {
        if (editingMode)
            removeItem()
        else
            viewState.goBack()
    }

    private fun removeItem() {
        val product = changingProduct ?: return
        uiScope.launch {
            App.dataBase.productsDao.delete(product)
            viewState.showMessage(R.string.product_removed)
            viewState.goBack()
        }
    }

    private fun addProduct(productName: String, productPrice: String) {
        uiScope.launch {
            viewState.showLoader(true)
            delay(1000)
            val product = Product(productName, parsePrice(productPrice))
            if (selectedPictureUri != null) product.pictureUri = selectedPictureUri.toString()
            App.dataBase.productsDao.insert(product)
            viewState.showLoader(false)
            viewState.showMessage(R.string.new_product_created)
            viewState.goBack()
        }
    }

    private fun saveChanges(productName: String, productPrice: String) {
        val product = (changingProduct ?: return).apply {
            name = productName
            price = parsePrice(productPrice)
            selectedPictureUri?.let { pictureUri = it.toString() }
        }
        uiScope.launch {
            App.dataBase.productsDao.update(product)
            viewState.showMessage(R.string.changes_saved)
            viewState.goBack()
        }
    }

    fun onAddImageClicked() {
        viewState.selectImageFromGallery()
    }

    private fun displayItemForEditing(itemId: Long) {
        uiScope.launch {
            changingProduct = App.dataBase.productsDao.getProductById(itemId)
            viewState.fillScreenForEditing(changingProduct)
        }
    }

    private fun parsePrice(price: String): Float = try {
        price.toFloat()
    } catch (e: NumberFormatException) {
        DEFAULT_PRICE
    }

    override fun onDestroy() {
        uiScope.cancel()
        super.onDestroy()
    }
}