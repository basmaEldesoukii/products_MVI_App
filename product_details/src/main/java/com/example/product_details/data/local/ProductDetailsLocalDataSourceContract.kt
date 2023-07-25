package com.example.product_details.data.local


interface ProductDetailsLocalDataSourceContract {

    suspend fun getProductDetailsByIdFromDataBase(productId: Int): ProductDetailsLocalEntity

    suspend fun insertProductDetails(item: ProductDetailsLocalEntity): Long

    suspend fun clearProductDetailsCashed(): Int
}