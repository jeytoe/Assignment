package com.example.androidassessment.main.userlist

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.androidassessment.component.modules.app.AppModule
import com.example.androidassessment.component.modules.network.userlist.NetworkUser
import com.example.androidassessment.component.modules.network.userlist.UserListService
import com.example.androidassessment.login.SharedPreferencesKey
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class UserListViewModel @Inject constructor(
    var compositeDisposable: CompositeDisposable,
    var userListService: UserListService,
    @Named(AppModule.SETTINGS_PREFERENCES_SHARED_PREFS)
    private var sharedPreferences: SharedPreferences
) : ViewModel() {

    fun getUserList(): Observable<List<NetworkUser>> {
        return userListService.getUserList()
    }

    fun logout() {
        sharedPreferences.edit()
            .putBoolean(SharedPreferencesKey.REMEMBER_LOGIN_KEY, false)
            .apply()
    }
}
