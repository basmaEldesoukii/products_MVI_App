package com.example.mviapplication.app_component

import com.example.common.Mapper
import com.example.product_details.data.ProductDetailsDataMapper
import com.example.product_details.data.ProductDetailsDataModel
import com.example.product_details.data.local.ProductDetailsLocalEntity
import com.example.products_list.data.ProductsListDataMapper
import com.example.products_list.data.ProductsListDataModel
import com.example.products_list.data.local.ProductsListLocalEntity
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    //region Locale Mappers
    @Binds
    abstract fun bindsLocalProductsListDataMapperMapper(mapper: ProductsListDataMapper): Mapper<ProductsListDataModel, ProductsListLocalEntity>

    @Binds
    abstract fun bindsLocalProductDetailsItemMapper(mapper: ProductDetailsDataMapper): Mapper<ProductDetailsDataModel, ProductDetailsLocalEntity>

    //endregion
}