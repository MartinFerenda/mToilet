package com.example.database.views

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.database.entities.User

@Dao
interface DAO {
    @Query("SELECT * FROM users")
    fun getUserList() : List<User>

    @Insert
    fun insertUser(user : User)
}