package com.example.androidassessment.splashscreen

import com.example.androidassessment.component.modules.database.User
import com.example.androidassessment.component.modules.database.UserRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.never
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SplashScreenViewModelTest {

    @InjectMocks
    lateinit var subject: SplashScreenViewModel

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var compositeDisposable: CompositeDisposable

    @Test
    fun initUserDatabase_givenDbIsEmpty_addUserIntoTable() {
        `when`(userRepository.getAllUser())
            .thenReturn(Observable.just(emptyList()))
        subject.initUserDatabase()

        verify(userRepository).insertUser(
            eq("john.doe@gmail.com"), eq("12345678")
        )
    }

    @Test
    fun initUserDatabase_givenDbIsNotEmpty_doesNotAddUser() {
        `when`(userRepository.getAllUser())
            .thenReturn(Observable.just(listOf(mock(User::class.java))))
        subject.initUserDatabase()

        verify(userRepository, never()).insertUser(any(), any())
    }
}
