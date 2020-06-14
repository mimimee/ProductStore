package com.example.productstore.presentation.base


import android.widget.Toast
import androidx.annotation.StringRes
import com.example.productstore.other.extensions.visible
import com.example.productstore.presentation.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import moxy.MvpAppCompatFragment

open class BaseFragment : MvpAppCompatFragment(), BaseView {
    protected val toolbar = (activity as MainActivity).toolbar

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
        (activity as MainActivity).toolbar_layout.visible = show
    }
}
