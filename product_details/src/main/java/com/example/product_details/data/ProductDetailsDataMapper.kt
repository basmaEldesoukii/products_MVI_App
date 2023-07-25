package com.example.product_details.data

import com.example.common.Mapper
import com.example.product_details.data.local.ProductDetailsLocalEntity
import javax.inject.Inject

class ProductDetailsDataMapper @Inject constructor() :
    Mapper<ProductDetailsDataModel, ProductDetailsLocalEntity> {

    override fun from(i: ProductDetailsDataModel?): ProductDetailsLocalEntity {
        return ProductDetailsLocalEntity(
            id = i?.id ?: 0,
            title = i?.title ?: "",
            price = i?.price ?: "",
            category = i?.category ?: "",
            description = i?.description ?: "",
            image = i?.image ?: ""
        )
    }

    override fun to(o: ProductDetailsLocalEntity?): ProductDetailsDataModel {
        return ProductDetailsDataModel(
            id = o?.id ?: 0,
            title = o?.title ?: "",
            price = o?.price ?: "",
            category = o?.category ?: "",
            description = o?.description ?: "",
            image = o?.image ?: ""
        )
    }
}