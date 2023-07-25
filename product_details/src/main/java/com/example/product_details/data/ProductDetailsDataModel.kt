package com.example.product_details.data

import java.io.Serializable

data class ProductDetailsDataModel(
    var id: Int,
    var title: String,
    var price: String,
    var category: String,
    var description: String,
    var image: String
    ): Serializable
