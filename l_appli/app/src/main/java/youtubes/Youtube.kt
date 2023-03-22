package youtubes

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.example.meditatio_appli.MainActivity
import com.example.meditatio_appli.R
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class Youtube : YouTubeBaseActivity() {

    private lateinit var icon : ImageView
    private lateinit var icon1 : ImageView
    private lateinit var title : TextView

    private val YOUTUBE_API_KEY = "AIzaSyA-gcsPXsUsTFq2ByPxdKUlI0IBd7aNQi8"

    private val VIDEO_ID_1 = "1Ls6ImJBXVY"
    private val VIDEO_ID_2 = "64sCja7s6lw"
    private val VIDEO_ID_3 = "aAy9ThYZqQY"
    private val VIDEO_ID_4 = "2yPSQnnLNDc"
    private val VIDEO_ID_5 = "_p9gauFHW4k"
    private val VIDEO_ID_6 = "aI9VbuedMLI"
    private val VIDEO_ID_7 = "KoD1uenU6go"
    private val VIDEO_ID_8 = "bzuS_6e1Toc"
    private val VIDEO_ID_9 = "tbqAmEYuyI4"
    private val VIDEO_ID_10 = "1falX-nelgY"

    private lateinit var youTubePlayer1: YouTubePlayerView
    private lateinit var youTubePlayer2: YouTubePlayerView
    private lateinit var youTubePlayer3: YouTubePlayerView
    private lateinit var youTubePlayer4: YouTubePlayerView
    private lateinit var youTubePlayer5: YouTubePlayerView
    private lateinit var youTubePlayer6: YouTubePlayerView
    private lateinit var youTubePlayer7: YouTubePlayerView
    private lateinit var youTubePlayer8: YouTubePlayerView
    private lateinit var youTubePlayer9: YouTubePlayerView
    private lateinit var youTubePlayer10: YouTubePlayerView
    private lateinit var miniature1 : ImageView
    private lateinit var miniature2 : ImageView
    private lateinit var miniature3 : ImageView
    private lateinit var miniature4 : ImageView
    private lateinit var miniature5 : ImageView
    private lateinit var miniature6 : ImageView
    private lateinit var miniature7 : ImageView
    private lateinit var miniature8 : ImageView
    private lateinit var miniature9 : ImageView
    private lateinit var miniature10 : ImageView

    private lateinit var youtubePlayerInit1 : YouTubePlayer.OnInitializedListener
    private lateinit var youtubePlayerInit2 : YouTubePlayer.OnInitializedListener
    private lateinit var youtubePlayerInit3 : YouTubePlayer.OnInitializedListener
    private lateinit var youtubePlayerInit4 : YouTubePlayer.OnInitializedListener
    private lateinit var youtubePlayerInit5 : YouTubePlayer.OnInitializedListener
    private lateinit var youtubePlayerInit6 : YouTubePlayer.OnInitializedListener
    private lateinit var youtubePlayerInit7 : YouTubePlayer.OnInitializedListener
    private lateinit var youtubePlayerInit8 : YouTubePlayer.OnInitializedListener
    private lateinit var youtubePlayerInit9 : YouTubePlayer.OnInitializedListener
    private lateinit var youtubePlayerInit10 : YouTubePlayer.OnInitializedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)

        title = findViewById(R.id.toolbar_title)
        icon = findViewById(R.id.icon)
        icon1 = findViewById(R.id.icon1)
        icon.setOnClickListener{loadFirstPage(applicationContext)}
        icon1.setOnClickListener{loadMeditationPage(applicationContext)}
        title.setText("Programme 1 (Remise en Forme)")

        youTubePlayer1 = findViewById(R.id.youtubePlayer1)
        youTubePlayer2 = findViewById(R.id.youtubePlayer2)
        youTubePlayer3 = findViewById(R.id.youtubePlayer3)
        youTubePlayer4 = findViewById(R.id.youtubePlayer4)
        youTubePlayer5 = findViewById(R.id.youtubePlayer5)
        youTubePlayer6 = findViewById(R.id.youtubePlayer6)
        youTubePlayer7 = findViewById(R.id.youtubePlayer7)
        youTubePlayer8 = findViewById(R.id.youtubePlayer8)
        youTubePlayer9 = findViewById(R.id.youtubePlayer9)
        youTubePlayer10 = findViewById(R.id.youtubePlayer10)
        miniature1 = findViewById(R.id.miniature1)
        miniature2 = findViewById(R.id.miniature2)
        miniature3 = findViewById(R.id.miniature3)
        miniature4 = findViewById(R.id.miniature4)
        miniature5 = findViewById(R.id.miniature5)
        miniature6 = findViewById(R.id.miniature6)
        miniature7 = findViewById(R.id.miniature7)
        miniature8 = findViewById(R.id.miniature8)
        miniature9 = findViewById(R.id.miniature9)
        miniature10 = findViewById(R.id.miniature10)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        if(prefs.getBoolean("youtube_in_app", true)) {

            youtubePlayerInit1 = init(VIDEO_ID_1)
            youTubePlayer1.setOnClickListener {
                youTubePlayer1.initialize(YOUTUBE_API_KEY, youtubePlayerInit1)
                miniature1.visibility = View.INVISIBLE
            }
            youtubePlayerInit2 = init(VIDEO_ID_2)
            youTubePlayer2.setOnClickListener {
                youTubePlayer2.initialize(YOUTUBE_API_KEY, youtubePlayerInit2)
                miniature2.visibility = View.INVISIBLE
            }
            youtubePlayerInit3 = init(VIDEO_ID_3)
            youTubePlayer3.setOnClickListener {
                youTubePlayer3.initialize(YOUTUBE_API_KEY, youtubePlayerInit3)
                miniature3.visibility = View.INVISIBLE
            }
            youtubePlayerInit4 = init(VIDEO_ID_4)
            youTubePlayer4.setOnClickListener {
                youTubePlayer4.initialize(YOUTUBE_API_KEY, youtubePlayerInit4)
                miniature4.visibility = View.INVISIBLE
            }
            youtubePlayerInit5 = init(VIDEO_ID_5)
            youTubePlayer5.setOnClickListener {
                youTubePlayer5.initialize(YOUTUBE_API_KEY, youtubePlayerInit5)
                miniature5.visibility = View.INVISIBLE
            }
            youtubePlayerInit6 = init(VIDEO_ID_6)
            youTubePlayer6.setOnClickListener {
                youTubePlayer6.initialize(YOUTUBE_API_KEY, youtubePlayerInit6)
                miniature6.visibility = View.INVISIBLE
            }
            youtubePlayerInit7 = init(VIDEO_ID_7)
            youTubePlayer7.setOnClickListener {
                youTubePlayer7.initialize(YOUTUBE_API_KEY, youtubePlayerInit7)
                miniature7.visibility = View.INVISIBLE
            }
            youtubePlayerInit8 = init(VIDEO_ID_8)
            youTubePlayer8.setOnClickListener {
                youTubePlayer8.initialize(YOUTUBE_API_KEY, youtubePlayerInit8)
                miniature8.visibility = View.INVISIBLE
            }
            youtubePlayerInit9 = init(VIDEO_ID_9)
            youTubePlayer9.setOnClickListener {
                youTubePlayer9.initialize(YOUTUBE_API_KEY, youtubePlayerInit9)
                miniature9.visibility = View.INVISIBLE
            }
            youtubePlayerInit10 = init(VIDEO_ID_10)
            youTubePlayer10.setOnClickListener {
                youTubePlayer10.initialize(YOUTUBE_API_KEY, youtubePlayerInit10)
                miniature10.visibility = View.INVISIBLE
            }

        }
        else{

            youTubePlayer1.setOnClickListener {
                val uri: Uri = Uri.parse("https://youtu.be/$VIDEO_ID_1")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            youTubePlayer2.setOnClickListener {
                val uri: Uri = Uri.parse("https://youtu.be/$VIDEO_ID_2")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            youTubePlayer3.setOnClickListener {
                val uri: Uri = Uri.parse("https://youtu.be/$VIDEO_ID_3")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            youTubePlayer4.setOnClickListener {
                val uri: Uri = Uri.parse("https://youtu.be/$VIDEO_ID_4")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            youTubePlayer5.setOnClickListener {
                val uri: Uri = Uri.parse("https://youtu.be/$VIDEO_ID_5")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            youTubePlayer6.setOnClickListener {
                val uri: Uri = Uri.parse("https://youtu.be/$VIDEO_ID_6")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            youTubePlayer7.setOnClickListener {
                val uri: Uri = Uri.parse("https://youtu.be/$VIDEO_ID_7")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            youTubePlayer8.setOnClickListener {
                val uri: Uri = Uri.parse("https://youtu.be/$VIDEO_ID_8")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            youTubePlayer9.setOnClickListener {
                val uri: Uri = Uri.parse("https://youtu.be/$VIDEO_ID_9")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            youTubePlayer10.setOnClickListener {
                val uri: Uri = Uri.parse("https://youtu.be/$VIDEO_ID_10")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        }
    }

    private fun loadMeditationPage(context: Context)
    {
        Toast.makeText(context, "You at at the méditation page", Toast.LENGTH_LONG).show()
        val intent = Intent(context, meditation::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
        context.startActivity(intent)
    }

    private fun loadFirstPage(context: Context)
    {
        Toast.makeText(context, "You return at the first page", Toast.LENGTH_LONG).show()
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
        context.startActivity(intent)
    }

    private fun init(id : String) : YouTubePlayer.OnInitializedListener
    {
        val youtubePlayerInit = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1?.loadVideo(id)
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(applicationContext,"Failed",Toast.LENGTH_SHORT).show()
            }
        }
        return youtubePlayerInit
    }
}
