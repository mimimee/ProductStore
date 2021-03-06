package com.example.productstore.presentation.productdetails

import android.net.Uri
import android.text.Editable
import com.example.productstore.App
import com.example.productstore.R
import com.example.productstore.data.db.entity.Product
import com.example.productstore.other.abstractions.SimpleTextWatcher
import kotlinx.coroutines.*
import moxy.InjectViewState
import moxy.MvpPresenter

private const val DEFAULT_PRICE = 0F
const val REQUEST_IMAGE_GET = 1
const val EDIT_PRODUCT_ID = "EDIT_PRODUCT_ID"

@InjectViewState
class ProductDetailsPresenter : MvpPresenter<ProductDetailsView>() {
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private var changingProduct: Product? = null
    private var nameError = ""
    private var priceError = ""
    var changedItemId: Long? = null
    var editingMode = false
    var selectedPictureUri: Uri? = null
    var nameListener = getNameListener()
    var priceListener = getPriceListener()

    override fun onFirstViewAttach() {
        changedItemId?.let { if (editingMode) displayItemForEditing(it) }
    }

    fun onFinalClick(productName: String, productPrice: String, address: String) {
        if (!areFieldsValid(productName, productPrice)) {
            viewState.setNameError(nameError)
            viewState.setPriceError(priceError)
        } else if (editingMode) {
            saveChanges(productName, productPrice, address)
        } else {
            addProduct(productName, productPrice, address)
        }
    }

    fun onCancelClicked() {
        if (editingMode)
            removeItem()
        else
            viewState.goBack()
    }

    private fun getNameListener() = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable) {
            nameError = if (s.isEmpty()) App.context.getString(R.string.field_cannot_be_empty) else ""
            viewState.setNameError(nameError)
        }
    }

    private fun getPriceListener() = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable) {
            priceError = if (s.isEmpty()) App.context.getString(R.string.field_cannot_be_empty) else ""
            viewState.setPriceError(priceError)
        }
    }

    private fun areFieldsValid(productName: String, productPrice: String): Boolean {
        val emptyFieldError = App.context.getString(R.string.field_cannot_be_empty)
        nameError = if (productName.isEmpty()) emptyFieldError else ""
        priceError = if (productPrice.isEmpty()) emptyFieldError else ""
        return priceError.isEmpty() && nameError.isEmpty()
    }

    private fun removeItem() {
        val product = changingProduct ?: return
        uiScope.launch {
            App.dataBase.productsDao.delete(product)
            viewState.showMessage(R.string.product_removed)
            viewState.goBack()
        }
    }

    private fun addProduct(productName: String, productPrice: String, address: String) {
        uiScope.launch {
            viewState.showLoader(true)
            delay(1000)
            val product = Product(
                name = productName,
                price = parsePrice(productPrice),
                storageAddress = address
            )
            if (selectedPictureUri != null) product.pictureUri = selectedPictureUri.toString()
            App.dataBase.productsDao.insert(product)
            viewState.showLoader(false)
            viewState.showMessage(R.string.new_product_created)
            viewState.goBack()
        }
    }

    private fun saveChanges(productName: String, productPrice: String, address: String) {
        val product = (changingProduct ?: return).apply {
            name = productName
            price = parsePrice(productPrice)
            storageAddress = address
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