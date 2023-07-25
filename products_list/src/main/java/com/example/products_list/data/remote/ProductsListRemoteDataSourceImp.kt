package com.example.products_list.data.remote

import com.example.products_list.data.ProductsListDataModel
import com.example.products_list.domain.ProductsListRemoteServices
import javax.inject.Inject

class ProductsListRemoteDataSourceImp @Inject constructor(
    private val apiService: ProductsListRemoteServices
) : ProductsListRemoteDataSourceContract {


    override suspend fun getProducts(): List<ProductsListDataModel> {
        return apiService.getProductsList()
    }
}