package com.example.androidassessment.component.modules

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.example.androidassessment.BuildConfig
import com.example.androidassessment.component.modules.network.NetworkObjectGraph
import com.example.androidassessment.component.modules.network.configurations.ApiConfiguration
import com.example.androidassessment.component.modules.network.configurations.ApiConfigurationImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun providesApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    fun providesContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun providesResources(): Resources {
        return application.resources
    }

    @Provides
    fun providesCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    fun providesMslApiConfiguration(): ApiConfiguration {
        return ApiConfigurationImpl(application.cacheDir)
    }

    @Provides
    @Singleton
    fun providesRetrofit(networkObjectGraph: NetworkObjectGraph): Retrofit {
        val okHttpBuilder = networkObjectGraph.okHttpClient()
            .newBuilder()
        if (!BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(networkObjectGraph.httpLoggingInterceptor())
        }
        val okHttpClient = okHttpBuilder.build()
        return networkObjectGraph.retrofit().newBuilder().client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideGson(networkObjectGraph: NetworkObjectGraph): Gson {
        return networkObjectGraph.gson()
    }

    companion object {
        const val SETTINGS_PREFERENCES_SHARED_PREFS = "SettingsPreferencesSharedPrefs"
    }
}
