package com.example.product_details.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDetailsDao {

    @Query("select * from ProductDetails where id=:id")
    fun getProductByID(id: Int): ProductDetailsLocalEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProductDetails(product: ProductDetailsLocalEntity): Long

    @Query("delete from ProductDetails")
    fun clearProductDetailsCash():Int
}