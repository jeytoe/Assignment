package com.example.androidassessment.component

import android.app.Activity
import com.example.androidassessment.MyApplication
import com.example.androidassessment.component.modules.AppInjectorsModule
import com.example.androidassessment.component.modules.AppModule
import com.example.androidassessment.splashscreen.SplashScreenActivity
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.DispatchingAndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,
    AppInjectorsModule::class,
    AppModule::class])
interface AppComponent {

    fun dispatchingAndroidInjector(): DispatchingAndroidInjector<Activity>

    fun inject(app: MyApplication)
}
