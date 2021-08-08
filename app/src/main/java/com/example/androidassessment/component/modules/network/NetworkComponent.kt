package com.example.androidassessment.component.modules.network

import com.example.androidassessment.component.modules.network.userlist.UserListService
import com.google.gson.Gson
import dagger.Component
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {
    fun retrofit(): Retrofit?
    fun okHttpClient(): OkHttpClient?
    fun httpLoggingInterceptor(): HttpLoggingInterceptor?
    fun gson(): Gson?
    fun userListService(): UserListService
}
