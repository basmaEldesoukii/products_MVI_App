package com.example.product_details.presentation

import com.example.base.interfaces.UiAction
import com.example.base.interfaces.UiState
import com.example.product_details.data.ProductDetailsDataModel

open class ProductDetailsContract {

    sealed class Action: UiAction {
        data class onFetchProductDetails(val id: Int): Action()
    }

    data class State(val productDetailsState: ProductDetailsState) : UiState

    sealed class ProductDetailsState {
        object Loading: ProductDetailsState()
        data class Success(val productsDetailsDataModel: ProductDetailsDataModel): ProductDetailsState()
        data class Error(val errorMsg: String?): ProductDetailsState()
    }
}