package com.ch.flavourfair.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ch.flavourfair.data.repository.ProductRepository
import com.ch.flavourfair.tools.MainCoroutineRule
import com.ch.flavourfair.utils.AssetWrapper
import com.ch.flavourfair.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

class HomeViewModelTest {

    @MockK
    private lateinit var repo: ProductRepository

    @MockK
    private lateinit var assetWrapper: AssetWrapper

    @get: Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { assetWrapper.getString(any()) } returns "any"
        coEvery { repo.getCategories() } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                        mockk(relaxed = true)
                    )
                )
            )
        }
        viewModel = spyk(
            HomeViewModel(repo),
            recordPrivateCalls = true
        )

        coEvery { repo.getProducts() } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                        mockk(relaxed = true)
                    )
                )
            )
        }
    }

    /*@Test
    fun `test category`() {
        runTest {
            verify { repo.getCategories() }
            val result = viewModel.categories.getOrAwaitValue(3)
            assert(result is ResultWrapper.Success)
        }
    }*/
}
