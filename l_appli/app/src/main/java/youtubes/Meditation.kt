package youtubes

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.preference.PreferenceManager
import com.example.meditatio_appli.MainActivity
import com.example.meditatio_appli.MainFitness
import com.example.meditatio_appli.R
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class meditation : YouTubeBaseActivity() {

    private lateinit var icon : ImageView
    private lateinit var toolbar: Toolbar
    private lateinit var title : TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meditation)


        toolbar = findViewById(R.id.toolbar3)
        setActionBar(toolbar)
        title = findViewById(R.id.toolbar_title)
        icon = findViewById(R.id.icon)
        icon.setOnClickListener{loadFirstPage(applicationContext)}
        title.setText("Welfare")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_fitness)
        {
            loadYoutubePage(this)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadYoutubePage(context: Context)
    {
        Toast.makeText(context, "You go at the Youtube page", Toast.LENGTH_LONG).show()
        val intent = Intent(context, Youtube::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
        context.startActivity(intent)
    }

    private fun loadFirstPage(context: Context)
    {
        Toast.makeText(context, "You return at the first page", Toast.LENGTH_LONG).show()
        val intent = Intent(context, MainFitness::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
        context.startActivity(intent)
    }
}