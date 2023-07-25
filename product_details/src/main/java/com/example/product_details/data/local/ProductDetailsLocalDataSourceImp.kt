package com.example.product_details.data.local

import javax.inject.Inject

class ProductDetailsLocalDataSourceImp @Inject constructor(
    private val productDetailsDao: ProductDetailsDao,
) : ProductDetailsLocalDataSourceContract {

    override suspend fun getProductDetailsByIdFromDataBase(productId: Int): ProductDetailsLocalEntity {
        return productDetailsDao.getProductByID(productId)
    }

    override suspend fun insertProductDetails(item: ProductDetailsLocalEntity): Long {
        return productDetailsDao.addProductDetails(item)
    }

    override suspend fun clearProductDetailsCashed(): Int {
        return productDetailsDao.clearProductDetailsCash()
    }
}