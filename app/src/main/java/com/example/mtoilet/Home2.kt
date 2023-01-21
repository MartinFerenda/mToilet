package com.example.mtoilet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Home2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)

        val loginButton = findViewById<Button>(R.id.qr_scan_button)
        loginButton.setOnClickListener {
            val intent = Intent(this, QRCodeScan::class.java)
            startActivity(intent)

            val myProfButton = findViewById<Button>(R.id.my_prof)
            myProfButton.setOnClickListener {
                val intent2 = Intent(this, MyProfile::class.java)
                startActivity(intent2)
            }
        }
    }
}