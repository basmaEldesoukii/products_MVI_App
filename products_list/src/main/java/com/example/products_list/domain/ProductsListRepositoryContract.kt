package com.example.products_list.domain

import com.example.common.Resource
import com.example.products_list.data.ProductsListDataModel
import kotlinx.coroutines.flow.Flow

interface ProductsListRepositoryContract {
    suspend fun getProductsList(): Flow<Resource<List<ProductsListDataModel>>>
}