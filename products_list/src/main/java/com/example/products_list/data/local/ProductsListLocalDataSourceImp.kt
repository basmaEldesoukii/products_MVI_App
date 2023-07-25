package com.example.products_list.data.local

import javax.inject.Inject

class ProductsListLocalDataSourceImp @Inject constructor(
    private val productsListDao: ProductsListDao,
) : ProductsListLocalDataSourceContract {

    override suspend fun getProductsListFromDataBase(): List<ProductsListLocalEntity> {
        return productsListDao.getProductsList()
    }

    override suspend fun insertProductsList(data: List<ProductsListLocalEntity>): List<Long> {
        return productsListDao.addProducts(data)
    }

    override suspend fun clearProductsListCashed(): Int {
        return productsListDao.clearProductsListCash()
    }
}