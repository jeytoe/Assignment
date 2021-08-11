package com.example.androidassessment.splashscreen

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.androidassessment.component.modules.app.AppModule
import com.example.androidassessment.component.modules.database.UserRepository
import com.example.androidassessment.login.SharedPreferencesKey
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class SplashScreenViewModel @Inject constructor(
    private var userRepository: UserRepository,
    private var compositeDisposable: CompositeDisposable,
    @Named(AppModule.SETTINGS_PREFERENCES_SHARED_PREFS)
    private var sharedPreferences: SharedPreferences
) : ViewModel() {

    fun initUserDatabase() {
        compositeDisposable.add(
            userRepository.getAllUser()
                .doOnNext {
                    if (it.isEmpty()) {
                        userRepository.insertUser(
                            "john.doe@gmail.com", "12345678"
                        )
                    }
                }.subscribe()
        )
    }

    fun getRememberLoginValue(): Boolean {
        return sharedPreferences.getBoolean(SharedPreferencesKey.REMEMBER_LOGIN_KEY, false)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
