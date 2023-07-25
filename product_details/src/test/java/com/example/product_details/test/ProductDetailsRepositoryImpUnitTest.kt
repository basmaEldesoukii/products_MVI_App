package com.example.product_details.test

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.common.Resource
import com.example.product_details.data.ProductDetailsDataMapper
import com.example.product_details.data.ProductDetailsRepositoryImp
import com.example.product_details.data.local.ProductDetailsLocalDataSourceContract
import com.example.product_details.data.remote.ProductDetailsRemoteDataSourceContract
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
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
@SmallTest
class ProductDetailsRepositoryImpUnitTest {

    @MockK
    private lateinit var localDataSourceContract: ProductDetailsLocalDataSourceContract

    @MockK
    private lateinit var remoteDataSourceContract: ProductDetailsRemoteDataSourceContract

    private val productDetailsDataMapper = ProductDetailsDataMapper()

    private lateinit var repository: ProductDetailsRepositoryImp

    @Before
    fun setup(){
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create RepositoryImp before every test
        repository = ProductDetailsRepositoryImp(
            localDataSource = localDataSourceContract,
            remoteDataSource = remoteDataSourceContract,
            productDetailsDataMapper = productDetailsDataMapper
        )
    }

    @Test
    fun test_get_product_details_from_remote_success() = runBlockingTest{
        val productDetails = TestDataGenerator.generateProductDetailsItem(TestDataGenerator.productId1)
        val affectedIds = 1L

        // Given
        coEvery { remoteDataSourceContract.getProductDetails(TestDataGenerator.productId1) } returns productDetails
        coEvery { localDataSourceContract.insertProductDetails(productDetailsDataMapper.from(productDetails)) } returns affectedIds
        coEvery { localDataSourceContract.getProductDetailsByIdFromDataBase(TestDataGenerator.productId1) } returns productDetailsDataMapper.from(productDetails)

        // When & Assertions
        val flow = repository.getProductDetails(TestDataGenerator.productId1)
        flow.test {
            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = (expected as Resource.Success).data
            Truth.assertThat(expected).isInstanceOf(Resource.Success::class.java)
            Truth.assertThat(expectedData.id).isEqualTo(productDetails.id)
            expectComplete()
        }

        // Then
        coVerify { remoteDataSourceContract.getProductDetails(TestDataGenerator.productId1) }
        coVerify { localDataSourceContract.insertProductDetails(productDetailsDataMapper.from(productDetails)) }
    }

    @Test
    fun test_get_product_details_from_local_when_remote_fail() = runBlockingTest {
        val productDetails = TestDataGenerator.generateProductDetailsItem(TestDataGenerator.productId1)

        // Given
        coEvery { remoteDataSourceContract.getProductDetails(TestDataGenerator.productId1) } throws Exception()
        coEvery { localDataSourceContract.getProductDetailsByIdFromDataBase(TestDataGenerator.productId1) } returns productDetailsDataMapper.from(productDetails)

        // When && Assertions
        val flow = repository.getProductDetails(TestDataGenerator.productId1)
        flow.test {
            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = (expected as Resource.Success).data
            Truth.assertThat(expected).isInstanceOf(Resource.Success::class.java)
            Truth.assertThat(expectedData.id).isEqualTo(productDetails.id)
            expectComplete()
        }

        // Then
        coVerify { remoteDataSourceContract.getProductDetails(TestDataGenerator.productId1) }
        coVerify { localDataSourceContract.getProductDetailsByIdFromDataBase(TestDataGenerator.productId1) }
    }

    @Test
    fun test_for_error_state_when_remote_and_local_fail_of_getting_products() = runBlockingTest {
        // Given
        coEvery { remoteDataSourceContract.getProductDetails(TestDataGenerator.productId1) } throws Exception()
        coEvery { localDataSourceContract.getProductDetailsByIdFromDataBase(TestDataGenerator.productId1) } throws Exception()

        // When && Assertions
        val flow = repository.getProductDetails(TestDataGenerator.productId1)
        flow.test {
            // Expect Resource.Error
            Truth.assertThat(expectItem()).isInstanceOf(Resource.Error::class.java)
            expectComplete()
        }

        // Then
        coVerify { remoteDataSourceContract.getProductDetails(TestDataGenerator.productId1) }
        coVerify { localDataSourceContract.getProductDetailsByIdFromDataBase(TestDataGenerator.productId1) }
    }
}