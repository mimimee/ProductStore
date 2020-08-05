package com.example.productstore.presentation.productdetails

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.productstore.App
import com.example.productstore.R
import com.example.productstore.other.extensions.showBackButton
import com.example.productstore.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_product_details.*
import moxy.presenter.InjectPresenter

class ProductDetailsFragment : BaseFragment(), ProductDetailsView {
    @InjectPresenter
    lateinit var presenter: ProductDetailsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupClicks()
    }

    private fun setupToolbar() {
        toolbar.title = getString(R.string.add_product)
        toolbar.showBackButton(true) { App.router.exit() }
    }

    private fun setupClicks() {
        add_product_btn.setOnClickListener {
            presenter.onAddProductClicked(name_et.text.toString(), price_et.text.toString())
        }
    }

    private fun testSend() {
        activity?.onBackPressed()
        targetFragment?.onActivityResult(14, Activity.RESULT_OK, Intent().putExtra("extra14", "extra14"))
    }
}
