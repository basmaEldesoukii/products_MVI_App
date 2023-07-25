package com.example.products_list.data

import com.example.common.Mapper
import com.example.common.Resource
import com.example.products_list.data.local.ProductsListLocalDataSourceContract
import com.example.products_list.data.local.ProductsListLocalEntity
import com.example.products_list.data.remote.ProductsListRemoteDataSourceContract
import com.example.products_list.domain.ProductsListRepositoryContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductsListRepositoryImp @Inject constructor(
    private val localDataSource: ProductsListLocalDataSourceContract,
    private val remoteDataSource: ProductsListRemoteDataSourceContract,
    private val productsListDataMapper: Mapper<ProductsListDataModel, ProductsListLocalEntity>,
): ProductsListRepositoryContract {

    override suspend fun getProductsList(): Flow<Resource<List<ProductsListDataModel>>> {
        return flow {
            try {
                // Get data from RemoteDataSource
                val data = remoteDataSource.getProducts()
                // Save to local
                localDataSource.insertProductsList(productsListDataMapper.fromList(data))
                // Emit data
                emit(Resource.Success(data))
            } catch (ex: Exception) {
                // If remote request fails
                try {
                    // Get data from LocalDataSource
                    val localData = localDataSource.getProductsListFromDataBase()
                    // Emit data
                    emit(Resource.Success(productsListDataMapper.toList(localData)))
                } catch (ex1: Exception) {
                    // Emit error
                    emit(Resource.Error(ex1))
                }
            }

        }
    }
}