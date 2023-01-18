package com.example.mtoilet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Tutorial1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial1)

        val nextTutorial1Button = findViewById<Button>(R.id.nextTutorial1)
       nextTutorial1Button.setOnClickListener {
           val intent = Intent(this, Tutorial2::class.java)
           startActivity(intent)
       }
    }
}