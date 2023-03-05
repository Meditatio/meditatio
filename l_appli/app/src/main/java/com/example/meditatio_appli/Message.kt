package com.example.meditatio_appli

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.meditatio_appli.R

class Message : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        val registerButton = findViewById<Button>(R.id.register_button_register)
        registerButton.setOnClickListener{
            val emailEditText = findViewById<EditText>(R.id.email_edittext_register)
            val email = emailEditText.text.toString()
            val passwordEditText = findViewById<EditText>(R.id.password_edittext_register)
            val password = passwordEditText.text.toString()
            Log.d("MessageActivity", "Email is:" + email)
            Log.d("MessageActivity", "Password is:" + password)
        }

        val alreadyHaveAccount = findViewById<TextView>(R.id.already_have_account_text_view)
        alreadyHaveAccount.setOnClickListener{
            Log.d("MessageActivity", "Try to show login activity")

            //launch the login activity
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)

        }
    }
}