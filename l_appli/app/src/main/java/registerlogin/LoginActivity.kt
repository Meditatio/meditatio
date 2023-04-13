package registerlogin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.meditatio_appli.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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


                    val fromId = FirebaseAuth.getInstance().uid
                    val ref = FirebaseDatabase.getInstance().getReference("/users/$fromId/interest/")
                    // Log.d("activity : ", ref.toString())

                    if (ref.toString() == "fitness")
                    {
                        val intent = Intent(this, MainFitness::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                    else if (ref.toString() == "cutting")
                    {
                        val intent = Intent(this, MainCutting::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                    else
                    {
                        val intent = Intent(this, MainBulking::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)

                    }

                    /* le code suivant n'ai pas dans le tuto !!!!
                     je l'ai ajouté pour pouvoir le login avec un compte déja créer pour test facilement
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)*/
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to log in: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
        val backToRegister = findViewById<TextView>(R.id.back_to_register_textview)
        backToRegister.setOnClickListener{
            finish()
        }
        val reset_password = findViewById<TextView>(R.id.reset_password)
        reset_password.setOnClickListener{
            Log.d("reset", "email to reset was send")
            val emailEditText = findViewById<EditText>(R.id.email_edittext_login)
            val email = emailEditText.text.toString()
            if (email.isEmpty())
            {
                Toast.makeText(this, "Please enter your email above", Toast.LENGTH_SHORT).show()

            }
            else
            {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                Toast.makeText(this, "An email has been sent to $email", Toast.LENGTH_SHORT).show()
            }

        }
    }

}