package com.ch.flavourfair.data.repository

import app.cash.turbine.test
import com.ch.flavourfair.data.network.firebase.auth.FirebaseAuthDataSource
import com.ch.flavourfair.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {

    @MockK
    lateinit var dataSource: FirebaseAuthDataSource

    private lateinit var repository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = UserRepositoryImpl(dataSource)
    }

    @Test
    fun `doLogin loading`() {
        coEvery { dataSource.doLogin(any(), any()) } returns true
        runTest {
            repository.doLogin("email", "password").map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                println(data)
                assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun `doLogin success`() {
        coEvery { dataSource.doLogin(any(), any()) } returns true
        runTest {
            repository.doLogin("email", "password").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                println(data)
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun `doLogin error`() {
        coEvery { dataSource.doLogin(any(), any()) } throws IllegalStateException()
        runTest {
            repository.doLogin("email", "pass").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                println(data)
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun `doRegister loading`() {
        coEvery { dataSource.doRegister(any(), any(), any()) } returns true
        runTest {
            repository.doRegister("full name", "email", "pass").map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                println(data)
                assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun `doRegister success`() {
        coEvery { dataSource.doRegister(any(), any(), any()) } returns true
        runTest {
            repository.doRegister("full name", "email", "pass").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                println(data)
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun `doRegister error`() {
        coEvery { dataSource.doRegister(any(), any(), any()) } throws IllegalStateException()
        runTest {
            repository.doRegister("full name", "email", "pass").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                println(data)
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun `update password loading`() {
        coEvery { dataSource.updatePassword(any()) } returns true
        runTest {
            repository.updatePassword("password").map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.updatePassword(any()) }
            }
        }
    }

    @Test
    fun `update password success`() {
        coEvery { dataSource.updatePassword(any()) } returns true
        runTest {
            repository.updatePassword("password").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.updatePassword(any()) }
            }
        }
    }

    @Test
    fun `update password error`() {
        coEvery { dataSource.updatePassword(any()) } throws IllegalStateException()
        runTest {
            repository.updatePassword("password").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.updatePassword(any()) }
            }
        }
    }
}
