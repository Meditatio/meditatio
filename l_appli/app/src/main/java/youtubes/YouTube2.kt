package youtubes

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
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
import com.example.meditatio_appli.MainBulking
import com.example.meditatio_appli.R
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class YouTube2 : YouTubeBaseActivity() {

    private lateinit var icon : ImageView
    private lateinit var title : TextView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube2)

        toolbar = findViewById(R.id.toolbar3)
        setActionBar(toolbar)
        title = findViewById(R.id.toolbar_title)
        icon = findViewById(R.id.icon)
        icon.setOnClickListener{loadFirstPage(applicationContext)}
        title.setText("Bulking")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_meditation)
        {
            loadMeditationPage(this)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadMeditationPage(context: Context)
    {
        Toast.makeText(context, "You at at the méditation page", Toast.LENGTH_LONG).show()
        val intent = Intent(context, Meditation2::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
        context.startActivity(intent)
    }

    private fun loadFirstPage(context: Context)
    {
        Toast.makeText(context, "You return at the first page", Toast.LENGTH_LONG).show()
        val intent = Intent(context, MainBulking::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
        context.startActivity(intent)
    }
}