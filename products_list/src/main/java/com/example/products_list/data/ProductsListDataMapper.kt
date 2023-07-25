package com.example.products_list.data

import com.example.common.Mapper
import com.example.products_list.data.local.ProductsListLocalEntity
import javax.inject.Inject

class ProductsListDataMapper @Inject constructor() :
    Mapper<ProductsListDataModel, ProductsListLocalEntity> {

    override fun from(i: ProductsListDataModel?): ProductsListLocalEntity {
        return ProductsListLocalEntity(
            id = i?.id ?: 0,
            title = i?.title ?: "",
            price = i?.price ?: "",
            category = i?.category ?: "",
            description = i?.description ?: "",
            image = i?.image ?: ""
        )
    }

    override fun to(o: ProductsListLocalEntity?): ProductsListDataModel {
        return ProductsListDataModel(
            id = o?.id ?: 0,
            title = o?.title ?: "",
            price = o?.price ?: "",
            category = o?.category ?: "",
            description = o?.description ?: "",
            image = o?.image ?: ""
        )
    }
}