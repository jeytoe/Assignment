package com.example.androidassessment.component.modules.app

import com.example.androidassessment.login.LoginActivity
import com.example.androidassessment.main.userlist.UserListFragment
import com.example.androidassessment.splashscreen.SplashScreenActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface AppInjectorsModule {

    @ContributesAndroidInjector
    fun contributesSplashScreenActivityInjector(): SplashScreenActivity?

    @ContributesAndroidInjector
    fun contributesLoginActivityInjector(): LoginActivity?

    @ContributesAndroidInjector
    fun contributesUserListFragmentInjector(): UserListFragment?
}
