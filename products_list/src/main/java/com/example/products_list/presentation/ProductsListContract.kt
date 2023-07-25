package com.example.products_list.presentation

import com.example.base.interfaces.UiAction
import com.example.base.interfaces.UiState
import com.example.products_list.data.ProductsListDataModel

class ProductsListContract {

    sealed class Action: UiAction {
        object onFetchProductsList: Action()
        data class onProductClicked(val productsListDataModel: ProductsListDataModel): Action()
    }

    data class State (val productsListState: ProductsListState,
                      val selectedProduct: ProductsListDataModel? = null
    ): UiState

    sealed class ProductsListState {
        object Loading: ProductsListState()
        data class Success(val productsListData: List<ProductsListDataModel>): ProductsListState()
        data class Error(val errorMsg: String?): ProductsListState()
    }
}