package com.example.products_list.utils

import com.example.products_list.data.ProductsListDataModel
import com.example.products_list.data.local.ProductsListLocalEntity

class TestDataGenerator {

    companion object{

        const val productId1 = 1
        private const val productId2 = 2
        private const val productId3 = 3


        // Data for UseCase Test
        fun generateProductsList(): List<ProductsListDataModel> {
            return listOf(
                generateProductItem(productId1),
                generateProductItem(productId2),
                generateProductItem(productId3)
            )
        }

        fun generateProductItem(productId: Int): ProductsListDataModel{
            return ProductsListDataModel(
                id = productId,
                title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
                price = "109.95",
                category = "men's clothing",
                description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
                image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
            )
        }
        //endOfRegion

        //Data for LocalData Test
        fun generateListOfLocalProductItem(): List<ProductsListLocalEntity> {
            return listOf(
                generateLocalProductItem(productId1),
                generateLocalProductItem(productId2),
                generateLocalProductItem(productId3)
            )
        }

        fun generateLocalProductItem(id: Int): ProductsListLocalEntity {
            return ProductsListLocalEntity(
                id = id,
                title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
                price = "109.95",
                category = "men's clothing",
                description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
                image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
            )
        }
        //endOfRegion

        //Data for RemoteData Test
        fun generateProductsListResponse(): List<ProductsListDataModel> {
            return listOf(generateProductsListItem())
        }

        fun generateProductsListItem(): ProductsListDataModel {
            return ProductsListDataModel(
                id = 1,
                title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
                price = "109.95",
                category = "men's clothing",
                description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
                image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
            )
        }
        //endOfRegion
    }
}