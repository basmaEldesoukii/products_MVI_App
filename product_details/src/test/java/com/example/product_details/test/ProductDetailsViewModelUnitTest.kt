package com.example.product_details.test

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.common.Resource
import com.example.product_details.domain.GetProductDetailsUseCase
import com.example.product_details.presentation.ProductDetailsContract
import com.example.product_details.presentation.ProductDetailsViewModel
import com.example.product_details.test.utils.MainCoroutineRule
import com.example.product_details.test.utils.TestDataGenerator
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime


@ExperimentalCoroutinesApi
@ExperimentalTime
@SmallTest
class ProductDetailsViewModelUnitTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var getProductDetailsUseCase: GetProductDetailsUseCase

    private lateinit var productsListViewModel: ProductDetailsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create MainViewModel before every test
        productsListViewModel = ProductDetailsViewModel(
            getProductDetailsUseCase = getProductDetailsUseCase
        )
    }

    @Test
    fun test_fetch_product_details_success() = runBlockingTest {
        val productDetails = TestDataGenerator.generateProductDetailsItem(TestDataGenerator.productId1)
        val productsFlow = flowOf(Resource.Success(productDetails))

        // Given
        coEvery { getProductDetailsUseCase.invoke(TestDataGenerator.productId1) } returns productsFlow

        // When && Assertions
        productsListViewModel.uiState.test {
            productsListViewModel.setAction(ProductDetailsContract.Action.onFetchProductDetails(TestDataGenerator.productId1))
            // Expect Resource.Loading from initial state
            Truth.assertThat(expectItem()).isEqualTo(
                ProductDetailsContract.State(
                    productDetailsState = ProductDetailsContract.ProductDetailsState.Loading
                )
            )
            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = (expected.productDetailsState as ProductDetailsContract.ProductDetailsState.Success).productsDetailsDataModel
            Truth.assertThat(expected).isEqualTo(
                ProductDetailsContract.State(
                    productDetailsState = ProductDetailsContract.ProductDetailsState.Success(productDetails)
                )
            )
            Truth.assertThat(expectedData).isEqualTo(productDetails)

            //Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }

        // Then
        coVerify { getProductDetailsUseCase.invoke(TestDataGenerator.productId1) }
    }

    @Test
    fun test_fetch_product_details_fail() = runBlockingTest {
        val productsErrorFlow = flowOf(Resource.Error(Exception("error")))

        // Given
        coEvery { getProductDetailsUseCase.invoke(TestDataGenerator.productId1) } returns productsErrorFlow

        // When && Assertions (UiState)
        productsListViewModel.uiState.test {
            // Call method inside of this scope
            productsListViewModel.setAction(ProductDetailsContract.Action.onFetchProductDetails(TestDataGenerator.productId1))
            // Expect Resource.Loading from initial state
            Truth.assertThat(expectItem()).isEqualTo(
                ProductDetailsContract.State(
                    productDetailsState = ProductDetailsContract.ProductDetailsState.Loading
                )
            )

            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = (expected.productDetailsState as ProductDetailsContract.ProductDetailsState.Error).errorMsg
            Truth.assertThat(expected).isEqualTo(
                ProductDetailsContract.State(
                    productDetailsState = ProductDetailsContract.ProductDetailsState.Error("error")
                )
            )
            Truth.assertThat(expectedData).isEqualTo("error")
            // Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }
        // Then
        coVerify { getProductDetailsUseCase.invoke(TestDataGenerator.productId1) }
    }
}