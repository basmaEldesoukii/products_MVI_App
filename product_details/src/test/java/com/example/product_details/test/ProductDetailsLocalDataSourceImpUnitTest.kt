package com.example.product_details.test

import androidx.test.filters.SmallTest
import com.example.product_details.data.local.ProductDetailsDao
import com.example.product_details.data.local.ProductDetailsLocalDataSourceImp
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
class ProductDetailsLocalDataSourceImpUnitTest {
    @MockK
    private lateinit var productDetailsDao: ProductDetailsDao

    private lateinit var localDataSourceImp: ProductDetailsLocalDataSourceImp

    @Before
    fun setup(){
        MockKAnnotations.init(this, relaxUnitFun = true)
        localDataSourceImp = ProductDetailsLocalDataSourceImp(
            productDetailsDao = productDetailsDao
        )
    }

    @Test
    fun test_add_product_details_success() = runBlockingTest {
        val localProducts = TestDataGenerator.generateProductDetailsLocalData()
        val expected = 1L

        // Given
        coEvery { productDetailsDao.addProductDetails(any()) } returns expected

        // When
        val returned = localDataSourceImp.insertProductDetails(localProducts)

        // Then
        coVerify { productDetailsDao.addProductDetails(any()) }

        // Assertion
        Truth.assertThat(returned).isEqualTo(expected)
    }

    @Test(expected = Exception::class)
    fun test_add_product_details_fail() = runBlockingTest {
        val localProducts = TestDataGenerator.generateProductDetailsLocalData()

        // Given
        coEvery { productDetailsDao.addProductDetails(any()) } throws Exception()

        // When
        localDataSourceImp.insertProductDetails(localProducts)

        // Then
        coVerify { productDetailsDao.addProductDetails(any()) }
    }

    @Test
    fun test_get_product_details_success() = runBlockingTest {
        val productDetails = TestDataGenerator.generateProductDetailsLocalData()
        val expected = productDetails

        // Given
        coEvery { productDetailsDao.getProductByID(any()) } returns expected

        // When
        val returned = localDataSourceImp.getProductDetailsByIdFromDataBase(productDetails.id)

        // Then
        coVerify { productDetailsDao.getProductByID(any()) }

        // Assertion
        Truth.assertThat(returned).isEqualTo(expected)
    }

    @Test(expected = Exception::class)
    fun test_get_product_details_fail() = runBlockingTest {
        val productDetails = TestDataGenerator.generateProductDetailsLocalData()

        // Given
        coEvery { productDetailsDao.getProductByID(any()) } throws Exception()

        // When
        localDataSourceImp.getProductDetailsByIdFromDataBase(productDetails.id)

        // Then
        coVerify { productDetailsDao.getProductByID(any()) }
    }

    @Test
    fun test_clear_all_product_details_success() = runBlockingTest {
        val localProducts = TestDataGenerator.generateListOfProductDetailsItem()
        val expected = localProducts.size // Affected row count

        // Given
        coEvery { productDetailsDao.clearProductDetailsCash() } returns expected

        // When
        val returned = localDataSourceImp.clearProductDetailsCashed()

        // Then
        coVerify { productDetailsDao.clearProductDetailsCash() }

        // Assertion
        Truth.assertThat(returned).isEqualTo(expected)
    }

    @Test(expected = Exception::class)
    fun test_clear_all_product_details_fail() = runBlockingTest {
        // Given
        coEvery { productDetailsDao.clearProductDetailsCash() } throws Exception()

        // When
        localDataSourceImp.clearProductDetailsCashed()

        // Then
        coVerify { productDetailsDao.clearProductDetailsCash() }
    }
}