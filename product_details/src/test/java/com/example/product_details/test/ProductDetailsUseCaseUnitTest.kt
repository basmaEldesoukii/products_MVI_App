package com.example.product_details.test

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.common.Resource
import com.example.product_details.domain.GetProductDetailsUseCase
import com.example.product_details.domain.ProductDetailsRepositoryContract
import com.example.product_details.test.utils.TestDataGenerator
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime


@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
class ProductDetailsUseCaseUnitTest {
    @MockK
    private lateinit var repository: ProductDetailsRepositoryContract

    private lateinit var getProductDetailsUseCase: GetProductDetailsUseCase


    @Before
    fun setup(){
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        getProductDetailsUseCase = GetProductDetailsUseCase(
            repository = repository
        )
    }

    @Test
    fun test_get_product_details_success() = runBlockingTest{
        val productDetails = TestDataGenerator.generateProductDetailsItem(TestDataGenerator.productId1)
        val productsFlow = flowOf(Resource.Success(productDetails))

        // Given
        coEvery { repository.getProductDetails(TestDataGenerator.productId1) } returns productsFlow

        // When & Assertions
        val result = getProductDetailsUseCase.invoke(TestDataGenerator.productId1)
        result.test {
            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = (expected as Resource.Success).data
            Truth.assertThat(expected).isInstanceOf(Resource.Success::class.java)
            Truth.assertThat(expectedData).isEqualTo(productsFlow.first().data)
            expectComplete()
        }

        // Then
        coVerify { repository.getProductDetails(TestDataGenerator.productId1) }
    }

    @Test
    fun test_get_product_details_fail() = runBlockingTest{
        val errorFlow = flowOf(Resource.Error(Exception()))

        // Given
        coEvery { repository.getProductDetails(TestDataGenerator.productId1) } returns errorFlow

        // When & Assertions
        val result = getProductDetailsUseCase.invoke(TestDataGenerator.productId1)
        result.test {
            // Expect Resource.Error
            Truth.assertThat(expectItem()).isInstanceOf(Resource.Error::class.java)
            expectComplete()
        }

        // Then
        coVerify { repository.getProductDetails(TestDataGenerator.productId1) }
    }

}