package notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.preference.PreferenceManager
import com.example.meditatio_appli.MainActivity
import com.example.meditatio_appli.R

const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"

class Notification : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent){

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        val mainIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_IMMUTABLE)

        if(prefs.getBoolean("daily_notifications", true)) {
            val notification = NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(intent.getStringExtra(titleExtra))
                .setContentText(intent.getStringExtra(messageExtra))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(notificationID, notification)
        }
    }

}