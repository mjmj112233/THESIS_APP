package com.example.thesis_app

import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.thesis_app.ui.theme.BlueGreen

@Composable
fun loadingScreen() {
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

                        // Setup the MediaController
                        val mediaController = MediaController(context)
                        mediaController.setAnchorView(this)
                        setMediaController(mediaController)

                        // Start the video
                        start()

                        // Make sure to loop the video if desired
                        setOnCompletionListener {
                            seekTo(0)
                            start()
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}