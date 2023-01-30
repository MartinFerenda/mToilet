package com.example.webservice

import android.content.Context
import com.example.core.entities.*
import com.example.database.actions.DatabaseRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class mToiletWebServiceAPICaller {

    private var retrofit : Retrofit
    private lateinit var retrofit2 : Retrofit
    private val baseUrl : String = "https://air2221.mobilisis.hr/api/api/"

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
    fun getAllUsers(context: Context, username : String){
        val serviceAPI = retrofit.create(mToiletWebServiceAPI::class.java)
        val call : Call<List<User>> = serviceAPI.getAllUsers()

        LoggedUser.foundInDatabase = false
        call.enqueue(object : Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful){
                    val databaseRepository = DatabaseRepository()
                    val allUsers : MutableList<com.example.database.entities.User> = mutableListOf()
                    for (u in response.body()!!){
                        val fetchedUser = com.example.database.entities.User(u.id, u.username, u.password, u.gender)
                        allUsers.add(fetchedUser)
                    }
                    databaseRepository.writeUsers(context, allUsers)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {

            }
        })
    }
    fun updateUserData(id : Int, user : User){
        val serviceAPI = retrofit.create(mToiletWebServiceAPI::class.java)
        val call : Call<User?>? = serviceAPI.updateUserData(id, user)

        call!!.enqueue(object : Callback<User?>{
            override fun onResponse(call: Call<User?>, response: Response<User?>) {

            }

            override fun onFailure(call: Call<User?>, t: Throwable) {

            }
        })
    }
    fun postNewEvent(event : Event){
        val serviceAPI = retrofit.create(mToiletWebServiceAPI::class.java)
        val call : Call<Event?>? = serviceAPI.postNewEvent(event)
        call!!.enqueue(object : Callback<Event?> {
            override fun onResponse(call: Call<Event?>, response: Response<Event?>) {
                val responseUser : Event? = response.body()
            }

            override fun onFailure(call: Call<Event?>, t: Throwable) {

            }
        })
    }
    fun getCheckPay(id : String){
        retrofit2 = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val serviceAPI = retrofit2.create(mToiletWebServiceAPI::class.java)
        val call : Call<Int?>? = serviceAPI.getCheckPay(id)

        call!!.enqueue(object : Callback<Int?>{
            override fun onResponse(call: Call<Int?>, response: Response<Int?>) {
                if (response.isSuccessful){
                    val databaseRepository = DatabaseRepository()
                    databaseRepository.writePaymentStatus(response.body())
                }
            }

            override fun onFailure(call: Call<Int?>, t: Throwable) {

            }
        })
    }
    fun getUrl(id: Int){
        retrofit2 = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val serviceAPI = retrofit2.create(mToiletWebServiceAPI::class.java)
        val call : Call<String?>? = serviceAPI.getUrl(id)

        call!!.enqueue(object : Callback<String?>{
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                if (response.isSuccessful){
                    val databaseRepository = DatabaseRepository()
                    databaseRepository.writePaymentUrl(response.body()!!.toString())
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {

            }
        })
    }
}