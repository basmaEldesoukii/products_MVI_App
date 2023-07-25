package com.example.products_list.domain

import com.example.common.Resource
import com.example.products_list.data.ProductsListDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn


class GetProductsListUseCase(private val repository: ProductsListRepositoryContract) {

    suspend fun invoke(): Flow<Resource<List<ProductsListDataModel>>> {
        return repository.getProductsList()
        //return repository.getProductsList().flowOn()
    }
}

