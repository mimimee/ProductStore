package com.example.productstore.other.cicerone

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.productstore.presentation.productdetails.ProductDetailsFragment
import com.example.productstore.presentation.productlist.ProductListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {
    class ProductListScreen(private val bundle: Bundle? = null) : SupportAppScreen() {
        override fun getFragment(): Fragment = ProductListFragment().apply { arguments = bundle }
    }

    class ProductDetailsScreen(private val bundle: Bundle? = null) : SupportAppScreen() {
        private val fragment = ProductDetailsFragment()

        override fun getFragment(): Fragment = fragment.apply { arguments = bundle }

        constructor(targetFragment: Fragment, requestCode: Int, bundle: Bundle? = null) : this(bundle) {
            fragment.setTargetFragment(targetFragment, requestCode)
        }
    }
}