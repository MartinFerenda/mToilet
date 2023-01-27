package com.example.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.core.entities.LoggedUser
import com.example.core.entities.User
import com.example.webservice.mToiletWebServiceAPICaller

class Repository {

    fun getAllUsers(username : String, context: Context){
        if (checkInternetConnection(context)){
            val caller = mToiletWebServiceAPICaller()
            caller.getAllUsers(context, username)
        }
    }
    fun postNewUser(user : User){
        val mToiletWebServiceAPICaller = mToiletWebServiceAPICaller()
        mToiletWebServiceAPICaller.postNewUser(user)
    }
    fun updateUserData(user : User){
        val mToiletWebServiceAPICaller = mToiletWebServiceAPICaller()
        mToiletWebServiceAPICaller.updateUserData(LoggedUser.id, user)
    }
    fun checkInternetConnection(context: Context) : Boolean{
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
    fun checkUsername() : String{
        /*
        val repository = Repository()
        repository.getAllUsers(userName.text.toString(), this)
        */
        //za registraciju i my profile treba!!!

        if(LoggedUser.foundInDatabase) {
            return "Username is already taken! Please choose different username!"
        }
        return ""
    }


}