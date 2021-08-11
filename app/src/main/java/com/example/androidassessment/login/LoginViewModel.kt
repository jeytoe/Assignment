package com.example.androidassessment.login

import android.content.SharedPreferences
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.example.androidassessment.R
import com.example.androidassessment.component.modules.app.AppModule.Companion.SETTINGS_PREFERENCES_SHARED_PREFS
import com.example.androidassessment.component.modules.database.User
import com.example.androidassessment.component.modules.database.UserRepository
import com.example.androidassessment.login.extension.isValidEmailAddress
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Named


class SharedPreferencesKey {
    companion object {
        const val REMEMBER_LOGIN_KEY = "REMEMBER_LOGIN_KEY"
    }
}

class LoginViewModel @Inject constructor(
    private var compositeDisposable: CompositeDisposable,
    private var userRepository: UserRepository,
    @Named(SETTINGS_PREFERENCES_SHARED_PREFS)
    private var sharedPreferences: SharedPreferences
) : ViewModel() {


    private var username = ""
    private var password = ""

    private var loginResult = BehaviorSubject.create<LoginResult>()
    var loginButtonClicked = PublishSubject.create<Unit>()

    fun setupBtnLoginStateEvent(
        usernameObs: Observable<CharSequence>,
        passwordObs: Observable<CharSequence>,
    ): Observable<Boolean> {
        compositeDisposable.add(usernameObs.doOnNext { username = it.toString() }
            .subscribe())
        compositeDisposable.add(passwordObs.doOnNext { password = it.toString() }
            .subscribe())
        return Observable.combineLatest(usernameObs, passwordObs)
        { username, password ->
            return@combineLatest username.isNotEmpty() && password.isNotEmpty()
        }
    }

    fun setupLoginEvents(): BehaviorSubject<LoginResult> {

        compositeDisposable.add(loginButtonClicked.flatMap outer@{
            if (!username.isValidEmailAddress()) {
                return@outer Observable.just(
                    LoginResult.Failed(R.string.invalid_username_format)
                )
            } else if (password.length < 8) {
                return@outer Observable.just(
                    LoginResult.Failed(R.string.invalid_password_format)
                )
            }
            return@outer userRepository.getUser(username, password)
                .flatMap {
                    return@flatMap if (it.isEmpty())
                        Observable.just(LoginResult.Failed(R.string.wrong_credentials))
                    else Observable.just(LoginResult.Succeeded(it[0]))
                }
        }.subscribe {
            loginResult.onNext(it)
        })

        return loginResult
    }

    fun updateRememberLogin(value: Boolean) {
        sharedPreferences.edit().putBoolean(SharedPreferencesKey.REMEMBER_LOGIN_KEY, value).apply()
    }
}

sealed class LoginResult {
    class Succeeded(var user: User) : LoginResult()
    class Failed(@StringRes var reason: Int) : LoginResult()
}
