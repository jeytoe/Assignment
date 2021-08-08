package com.example.androidassessment.component.modules.app

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.example.androidassessment.component.modules.network.NetworkObjectGraph
import com.example.androidassessment.component.modules.network.configurations.ApiConfiguration
import com.example.androidassessment.component.modules.network.configurations.ApiConfigurationImpl
import com.example.androidassessment.component.modules.network.configurations.SchedulerConfigurationImp
import com.example.androidassessment.component.modules.network.userlist.UserListService
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
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
    fun providesNetworkObjectGraph(): NetworkObjectGraph {
        return NetworkObjectGraph(
            ApiConfigurationImpl(application.cacheDir),
            SchedulerConfigurationImp(),
            application.applicationContext
        )
    }

    companion object {
        const val SETTINGS_PREFERENCES_SHARED_PREFS = "SettingsPreferencesSharedPrefs"
    }
}
