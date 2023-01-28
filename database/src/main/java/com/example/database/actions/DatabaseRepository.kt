package com.example.database.actions

import android.content.Context
import com.example.core.entities.LoggedUser
import com.example.core.entities.PaymentInfo
import com.example.core.entities.PaymentUrl
import com.example.database.entities.User
import com.example.database.views.MainDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DatabaseRepository {
    private lateinit var appDatabase : MainDatabase
    fun writeUsers(context: Context, users : List<User>){
        appDatabase = MainDatabase.getInstance(context)
        LoggedUser.allUsers.clear()
        for(u in users) {
            val user = com.example.core.entities.User(u.id, u.username, u.password, u.gender)
            LoggedUser.allUsers.add(user)
        }
    }
    fun writePaymentUrl(url : String){
        PaymentInfo.paymentUri = url
    }
    fun writePaymentStatus(status : Int?){
        PaymentInfo.paymentStatus = status
    }
}