package com.example.productstore.other.extensions

import android.view.View
import android.widget.Toolbar
import com.example.productstore.R
import java.lang.StringBuilder

var View.visible: Boolean
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }
    get() = visibility == View.VISIBLE


fun Toolbar.showBackButton(show: Boolean, action: (() -> Unit)? = null) {
    if (show) {
        setNavigationIcon(R.drawable.ic_arrow_back_24dp)
        setNavigationOnClickListener { action?.invoke() }
    } else {
        navigationIcon = null
        setNavigationOnClickListener(null)
    }
}

fun Float?.toRubles(): String {
    val price = this?.toString() ?: "0"
    return StringBuilder(price).append(" \u20BD").toString()
}