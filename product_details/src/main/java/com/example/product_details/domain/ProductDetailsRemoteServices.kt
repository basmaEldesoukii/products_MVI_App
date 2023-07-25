package com.example.product_details.domain

import com.example.product_details.data.ProductDetailsDataModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductDetailsRemoteServices {
    @GET("/products/{productId}")
    suspend fun getProductDetails(@Path("productId")productId: Int): ProductDetailsDataModel
}