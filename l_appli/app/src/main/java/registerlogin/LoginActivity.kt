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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import messages.LatestMessagesActivity

class LoginActivity: AppCompatActivity() {
    private var interest: String? = null

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

                    val uid = it.result.user?.uid

                    // Créez une référence à l'objet utilisateur dans Firebase Realtime Database
                    val ref = FirebaseDatabase.getInstance().getReference("/users/$uid/interest/")

                    // Lire la valeur de l'intérêt de l'utilisateur
                    ref.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            // Récupérez la valeur de l'intérêt de l'utilisateur
                            interest = snapshot.getValue(String::class.java)
                            Log.d("Login", "activity : $interest")
                            callActivity(interest.toString())
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Gérer l'erreur de lecture de la base de données
                            Toast.makeText(this@LoginActivity, "Failed to read interest: ${error.message}", Toast.LENGTH_SHORT).show()
                        }
                    })



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

    fun callActivity(interest:String){
        if (interest == "fitness")
        {
            val intent = Intent(this, MainFitness::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        else if (interest == "cutting")
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
    }
}