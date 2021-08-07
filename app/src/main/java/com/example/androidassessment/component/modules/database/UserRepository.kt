package com.example.androidassessment.component.modules.database

import io.reactivex.Observable

interface UserRepository {

    fun insertUser(
        username: String,
        password: String
    ): Observable<Boolean>

    fun getUser(username: String, password: String): Observable<User>

    fun clearUsers()
}
