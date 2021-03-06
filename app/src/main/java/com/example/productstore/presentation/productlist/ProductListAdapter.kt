package com.example.productstore.presentation.productlist

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productstore.R
import com.example.productstore.data.db.entity.Product
import com.example.productstore.other.extensions.toRubles
import com.example.productstore.other.extensions.visible
import kotlinx.android.synthetic.main.product_list_item.view.product_name
import kotlinx.android.synthetic.main.product_list_item.view.product_price
import kotlinx.android.synthetic.main.product_list_item.view.product_small_image
import kotlinx.android.synthetic.main.product_list_item.view.*

private const val PRODUCT_TYPE = 100
private const val PLACEHOLDER_TYPE = 200
private const val LAST_ITEM_TYPE = 300

class ProductListAdapter(
    private val onItemClickListener: (itemId: Long, itemPosition: Int) -> Unit,
    private val onMapMarkerClickListener: (address: String?) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = arrayListOf<Product>()

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
        if (holder is ProductHolder) holder.bind(data[position])
    }

    override fun getItemCount() = if (data.isEmpty()) 1 else data.size + 1

    inner class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { data[adapterPosition].id?.let { id -> onItemClickListener.invoke(id, adapterPosition) } }
            itemView.product_marker_icon.setOnClickListener { onMapMarkerClickListener.invoke(data[adapterPosition].storageAddress) }
        }

        fun bind(item: Product) {
            itemView.run {
                product_name.text = item.name
                product_price.text = item.price?.toRubles()
                product_marker_icon.visible = !item.storageAddress.isNullOrEmpty()

                if (item.pictureUri.isNullOrEmpty())
                    product_small_image.setImageResource(R.drawable.ic_box)
                else
                    Glide.with(context)
                        .load(Uri.parse(item.pictureUri))
                        .placeholder(R.drawable.ic_box)
                        .error(R.drawable.ic_box)
                        .into(product_small_image)
            }
        }
    }

    inner class PlaceHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    inner class LastItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}