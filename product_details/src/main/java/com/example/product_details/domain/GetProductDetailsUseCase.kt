package com.example.product_details.domain

import com.example.common.Resource
import com.example.product_details.data.ProductDetailsDataModel
import kotlinx.coroutines.flow.Flow

class GetProductDetailsUseCase(val repository: ProductDetailsRepositoryContract) {

    suspend fun invoke(id: Int): Flow<Resource<ProductDetailsDataModel>> {
        return repository.getProductDetails(id)
    }
}