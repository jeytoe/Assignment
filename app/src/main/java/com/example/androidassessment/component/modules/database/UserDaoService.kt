package com.example.androidassessment.component.modules.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDaoService {

  @Query("SELECT * FROM user where username = :username and password = :password")
  fun getUser(username: String, password: String) : User

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertUser(user: User)

  @Query("DELETE FROM user WHERE username = :username")
  fun deleteUserByUsername(username: String)

  @Query("DELETE FROM user")
  fun clearAll()
}
