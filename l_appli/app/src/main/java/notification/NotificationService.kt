package notification

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.Executor

class NotificationService : Service() {

    var timer : Timer? = null
    var timerTask : TimerTask? = null
    val timerSeconds : Long = 5
    val handler = Handler(Looper.getMainLooper())


    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.d("TAG", "message")

        handler.postDelayed({ sendNotification() }, timerSeconds*1000)

        return Service.START_STICKY
    }

    override fun onDestroy() {

        handler.removeCallbacks { sendNotification() }
        super.onDestroy()
    }

    private fun sendNotification() {

        val intent = Intent(applicationContext, notification.Notification::class.java)
        val title = "Hey, it's been a while!"
        val message = "We haven't seen you in a while, don't forget to practice regularly!"
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)
        sendBroadcast(intent)
    }
}