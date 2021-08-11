package com.example.androidassessment.component.modules.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import com.example.androidassessment.component.modules.network.NetworkObjectGraph
import com.example.androidassessment.component.modules.network.configurations.ApiConfiguration
import com.example.androidassessment.component.modules.network.configurations.ApiConfigurationImpl
import com.example.androidassessment.component.modules.network.configurations.SchedulerConfigurationImp
import com.example.androidassessment.component.modules.network.userlist.UserListService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Named
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
    fun provideUserListService(networkObjectGraph: NetworkObjectGraph): UserListService {
        return networkObjectGraph.userListService()
    }

    @Provides
    @Singleton
    fun provideGson(networkObjectGraph: NetworkObjectGraph): Gson {
        return networkObjectGraph.gson()
    }

    @Provides
    fun providesNetworkObjectGraph(): NetworkObjectGraph {
        return NetworkObjectGraph(
            ApiConfigurationImpl(application.cacheDir),
            SchedulerConfigurationImp(),
            application.applicationContext
        )
    }

    @Provides
    @Named(SETTINGS_PREFERENCES_SHARED_PREFS)
    fun provideSettingsPreferencesSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SETTINGS_PREFERENCES_SHARED_PREFS, Context.MODE_PRIVATE)
    }

    companion object {
        const val SETTINGS_PREFERENCES_SHARED_PREFS = "SettingsPreferencesSharedPrefs"
    }
}
