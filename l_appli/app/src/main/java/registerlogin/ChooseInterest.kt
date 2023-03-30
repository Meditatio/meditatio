package registerlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import com.example.meditatio_appli.MainActivity
import com.example.meditatio_appli.R
import com.google.firebase.database.FirebaseDatabase
import models.User
import kotlin.collections.hashMapOf


class ChooseInterest : AppCompatActivity() {
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_interest)

        getSelection()
    }

    private fun getSelection(){
        val buttonMeditation = findViewById<ToggleButton>(R.id.button_meditation)
        val buttonBulking = findViewById<ToggleButton>(R.id.button_bulking)
        val buttonCutting = findViewById<ToggleButton>(R.id.button_cutting)
        val buttonValidate = findViewById<Button>(R.id.button_validate)
        buttonValidate.isEnabled = false;

        var meditationIsChosen = false;
        var bulkingIsChosen = false;
        var cuttingIsChosen = false;

        buttonMeditation.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                meditationIsChosen = true;
                bulkingIsChosen = false;
                buttonBulking.isChecked = false;
                cuttingIsChosen = false;
                buttonCutting.isChecked = false;
                buttonValidate.isEnabled = true;
            }
            else {
                if (cuttingIsChosen || bulkingIsChosen){
                    meditationIsChosen = false;
                }else{
                    buttonMeditation.isChecked = true;
                }
            }
        }
        buttonBulking.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                buttonValidate.isEnabled = true;
                bulkingIsChosen = true;
                meditationIsChosen = false;
                buttonMeditation.isChecked = false;
                cuttingIsChosen = false;
                buttonCutting.isChecked = false;
            } else {
                if (meditationIsChosen || cuttingIsChosen) {
                    bulkingIsChosen = false
                }else{
                    buttonBulking.isChecked = true;
                }
            }
        }
        buttonCutting.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                buttonValidate.isEnabled = true;
                cuttingIsChosen = true;
                meditationIsChosen = false;
                buttonMeditation.isChecked = false;
                bulkingIsChosen = false
                buttonBulking.isChecked = false;
            } else {
                if (meditationIsChosen || bulkingIsChosen){
                    cuttingIsChosen = false;
                }else{
                    buttonCutting.isChecked = true;
                }
            }
        }

        user = intent.getParcelableExtra("user") ?: throw IllegalArgumentException("User object is missing")

        buttonValidate.setOnClickListener(){
            if (bulkingIsChosen){
                onValidate(user, "bulking");
            } else if (meditationIsChosen){
                onValidate(user, "meditation");
            } else {
                onValidate(user, "cutting");
            }
        }
    }

    private fun onValidate(user: User, interest: String){
        user.interest = interest;
        val uid = user.uid;
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val updates = mapOf<String, Any?>(
            "interest" to interest
        )

        // Update the attribute in the database
        ref.updateChildren(updates as Map<String, Any?>)

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}

