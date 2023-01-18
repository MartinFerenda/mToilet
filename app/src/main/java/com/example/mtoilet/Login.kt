package com.example.mtoilet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.core.entities.LoggedUser
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
            caller.getAllUsers()
            for (u in caller.allUsers){
                if(u.username == username.text.toString() && u.password == password.text.toString()){

                    LoggedUser.id = u.id
                    LoggedUser.username = u.username
                    LoggedUser.gender = u.gender

                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                }
            }

        }
    }
}