package com.singaporeair.recentsearch

import android.content.Context
import androidx.room.Room
import com.example.androidassessment.component.modules.database.UserDatabase
import com.example.androidassessment.component.modules.database.UserRepository
import com.example.androidassessment.component.modules.database.UserRepositoryImp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UserDatabaseModule constructor(context: Context) {

    private var userDatabase: UserDatabase = Room.databaseBuilder<UserDatabase>(
        context, UserDatabase::class.java, USER_DB
    )
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
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
