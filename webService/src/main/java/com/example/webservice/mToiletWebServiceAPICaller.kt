package com.example.webservice

import androidx.core.provider.FontsContractCompat
import com.example.core.entities.LoggedUser
import com.example.core.entities.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class mToiletWebServiceAPICaller {

    private var retrofit : Retrofit
    lateinit var allUsers: MutableList<User>
    private val baseUrl : String = "https://air2221.mobilisis.hr/api/"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun postNewUser(user : User){
        val serviceAPI = retrofit.create(mToiletWebServiceAPI::class.java)
        val call : Call<User?>? = serviceAPI.postNewUser(user)
        call!!.enqueue(object : Callback<User?> {
            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                val responseUser : User? = response.body()
            }

            override fun onFailure(call: Call<User?>, t: Throwable) {

            }
        })
    }
    fun getAllUsers(username : String){
        val serviceAPI = retrofit.create(mToiletWebServiceAPI::class.java)
        val call : Call<List<User>> = serviceAPI.getAllUsers()
        var usersResponse : MutableList<User> = mutableListOf()
        this.allUsers = mutableListOf()

        LoggedUser.foundInDatabase = false
        call.enqueue(object : Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful){
                    for (u in response.body()!!){
                        if (username == u.username){
                            LoggedUser.foundInDatabase = true
                        }
                        val fetchedUser = User(u.id, u.username, u.password, u.gender)
                        usersResponse.add(fetchedUser)
                    }
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {

            }
        })

        this.allUsers = usersResponse
    }
    fun updateUserData(user : User){

    }
}