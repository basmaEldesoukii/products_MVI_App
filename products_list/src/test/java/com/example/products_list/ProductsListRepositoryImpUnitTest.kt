package com.example.products_list

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.common.Mapper
import com.example.common.Resource
import com.example.products_list.data.ProductsListDataMapper
import com.example.products_list.data.ProductsListDataModel
import com.example.products_list.data.ProductsListRepositoryImp
import com.example.products_list.data.local.ProductsListLocalDataSourceContract
import com.example.products_list.data.local.ProductsListLocalEntity
import com.example.products_list.data.remote.ProductsListRemoteDataSourceContract
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
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
@SmallTest
class ProductsListRepositoryImpUnitTest {

    @MockK
    private lateinit var localDataSourceContract: ProductsListLocalDataSourceContract

    @MockK
    private lateinit var remoteDataSourceContract: ProductsListRemoteDataSourceContract

    private val productsListDataMapper = ProductsListDataMapper()

    private lateinit var repository: ProductsListRepositoryImp

    @Before
    fun setup(){
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create RepositoryImp before every test
        repository = ProductsListRepositoryImp(
            localDataSource = localDataSourceContract,
            remoteDataSource = remoteDataSourceContract,
            productsListDataMapper = productsListDataMapper
        )
    }

    @Test
    fun test_get_products_from_remote_success() = runBlockingTest{
        val products = TestDataGenerator.generateProductsList()
        val affectedIds = MutableList(products.size) { index -> index.toLong() }

        // Given
        coEvery { remoteDataSourceContract.getProducts() } returns products
        coEvery { localDataSourceContract.insertProductsList(productsListDataMapper.fromList(products)) } returns affectedIds
        coEvery { localDataSourceContract.getProductsListFromDataBase() } returns productsListDataMapper.fromList(products)

        // When & Assertions
        val flow = repository.getProductsList()
        flow.test {
            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = (expected as Resource.Success).data
            Truth.assertThat(expected).isInstanceOf(Resource.Success::class.java)
            Truth.assertThat(expectedData[0].id).isEqualTo(products[0].id)
            expectComplete()
        }

        // Then
        coVerify { remoteDataSourceContract.getProducts() }
        coVerify { localDataSourceContract.insertProductsList(productsListDataMapper.fromList(products)) }
    }

    @Test
    fun test_get_products_from_local_when_remote_fail() = runBlockingTest {
        val products = TestDataGenerator.generateProductsList()

        // Given
        coEvery { remoteDataSourceContract.getProducts() } throws Exception()
        coEvery { localDataSourceContract.getProductsListFromDataBase() } returns productsListDataMapper.fromList(products)

        // When && Assertions
        val flow = repository.getProductsList()
        flow.test {
            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = (expected as Resource.Success).data
            Truth.assertThat(expected).isInstanceOf(Resource.Success::class.java)
            Truth.assertThat(expectedData[0].id).isEqualTo(products[0].id)
            expectComplete()
        }

        // Then
        coVerify { remoteDataSourceContract.getProducts() }
        coVerify { localDataSourceContract.getProductsListFromDataBase() }
    }

    @Test
    fun test_for_error_state_when_remote_and_local_fail_of_getting_products() = runBlockingTest {
        // Given
        coEvery { remoteDataSourceContract.getProducts() } throws Exception()
        coEvery { localDataSourceContract.getProductsListFromDataBase() } throws Exception()

        // When && Assertions
        val flow = repository.getProductsList()
        flow.test {
            // Expect Resource.Error
            Truth.assertThat(expectItem()).isInstanceOf(Resource.Error::class.java)
            expectComplete()
        }

        // Then
        coVerify { remoteDataSourceContract.getProducts() }
        coVerify { localDataSourceContract.getProductsListFromDataBase() }
    }
}