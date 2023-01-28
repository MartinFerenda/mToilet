package com.example.mtoilet

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

class MyProfile : AppCompatActivity() {
    private lateinit var usernameText : EditText
    private lateinit var passwordText : EditText
    private lateinit var genderRadio : RadioGroup
    private lateinit var myProfileErrorMessage : TextView
    private lateinit var saveChangedData : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        usernameText = findViewById(R.id.username)
        passwordText = findViewById(R.id.password)
        genderRadio = findViewById(R.id.gender_radio_group)
        myProfileErrorMessage = findViewById(R.id.my_profile_message)
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

        val repository = Repository()
        repository.getAllUsers(usernameText.text.toString(), this)

        saveChangedData.setOnClickListener {
            val checkedButtonId: Int = genderRadio.checkedRadioButtonId
            var gender = ""
            if(checkedButtonId != -1){
                val choice: RadioButton = findViewById(checkedButtonId)
                gender = choice.text.toString()
            }
            if (checkUsername(usernameText.text.toString()) == "") {
                val userUpdated = User(
                    LoggedUser.id,
                    usernameText.text.toString(),
                    passwordText.text.toString(),
                    gender
                )
                repository.updateUserData(userUpdated)
                LoggedUser.username = usernameText.text.toString()
                LoggedUser.password = passwordText.text.toString()
                LoggedUser.gender = gender
                finish()
            }else{
                myProfileErrorMessage.text = "Username is already taken! Please choose different username!"
            }
        }
    }
    private fun checkUsername(newUsername : String) : String{
        for (u in LoggedUser.allUsers) {
            if (u.username == newUsername && u.id != LoggedUser.id) {
                return "Username is already taken! Please choose different username!"
            }
        }
        return ""
    }
}