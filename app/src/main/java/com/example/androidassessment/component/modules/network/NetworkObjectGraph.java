package com.example.androidassessment.component.modules.network;

import android.content.Context;

import com.example.androidassessment.component.modules.network.configurations.ApiConfiguration;
import com.example.androidassessment.component.modules.network.configurations.SchedulerConfiguration;
import com.example.androidassessment.component.modules.network.userlist.UserListService;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class NetworkObjectGraph {

    private final NetworkComponent component;
    private final Context context;

    public NetworkObjectGraph(ApiConfiguration configuration,
                              SchedulerConfiguration schedulerConfiguration,
                              Context context
    ) {
        this.context = context;
        component = DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule(configuration,
                        schedulerConfiguration, context))
                .build();
    }

    public Retrofit retrofit() {
        return component.retrofit();
    }

    public OkHttpClient okHttpClient() {
        return component.okHttpClient();
    }

    public HttpLoggingInterceptor httpLoggingInterceptor() {
        return component.httpLoggingInterceptor();
    }

    public Gson gson() {
        return component.gson();
    }

    public UserListService userListService() {
        return component.userListService();
    }
}
