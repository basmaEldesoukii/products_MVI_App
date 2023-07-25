package com.example.product_details.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductDetailsLocalEntity::class], version = 1, exportSchema = false)
abstract class ProductDetailsLocalDatabase: RoomDatabase() {

    abstract fun productDetailsDao(): ProductDetailsDao
}