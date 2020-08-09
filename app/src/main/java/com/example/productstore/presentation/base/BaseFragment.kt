package com.example.productstore.presentation.base


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import com.example.productstore.other.extensions.visible
import com.example.productstore.presentation.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import moxy.MvpAppCompatFragment

open class BaseFragment : MvpAppCompatFragment(), BaseView {
    protected lateinit var toolbar: Toolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = (activity as MainActivity).activity_toolbar
        (activity as MainActivity).setSupportActionBar(toolbar)
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(@StringRes resId: Int) {
        Toast.makeText(requireContext(), getString(resId), Toast.LENGTH_SHORT).show()
    }

    override fun showLoader(show: Boolean) {
        (activity as MainActivity).loader.visible = show
    }

    override fun showToolbar(show: Boolean) {
        (activity as MainActivity).toolbar.visible = show
    }
}
