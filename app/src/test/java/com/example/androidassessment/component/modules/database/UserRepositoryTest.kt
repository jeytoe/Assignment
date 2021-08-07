package com.example.androidassessment.component.modules.database

import com.nhaarman.mockito_kotlin.eq
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {

    @InjectMocks
    lateinit var subject: UserRepositoryImp

    @Mock
    lateinit var userDaoService: UserDaoService

    @Test
    fun getAllUser() {
        val mockUser = User("username", "password")
        `when`(userDaoService.getUserTable())
            .thenReturn(Observable.just(listOf(mockUser)))

        val actual = subject.getAllUser().test()
        actual.assertValue { it.size == 1 }
        actual.assertValue { it[0].username == "username" }
        actual.assertValue { it[0].password == "password" }
    }

    @Test
    fun clearUsers() {
        subject.clearUsers()

        verify(userDaoService).clearAll()
    }

    @Test
    fun insertUser() {
        subject.insertUser("username", "password")

        verify(userDaoService).insertUser(
            eq(User("username", "password")))
    }

    @Test
    fun getUser_givenWrongUser_returnEmptyList() {
        `when`(userDaoService.getUser("invalidUsername", "password"))
            .thenReturn(Observable.just(emptyList()))

        val actual = subject.getUser("invalidUsername", "password").test()

        actual.assertValue{ it.isEmpty() }
    }

    @Test
    fun getUser_givenCorrectUser_returnNonEmptyList() {
        `when`(userDaoService.getUser("validUsername", "password"))
            .thenReturn(Observable.just(listOf(mock(User::class.java))))

        val actual = subject.getUser("validUsername", "password").test()

        actual.assertValue{ it.size == 1 }
    }
}
