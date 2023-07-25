package com.example.product_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.BaseViewModel
import com.example.common.Resource
import com.example.product_details.domain.GetProductDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class ProductDetailsViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
) : BaseViewModel<ProductDetailsContract.Action, ProductDetailsContract.State>() {


    override fun createInitialState(): ProductDetailsContract.State {
        return ProductDetailsContract.State(ProductDetailsContract.ProductDetailsState.Loading)
    }

    override fun handleAction(action: ProductDetailsContract.Action) {
        when (action) {
            is ProductDetailsContract.Action.onFetchProductDetails -> {
                getProductDetails(action.id)
            }
        }
    }


    private fun getProductDetails(id: Int) {
        viewModelScope.launch {
            getProductDetailsUseCase.invoke(id)
                .onStart { emit(Resource.Loading) }
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            // Set State
                            setState { copy(productDetailsState = ProductDetailsContract.ProductDetailsState.Loading) }
                        }
                        is Resource.Success -> {
                            // Set State
                            setState { copy(productDetailsState = ProductDetailsContract.ProductDetailsState.Success(it.data)) }
                        }
                        is Resource.Error -> {
                            // Set State
                            setState { copy(productDetailsState = ProductDetailsContract.ProductDetailsState.Error(it.exception.message)) }
                        }

                        else -> {}
                    }
                }


        }

    }

}