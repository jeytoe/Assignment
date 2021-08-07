package com.example.androidassessment.component

import android.app.Activity
import com.example.androidassessment.MyApplication
import com.example.androidassessment.component.modules.app.AppInjectorsModule
import com.example.androidassessment.component.modules.app.AppModule
import com.example.androidassessment.login.LoginActivity
import com.example.androidassessment.component.modules.database.UserDatabaseModule
import com.example.androidassessment.component.modules.database.UserRepository
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.DispatchingAndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,
    AppInjectorsModule::class,
    AppModule::class,
    UserDatabaseModule::class])
interface AppComponent {

    fun dispatchingAndroidInjector(): DispatchingAndroidInjector<Activity>

    fun inject(app: MyApplication)

    fun userRepository(): UserRepository
}
