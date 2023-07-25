package com.example.products_list.data.remote

import com.example.products_list.data.ProductsListDataModel

interface ProductsListRemoteDataSourceContract {

    suspend fun getProducts(): List<ProductsListDataModel>

}