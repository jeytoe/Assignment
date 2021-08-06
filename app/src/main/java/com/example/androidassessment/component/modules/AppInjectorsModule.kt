package com.example.androidassessment.component.modules

import com.example.androidassessment.splashscreen.SplashScreenActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface AppInjectorsModule {

    @ContributesAndroidInjector
    fun contributesSplashScreenActivityInjector(): SplashScreenActivity?
}
