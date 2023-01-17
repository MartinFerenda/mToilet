package com.example.webservice

import com.example.core.entities.User
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class mToiletWebServiceAPICaller {
    private var retrofit : Retrofit
    lateinit var allUsers: List<User>
    private val baseUrl : String = "https://air2221.mobilisis.hr/api/"

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
                val responseUser: User? = response.body()
            }

            override fun onFailure(call: Call<User?>, t: Throwable) {

            }
        })
    }

    fun getAllUsers() {
        val serviceAPI = retrofit.create(mToiletWebServiceAPI::class.java)
        val call : Call<List<User>?>? = serviceAPI.getAllUsers()
        var usersResponse: List<User>? = listOf()

        var ide: Boolean = true

        call!!.enqueue(object : Callback<List<User>?> {
            override fun onResponse(call: Call<List<User>?>, response: Response<List<User>?>) {
                ide = false
                if (response.isSuccessful) {
                    usersResponse = response.body()
                }
            }
            override fun onFailure(call: Call<List<User>?>, t: Throwable) {
                ide = false
            }
        })
        this.allUsers = listOf()
        this.allUsers = usersResponse!!
    }
}