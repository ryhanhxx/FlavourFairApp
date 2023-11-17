package com.ch.flavourfair.data.network.api.datasource

import com.ch.flavourfair.data.network.api.model.category.CategoriesResponse
import com.ch.flavourfair.data.network.api.model.order.OrderRequest
import com.ch.flavourfair.data.network.api.model.order.OrderResponse
import com.ch.flavourfair.data.network.api.model.product.ProductsResponse
import com.ch.flavourfair.data.network.api.service.FlavourfairApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FlavourfairApiDataSourceTest {

    @MockK
    lateinit var service: FlavourfairApiService

    private lateinit var dataSource: FlavourfairDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = FlavourfairApiDataSource(service)
    }

    @Test
    fun getProducts() {
        runTest {
            val mockResponse = mockk<ProductsResponse>(relaxed = true)
            coEvery { service.getProducts(any()) } returns mockResponse
            val response = dataSource.getProducts("makanan")
            coVerify { service.getProducts(any()) } // verif service sudah terpanggil
            assertEquals(response, mockResponse) // mencocokan hasil antara actual dengan expected
        }
    }

    @Test
    fun getCategories() {
        runTest {
            val mockResponse = mockk<CategoriesResponse>(relaxed = true)
            coEvery { service.getCategories() } returns mockResponse
            val response = dataSource.getCategories()
            coVerify { service.getCategories() } // verif service sudah terpanggil
            assertEquals(response, mockResponse) // mencocokan hasil antara actual dengan expected
        }
    }

    @Test
    fun createOrder() {
        runTest {
            val mockResponse = mockk<OrderResponse>(relaxed = true)
            val mockRequest = mockk<OrderRequest>(relaxed = true)
            coEvery { service.createOrder(any()) } returns mockResponse
            val response = dataSource.createOrder(mockRequest)
            coVerify { service.createOrder(any()) }
            assertEquals(response, mockResponse)
        }
    }
}
