package com.example.mtoilet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.core.entities.LoggedUser

class Home2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)

        val loginButton = findViewById<Button>(R.id.qr_scan_button)
        val myProfButton = findViewById<Button>(R.id.my_prof)
        val logoutButton = findViewById<Button>(R.id.logout)

        loginButton.setOnClickListener {
            val intent = Intent(this, QRCodeScan::class.java)
            startActivity(intent)
        }
        myProfButton.setOnClickListener {
            val intent2 = Intent(this, MyProfile::class.java)
            startActivity(intent2)
        }
        logoutButton.setOnClickListener{
            logoutUser()
            val intent3 = Intent(this, Login::class.java)
            startActivity(intent3)
            this.finish()
        }
    }

    private fun logoutUser() {
        LoggedUser.id = 0
        LoggedUser.username = ""
        LoggedUser.password = ""
        LoggedUser.gender = ""
    }
}