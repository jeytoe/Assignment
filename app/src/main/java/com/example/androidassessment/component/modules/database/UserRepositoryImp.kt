package com.example.androidassessment.component.modules.database

import io.reactivex.Observable
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val userDaoService: UserDaoService
) : UserRepository {

    override fun insertUser(username: String, password: String): Observable<Boolean> {
        userDaoService.insertUser(User(username, password))
        return Observable.just(true)
    }

    override fun getUser(username: String, password: String): Observable<User> {
        return Observable.just(userDaoService.getUser(username, password))
    }

    override fun clearUsers() {
        userDaoService.clearAll()
    }


}
