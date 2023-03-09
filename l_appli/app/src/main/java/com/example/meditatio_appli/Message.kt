package com.example.meditatio_appli

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.meditatio_appli.R
import com.google.firebase.auth.FirebaseAuth

class Message : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        val registerButton = findViewById<Button>(R.id.register_button_register)
        registerButton.setOnClickListener{
            performRegister()
        }

        val alreadyHaveAccount = findViewById<TextView>(R.id.already_have_account_text_view)
        alreadyHaveAccount.setOnClickListener{
            Log.d("MessageActivity", "Try to show login activity")

            //launch the login activity
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)

        }
    }

    private fun performRegister(){
        val emailEditText = findViewById<EditText>(R.id.email_edittext_register)
        val email = emailEditText.text.toString()
        val passwordEditText = findViewById<EditText>(R.id.password_edittext_register)
        val password = passwordEditText.text.toString()

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Email and Password are required",
                Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("MessageActivity", "Email is:" + email)
        Log.d("MessageActivity", "Password is:" + password)

        //Firebase auth to create a user with email and pwd
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (!it.isSuccessful) return@addOnCompleteListener

                //else if successful
                Log.d("Message","Successfully created user with uid: ${it.result.user?.uid}")
            }
            .addOnFailureListener{
                Log.d("Message", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Email and Password are required",
                    Toast.LENGTH_SHORT).show()
            }
    }
}