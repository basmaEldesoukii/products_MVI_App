package com.example.mviapplication.app_component

import com.example.common.Mapper
import com.example.product_details.data.ProductDetailsDataModel
import com.example.product_details.data.remote.ProductDetailsRemoteDataSourceImp
import com.example.product_details.data.local.ProductDetailsLocalDataSourceImp
import com.example.product_details.data.ProductDetailsRepositoryImp
import com.example.product_details.data.local.ProductDetailsDao
import com.example.product_details.data.local.ProductDetailsLocalEntity
import com.example.product_details.domain.ProductDetailsRemoteServices
import com.example.product_details.domain.ProductDetailsRepositoryContract
import com.example.products_list.data.ProductsListDataModel
import com.example.products_list.data.local.ProductsListLocalDataSourceImp
import com.example.products_list.data.remote.ProductsListRemoteDataSourceImp
import com.example.products_list.data.ProductsListRepositoryImp
import com.example.products_list.data.local.ProductsListDao
import com.example.products_list.data.local.ProductsListLocalEntity
import com.example.products_list.domain.ProductsListRemoteServices
import com.example.products_list.domain.ProductsListRepositoryContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideProductsListLocalDataSource(
        productsListDao: ProductsListDao,
    ) = ProductsListLocalDataSourceImp(productsListDao)

    @Singleton
    @Provides
    fun provideProductDetailsLocalDataSource(
        productDetailsDao: ProductDetailsDao,
    ) = ProductDetailsLocalDataSourceImp(productDetailsDao)


    @Singleton
    @Provides
    fun provideProductsListRemoteDataSource(
        apiService: ProductsListRemoteServices,
    ) = ProductsListRemoteDataSourceImp(
        apiService
    )

    @Singleton
    @Provides
    fun provideProductDetailsRemoteDataSource(
        apiService: ProductDetailsRemoteServices,
    ) = ProductDetailsRemoteDataSourceImp(
        apiService
    )

    @Singleton
    @Provides
    fun provideProductsListRepository(
        localDataSource: ProductsListLocalDataSourceImp,
        remoteDataSource: ProductsListRemoteDataSourceImp,
        mapper: Mapper<ProductsListDataModel, ProductsListLocalEntity>
    ): ProductsListRepositoryContract =
        ProductsListRepositoryImp(
            localDataSource,
            remoteDataSource,
            mapper
        )

    @Singleton
    @Provides
    fun provideProductDetailsRepository(
        localDataSource: ProductDetailsLocalDataSourceImp,
        remoteDataSource: ProductDetailsRemoteDataSourceImp,
        mapper: Mapper<ProductDetailsDataModel, ProductDetailsLocalEntity>
    ): ProductDetailsRepositoryContract =
        ProductDetailsRepositoryImp(
            localDataSource,
            remoteDataSource,
            mapper
        )

}