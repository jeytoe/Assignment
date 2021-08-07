package com.example.androidassessment.splashscreen

import androidx.lifecycle.ViewModel
import com.example.androidassessment.component.modules.database.UserRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SplashScreenViewModel @Inject constructor(
    private var userRepository: UserRepository,
    private var compositeDisposable: CompositeDisposable
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

    override fun onCleared() {
        super.onCleared()
    }
}
