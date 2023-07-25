package com.example.products_list

import androidx.test.filters.SmallTest
import com.example.products_list.data.remote.ProductsListRemoteDataSourceContract
import com.example.products_list.data.remote.ProductsListRemoteDataSourceImp
import com.example.products_list.domain.ProductsListRemoteServices
import com.example.products_list.utils.TestDataGenerator
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.lang.Exception

@ExperimentalCoroutinesApi
@SmallTest
class ProductsListRemoteDataSourceImpUnitTest {

    @MockK
    private lateinit var remoteServices: ProductsListRemoteServices

    private lateinit var remoteDataSource: ProductsListRemoteDataSourceImp

    @Before
    fun setup(){
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create RemoteDataSourceImp before every test
        remoteDataSource = ProductsListRemoteDataSourceImp(
            apiService = remoteServices
        )
    }

    @Test
    fun test_get_products_success() = runBlockingTest {
        val productsResponse = TestDataGenerator.generateProductsListResponse()

        // Given
        coEvery { remoteServices.getProductsList() } returns productsResponse

        // When
        val result = remoteDataSource.getProducts()

        // Then
        coVerify { remoteServices.getProductsList() }

        // Assertion
        val expected = productsResponse
        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test(expected = Exception::class)
    fun test_get_products_fail() = runBlockingTest {
        // Given
        coEvery { remoteServices.getProductsList() } throws Exception()

        // When
        remoteDataSource.getProducts()

        // Then
        coVerify { remoteServices.getProductsList() }
    }

}