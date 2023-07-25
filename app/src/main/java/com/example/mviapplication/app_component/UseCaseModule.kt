package com.example.mviapplication.app_component

import com.example.product_details.domain.GetProductDetailsUseCase
import com.example.product_details.domain.ProductDetailsRepositoryContract
import com.example.products_list.domain.GetProductsListUseCase
import com.example.products_list.domain.ProductsListRepositoryContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetProductDetailsUseCase(
        productDetailsRepository: ProductDetailsRepositoryContract,
    ): GetProductDetailsUseCase =
        GetProductDetailsUseCase(productDetailsRepository)


    @Singleton
    @Provides
    fun provideGetProductsListUseCase(
        productsListRepository: ProductsListRepositoryContract,
    ): GetProductsListUseCase =
        GetProductsListUseCase(productsListRepository)

}