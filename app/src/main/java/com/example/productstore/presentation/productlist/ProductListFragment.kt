package com.example.productstore.presentation.productlist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.productstore.R
import com.example.productstore.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_productlist.*

class ProductListFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_productlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecycler()
    }

    private fun setupToolbar() {
        toolbar.title = getString(R.string.products)
    }

    private fun setupRecycler() {
        product_recycler.adapter = ProductListAdapter()
    }
}
