package com.example.androidassessment.component.modules.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UserDatabaseModule constructor(context: Context) {

    private var userDatabase: UserDatabase = Room.databaseBuilder(
        context, UserDatabase::class.java, USER_DB
    )
        .fallbackToDestructiveMigration()
        .build()


    @Singleton
    @Provides
    fun providesUserDatabase(): UserDatabase = userDatabase

    @Provides
    fun providesUserDaoService(
        userDatabase: UserDatabase
    ) = userDatabase.userDaoService()

    @Provides
    fun providesUserRepository(): UserRepository {
        return UserRepositoryImp(userDatabase.userDaoService())
    }

    companion object {
        private const val USER_DB: String = "user_db"
    }
}
