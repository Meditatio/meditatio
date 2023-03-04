package com.example.meditatio_appli

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class meditation : YouTubeBaseActivity() {

    private lateinit var icon : ImageView
    private lateinit var icon1 : ImageView
    private lateinit var title : TextView

    private val YOUTUBE_API_KEY = "AIzaSyA-gcsPXsUsTFq2ByPxdKUlI0IBd7aNQi8"

    private val VIDEO_ID_1 = "oMZeHZ7KgBA"
    private val VIDEO_ID_2 = "kcX7nzM70oA"
    private val VIDEO_ID_3 = "NUMifav8i6Y"
    private val VIDEO_ID_4 = "buDTNxAjQII"
    private val VIDEO_ID_5 = "QEWcTbm6OXk"

    private lateinit var youTubePlayer1: YouTubePlayerView
    private lateinit var youTubePlayer2: YouTubePlayerView
    private lateinit var youTubePlayer3: YouTubePlayerView
    private lateinit var youTubePlayer4: YouTubePlayerView
    private lateinit var youTubePlayer5: YouTubePlayerView

    private lateinit var miniature1 : ImageView
    private lateinit var miniature2 : ImageView
    private lateinit var miniature3 : ImageView
    private lateinit var miniature4 : ImageView
    private lateinit var miniature5 : ImageView

    lateinit var youtubePlayerInit1 : YouTubePlayer.OnInitializedListener
    lateinit var youtubePlayerInit2 : YouTubePlayer.OnInitializedListener
    lateinit var youtubePlayerInit3 : YouTubePlayer.OnInitializedListener
    lateinit var youtubePlayerInit4 : YouTubePlayer.OnInitializedListener
    lateinit var youtubePlayerInit5 : YouTubePlayer.OnInitializedListener


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meditation)


        title = findViewById(R.id.toolbar_title)
        icon = findViewById(R.id.icon)
        icon1 = findViewById(R.id.icon1)
        icon.setOnClickListener{loadFirstPage(applicationContext)}
        icon1.setOnClickListener{loadYoutubePage(applicationContext)}
        title.setText("Programme 1 (Bien être)")

        youTubePlayer1 = findViewById(R.id.youtubePlayer1)
        youTubePlayer2 = findViewById(R.id.youtubePlayer2)
        youTubePlayer3 = findViewById(R.id.youtubePlayer3)
        youTubePlayer4 = findViewById(R.id.youtubePlayer4)
        youTubePlayer5 = findViewById(R.id.youtubePlayer5)

        miniature1 = findViewById(R.id.miniature1)
        miniature2 = findViewById(R.id.miniature2)
        miniature3 = findViewById(R.id.miniature3)
        miniature4 = findViewById(R.id.miniature4)
        miniature5 = findViewById(R.id.miniature5)

        youtubePlayerInit1 = init(VIDEO_ID_1)
        youTubePlayer1.setOnClickListener {
            youTubePlayer1.initialize(YOUTUBE_API_KEY,youtubePlayerInit1)
            miniature1.visibility = View.INVISIBLE
        }
        youtubePlayerInit2 = init(VIDEO_ID_2)
        youTubePlayer2.setOnClickListener {
            youTubePlayer2.initialize(YOUTUBE_API_KEY,youtubePlayerInit2)
            miniature2.visibility = View.INVISIBLE
        }
        youtubePlayerInit3 = init(VIDEO_ID_3)
        youTubePlayer3.setOnClickListener {
            youTubePlayer3.initialize(YOUTUBE_API_KEY,youtubePlayerInit3)
            miniature3.visibility = View.INVISIBLE
        }
        youtubePlayerInit4 = init(VIDEO_ID_4)
        youTubePlayer4.setOnClickListener {
            youTubePlayer4.initialize(YOUTUBE_API_KEY,youtubePlayerInit4)
            miniature4.visibility = View.INVISIBLE
        }
        youtubePlayerInit5 = init(VIDEO_ID_5)
        youTubePlayer5.setOnClickListener {
            youTubePlayer5.initialize(YOUTUBE_API_KEY,youtubePlayerInit5)
            miniature5.visibility = View.INVISIBLE
        }

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
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
        context.startActivity(intent)
    }

    fun init(id : String) : YouTubePlayer.OnInitializedListener
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