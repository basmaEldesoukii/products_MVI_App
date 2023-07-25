package com.example.products_list.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductsListLocalEntity::class], version = 1, exportSchema = false)
abstract class ProductsListLocalDatabase: RoomDatabase() {

    abstract fun productsListDao(): ProductsListDao
}