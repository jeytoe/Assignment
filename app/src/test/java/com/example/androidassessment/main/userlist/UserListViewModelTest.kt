package com.example.androidassessment.main.userlist

import android.content.SharedPreferences
import com.example.androidassessment.component.modules.network.userlist.NetworkUser
import com.example.androidassessment.component.modules.network.userlist.UserListService
import com.example.androidassessment.login.SharedPreferencesKey
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserListViewModelTest {

    @InjectMocks
    lateinit var subject: UserListViewModel

    @Mock
    lateinit var compositeDisposable: CompositeDisposable

    @Mock
    lateinit var sharedPreferences: SharedPreferences

    @Mock
    lateinit var userListService: UserListService

    @Test
    fun getUserList() {
        var mockObs: Observable<List<NetworkUser>> = mock(Observable::class.java) as Observable<List<NetworkUser>>
        `when`(userListService.getUserList())
            .thenReturn(mockObs)

        val actual = subject.getUserList()

        assertThat(actual).isEqualTo(mockObs)
    }

    @Test
    fun logout_verifySharePreferencesCalled() {
        val editor = mock(SharedPreferences.Editor::class.java)
        `when`(sharedPreferences.edit())
            .thenReturn(editor)
        `when`(editor.putBoolean(Mockito.anyString(), Mockito.anyBoolean()))
            .thenReturn(editor)
        subject.logout()

        Mockito.verify(sharedPreferences).edit()
        Mockito.verify(editor).putBoolean(SharedPreferencesKey.REMEMBER_LOGIN_KEY, false)
        Mockito.verify(editor).apply()
    }
}
