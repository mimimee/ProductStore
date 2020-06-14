package com.example.productstore.other.extensions

import android.view.View

var View.visible: Boolean
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }
    get() = visibility == View.VISIBLE

