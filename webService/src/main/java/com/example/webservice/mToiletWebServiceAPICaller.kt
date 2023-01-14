package com.example.webservice

import android.util.Log
import android.widget.EditText
import com.example.core.entities.User
import com.example.webservice.responses.UsersResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class mToiletWebServiceAPICaller {
    var retrofit : Retrofit
    var allUsers: MutableList<User> = mutableListOf()
    var found: Boolean = false
    val baseUrl : String = "https://air2221.mobilisis.hr/api/"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()
    }

    fun postNewUser(user: User){
        val serviceAPI = retrofit.create(mToiletWebServiceAPI::class.java)
        val call: Call<User?>? = serviceAPI.postNewUser(user)

        call!!.enqueue(object : Callback<User?>{
             override fun onResponse(call: Call<User?>, response: Response<User?>) {
                val response: User? = response.body()
            }

            override fun onFailure(call: Call<User?>, t: Throwable) {

            }
        })
    }

    fun getAllUsers(){
        val serviceAPI = retrofit.create(mToiletWebServiceAPI::class.java)
        val call: Call<MutableList<User>> = serviceAPI.getAllUsers()

        call.enqueue(object : Callback<MutableList<User>>{
            override fun onResponse(call: Call<MutableList<User>>, response: Response<MutableList<User>>) {
                if (response.isSuccessful) {
                    val usersResponse = response.body()!!.toMutableList()
                    this@mToiletWebServiceAPICaller.allUsers = usersResponse
                }
            }

            override fun onFailure(call: Call<MutableList<User>>, t: Throwable) {

            }
        })
    }
}