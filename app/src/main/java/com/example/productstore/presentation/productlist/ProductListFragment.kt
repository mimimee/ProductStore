package com.example.productstore.presentation.productlist

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import com.example.productstore.R
import com.example.productstore.other.extensions.showBackButton
import com.example.productstore.other.extensions.visible
import com.example.productstore.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_productlist.*
import moxy.presenter.InjectPresenter

class ProductListFragment : BaseFragment(), ProductListView {
    @InjectPresenter
    lateinit var presenter: ProductListPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_productlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated()
        setupToolbar()
        setupRecycler()
        setupClicks()
    }

    private fun setupToolbar() {
        toolbar.title = getString(R.string.products)
        toolbar.showBackButton(false)
    }

    private fun setupRecycler() {
        product_recycler.adapter = presenter.adapter
    }

    private fun setupClicks() {
        fab.setOnClickListener { presenter.onFabClick() }
    }

    override fun showFab(show: Boolean) {
        if (show) {
            fab.isEnabled = true
            fab.visible = true
            fab.animate()
                .setStartDelay(200)
                .setDuration(150)
                .alpha(1F)
        } else {
            fab.isEnabled = false
            fab.animate()
                .setStartDelay(50)
                .setDuration(150)
                .alpha(0F)
                .setListener(presenter.getAnimatorListenerAdapter())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.seach_menu, menu)
        val searchItem = menu?.findItem(R.id.menu_search_item)
        (searchItem?.actionView as SearchView).setOnQueryTextListener(presenter.searchQueryListener)
    }
}
