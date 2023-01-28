package com.example.mtoilet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.example.core.entities.LoggedUser
import com.example.core.entities.User
import com.example.repository.Repository

class Registration : AppCompatActivity() {

    private lateinit var userName: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword : EditText
    private lateinit var gender: String
    private lateinit var btnRegister: Button
    private lateinit var btnAlreadyHaveAccount : Button
    private lateinit var radioGroup: RadioGroup
    private lateinit var tVMessage : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        userName = findViewById(R.id.username)
        password = findViewById(R.id.password)
        confirmPassword = findViewById(R.id.confirmPassword)
        btnRegister = findViewById(R.id.createMyAccount)
        btnAlreadyHaveAccount = findViewById(R.id.i_have_account)
        radioGroup = findViewById(R.id.gender_radio_group)
        tVMessage = findViewById(R.id.register_message)

        var message = ""

        val repository = Repository()
        repository.getAllUsers(userName.text.toString(), this)

        btnRegister.setOnClickListener {
            val checkedButtonId: Int = radioGroup.checkedRadioButtonId

            if (!repository.checkInternetConnection(this)){
                message = "Please check your internet connection!"
                tVMessage.text = message
            }else{
                if (userName.text.toString() == "" || password.text.toString() == "" ||
                        confirmPassword.text.toString() == "" || checkedButtonId == -1){
                    message = "All fields are required!"
                    tVMessage.text = message
                }else{
                    message = checkPasswords()
                    if (message != ""){
                        tVMessage.text = message
                    }else{
                        message = checkUsername(userName.text.toString())
                        if (message != ""){
                            tVMessage.text = message
                        }else{
                            if (password.text.length < 6){
                                message = "Password must be at least 6 characters long!"
                                tVMessage.text = message
                            }else {
                                val choice: RadioButton = findViewById(checkedButtonId)
                                gender = choice.text.toString()
                                val newUser =
                                    User(1, userName.text.toString(), password.text.toString(), gender)
                                repository.postNewUser(newUser)

                                val intent = Intent(this, Home2::class.java)
                                startActivity(intent)
                            }
                        }
                    }
                }
            }
        }
        btnAlreadyHaveAccount.setOnClickListener{
            val intent2 = Intent(this, Login::class.java)
            startActivity(intent2)
            finish()
        }
    }
    private fun checkPasswords() : String{
        return if(password.text.toString() != confirmPassword.text.toString()){
            "Passwords don't match!"
        }else{
            ""
        }
    }
    private fun checkUsername(newUsername : String) : String{
        for (u in LoggedUser.allUsers) {
            if (u.username == newUsername) {
                return "Username is already taken! Please choose different username!"
            }
        }
        return ""
    }
}