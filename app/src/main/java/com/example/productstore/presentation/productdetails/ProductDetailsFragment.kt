package com.example.productstore.presentation.productdetails

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.productstore.App
import com.example.productstore.R
import com.example.productstore.data.db.entity.Product
import com.example.productstore.other.extensions.showBackButton
import com.example.productstore.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_product_details.*
import moxy.presenter.InjectPresenter

class ProductDetailsFragment : BaseFragment(), ProductDetailsView {
    @InjectPresenter
    lateinit var presenter: ProductDetailsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.getLong(EDIT_PRODUCT_ID)?.let {
            presenter.changedItemId = it
            presenter.editingMode = true
        }
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbarAndTitles()
        setupClicks()
    }

    private fun setupToolbarAndTitles() {
        toolbar.title = getString(if (presenter.editingMode) R.string.edit_product else R.string.add_product)
        toolbar.showBackButton(true) { goBack() }
        add_product_btn.text = getString(if (presenter.editingMode) R.string.save_changes else R.string.add_product)
        cancel_btn.text = getString(if (presenter.editingMode) R.string.remove_product else R.string.cancel)
    }

    private fun setupClicks() {
        add_product_btn.setOnClickListener { presenter.onFinalClick(name_et.text.toString(), price_et.text.toString()) }
        product_image.setOnClickListener { presenter.onAddImageClicked() }
        cancel_btn.setOnClickListener { presenter.onCancelClicked() }
    }

    override fun fillScreenForEditing(product: Product?) {
        name_et.setText(product?.name.toString())
        price_et.setText(product?.price.toString())
        Glide.with(this)
            .load(Uri.parse(product?.pictureUri))
            .placeholder(R.drawable.ic_add_photo_light)
            .into(product_image)
    }

    override fun goBack() {
        App.router.exit()
    }

    override fun selectImageFromGallery() {
        val packageManager = activity?.packageManager ?: return
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" }
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_GET && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data ?: return
            presenter.selectedPictureUri = imageUri
            Glide.with(this)
                .load(imageUri)
                .into(product_image)
        }
    }
}
