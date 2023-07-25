package com.example.mviapplication.app_component

import android.content.Context
import androidx.room.Room
import com.example.product_details.data.local.ProductDetailsLocalDatabase
import com.example.products_list.data.local.ProductsListDao
import com.example.products_list.data.local.ProductsListLocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideProductsListDatabase(@ApplicationContext application: Context): ProductsListLocalDatabase {
        return Room
            .databaseBuilder(application, ProductsListLocalDatabase::class.java, "products_list_database")
            .build()
    }

    @Provides
    @Singleton
    fun provideProductsListDao(productsListDataBase: ProductsListLocalDatabase) =
        productsListDataBase.productsListDao()

    @Provides
    @Singleton
    fun provideProductDetailsDatabase(@ApplicationContext application: Context): ProductDetailsLocalDatabase {
        return Room
            .databaseBuilder(application, ProductDetailsLocalDatabase::class.java, "product_details_database")
            .build()
    }

    @Provides
    @Singleton
    fun provideProductDetailsDao(productDetailsDataBase: ProductDetailsLocalDatabase) =
        productDetailsDataBase.productDetailsDao()
}