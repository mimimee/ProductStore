package com.example.productstore.presentation.productlist


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.productstore.R
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

    private fun setupClicks() {
        fab.setOnClickListener { selectImage() }
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
            val bitMap = MediaStore.Images.Media.getBitmap(context?.contentResolver, pictureUri)
            presenter.adapter.pic = pictureUri
            presenter.adapter.thumbnail = bitMap
            presenter.adapter.notifyDataSetChanged()
        }
    }
}
