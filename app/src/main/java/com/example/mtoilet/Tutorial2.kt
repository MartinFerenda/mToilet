package com.example.mtoilet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Tutorial2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial2)

        val nextTutorial2Button = findViewById<Button>(R.id.nextTutorial2)
        val backTutorial2Button = findViewById<Button>(R.id.backTutorial2)
        nextTutorial2Button.setOnClickListener {
            val intentNext = Intent(this, Tutorial3::class.java)
            startActivity(intentNext)
        }
        backTutorial2Button.setOnClickListener {
            this.finish()
        }
    }
}