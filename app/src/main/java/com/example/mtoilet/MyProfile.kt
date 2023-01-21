package com.example.mtoilet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.core.entities.LoggedUser
import com.example.core.entities.User

class MyProfile : AppCompatActivity() {
    lateinit var usernameText : EditText
    lateinit var passwordText : EditText
    lateinit var genderRadio : RadioGroup
    lateinit var saveChangedData : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        usernameText = findViewById(R.id.username)
        passwordText = findViewById(R.id.password)
        genderRadio = findViewById(R.id.gender_radio_group)
        saveChangedData = findViewById(R.id.createMyAccount)

        usernameText.setText(LoggedUser.username)
        passwordText.setText(LoggedUser.password)
        if (LoggedUser.gender == "Male"){
            val maleButton = findViewById<RadioButton>(R.id.radio_male)
            maleButton.isChecked = true
        }else{
            val femaleButton = findViewById<RadioButton>(R.id.radio_female)
            femaleButton.isChecked = true
        }

        saveChangedData.setOnClickListener {
            val checkedButtonId: Int = genderRadio.checkedRadioButtonId
            var gender : String = ""
            if(checkedButtonId != -1){                  //checkPasswords() && checkUsername()!!!
                val choice: RadioButton = findViewById(checkedButtonId)
                gender = choice.text.toString()
            }
            var userUpdated = User(LoggedUser.id, usernameText.text.toString(), passwordText.text.toString(), gender)
            //Toast(this)
            //updateaj ga!!! pozvati funkciju!!
            //finish()
        }
    }
}