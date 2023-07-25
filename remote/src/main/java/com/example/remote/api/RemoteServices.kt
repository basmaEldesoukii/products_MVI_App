package com.example.remote.api

import com.example.product_details.data.ProductDetailsDataModel
import com.example.products_list.data.ProductsListDataModel
import retrofit2.http.GET

interface RemoteServices {
    @GET("/products")
    suspend fun getProductsList(): ProductsListDataModel

    @GET("/products/{productId}")
    suspend fun getProductDetails(): ProductDetailsDataModel

}