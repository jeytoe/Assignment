package com.example.androidassessment.component.modules;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.example.androidassessment.BuildConfig;
import com.example.androidassessment.component.modules.network.NetworkObjectGraph;
import com.example.androidassessment.component.modules.network.configurations.ApiConfiguration;
import com.example.androidassessment.component.modules.network.configurations.ApiConfigurationImpl;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import retrofit2.Retrofit;

@Module
public class AppModule {

    public static final String SETTINGS_PREFERENCES_SHARED_PREFS = "SettingsPreferencesSharedPrefs";


    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }


    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return application;
    }

    @Provides
    @Singleton
    Resources providesResources() {
        return application.getResources();
    }

    @Provides
    CompositeDisposable providesCompositeDisposable() {
        return new CompositeDisposable();
    }


    @Provides
    ApiConfiguration providesMslApiConfiguration() {
        return new ApiConfigurationImpl(application.getCacheDir());
    }


    @Provides
    @Singleton
    Retrofit providesRetrofit(NetworkObjectGraph networkObjectGraph) {

        Builder okHttpBuilder = networkObjectGraph.okHttpClient()
                .newBuilder();

        if (!BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(networkObjectGraph.httpLoggingInterceptor());
        }

        OkHttpClient okHttpClient = okHttpBuilder.build();
        return networkObjectGraph.retrofit().newBuilder().client(okHttpClient).build();
    }

    @Provides
    @Singleton
    Gson provideGson(NetworkObjectGraph networkObjectGraph) {
        return networkObjectGraph.gson();
    }
}
