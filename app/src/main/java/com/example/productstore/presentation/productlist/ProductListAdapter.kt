package com.example.productstore.presentation.productlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.productstore.R

class ProductListAdapter : RecyclerView.Adapter<ProductListAdapter.ProductHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        return ProductHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return 7
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {

    }

    inner class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}