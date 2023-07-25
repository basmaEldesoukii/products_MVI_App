package com.example.product_details.data.remote

import com.example.product_details.data.ProductDetailsDataModel
import com.example.product_details.domain.ProductDetailsRemoteServices
import javax.inject.Inject

class ProductDetailsRemoteDataSourceImp @Inject constructor(
    private val apiService: ProductDetailsRemoteServices,
) : ProductDetailsRemoteDataSourceContract {

    override suspend fun getProductDetails(productId: Int): ProductDetailsDataModel {
        val networkData = apiService.getProductDetails(productId)
        return networkData
    }
}