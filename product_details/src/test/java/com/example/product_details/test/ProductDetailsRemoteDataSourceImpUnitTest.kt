package com.example.product_details.test

import androidx.test.filters.SmallTest
import com.example.product_details.data.remote.ProductDetailsRemoteDataSourceImp
import com.example.product_details.domain.ProductDetailsRemoteServices
import com.example.product_details.test.utils.TestDataGenerator
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
class ProductDetailsRemoteDataSourceImpUnitTest {

    @MockK
    private lateinit var remoteServices: ProductDetailsRemoteServices

    private lateinit var remoteDataSource: ProductDetailsRemoteDataSourceImp

    @Before
    fun setup(){
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create RemoteDataSourceImp before every test
        remoteDataSource = ProductDetailsRemoteDataSourceImp(
            apiService = remoteServices
        )
    }

    @Test
    fun test_get_product_details_success() = runBlockingTest {
        val productDetailsResponse = TestDataGenerator.generateProductDetailsItem(TestDataGenerator.productId1)

        // Given
        coEvery { remoteServices.getProductDetails(TestDataGenerator.productId1) } returns productDetailsResponse

        // When
        val result = remoteDataSource.getProductDetails(TestDataGenerator.productId1)

        // Then
        coVerify { remoteServices.getProductDetails(TestDataGenerator.productId1) }

        // Assertion
        val expected = productDetailsResponse
        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test(expected = Exception::class)
    fun test_get_product_details_fail() = runBlockingTest {
        // Given
        coEvery { remoteServices.getProductDetails(TestDataGenerator.productId1) } throws Exception()

        // When
        remoteDataSource.getProductDetails(TestDataGenerator.productId1)

        // Then
        coVerify { remoteServices.getProductDetails(TestDataGenerator.productId1) }
    }
}