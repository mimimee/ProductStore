package com.example.productstore.presentation.productlist


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.productstore.R
import com.example.productstore.data.db.entity.ProductEntity
import com.example.productstore.data.db.repository.ProductsRepository
import com.example.productstore.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_productlist.*
import moxy.presenter.InjectPresenter

class ProductListFragment : BaseFragment(), ProductListView {
    @InjectPresenter
    lateinit var presenter: ProductListpresenter

    val REQUEST_IMAGE_GET = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_productlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecycler()
        setupClicks()
    }

    private fun setupToolbar() {
        toolbar.title = getString(R.string.products)
    }

    private fun setupRecycler() {
        product_recycler.adapter = presenter.adapter
    }

    @SuppressLint("CheckResult")
    private fun setupClicks() {
        fab.setOnClickListener { selectImage() }
        ProductsRepository.insertItem(ProductEntity("test", 100F))
            .doOnSubscribe { Log.d("kek", Thread.currentThread().name + "onSub") }
            .doFinally { Log.d("kek", Thread.currentThread().name + " fin") }
            .subscribe {
                Log.d("kek", Thread.currentThread().name + " subs")
                it.toString()
            }
    }


    private fun selectImage() {
        val packageManager = activity?.packageManager ?: return
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" }
        if (intent.resolveActivity(packageManager) != null) {
            intent.putExtra("kektest", 123)
            startActivityForResult(intent, REQUEST_IMAGE_GET)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_GET && resultCode == Activity.RESULT_OK) {
            val pictureUri = data?.data
            presenter.adapter.pic = pictureUri
            presenter.adapter.notifyDataSetChanged()
        }
    }
}
