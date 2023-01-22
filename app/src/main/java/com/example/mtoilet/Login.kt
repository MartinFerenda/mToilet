package com.example.mtoilet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.core.entities.LoggedUser
import com.example.core.entities.User
import com.example.webservice.mToiletWebServiceAPICaller

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username: EditText = findViewById(R.id.username)
        val password: EditText = findViewById(R.id.password)

        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {

            //val intent = Intent(this, Home::class.java)
            //startActivity(intent)

            val caller = mToiletWebServiceAPICaller()
            caller.getAllUsers(username.text.toString())

            var found = false
            val message : TextView = findViewById(R.id.login_message)
            val listUsers : MutableList<User> = mutableListOf()
            listUsers.add(User(21, "novi", "nova", "Male"))
            listUsers.add(User(23, "novi2", "nova2", "Male"))

            for (u in listUsers){
                if(u.username == username.text.toString() && u.password == password.text.toString()){
                    found = true
                    message.text = ""

                    LoggedUser.id = u.id
                    LoggedUser.username = u.username
                    LoggedUser.password = u.password
                    LoggedUser.gender = u.gender

                    val intent = Intent(this, Home2::class.java)
                    startActivity(intent)
                }
            }
            if (!found){
                message.text = "Wrong username or password!"
            }
        }
    }
}