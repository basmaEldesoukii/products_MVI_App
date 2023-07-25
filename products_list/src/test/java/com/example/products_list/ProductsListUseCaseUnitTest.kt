package com.example.products_list

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.common.Resource
import com.example.products_list.domain.GetProductsListUseCase
import com.example.products_list.domain.ProductsListRepositoryContract
import com.example.products_list.utils.TestDataGenerator
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
class ProductsListUseCaseUnitTest {

    @MockK
    private lateinit var repository: ProductsListRepositoryContract

    private lateinit var getProductsListUseCase: GetProductsListUseCase


    @Before
    fun setup(){
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        getProductsListUseCase = GetProductsListUseCase(
            repository = repository,
        )
    }

    @Test
    fun test_get_products_list_success() = runBlockingTest{
        val products = TestDataGenerator.generateProductsList()
        val productsFlow = flowOf(Resource.Success(products))

        // Given
        coEvery { repository.getProductsList() } returns productsFlow

        // When & Assertions
        val result = getProductsListUseCase.invoke()
        result.test {
            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = (expected as Resource.Success).data
            Truth.assertThat(expected).isInstanceOf(Resource.Success::class.java)
            Truth.assertThat(expectedData).isEqualTo(productsFlow.first().data)
            expectComplete()
        }

        // Then
        coVerify { repository.getProductsList() }
    }

    @Test
    fun test_get_products_list_fail() = runBlockingTest{

        val errorFlow = flowOf(Resource.Error(Exception()))

        // Given
        coEvery { repository.getProductsList() } returns errorFlow

        // When & Assertions
        val result = getProductsListUseCase.invoke()
        result.test {
            // Expect Resource.Error
            Truth.assertThat(expectItem()).isInstanceOf(Resource.Error::class.java)
            expectComplete()
        }

        // Then
        coVerify { repository.getProductsList() }
    }


}