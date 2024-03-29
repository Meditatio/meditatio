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
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.meditatio_appli.*
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import messages.ChatLogActivity
import messages.LatestMessagesActivity
import models.User

class Objectifs : YouTubeBaseActivity() , SensorEventListener{


    private lateinit var list : ListView
    private lateinit var icon : ImageView
    private lateinit var title : TextView
    private lateinit var adapter : ArrayAdapter<String>
    private lateinit var array : Array<String>
    private lateinit var array1 : Array<Boolean>
    private lateinit var toolbar: Toolbar

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

    companion object{
        var currentUser: User? = null
        val TAG = "Objectifs"
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_objectifs)

        toolbar = findViewById(R.id.toolbar3)
        setActionBar(toolbar)
        array = arrayOf("Sport","Eat healthy","To take some fresh air","Meditation")
        array1 = arrayOf(false,false,false,false)
        adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,array)
        list = findViewById(R.id.listView_data)
        list.adapter = adapter
        circularProgressBar = findViewById(R.id.circularProgressBar)
        stepsTaken = findViewById(R.id.stepTaken)
        score = findViewById(R.id.Score)
        nbVerre = findViewById(R.id.nbVerre)
        buttonPlus = findViewById(R.id.buttonPlus)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        title = findViewById(R.id.toolbar_title)
        icon = findViewById(R.id.icon)
        icon.setOnClickListener{loadFirstPage(applicationContext)}
        title.setText("My Objectives")

        fetchCurrentUser()
        val uid = FirebaseAuth.getInstance().uid
        loadData()
        buttonPlus.setOnClickListener()
        {
            if (NbVerre < 10)
            {
                NbVerre++
                Score += 500
                saveData()
                score.setText("My Score : "+ Score.toString())
                nbVerre.setText(NbVerre.toString())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor == null)
        {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_objectif,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_done)
        {
            for (i in 0 until list.count) {
                if (list.isItemChecked(i))
                {
                    if(!array1[i])
                        Score+=1000
                    array1[i] = true
                    score.setText("My Score : "+ Score.toString())
                }
                else
                {
                    if(array1[i])
                        Score-=1000
                    array1[i] = false
                    score.setText("My Score : "+ Score.toString())
                }
                saveData()
            }
        }
        else if (item.itemId == R.id.item_reset)
        {
            totalSteps = 0
            Score = 0
            NbVerre = 0
            array1 = arrayOf(false,false,false,false)
            stepsTaken.setText("0")
            for (i in 0 until list.count) {
                list.setItemChecked(i,false)
            }
            score.setText("My Score : "+ Score.toString())
            nbVerre.setText("0")
            circularProgressBar.apply {
                setProgressWithAnimation(totalSteps.toFloat())
            }
            saveData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveData() {
        val uid = FirebaseAuth.getInstance().uid

        val step = FirebaseDatabase.getInstance().getReference("/users/$uid/step")
        step.setValue(totalSteps.toString())

        val score = FirebaseDatabase.getInstance().getReference("/users/$uid/score")
        score.setValue(Score.toString())

        val verre = FirebaseDatabase.getInstance().getReference("/users/$uid/verre")
        verre.setValue(NbVerre.toString())

        val sport = FirebaseDatabase.getInstance().getReference("/users/$uid/sport")
        sport.setValue(array1[0].toString())

        val eat = FirebaseDatabase.getInstance().getReference("/users/$uid/eat")
        eat.setValue(array1[1].toString())

        val fresh = FirebaseDatabase.getInstance().getReference("/users/$uid/fresh")
        fresh.setValue(array1[2].toString())

        val meditation = FirebaseDatabase.getInstance().getReference("/users/$uid/meditation")
        meditation.setValue(array1[3].toString())
    }

    private fun loadData() {
        val uid = FirebaseAuth.getInstance().uid
        val step = FirebaseDatabase.getInstance().getReference("/users/$uid/step")
        step.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(String::class.java)
                totalSteps = value.toString().toInt()
                stepsTaken.setText(totalSteps.toString())
                circularProgressBar.apply {
                    setProgressWithAnimation(totalSteps.toFloat())
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // handle error
            }
        })
        val score1 = FirebaseDatabase.getInstance().getReference("/users/$uid/score")
        score1.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(String::class.java)
                Score = value.toString().toInt()
                score.setText("My Score : "+ Score.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // handle error
            }
        })
        val verre = FirebaseDatabase.getInstance().getReference("/users/$uid/verre")
        verre.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(String::class.java)
                NbVerre = value.toString().toInt()
                nbVerre.setText(NbVerre.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // handle error
            }
        })

        val sport = FirebaseDatabase.getInstance().getReference("/users/$uid/sport")
        sport.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(String::class.java)
                array1[0] = value.toString().toBoolean()
                list.setItemChecked(0,array1[0])
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // handle error
            }
        })

        val eat = FirebaseDatabase.getInstance().getReference("/users/$uid/eat")
        eat.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(String::class.java)
                array1[1] = value.toString().toBoolean()
                list.setItemChecked(1,array1[1])
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // handle error
            }
        })

        val fresh = FirebaseDatabase.getInstance().getReference("/users/$uid/fresh")
        fresh.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(String::class.java)
                array1[2] = value.toString().toBoolean()
                list.setItemChecked(2,array1[2])
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // handle error
            }
        })

        val meditation = FirebaseDatabase.getInstance().getReference("/users/$uid/meditation")
        meditation.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(String::class.java)
                array1[3] = value.toString().toBoolean()
                list.setItemChecked(3,array1[3])
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // handle error
            }
        })
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    private fun fetchCurrentUser(){
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                Objectifs.currentUser = p0.getValue(User::class.java)
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }
    private fun loadFirstPage(context: Context)
    {
        if (currentUser?.interest.toString() == "cutting")
        {
            Toast.makeText(context, "You return at the first page", Toast.LENGTH_LONG).show()
            val intent = Intent(context, MainCutting::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
            context.startActivity(intent)
        }
        else if (currentUser?.interest.toString() == "bulking")
        {
            Toast.makeText(context, "You return at the first page", Toast.LENGTH_LONG).show()
            val intent = Intent(context, MainBulking::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
            context.startActivity(intent)
        }
        else
        {
            Toast.makeText(context, "You return at the first page", Toast.LENGTH_LONG).show()
            val intent = Intent(context, MainFitness::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
            context.startActivity(intent)
        }
    }

}