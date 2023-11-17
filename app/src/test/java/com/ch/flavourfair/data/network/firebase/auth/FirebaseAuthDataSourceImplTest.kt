package com.ch.flavourfair.data.network.firebase.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import io.mockk.MockKAnnotations.init
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FirebaseAuthDataSourceImplTest {

    @MockK(relaxed = true)
    lateinit var firebaseAuth: FirebaseAuth

    lateinit var dataSource: FirebaseAuthDataSource

    @Before
    fun setUp() {
        init(this)
        dataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
    }

    @Test
    fun `test login`() {
        every { firebaseAuth.signInWithEmailAndPassword(any(), any()) } returns mockTaskAuthResult()
        runTest {
            val result = dataSource.doLogin("email", "password")
            Assert.assertEquals(result, true)
        }
    }

    @Test
    fun `test update email`() {
        every { firebaseAuth.currentUser?.updateEmail(any()) } returns mockTask()
        runTest {
            val result = dataSource.updateEmail("new email")
            TestCase.assertEquals(result, true)
            verify { firebaseAuth.currentUser?.updateEmail(any()) }
        }
    }

    @Test
    fun `test update password`() {
        every { firebaseAuth.currentUser?.updatePassword(any()) } returns mockTask()
        runTest {
            val result = dataSource.updatePassword("new password")
            TestCase.assertEquals(result, true)
            verify { firebaseAuth.currentUser?.updatePassword(any()) }
        }
    }

    @Test
    fun `test change password`() {
        every { firebaseAuth.sendPasswordResetEmail(any()) } returns mockTask()
        every { firebaseAuth.currentUser?.email } returns "email"
        runTest {
            val result = dataSource.sendChangePasswordRequestByEmail()
            TestCase.assertEquals(result, true)
            verify { firebaseAuth.sendPasswordResetEmail(any()) }
        }
    }

    private fun mockTask(exception: Exception? = null): Task<Void> {
        val task: Task<Void> = mockk(relaxed = true)
        every { task.isComplete } returns true
        every { task.exception } returns exception
        every { task.isCanceled } returns false
        val relaxedVoid: Void = mockk(relaxed = true)
        every { task.result } returns relaxedVoid
        return task
    }

    private fun mockTaskAuthResult(exception: Exception? = null): Task<AuthResult> {
        val task: Task<AuthResult> = mockk(relaxed = true)
        every { task.isComplete } returns true
        every { task.exception } returns exception
        every { task.isCanceled } returns false
        val relaxedResult: AuthResult = mockk(relaxed = true)
        every { task.result } returns relaxedResult
        every { task.result.user } returns mockk()
        return task
    }
}
