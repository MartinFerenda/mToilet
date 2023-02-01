package com.example.mtoilet.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import com.example.core.entities.Event
import com.example.core.entities.LoggedUser
import com.example.core.entities.User
import com.example.webservice.mToiletWebServiceAPICaller

class Repository {
    fun getAllUsers(username : String, context: Context){
        if (checkInternetConnection(context)){
            val caller = mToiletWebServiceAPICaller()
            caller.getAllUsers(context, username)
        }else{
            Toast.makeText(context, "No network connection!", Toast.LENGTH_SHORT).show()
        }
    }
    fun postNewUser(user : User, context : Context){
        if (checkInternetConnection(context)) {
            val mToiletWebServiceAPICaller = mToiletWebServiceAPICaller()
            mToiletWebServiceAPICaller.postNewUser(user)
        }else{
            Toast.makeText(context, "No network connection!", Toast.LENGTH_SHORT).show()
        }
    }
    fun updateUserData(user : User, context: Context){
        if (checkInternetConnection(context)) {
            val mToiletWebServiceAPICaller = mToiletWebServiceAPICaller()
            mToiletWebServiceAPICaller.updateUserData(LoggedUser.id, user)
        }else{
            Toast.makeText(context, "No network connection!", Toast.LENGTH_SHORT).show()
        }
    }
    fun postNewEvent(event: Event, context: Context){
        if (checkInternetConnection(context)) {
            val mToiletWebServiceAPICaller = mToiletWebServiceAPICaller()
            mToiletWebServiceAPICaller.postNewEvent(event)
        }else{
            Toast.makeText(context, "No network connection!", Toast.LENGTH_SHORT).show()
        }
    }
    fun getCheckPay(id : String, context: Context) : Boolean{
        if (checkInternetConnection(context)) {
            val mToiletWebServiceAPICaller = mToiletWebServiceAPICaller()
            return mToiletWebServiceAPICaller.getCheckPay(id)
        }else{
            Toast.makeText(context, "No network connection!", Toast.LENGTH_SHORT).show()
            return false
        }
    }
    fun getUrl(id: Int, context: Context): Boolean {
        if (checkInternetConnection(context)) {
            val mToiletWebServiceAPICaller = mToiletWebServiceAPICaller()
            return mToiletWebServiceAPICaller.getUrl(id)
        }else{
            Toast.makeText(context, "No network connection!", Toast.LENGTH_SHORT).show()
            return false
        }
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
}