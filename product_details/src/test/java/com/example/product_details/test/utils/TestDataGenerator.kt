package com.example.product_details.test.utils

import com.example.product_details.data.ProductDetailsDataModel
import com.example.product_details.data.local.ProductDetailsLocalEntity

class TestDataGenerator {
    companion object {

        const val productId1 = 1
        private const val productId2 = 2
        private const val productId3 = 3

        fun generateListOfProductDetailsItem(): List<ProductDetailsDataModel> {
            return listOf(
                generateProductDetailsItem(productId1),
                generateProductDetailsItem(productId2),
                generateProductDetailsItem(productId3)
            )
        }

        fun generateProductDetailsItem(productId: Int): ProductDetailsDataModel {
            return ProductDetailsDataModel(
                id = productId,
                title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
                price = "109.95",
                category = "men's clothing",
                description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
                image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
            )
        }

        fun generateProductDetailsLocalData(): ProductDetailsLocalEntity {
            return ProductDetailsLocalEntity(
                id = productId1,
                title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
                price = "109.95",
                category = "men's clothing",
                description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
                image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
            )

        }
    }
}