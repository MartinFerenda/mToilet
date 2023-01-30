package com.example.webservice

import com.example.core.entities.Event
import com.example.core.entities.User
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path


interface mToiletWebServiceAPI {
    @POST("users")
    fun postNewUser(@Body user: User?) : Call<User?>?

    @GET("users")
    fun getAllUsers() : Call<List<User>>

    @PUT("users/{id}")
    fun updateUserData(@Path("id") id : Int, @Body user : User?) : Call<User?>?

    @POST("events")
    fun postNewEvent(@Body event : Event?) : Call<Event?>?

    @POST("checkpay/{id}")
    fun getCheckPay(@Path("id") id : String) : Call<Int?>?

    @POST("pay/{id}")
    fun getUrl(@Path("id") id : Int) : Call<String?>?
}