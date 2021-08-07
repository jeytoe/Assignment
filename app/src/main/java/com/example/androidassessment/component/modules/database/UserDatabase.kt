package com.example.androidassessment.component.modules.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [User::class],
    version = UserDatabase.VERSION,
    exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

  companion object {
    const val VERSION = 1
  }

  abstract fun userDaoService(): UserDaoService
}
