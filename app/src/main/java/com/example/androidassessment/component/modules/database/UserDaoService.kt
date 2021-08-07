package com.example.androidassessment.component.modules.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable

@Deprecated("Shouldn't be used directly")
@Dao
interface UserDaoService {

    @Query("SELECT * FROM user")
    fun getUserTable(): Observable<List<User>>

    @Query("SELECT * FROM user where username = :username and password = :password")
    fun getUser(username: String, password: String): Observable<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("DELETE FROM user WHERE username = :username")
    fun deleteUserByUsername(username: String)

    @Query("DELETE FROM user")
    fun clearAll()
}
