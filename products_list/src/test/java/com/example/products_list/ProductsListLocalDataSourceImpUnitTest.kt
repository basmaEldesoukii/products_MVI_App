package com.example.products_list

import androidx.test.filters.SmallTest
import com.example.products_list.data.ProductsListDataMapper
import com.example.products_list.data.local.ProductsListDao
import com.example.products_list.data.local.ProductsListLocalDataSourceImp
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
class ProductsListLocalDataSourceImpUnitTest {

    @MockK
    private lateinit var productsListDao: ProductsListDao

    private lateinit var localDataSourceImp: ProductsListLocalDataSourceImp

    @Before
    fun setup(){
        MockKAnnotations.init(this, relaxUnitFun = true)
        localDataSourceImp = ProductsListLocalDataSourceImp(
            productsListDao = productsListDao
        )
    }

    @Test
    fun test_add_products_success() = runBlockingTest {
        val localProducts = TestDataGenerator.generateListOfLocalProductItem()
        val expected = MutableList(localProducts.size) { index -> index.toLong() }

        // Given
        coEvery { productsListDao.addProducts(any()) } returns expected

        // When
        val returned = localDataSourceImp.insertProductsList(localProducts)

        // Then
        coVerify { productsListDao.addProducts(any()) }

        // Assertion
        Truth.assertThat(returned).hasSize(expected.size)
    }

    @Test(expected = Exception::class)
    fun test_add_products_fail() = runBlockingTest {
        val localProducts = TestDataGenerator.generateListOfLocalProductItem()

        // Given
        coEvery { productsListDao.addProducts(any()) } throws Exception()

        // When
        localDataSourceImp.insertProductsList(localProducts)

        // Then
        coVerify { productsListDao.addProducts(any()) }
    }

    @Test
    fun test_get_products_success() = runBlockingTest {
        val expected = TestDataGenerator.generateListOfLocalProductItem()

        // Given
        coEvery { productsListDao.getProductsList() } returns expected

        // When
        val returned = localDataSourceImp.getProductsListFromDataBase()

        // Then
        coVerify { productsListDao.getProductsList() }

        // Assertion
        Truth.assertThat(returned).containsExactlyElementsIn(expected)
    }

    @Test(expected = Exception::class)
    fun test_get_products_fail() = runBlockingTest {
        // Given
        coEvery { productsListDao.getProductsList() } throws Exception()

        // When
        localDataSourceImp.getProductsListFromDataBase()

        // Then
        coVerify { productsListDao.getProductsList() }
    }

    @Test
    fun test_clear_all_products_success() = runBlockingTest {
        val localProducts = TestDataGenerator.generateListOfLocalProductItem()
        val expected = localProducts.size // Affected row count

        // Given
        coEvery { productsListDao.clearProductsListCash() } returns expected

        // When
        val returned = localDataSourceImp.clearProductsListCashed()

        // Then
        coVerify { productsListDao.clearProductsListCash() }

        // Assertion
        Truth.assertThat(returned).isEqualTo(expected)
    }

    @Test(expected = Exception::class)
    fun test_clear_all_products_fail() = runBlockingTest {
        // Given
        coEvery { productsListDao.clearProductsListCash() } throws Exception()

        // When
        localDataSourceImp.clearProductsListCashed()

        // Then
        coVerify { productsListDao.clearProductsListCash() }
    }

}