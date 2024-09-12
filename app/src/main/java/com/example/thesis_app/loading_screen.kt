package com.example.thesis_app

import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.thesis_app.ui.theme.BlueGreen
import kotlinx.coroutines.delay

@Composable
fun loadingScreen(navController: NavController) {
    val context = LocalContext.current
    val videoUri = "android.resource://${context.packageName}/raw/spot_loading"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGreen),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Container to control the size and position of the VideoView
        Box(
            modifier = Modifier
                .size(150.dp) // Set the desired size here
                .background(BlueGreen),
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                factory = { context ->
                    VideoView(context).apply {
                        setVideoURI(Uri.parse(videoUri))
                        setMediaController(MediaController(context).also {
                            it.setAnchorView(this)
                            it.setMediaPlayer(this)
                        })
                        start()
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    LaunchedEffect(Unit) {
        delay(1000)
        navController.navigate("third")
    }
}
