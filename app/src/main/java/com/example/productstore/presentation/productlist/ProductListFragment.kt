package com.example.productstore.presentation.productlist


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        return inflater.inflate(R.layout.fragment_productlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated()
        setupToolbar()
        setupRecycler()
        setupClicks()
        setupFab()
    }

    private fun setupToolbar() {
        toolbar.title = getString(R.string.products)
        toolbar.showBackButton(false)
    }

    private fun setupRecycler() {
        product_recycler.adapter = presenter.adapter
    }

    private fun setupFab() {
        fab.setOnClickListener { presenter.onFabClick() }
    }

    private fun setupClicks() {

//        val subscribe = ProductsRepository.insertItem(ProductEntity("test", 100F))
//            .doOnSubscribe { Log.d("kek", Thread.currentThread().name + " doOnSubscribe") }
//            .doFinally { Log.d("kek", Thread.currentThread().name + " doFinally") }
//            .subscribe {
//                Log.d("kek", Thread.currentThread().name + " subs")
//                it.toString()
//                val r = Realm.getDefaultInstance()
//                val k = r.where<ProductEntity>().findAll()
//                Log.d("kek2", k.toString())
//            }

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
                .setListener(presenter.getAnimatorListenerAdapter { fab.visible = false })
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
//            presenter.adapter.pic = pictureUri
            presenter.adapter.notifyDataSetChanged()
        }
    }
}
