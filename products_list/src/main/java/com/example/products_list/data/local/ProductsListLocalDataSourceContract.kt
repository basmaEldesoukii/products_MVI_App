package com.example.products_list.data.local


interface ProductsListLocalDataSourceContract {

    suspend fun getProductsListFromDataBase(): List<ProductsListLocalEntity>

    suspend fun insertProductsList(data: List<ProductsListLocalEntity>): List<Long>

    suspend fun clearProductsListCashed(): Int

}