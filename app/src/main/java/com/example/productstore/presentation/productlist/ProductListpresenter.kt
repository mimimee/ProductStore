package com.example.productstore.presentation.productlist

import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ProductListpresenter : MvpPresenter<ProductListView>() {
    val adapter = ProductListAdapter()
}