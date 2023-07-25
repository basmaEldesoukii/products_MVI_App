package com.example.products_list.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.products_list.data.ProductsListDataModel

class ProductListDiffCallback: DiffUtil.ItemCallback<ProductsListDataModel>() {
    override fun areItemsTheSame(
        oldItem: ProductsListDataModel,
        newItem: ProductsListDataModel
    ): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: ProductsListDataModel,
        newItem: ProductsListDataModel
    ): Boolean = oldItem == newItem
}