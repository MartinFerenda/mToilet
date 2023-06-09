package com.example.mtoilet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Tutorial4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial4)

        val nextTutorial4Button = findViewById<Button>(R.id.nextTutorial4)
        val backTutorial4Button = findViewById<Button>(R.id.backTutorial4)

        nextTutorial4Button.setOnClickListener {
            val intentNext = Intent(this, Registration::class.java)
            startActivity(intentNext)
        }
        backTutorial4Button.setOnClickListener {
            this.finish()
        }
    }
}