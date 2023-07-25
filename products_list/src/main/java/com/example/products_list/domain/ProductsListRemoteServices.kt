package com.example.products_list.domain

import com.example.products_list.data.ProductsListDataModel
import retrofit2.http.GET

interface ProductsListRemoteServices {
    @GET("/products")
    suspend fun getProductsList(): List<ProductsListDataModel>
}