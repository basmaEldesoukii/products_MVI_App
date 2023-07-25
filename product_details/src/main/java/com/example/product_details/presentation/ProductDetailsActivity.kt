package com.example.product_details.presentation

import android.content.Intent
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.base.com.example.base.interfaces.BaseActivity
import com.example.common.Constants
import com.example.product_details.data.ProductDetailsDataModel
import com.example.product_details.databinding.ProductDetailsLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailsActivity: BaseActivity<ProductDetailsLayoutBinding>() {

    override val bindLayout: (LayoutInflater) -> ProductDetailsLayoutBinding
        get() = ProductDetailsLayoutBinding::inflate
    private var id: Int? = null
    private lateinit var viewModel: ProductDetailsViewModel

    override fun prepareView() {
        viewModel = ViewModelProvider(this)[ProductDetailsViewModel::class.java]
        id = intent.extras?.getInt(Constants.PRODUCT_ID_DATA_EXTRA)
        if (id != null) {
            viewModel.setAction(ProductDetailsContract.Action.onFetchProductDetails(id!!))
        }
        initObservers()
    }


    /**
     * Initialize Observers
     */
    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (val state = it.productDetailsState) {

                        is ProductDetailsContract.ProductDetailsState.Loading -> {
                            binding.loadingView.root.isVisible = true
                        }

                        is ProductDetailsContract.ProductDetailsState.Success -> {
                            val data = state.productsDetailsDataModel
                            binding.productTitle.text = data.title
                            binding.priceTxt.text = data.price
                            binding.categoryTxt.text = data.category
                            binding.descriptionTxt.text = data.description
                            //binding.productImg.setImageResource(data.image.toInt())
                            binding.loadingView.root.isVisible = false
                        }

                        is ProductDetailsContract.ProductDetailsState.Error -> {
                            binding.loadingView.root.isVisible = false
                            binding.errorView.root.isVisible = true
                        }
                    }
                }
            }
        }
    }


    override fun onBackPressed() {
        startActivity(Intent(this, ProductDetailsActivity::class.java))
        finish()

    }
}