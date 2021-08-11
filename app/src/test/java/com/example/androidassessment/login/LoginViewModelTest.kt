package com.example.androidassessment.login

import android.content.SharedPreferences
import com.example.androidassessment.component.modules.database.User
import com.example.androidassessment.component.modules.database.UserRepository
import com.nhaarman.mockito_kotlin.eq
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @InjectMocks
    lateinit var subject: LoginViewModel

    @Mock
    lateinit var compositeDisposable: CompositeDisposable

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var sharedPreferences: SharedPreferences

    @Test
    fun setupBtnLoginStateEvent_givenPasswordIsEmpty_shouldEmitFalse() {
        val usernameObs: Observable<CharSequence> = Observable.just("123")
        val passwordObs: Observable<CharSequence> = Observable.just("")

        val actual = subject.setupBtnLoginStateEvent(
            usernameObs, passwordObs
        ).test()

        actual.assertValue { !it }
    }

    @Test
    fun setupBtnLoginStateEvent_givenUsernameIsEmpty_shouldEmitFalse() {
        val usernameObs: Observable<CharSequence> = Observable.just("")
        val passwordObs: Observable<CharSequence> = Observable.just("asd")

        val actual = subject.setupBtnLoginStateEvent(
            usernameObs, passwordObs
        ).test()

        actual.assertValue { !it }
    }

    @Test
    fun setupBtnLoginStateEvent_givenUsernameAndPasswordNotEmpty_shouldEmitTrue() {
        val usernameObs: Observable<CharSequence> = Observable.just("asdads")
        val passwordObs: Observable<CharSequence> = Observable.just("asd")

        val actual = subject.setupBtnLoginStateEvent(
            usernameObs, passwordObs
        ).test()

        actual.assertValue { it }
    }

    @Test
    fun setupLoginEvents_whenFoundUser_emitSucceededEvent() {
        `when`(userRepository.getUser("", ""))
            .thenReturn(Observable.just(listOf(mock(User::class.java))))
        val actual = subject.setupLoginEvents().test()
        subject.loginButtonClicked.onNext(Unit)

        //call with initial values of username and password
        verify(userRepository).getUser(eq(""), eq(""))
        actual.assertValue { it is LoginResult.Succeeded }
    }

    @Test
    fun setupLoginEvents_whenNotAbleToFindUser_emitFailedEvent() {
        `when`(userRepository.getUser("", ""))
            .thenReturn(Observable.just(emptyList()))
        val actual = subject.setupLoginEvents().test()
        subject.loginButtonClicked.onNext(Unit)

        //call with initial values of username and password
        verify(userRepository).getUser(eq(""), eq(""))
        actual.assertValue { it is LoginResult.Failed }
    }

    @Test
    fun updateRememberLogin_verifySharePreferencesCalled() {
        val editor = mock(SharedPreferences.Editor::class.java)
        `when`(sharedPreferences.edit())
            .thenReturn(editor)
        `when`(editor.putBoolean(anyString(), anyBoolean()))
            .thenReturn(editor)
        subject.updateRememberLogin(true)

        verify(sharedPreferences).edit()
        verify(editor).putBoolean(SharedPreferencesKey.REMEMBER_LOGIN_KEY, true)
        verify(editor).apply()
    }
}
