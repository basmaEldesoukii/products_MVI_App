package com.example.products_list.data

import java.io.Serializable

data class ProductsListDataModel(
    var id: Int,
    var title: String,
    var price: String,
    var category: String,
    var description: String,
    var image: String
) : Serializable
