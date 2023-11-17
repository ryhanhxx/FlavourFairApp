package com.ch.flavourfair.data.repository

import app.cash.turbine.test
import com.ch.flavourfair.data.network.api.datasource.FlavourfairDataSource
import com.ch.flavourfair.data.network.api.model.category.CategoriesResponse
import com.ch.flavourfair.data.network.api.model.category.CategoryResponse
import com.ch.flavourfair.data.network.api.model.product.ProductItemResponse
import com.ch.flavourfair.data.network.api.model.product.ProductsResponse
import com.ch.flavourfair.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ProductRepositoryImplTest {

    @MockK
    lateinit var remoteDataSource: FlavourfairDataSource

    private lateinit var repository: ProductRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = ProductRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `get categories, with result loading`() {
        val mockCategoryResponse = mockk<CategoriesResponse>()
        runTest {
            coEvery { remoteDataSource.getCategories() } returns mockCategoryResponse
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { remoteDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories, with result success`() {
        val fakeCategoryResponse = CategoryResponse(
            id = "124",
            imageUrl = "url",
            nama = "name",
            slug = "slug"
        )
        val fakeCategoriesResponse = CategoriesResponse(
            code = 200,
            status = true,
            message = "Success",
            data = listOf(fakeCategoryResponse)
        )
        runTest {
            coEvery { remoteDataSource.getCategories() } returns fakeCategoriesResponse
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.id, "124")
                coVerify { remoteDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories, with result empty`() {
        val fakeCategoriesResponse = CategoriesResponse(
            code = 200,
            status = true,
            message = "Success but empty",
            data = emptyList()
        )
        runTest {
            coEvery { remoteDataSource.getCategories() } returns fakeCategoriesResponse
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { remoteDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories, with result error`() {
        runTest {
            coEvery { remoteDataSource.getCategories() } throws IllegalStateException("Mock error")
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { remoteDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get products, with result loading`() {
        val mockProductResponse = mockk<ProductsResponse>()
        runTest {
            coEvery { remoteDataSource.getProducts(any()) } returns mockProductResponse
            repository.getProducts("snack").map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { remoteDataSource.getProducts(any()) }
            }
        }
    }

    @Test
    fun `get products, with result success`() {
        val fakeProductItemResponse = ProductItemResponse(
            alamatResto = "desc",
            id = 1,
            nama = "name",
            harga = 12000.0,
            imageUrl = "url",
            detail = "desc"
        )
        val fakeProductsResponse = ProductsResponse(
            code = 200,
            status = true,
            message = "Success",
            data = listOf(fakeProductItemResponse)
        )
        runTest {
            coEvery { remoteDataSource.getProducts(any()) } returns fakeProductsResponse
            repository.getProducts("sembarang").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.id, 1)
                coVerify { remoteDataSource.getProducts(any()) }
            }
        }
    }

    @Test
    fun `get products, with result empty`() {
        val fakeProductsResponse = ProductsResponse(
            code = 200,
            status = true,
            message = "Success",
            data = emptyList()
        )
        runTest {
            coEvery { remoteDataSource.getProducts(any()) } returns fakeProductsResponse
            repository.getProducts().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { remoteDataSource.getProducts(any()) }
            }
        }
    }

    @Test
    fun `get products, with result error`() {
        runTest {
            coEvery { remoteDataSource.getProducts(any()) } throws IllegalStateException("Mock error")
            repository.getProducts("sabeb").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { remoteDataSource.getProducts(any()) }
            }
        }
    }
}
