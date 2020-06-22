package com.example.productstore.presentation.productlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.productstore.R
import com.example.productstore.data.db.entity.ProductEntity

private const val PRODUCT_TYPE = 100
private const val PLACEHOLDER_TYPE = 200
private const val LAST_ITEM_TYPE = 300

class ProductListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val data = mutableListOf<ProductEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PRODUCT_TYPE -> ProductHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false))
            PLACEHOLDER_TYPE -> PlaceHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_placeholder, parent, false))
            else -> LastItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_last_item, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            data.isEmpty() -> PLACEHOLDER_TYPE
            position == itemCount - 1 -> LAST_ITEM_TYPE
            else -> PRODUCT_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductHolder) holder.bind()
    }

    override fun getItemCount() = if (data.isEmpty()) 1 else data.size + 1

    inner class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
//            Picasso.get()
//                .load(pic)
//                .centerCrop()
//                .resizeDimen(R.dimen.small_picture_size, R.dimen.small_picture_size)
//                .into(itemView.product_small_image)
        }
    }

    inner class PlaceHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    inner class LastItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}