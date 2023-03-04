package com.example.meditatio_appli

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.meditatio_appli.ui.theme.Meditatio_AppliTheme
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import javax.sql.DataSource

class YoutubePlay : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Meditatio_AppliTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainContent1()
                }
            }
        }
    }
}

@Composable
fun PlayVideo(context: Context, videoUrl : String)
{
    /*val player = SimpleExoPlayer.Builder(context).build()
    val playerView = PlayerView(context)
    val mediaItem = MediaItem.fromUri(videoUrl)
    val playWhenReady by rememberSaveable {
        mutableStateOf(true)
    }
    player.setMediaItem(mediaItem)
    playerView.player = player

    LaunchedEffect(player)
    {
        player.prepare()
        player.playWhenReady = playWhenReady
    }*/
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply{
            val dataSource = DefaultDataSource.Factory(context)
            val source = ProgressiveMediaSource.Factory(dataSource)
                .createMediaSource(MediaItem.fromUri(Uri.parse(videoUrl)))
            addMediaSource(source)
            prepare()
        }
    }
    AndroidView(factory = {
        PlayerView(it).apply {
            this.player = exoPlayer
            useController = true
            setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
        }
    })
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainContent1() { // function to show wrap all the previous functions
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar =
        {
            TopAppBar(title = {Text("Programme 1")},backgroundColor = MaterialTheme.colors.primary,
                navigationIcon = {
                    IconButton(onClick =
                    {
                        loadFirstPage(context)
                    })
                    {
                        Icon(
                            //imageVector = Icons.Default.AccountBox,
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Return",
                            modifier = Modifier.size(ButtonDefaults.IconSize))
                    }
                })
        },
        //topBar = { TopAppBar(title = {Text("Programme 1")},backgroundColor = MaterialTheme.colors.primary)  },

        drawerContent = { Text(text = "Drawer Menu 1") },

        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()))
            {
                /*PlayVideo(context = context, videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
                Spacer(modifier = Modifier.height(50.dp))*/
                PlayVideo(context = context, videoUrl = "https://www.youtube.com/watch?v=kJvl7GjjkI0")
                Spacer(modifier = Modifier.height(50.dp))
                PlayVideo(context = context, videoUrl = "https://www.youtube.com/watch?v=gqHEx5Sf_9c")
            }
        },
        //bottomBar = { BottomAppBar(backgroundColor = MaterialTheme.colors.primary) { Text("Bottom Bar") } }
    )
}

fun loadFirstPage(context: Context)
{
    Toast.makeText(context, "You return at the first page", Toast.LENGTH_LONG).show()
    val intent = Intent(context, MainActivity::class.java)
    //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
    context.startActivity(intent)
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {

    Meditatio_AppliTheme {
        MainContent1()
    }
}