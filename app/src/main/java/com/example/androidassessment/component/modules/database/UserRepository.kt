package com.example.androidassessment.component.modules.database

import io.reactivex.Observable

interface UserRepository {

    fun insertUser(
        username: String,
        password: String
    ): Observable<Boolean>

    fun getUser(username: String, password: String): Observable<List<User>>

    fun getAllUser(): Observable<List<User>>

    fun clearUsers()
}
