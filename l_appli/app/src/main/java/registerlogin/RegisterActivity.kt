package registerlogin

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.meditatio_appli.*
import com.example.meditatio_appli.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import messages.LatestMessagesActivity
import models.User
import java.util.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        val registerButton = findViewById<Button>(R.id.register_button_register)
        registerButton.setOnClickListener {
            performRegister()
        }

        val alreadyHaveAccount = findViewById<TextView>(R.id.already_have_account_text_view)
        alreadyHaveAccount.setOnClickListener {
            Log.d("RegisterActivity", "Try to show login activity")

            //launch the login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
        val selectPhoto = findViewById<Button>(R.id.selectphoto_button_register)
        selectPhoto.setOnClickListener()
        {
            Log.d("RegisterActivity", "Try to show photo selector")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

var selectedPhotoUri: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == RESULT_OK && data != null)
        {
            //proceed and check what the selected image was...
            Log.d("RegisterActivity", "Photo was selected")

            //detect what is the picture selected
            selectedPhotoUri = data.data //represent the location of where the image is stored
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            val selectPhotoCircle = findViewById<CircleImageView>(R.id.selectphoto_imageview_register)

            selectPhotoCircle.setImageBitmap(bitmap)
            val selectPhotoButton = findViewById<Button>(R.id.selectphoto_button_register)
            selectPhotoButton.alpha = 0f

            /*val bitmapDrawable = BitmapDrawable(bitmap)
            val selectPhoto = findViewById<Button>(R.id.selectphoto_button_register)
            selectPhoto.setBackgroundDrawable(bitmapDrawable)*/
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

        Log.d("RegisterActivity", "Email is:" + email)
        Log.d("RegisterActivity", "Password is:" + password)

        //Firebase auth to create a user with email and pwd
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (!it.isSuccessful) return@addOnCompleteListener

                //else if successful
                Log.d("RegisterActivity","Successfully created user with uid: ${it.result.user?.uid}")

                uploadImageToFirebaseStorage()
            }
            .addOnFailureListener{
                Log.d("RegisterActivity", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Email and Password are required or the account already exists",
                    Toast.LENGTH_SHORT).show()
            }

    }

    private fun uploadImageToFirebaseStorage()
    {
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/image/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("RegisterActivity","Successfully uploaded image:${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    it.toString()
                    Log.d("RegisterActivity", "File location: $it")

                    saveUserToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener{
                Log.d("RegisterActivity", "Failed to upload image to storage: ${it.message}")
            }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String)
    {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val username = findViewById<TextView>(R.id.username_edittext_register)
        val user = User(uid, username.text.toString(), profileImageUrl,"","")
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "Saving the user to Firebase Database")
                val intent = Intent(this, ChooseInterest::class.java)
                intent.putExtra("user", user)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener{
                Log.d("RegisterActivity", "Failed to set value to the datebase: ${it.message}")
            }
    }
}