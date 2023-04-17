package com.example.meditatio_appli

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import registerlogin.RegisterActivity

class Launching : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launching)

        mAuth = FirebaseAuth.getInstance()

        val currentUser: FirebaseUser? = mAuth.currentUser


        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            getInterest(currentUser)
        }else {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun getInterest(currentUser: FirebaseUser?){
        val userRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("users").child(currentUser!!.uid)

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var interest = dataSnapshot.child("interest").getValue(String::class.java)
                Log.d("Login", "interest : $interest")
                goToMain(interest!!)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun goToMain(interest:String){
        if (interest == "fitness")
        {
            val intent = Intent(this, MainFitness::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        else if (interest == "cutting")
        {
            val intent = Intent(this, MainCutting::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        else
        {
            val intent = Intent(this, MainBulking::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()

        }
    }
}