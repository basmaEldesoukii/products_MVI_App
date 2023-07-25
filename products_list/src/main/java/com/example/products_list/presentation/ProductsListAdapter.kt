package com.example.products_list.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.base.BaseRecyclerAdapter
import com.example.products_list.data.ProductsListDataModel
import com.example.products_list.databinding.SingleProductCardBinding

class ProductsListAdapter constructor(
    private val viewModel: ProductsListViewModel? = null
) :
    BaseRecyclerAdapter<ProductsListDataModel, SingleProductCardBinding, ProductItemViewHolder>(ProductListDiffCallback())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        val binding = SingleProductCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ProductItemViewHolder(
            binding = binding, viewModel = viewModel
        )

    }

}