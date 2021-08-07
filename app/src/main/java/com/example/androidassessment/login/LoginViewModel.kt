package com.example.androidassessment.login

import androidx.lifecycle.ViewModel
import com.example.androidassessment.component.modules.database.User
import com.example.androidassessment.component.modules.database.UserRepository
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private var compositeDisposable: CompositeDisposable,
    private var userRepository: UserRepository
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
        compositeDisposable.add(
            loginButtonClicked.flatMap {
                return@flatMap userRepository.getUser(username, password)
            }.subscribe {
                if (it.isEmpty()) {
                    loginResult.onNext(LoginResult.Failed(""))
                } else {
                    loginResult.onNext(LoginResult.Succeeded(it[0]))
                }
            }
        )
        return loginResult
    }
}

sealed class LoginResult {
    class Succeeded(var user: User) : LoginResult()
    class Failed(var reason: String) : LoginResult()
}
