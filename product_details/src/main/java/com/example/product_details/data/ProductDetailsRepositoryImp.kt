package com.example.product_details.data

import com.example.common.Mapper
import com.example.common.Resource
import com.example.product_details.data.local.ProductDetailsLocalDataSourceContract
import com.example.product_details.data.local.ProductDetailsLocalEntity
import com.example.product_details.data.remote.ProductDetailsRemoteDataSourceContract
import com.example.product_details.domain.ProductDetailsRepositoryContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductDetailsRepositoryImp @Inject constructor(
    private val localDataSource: ProductDetailsLocalDataSourceContract,
    private val remoteDataSource: ProductDetailsRemoteDataSourceContract,
    private val productDetailsDataMapper: Mapper<ProductDetailsDataModel, ProductDetailsLocalEntity>,
): ProductDetailsRepositoryContract {

    override suspend fun getProductDetails(id: Int): Flow<Resource<ProductDetailsDataModel>> {
        return flow {
            try {
                // Get data from RemoteDataSource
                val product = remoteDataSource.getProductDetails(id)
                // Save to local
                localDataSource.insertProductDetails(productDetailsDataMapper.from(product))
                // Emit data
                emit(Resource.Success(product))
            } catch (ex: Exception) {
                // If remote request fails
                try {
                    // Get data from LocalDataSource
                    val localData = localDataSource.getProductDetailsByIdFromDataBase(id)
                    // Emit data
                    emit(Resource.Success(productDetailsDataMapper.to(localData)))
                } catch (ex1: Exception) {
                    // Emit error
                    emit(Resource.Error(ex1))
                }
            }
        }
    }
}