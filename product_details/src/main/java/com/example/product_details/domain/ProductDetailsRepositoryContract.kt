package com.example.product_details.domain

import com.example.common.Resource
import com.example.product_details.data.ProductDetailsDataModel
import kotlinx.coroutines.flow.Flow


interface ProductDetailsRepositoryContract {
    suspend fun getProductDetails(id: Int): Flow<Resource<ProductDetailsDataModel>>
}