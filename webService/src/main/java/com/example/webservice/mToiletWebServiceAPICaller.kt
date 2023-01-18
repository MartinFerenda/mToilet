package com.example.webservice

import com.example.core.entities.User
import com.example.webservice.responses.UsersResponse
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
        val retrofit2 = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val serviceAPI = retrofit2.create(mToiletWebServiceAPI::class.java)
        val call : Call<List<User>> = serviceAPI.getAllUsers()

        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful){
                    val usersResponse = response.body()!!
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                val ide = false
            }

        })
        //this.allUsers = listOf()
        //this.allUsers = usersResponse!!
    }
}