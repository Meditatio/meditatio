package com.example.meditatio_appli

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.preference.PreferenceManager
import capteurs.Objectifs
import com.example.meditatio_appli.ui.theme.Meditatio_AppliTheme
import messages.LatestMessagesActivity
import notification.*
import settings.SettingsActivity
import youtubes.YouTube1
import youtubes.YouTube2
import youtubes.Youtube
import java.util.*

class MainFitness : ComponentActivity() {
    private lateinit var youtube : ConstraintLayout
    private lateinit var objectif : ConstraintLayout
    private lateinit var messages : ConstraintLayout
    private lateinit var settings : ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fitness)

        youtube = findViewById<ConstraintLayout>(R.id.background1)
        youtube.setOnClickListener{loadYoutubePage(context = applicationContext)}
        objectif = findViewById<ConstraintLayout>(R.id.background2)
        objectif.setOnClickListener{ loadObjectifsPage(context = applicationContext) }
        messages = findViewById<ConstraintLayout>(R.id.background3)
        messages.setOnClickListener{ loadMessagePage(context = applicationContext) }
        settings = findViewById<ConstraintLayout>(R.id.background4)
        settings.setOnClickListener{ loadSettingsPage(context = applicationContext) }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        if(prefs.getBoolean("daily_notifications", true)) scheduleNotification()
        Log.d("TAG", "ee")
    }

    override fun onStart() {
        Log.d("TAG", "e")

        val notificationService = Intent(applicationContext, NotificationService::class.java)
        stopService(notificationService)
        super.onStart()
    }

    override fun onStop() {

        val notificationService = Intent(applicationContext, NotificationService::class.java)
        startService(notificationService)

        super.onStop()
    }

    private fun scheduleNotification() {
        val intent = Intent(applicationContext, Notification::class.java)
        val title = "Don't forget your daily exercise!"
        val message = "Did you do your exercise for the day? Remember to practice daily!"
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)
        val notificationHour = 21

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = Calendar.getInstance().apply {
            if (get(Calendar.HOUR_OF_DAY) >= notificationHour) {
                add(Calendar.DAY_OF_MONTH, 1)
            }

            set(Calendar.HOUR_OF_DAY, notificationHour)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Daily reminder"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }
}

private fun loadYoutubePage(context: Context)
{
    Toast.makeText(context, "You go at the Youtube page", Toast.LENGTH_LONG).show()
    val intent = Intent(context, Youtube::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
    context.startActivity(intent)
}

private fun loadMessagePage(context: Context)
{
    val intent = Intent(context, LatestMessagesActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
    context.startActivity(intent)
}

private fun loadSettingsPage(context: Context)
{
    val intent = Intent(context, SettingsActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
    context.startActivity(intent)
}

private fun loadObjectifsPage(context: Context)
{
    val intent = Intent(context, Objectifs::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
    context.startActivity(intent)
}
