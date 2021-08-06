package com.example.androidassessment

import android.app.Application
import com.example.androidassessment.component.AppComponent
import com.example.androidassessment.component.DaggerAppComponent
import com.example.androidassessment.component.modules.AppModule
import com.squareup.leakcanary.LeakCanary
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MyApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    private var appComponent: AppComponent? = null

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()

        getAppComponent()
        initSdk()

    }

    private fun initSdk() {
        LeakCanary.install(this)
        Picasso.setSingletonInstance(Picasso.Builder(this).build())
    }

    private fun getAppComponent(): AppComponent? {
        if (appComponent == null) {
            buildAppComponent()
        }
        return appComponent
    }

    private fun buildAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        appComponent?.run { inject(this@MyApplication) }
    }
}
