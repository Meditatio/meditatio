package settings

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.meditatio_appli.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import models.User
import notification.notificationID
import registerlogin.RegisterActivity
import kotlin.system.exitProcess


class SettingsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    var sharedPreferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
        if (key == "program") {
            val interest: String = if(prefs!!.getString("program", "") == "bulking") {
                "bulking"
                //intent = Intent(applicationContext, MainBulking::class.java)
            } else if (prefs.getString("program", "") == "cutting"){
                "cutting"
                //intent = Intent(applicationContext, MainCutting::class.java)
            } else{
                "fitness"
                //intent = Intent(applicationContext, MainFitness::class.java)
            }

            val uid = FirebaseAuth.getInstance().uid

            val interestRef = FirebaseDatabase.getInstance().getReference("/users/$uid/interest")
            interestRef.setValue(interest)


            intent = Intent(applicationContext, Launching::class.java)

            val pendingIntent = PendingIntent.getActivity(
                applicationContext,
                1,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager[AlarmManager.RTC, System.currentTimeMillis() + 100] = pendingIntent
            exitProcess(0)

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Settings";

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        prefs.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener)

    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            R.id.item1 -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, RegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSharedPreferenceChanged(prefs: SharedPreferences?, key: String?) {
        if (key == "program") {
            val intent: Intent
            val interest: String = if(prefs!!.getString("program", "") == "bulking") {
                "bulking"
                //intent = Intent(applicationContext, MainBulking::class.java)
            } else if (prefs.getString("program", "") == "cutting"){
                "cutting"
                //intent = Intent(applicationContext, MainCutting::class.java)
            } else{
                "fitness"
                //intent = Intent(applicationContext, MainFitness::class.java)
            }

            val uid = FirebaseAuth.getInstance().uid

            val interestRef = FirebaseDatabase.getInstance().getReference("https://meditatio-b6c2e-default-rtdb.firebaseio.com/users/$uid/interest")
            interestRef.setValue(interest)


            /*intent = Intent(applicationContext, Launching::class.java)

            val pendingIntent = PendingIntent.getActivity(
                applicationContext,
                1,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager[AlarmManager.RTC, System.currentTimeMillis() + 100] = pendingIntent
            exitProcess(0)*/

        }
    }
}