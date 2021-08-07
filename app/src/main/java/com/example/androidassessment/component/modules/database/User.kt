package com.example.androidassessment.component.modules.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "user")
data class User(
    @field:PrimaryKey
    val username: String,
    val password: String)

