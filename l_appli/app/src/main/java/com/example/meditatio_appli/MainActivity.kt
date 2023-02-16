package com.example.meditatio_appli

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.meditatio_appli.ui.theme.Meditatio_AppliTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Meditatio_AppliTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainContent()
                }
            }
        }
    }
}

@Composable
fun ValidateButton() { // function to show the validate button
    val context = LocalContext.current

    Button(
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            loadSecondPage(context)
        }) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Youtube Page"
        )
        Text(text = "Youtube Page")
    }
}

fun loadSecondPage(context: Context)
{
    Toast.makeText(context, "You go at the Youtube page", Toast.LENGTH_LONG).show()
    val intent = Intent(context, Youtube::class.java)
    context.startActivity(intent)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainContent() { // function to show wrap all the previous functions
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = { TopAppBar(title = {Text("Meditatio")},backgroundColor = MaterialTheme.colors.primary)  },

        drawerContent = { Text(text = "Drawer Menu 1") },

        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()))
            {


                Spacer(modifier = Modifier.height(50.dp))
                ValidateButton()
            }
        },
        //bottomBar = { BottomAppBar(backgroundColor = MaterialTheme.colors.primary) { Text("Bottom Bar") } }
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    Meditatio_AppliTheme {
        MainContent()
    }
}