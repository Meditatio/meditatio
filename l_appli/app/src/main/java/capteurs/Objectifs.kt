package capteurs

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.meditatio_appli.MainActivity
import com.example.meditatio_appli.R
import com.google.android.youtube.player.YouTubeBaseActivity
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import messages.ChatLogActivity

class Objectifs : YouTubeBaseActivity() , SensorEventListener{


    private lateinit var icon : ImageView
    private lateinit var icon1 : ImageView
    private lateinit var title : TextView
    private lateinit var reset : ImageView

    private var sensorManager : SensorManager? = null

    private var running = false
    private var totalSteps = 0
    var Score = 0
    private var NbVerre = 0

    private lateinit var stepsTaken : TextView
    private lateinit var score : TextView
    private lateinit var nbVerre : TextView
    private lateinit var buttonPlus : ImageView
    private lateinit var circularProgressBar : CircularProgressBar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_objectifs)

        circularProgressBar = findViewById(R.id.circularProgressBar)
        stepsTaken = findViewById(R.id.stepTaken)
        score = findViewById(R.id.Score)
        reset = findViewById(R.id.reset)
        nbVerre = findViewById(R.id.nbVerre)
        buttonPlus = findViewById(R.id.buttonPlus)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        title = findViewById(R.id.toolbar_title)
        icon = findViewById(R.id.icon)
        icon1 = findViewById(R.id.icon1)
        icon1.visibility = View.INVISIBLE
        icon.setOnClickListener{loadFirstPage(applicationContext)}
        title.setText("My Objectives")

        loadData()
        buttonPlus.setOnClickListener()
        {
            if (NbVerre < 9)
            {
                NbVerre++
                Score += 500
                saveData()
                score.setText("My Score : "+ Score.toString())
                nbVerre.setText(NbVerre.toString())
            }
        }
        reset.setOnClickListener()
        {
            totalSteps = 0
            Score = 0
            NbVerre = 0
            stepsTaken.setText("0")
            score.setText("My Score : "+ Score.toString())
            nbVerre.setText("0")
            saveData()
        }
    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor == null)
        {
            Log.d(ChatLogActivity.TAG, "No sensor")
            Toast.makeText(this, "No Sensor detected on this Device", Toast.LENGTH_LONG).show()
        }
        else
            sensorManager?.registerListener(this,stepSensor,SensorManager.SENSOR_DELAY_UI)

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (running)
            totalSteps = p0!!.values[0].toInt()
        if (totalSteps >= 10000){
            Score+=5000
            score.setText("My Score : "+ Score.toString())
        }
        stepsTaken.setText(totalSteps.toString())
        saveData()
        circularProgressBar.apply {
            setProgressWithAnimation(totalSteps.toFloat())
        }

    }

    fun getContext() : Context
    {
        return this
    }

    private fun saveData() {
        val sharedPreferences = getSharedPreferences("myPrefs",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("key1",totalSteps.toFloat())
        editor.putFloat("key2",Score.toFloat())
        editor.putFloat("key3",NbVerre.toFloat())
        editor.apply()
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences("myPrefs",Context.MODE_PRIVATE)
        val saveNumber = sharedPreferences.getFloat("key1",0f)
        val saveNumber1 = sharedPreferences.getFloat("key2",0f)
        val saveNumber2 = sharedPreferences.getFloat("key3",0f)
        totalSteps = saveNumber.toInt()
        Score = saveNumber1.toInt()
        NbVerre = saveNumber2.toInt()
        score.setText("My Score : "+ Score.toString())
        nbVerre.setText(NbVerre.toString())
        stepsTaken.setText(totalSteps.toString())

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    private fun loadFirstPage(context: Context)
    {
        Toast.makeText(context, "You return at the first page", Toast.LENGTH_LONG).show()
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
        context.startActivity(intent)
    }
}