package com.example.androidassessment.component

import com.example.androidassessment.MyApplication
import com.example.androidassessment.component.modules.AppModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class])
interface AppComponent {
    fun inject(app: MyApplication)
}
