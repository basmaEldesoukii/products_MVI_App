package com.example.product_details.data.remote

import com.example.product_details.data.ProductDetailsDataModel


interface ProductDetailsRemoteDataSourceContract {

    suspend fun getProductDetails(productId: Int): ProductDetailsDataModel

}