package com.example.database.actions

import android.content.Context
import com.example.database.entities.User
import com.example.database.views.MainDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DatabaseRepository {
    private lateinit var appDatabase : MainDatabase
    fun writeUsers(context: Context, users : List<User>){
        appDatabase = MainDatabase.getInstance(context)
        GlobalScope.launch(Dispatchers.IO) {
            for(u in users) {
                val user = User(u.id, u.username, u.password, u.gender)
                appDatabase.userDao().insertUser(user)
            }
        }
    }
    fun readUsers(context: Context) : List<User>{
        var localUsers : List<User> = listOf()
        appDatabase = MainDatabase.getInstance(context)
        GlobalScope.launch {
            localUsers = appDatabase.userDao().getUserList()
        }
        return localUsers
    }
}