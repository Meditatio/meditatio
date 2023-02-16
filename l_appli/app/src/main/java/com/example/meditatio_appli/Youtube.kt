package com.example.meditatio_appli

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class Youtube : YouTubeBaseActivity() {


    val VIDEO_ID = "vduKkK10eag"
    val YOUTUBE_API_KEY = "AIzaSyA-gcsPXsUsTFq2ByPxdKUlI0IBd7aNQi8"

    private lateinit var youTubePlayer: YouTubePlayerView
    private lateinit var btnPlay : Button
    private lateinit var btnReturn : Button

    lateinit var youtubePlayerInit : YouTubePlayer.OnInitializedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)

        youTubePlayer = findViewById(R.id.youtubePlayer)
        btnPlay = findViewById(R.id.btnPlay)
        btnReturn = findViewById(R.id.btnReturn)

        youtubePlayerInit = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1?.loadVideo(VIDEO_ID)
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(applicationContext,"Failed",Toast.LENGTH_SHORT).show()
            }
        }
        btnPlay.setOnClickListener{
            youTubePlayer.initialize(YOUTUBE_API_KEY,youtubePlayerInit)
        }
        btnReturn.setOnClickListener{
            loadFirstPage(applicationContext)
        }
    }
    fun loadFirstPage(context: Context)
    {
        Toast.makeText(context, "You return at the first page", Toast.LENGTH_LONG).show()
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
        context.startActivity(intent)
    }
}