package com.example.mtoilet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.core.entities.User
import com.example.webservice.mToiletWebServiceAPICaller

class Registration : AppCompatActivity() {

    private lateinit var userName: EditText
    private lateinit var password: EditText
    private lateinit var gender: String
    private lateinit var btnRegister: Button
    private lateinit var radioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        userName = findViewById(R.id.username)
        password = findViewById(R.id.password)
        btnRegister = findViewById(R.id.createMyAccount)
        radioGroup = findViewById(R.id.gender_radio_group)

        btnRegister.setOnClickListener {
            val mToiletWebServiceAPICaller = mToiletWebServiceAPICaller()
            val checkedButtonId: Int = radioGroup.checkedRadioButtonId
            if(checkedButtonId != -1 && checkPasswords() && checkUsername()){
                val choice: RadioButton = findViewById(checkedButtonId)
                gender = choice.text.toString()
                val newUser = User(null, userName.text.toString(), password.text.toString(), gender)
                mToiletWebServiceAPICaller.postNewUser(newUser)

                val intent = Intent(this, Home::class.java)
                startActivity(intent)
            }
        }
    }
    private fun checkPasswords() : Boolean{
        val password: EditText = findViewById(R.id.password)
        val confirmPassword: EditText = findViewById(R.id.confirmPassword)
        return password.text.toString() == confirmPassword.text.toString()
    }
    private fun checkUsername() : Boolean{
        val username: EditText = findViewById(R.id.username)
        val caller = mToiletWebServiceAPICaller()
        caller.getAllUsers()
        for (u in caller.allUsers){
            if(u.username == username.text.toString()){
                return false
            }
        }
        return true
    }
}