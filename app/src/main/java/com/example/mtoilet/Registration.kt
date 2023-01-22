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
import com.example.webservice.mToiletWebServiceAPICaller

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

        btnRegister.setOnClickListener {
            val mToiletWebServiceAPICaller = mToiletWebServiceAPICaller()
            val checkedButtonId: Int = radioGroup.checkedRadioButtonId

            if (userName.text.toString() == "" || password.text.toString() == "" ||
                    confirmPassword.text.toString() == "" || checkedButtonId == -1){
                message = "All fields are required!"
                tVMessage.text = message
            }else{
                message = checkPasswords()
                if (message != ""){
                    tVMessage.text = message
                }else{
                    message = checkUsername()
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
                            mToiletWebServiceAPICaller.postNewUser(newUser)

                            val intent = Intent(this, Home2::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
        btnAlreadyHaveAccount.setOnClickListener{
            val intent2 = Intent(this, Login::class.java)
            startActivity(intent2)
        }
    }
    private fun checkPasswords() : String{
        return if(password.text.toString() != confirmPassword.text.toString()){
            "Passwords don't match!"
        }else{
            ""
        }
    }
    private fun checkUsername() : String{
        val caller = mToiletWebServiceAPICaller()
        caller.getAllUsers(userName.text.toString())

        if(LoggedUser.foundInDatabase) {
            return "Username is already taken! Please choose different username!"
        }
        return ""
    }
}