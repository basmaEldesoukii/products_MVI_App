package com.example.products_list.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductsListDao {

    @Query("select * from ProductsList")
    fun getProductsList(): List<ProductsListLocalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProducts(products: List<ProductsListLocalEntity>): List<Long>

    @Query("delete from ProductsList")
    fun clearProductsListCash():Int
}