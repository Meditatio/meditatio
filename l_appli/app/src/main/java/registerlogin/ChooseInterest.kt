package registerlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.ToggleButton
import com.example.meditatio_appli.MainActivity
import com.example.meditatio_appli.R
import com.google.firebase.database.FirebaseDatabase
import models.User


class ChooseInterest : AppCompatActivity() {
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_interest)

        getSelection()
    }

    private fun getSelection(){
        val buttonFitness = findViewById<ToggleButton>(R.id.button_fitness)
        val buttonBulking = findViewById<ToggleButton>(R.id.button_bulking)
        val buttonCutting = findViewById<ToggleButton>(R.id.button_cutting)
        val buttonValidate = findViewById<Button>(R.id.button_validate)
        buttonValidate.isEnabled = false;
        val buttonCoach = findViewById<Switch>(R.id.button_coach)

        var fitnessIsChosen = false;
        var bulkingIsChosen = false;
        var cuttingIsChosen = false;
        var coachIsChosen = false;

        buttonFitness.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                fitnessIsChosen = true;
                bulkingIsChosen = false;
                buttonBulking.isChecked = false;
                cuttingIsChosen = false;
                buttonCutting.isChecked = false;
                buttonValidate.isEnabled = true;
            }
            else {
                if (cuttingIsChosen || bulkingIsChosen){
                    fitnessIsChosen = false;
                }else{
                    buttonFitness.isChecked = true;
                }
            }
        }
        buttonBulking.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                buttonValidate.isEnabled = true;
                bulkingIsChosen = true;
                fitnessIsChosen = false;
                buttonFitness.isChecked = false;
                cuttingIsChosen = false;
                buttonCutting.isChecked = false;
            } else {
                if (fitnessIsChosen || cuttingIsChosen) {
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
                fitnessIsChosen = false;
                buttonFitness.isChecked = false;
                bulkingIsChosen = false
                buttonBulking.isChecked = false;
            } else {
                if (fitnessIsChosen || bulkingIsChosen){
                    cuttingIsChosen = false;
                }else{
                    buttonCutting.isChecked = true;
                }
            }
        }

        buttonCoach.setOnCheckedChangeListener { _, isChecked ->
            coachIsChosen = isChecked
        }

        user = intent.getParcelableExtra("user") ?: throw IllegalArgumentException("User object is missing")

        buttonValidate.setOnClickListener(){
            if (bulkingIsChosen){
                onValidate(user, "bulking", coachIsChosen);
            } else if (fitnessIsChosen){
                onValidate(user, "fitness",coachIsChosen);
            } else {
                onValidate(user, "cutting",coachIsChosen);
            }
        }
    }

    private fun onValidate(user: User, interest: String, coachBool: Boolean){
        val coach = if (coachBool) "true" else "false"
        user.interest = interest
        user.coach = coach
        val uid = user.uid;
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val updates = mapOf<String, Any?>(
            "interest" to interest,
            "coach" to coach
        )

        // Update the attribute in the database
        ref.updateChildren(updates as Map<String, Any?>)

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}

