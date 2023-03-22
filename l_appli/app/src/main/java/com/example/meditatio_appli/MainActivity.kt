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
import androidx.preference.PreferenceManager
import com.example.meditatio_appli.ui.theme.Meditatio_AppliTheme
import com.google.android.exoplayer2.util.NotificationUtil.createNotificationChannel
import notification.channelID
import notification.messageExtra
import notification.notificationID
import notification.titleExtra
import registerlogin.RegisterActivity
import settings.SettingsActivity
import youtubes.Meditation1
import youtubes.YouTube1
import youtubes.YouTube2
import youtubes.Youtube
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Meditatio_AppliTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainContent()
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        if(prefs.getBoolean("daily_notifications", true)) scheduleNotification()

    }

    private fun scheduleNotification() {
        val intent = Intent(applicationContext, notification.Notification::class.java)
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

@Composable
fun ValidateButton() { // function to show the validate button
    val context = LocalContext.current

    Button(
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            loadYoutubePage(context)
        }) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Programme 1 Page"
        )
        Text(text = "Programme 1 Page")
    }
}
@Composable
fun ValidateButton2() { // function to show the validate button
    val context = LocalContext.current

    Button(
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            loadMessagePage(context)
        }) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Message Page"
        )
        Text(text = "Message Page")
    }
}

@Composable
fun ValidateButton3() { // function to show the validate button
    val context = LocalContext.current

    Button(
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            loadYoutube1Page(context)
        }) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Programme 2 Page"
        )
        Text(text = "Programme 2 Page")
    }
}

@Composable
fun ValidateButton4() { // function to show the validate button
    val context = LocalContext.current

    Button(
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            loadYoutube2Page(context)
        }) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Programme 3 Page"
        )
        Text(text = "Programme 3 Page")
    }
}

@Composable
fun ValidateButton5() { // function to show the validate button
    val context = LocalContext.current

    Button(
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            loadSettingsPage(context)
        }) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Paramètres"
        )
        Text(text = "Paramètres")
    }
}

private fun loadYoutubePage(context: Context)
{
    Toast.makeText(context, "You go at the Youtube page", Toast.LENGTH_LONG).show()
    val intent = Intent(context, Youtube::class.java)
    context.startActivity(intent)
}

private fun loadYoutube1Page(context: Context)
{
    Toast.makeText(context, "You go at the Youtube page", Toast.LENGTH_LONG).show()
    val intent = Intent(context, YouTube1::class.java)
    context.startActivity(intent)
}

private fun loadYoutube2Page(context: Context)
{
    Toast.makeText(context, "You go at the Youtube page", Toast.LENGTH_LONG).show()
    val intent = Intent(context, YouTube2::class.java)
    context.startActivity(intent)
}

private fun loadMessagePage(context: Context)
{
    val intent = Intent(context, RegisterActivity::class.java)
    context.startActivity(intent)
}

private fun loadSettingsPage(context: Context)
{
    val intent = Intent(context, SettingsActivity::class.java)
    context.startActivity(intent)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainContent() { // function to show wrap all the previous functions
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,

        topBar = { TopAppBar(title = {Text("Meditatio")},backgroundColor = MaterialTheme.colors.primary)  },

        drawerContent = { Text(text = "Drawer Menu 1") },

        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()))
            {


                Spacer(modifier = Modifier.height(50.dp))
                ValidateButton()
                ValidateButton3()
                ValidateButton4()
                ValidateButton2()
                ValidateButton5()
            }
        },
        //bottomBar = { BottomAppBar(backgroundColor = MaterialTheme.colors.primary) { Text("Bottom Bar") } }
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    Meditatio_AppliTheme {
        MainContent()
    }
}