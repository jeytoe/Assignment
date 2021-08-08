package com.example.androidassessment.component.modules.network

import android.content.Context
import com.example.androidassessment.BuildConfig
import com.example.androidassessment.component.modules.network.calladapters.HttpExceptionCallAdapterFactory
import com.example.androidassessment.component.modules.network.calladapters.NetworkErrorCallAdapterFactory.Companion.create
import com.example.androidassessment.component.modules.network.calladapters.ObserveOnSchedulerCallAdapterFactory
import com.example.androidassessment.component.modules.network.configurations.ApiConfiguration
import com.example.androidassessment.component.modules.network.configurations.SchedulerConfiguration
import com.example.androidassessment.component.modules.network.userlist.UserListService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule(
    private val apiConfiguration: ApiConfiguration,
    private val schedulerConfiguration: SchedulerConfiguration,
    private val context: Context
) {
    @Provides
    @Singleton
    fun providesConfiguration(): ApiConfiguration {
        return apiConfiguration
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        val mainScheduler = schedulerConfiguration.mainScheduler()
        val ioScheduler = schedulerConfiguration.ioScheduler()
        return Retrofit.Builder()
            .baseUrl(apiConfiguration.apiHost)
            .addCallAdapterFactory(ObserveOnSchedulerCallAdapterFactory.create(mainScheduler))
            .addCallAdapterFactory(create(gson))
            .addCallAdapterFactory(HttpExceptionCallAdapterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(ioScheduler))
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        cache: Cache?,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
         builder
            .readTimeout(apiConfiguration.timeoutSeconds.toLong(), TimeUnit.SECONDS)
            .connectTimeout(apiConfiguration.timeoutSeconds.toLong(), TimeUnit.SECONDS)
            .cache(cache)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun providesOkHttpCache(): Cache {
        return Cache(
            apiConfiguration.cacheDir, apiConfiguration.cacheSize
                .toLong()
        )
    }

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    fun providesContext(): Context {
        return context
    }

    @Provides
    fun providesUserListService(retrofit: Retrofit): UserListService {
        return retrofit.create(UserListService::class.java)
    }
}
