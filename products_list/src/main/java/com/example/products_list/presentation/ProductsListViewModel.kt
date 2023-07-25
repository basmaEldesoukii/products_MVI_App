package com.example.products_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.BaseViewModel
import com.example.common.Resource
import com.example.products_list.data.ProductsListDataModel
import com.example.products_list.domain.GetProductsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ProductsListViewModel @Inject constructor(
    private val getProductsListUseCase: GetProductsListUseCase)
    : BaseViewModel<ProductsListContract.Action, ProductsListContract.State>() {

   /* private val _uiState : MutableStateFlow<ProductsListContract.ProductsListState> = MutableStateFlow(ProductsListContract.ProductsListState.Loading)
    val uiState = _uiState.asStateFlow()*/

    override fun createInitialState(): ProductsListContract.State {
        return ProductsListContract.State(ProductsListContract.ProductsListState.Loading)
    }


    override fun handleAction(action: ProductsListContract.Action) {
        when (action) {
            is ProductsListContract.Action.onFetchProductsList -> {
                getProductsList()
            }
            is ProductsListContract.Action.onProductClicked -> {
                val item = action.productsListDataModel
                setSelectedProduct(item)
            }
        }
    }

    private fun getProductsList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
            getProductsListUseCase.invoke()
                .onStart { emit(Resource.Loading) }
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            // Set State
                           // _uiState.update { ProductsListContract.ProductsListState.Loading }
                            setState { copy(productsListState = ProductsListContract.ProductsListState.Loading) }
                        }
                        is Resource.Success -> {
                            // Set State
                            setState { copy(productsListState = ProductsListContract.ProductsListState.Success(it.data)) }
                        }
                        is Resource.Error -> {
                            setState { copy(productsListState = ProductsListContract.ProductsListState.Error(it.exception.message)) }
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    /**
     * Set selected post item
     */
    private fun setSelectedProduct(product: ProductsListDataModel?) {
        // Set State
        setState { copy(selectedProduct = product) }
    }
}