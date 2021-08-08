package com.example.androidassessment.component.modules.network.userlist

import io.reactivex.Observable
import retrofit2.http.GET

interface UserListService {
    @GET("/users")
    fun getUserList(): Observable<List<NetworkUser>>
}
