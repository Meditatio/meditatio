package registerlogin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.meditatio_appli.R
import com.google.firebase.auth.FirebaseAuth
import messages.LatestMessagesActivity

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

                    // le code suivant n'ai pas dans le tuto !!!!
                    // je l'ai ajouté pour pouvoir le login avec un compte déja créer pour test facilement
                    val intent = Intent(this, LatestMessagesActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
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