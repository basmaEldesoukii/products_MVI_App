package com.example.products_list.presentation

import android.content.Intent
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.base.com.example.base.interfaces.BaseActivity
import com.example.common.Constants
import com.example.product_details.presentation.ProductDetailsActivity
import com.example.products_list.databinding.ProductsListLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsListActivity: BaseActivity<ProductsListLayoutBinding>() {

    override val bindLayout: (LayoutInflater) -> ProductsListLayoutBinding
        get() = ProductsListLayoutBinding::inflate
    private lateinit var adapter: ProductsListAdapter
    private lateinit var viewModel: ProductsListViewModel

    override fun prepareView() {
        viewModel = ViewModelProvider(this). get(ProductsListViewModel::class.java)
        viewModel.setAction(ProductsListContract.Action.onFetchProductsList)
        adapter = ProductsListAdapter(viewModel)
        binding.productsRecyclerView.adapter = adapter
        initObservers()
    }

    /**
     * Initialize Observers
     */
    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if (it.selectedProduct != null) {
                        val intent = Intent(
                            this@ProductsListActivity,
                            ProductDetailsActivity::class.java
                        )
                        intent.putExtra(Constants.PRODUCT_ID_DATA_EXTRA, it.selectedProduct.id)
                        startActivity(intent)
                    } else {
                        when (val state = it.productsListState) {
                            is ProductsListContract.ProductsListState.Loading  -> {
                                binding.loadingView.root.isVisible = true
                            }
                            is ProductsListContract.ProductsListState.Success -> {
                                val data = state.productsListData
                                adapter.submitList(data)
                                binding.loadingView.root.isVisible = false
                            }
                            is ProductsListContract.ProductsListState.Error -> {
                                binding.loadingView.root.isVisible = false
                                binding.errorView.root.isVisible = true
                            }
                        }
                    }

                }
            }
        }
    }

}