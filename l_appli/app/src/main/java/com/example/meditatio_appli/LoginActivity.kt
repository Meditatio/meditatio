package com.example.meditatio_appli

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.login_button_login)
        loginButton.setOnClickListener{
            val emailEditText = findViewById<EditText>(R.id.email_edittext_login)
            val email = emailEditText.text.toString()
            val passwordEditText = findViewById<EditText>(R.id.password_edittext_login)
            val password = passwordEditText.text.toString()

            Log.d("Login", "Attempt login with email/pw: $email/***")

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener

                    Log.d("Login", "Successfully logged in: ${it.result.user?.uid}")
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to log in: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
        val backToRegister = findViewById<TextView>(R.id.back_to_register_textview)
        backToRegister.setOnClickListener{
            finish()
        }
    }

}