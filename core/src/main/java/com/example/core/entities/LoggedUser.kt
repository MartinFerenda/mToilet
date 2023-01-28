package com.example.core.entities

object LoggedUser {
    var id : Int = 0
    var username : String = ""
    var password : String = ""
    var gender : String = ""
    var foundInDatabase : Boolean = false
    var allUsers : MutableList<User> = mutableListOf()
}