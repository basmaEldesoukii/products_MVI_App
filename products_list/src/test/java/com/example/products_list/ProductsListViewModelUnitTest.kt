package com.example.products_list

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.common.Resource
import com.example.products_list.domain.GetProductsListUseCase
import com.example.products_list.presentation.ProductsListContract
import com.example.products_list.presentation.ProductsListViewModel
import com.example.products_list.utils.MainCoroutineRule
import com.example.products_list.utils.TestDataGenerator
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
class ProductsListViewModelUnitTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var getProductsListUseCase: GetProductsListUseCase

    private lateinit var productsListViewModel: ProductsListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create MainViewModel before every test
        productsListViewModel = ProductsListViewModel(
            getProductsListUseCase = getProductsListUseCase
        )
    }

    @Test
    fun test_fetch_products_list_success() = runBlockingTest {
        val products = TestDataGenerator.generateProductsList()
        val productsFlow = flowOf(Resource.Success(products))

        // Given
        coEvery { getProductsListUseCase.invoke() } returns productsFlow

        // When && Assertions
        productsListViewModel.uiState.test {
            productsListViewModel.setAction(ProductsListContract.Action.onFetchProductsList)
            // Expect Resource.Loading from initial state
            Truth.assertThat(expectItem()).isEqualTo(
                ProductsListContract.State(
                    productsListState = ProductsListContract.ProductsListState.Loading,
                    selectedProduct = null
                )
            )
            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = (expected.productsListState as ProductsListContract.ProductsListState.Success).productsListData
            Truth.assertThat(expected).isEqualTo(
                ProductsListContract.State(
                    productsListState = ProductsListContract.ProductsListState.Success(products),
                    selectedProduct = null
                )
            )
            Truth.assertThat(expectedData).containsExactlyElementsIn(products)

            //Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }

        // Then
        coVerify { getProductsListUseCase.invoke() }
    }

    @Test
    fun test_fetch_products_list_fail() = runBlockingTest {
        val productsErrorFlow = flowOf(Resource.Error(Exception("error")))

        // Given
        coEvery { getProductsListUseCase.invoke() } returns productsErrorFlow

        // When && Assertions (UiState)
        productsListViewModel.uiState.test {
            // Call method inside of this scope
            productsListViewModel.setAction(ProductsListContract.Action.onFetchProductsList)
            // Expect Resource.Loading from initial state
            Truth.assertThat(expectItem()).isEqualTo(
                ProductsListContract.State(
                    productsListState = ProductsListContract.ProductsListState.Loading,
                    selectedProduct = null
                )
            )

            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = (expected.productsListState as ProductsListContract.ProductsListState.Error).errorMsg
            Truth.assertThat(expected).isEqualTo(
                ProductsListContract.State(
                    productsListState = ProductsListContract.ProductsListState.Error("error"),
                    selectedProduct = null
                )
            )
            Truth.assertThat(expectedData).isEqualTo("error")
            // Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }
        // Then
        coVerify { getProductsListUseCase.invoke() }
    }

    @Test
    fun test_select_product() = runBlockingTest {
        val productItem = TestDataGenerator.generateProductItem(TestDataGenerator.productId1)

        // Given (no-op)

        // When && Assertions
        productsListViewModel.uiState.test {
            // Call method inside of this scope
            productsListViewModel.setAction(ProductsListContract.Action.onProductClicked(productsListDataModel = productItem))
            // Expect Resource.Loading from initial state
            Truth.assertThat(expectItem()).isEqualTo(
                ProductsListContract.State(
                    productsListState = ProductsListContract.ProductsListState.Loading,
                    selectedProduct = null
                )
            )
            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = expected.selectedProduct

            Truth.assertThat(expected).isEqualTo(
                ProductsListContract.State(
                    productsListState = ProductsListContract.ProductsListState.Loading,
                    selectedProduct = productItem
                )
            )
            Truth.assertThat(expectedData).isEqualTo(productItem)
            // Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }
        // Then (no-op)
    }
}