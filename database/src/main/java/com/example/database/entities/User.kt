package com.example.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey val id : Int,
    @ColumnInfo(name = "username") val username : String,
    @ColumnInfo(name = "password") val password : String,
    @ColumnInfo(name = "gender") val gender : String
    )