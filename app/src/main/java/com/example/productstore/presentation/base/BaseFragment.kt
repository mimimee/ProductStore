package com.example.productstore.presentation.base


import android.widget.Toast
import androidx.annotation.StringRes
import moxy.MvpAppCompatFragment

open class BaseFragment : MvpAppCompatFragment(), BaseView {

    override fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(@StringRes resId: Int) {
        Toast.makeText(requireContext(), getString(resId), Toast.LENGTH_SHORT).show()
    }

    override fun showLoader(show: Boolean) {

    }
}
