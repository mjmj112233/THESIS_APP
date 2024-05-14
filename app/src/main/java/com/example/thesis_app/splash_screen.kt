package com.example.thesis_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thesis_app.ui.theme.BlueGreen
import com.example.thesis_app.ui.theme.DirtyWhite
import com.example.thesis_app.ui.theme.Slime
import com.example.thesis_app.ui.theme.captionFont
import com.example.thesis_app.ui.theme.titleFont

@Composable
fun splashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGreen),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.sample_logo),
            contentDescription = "Test Logo",
            modifier = Modifier.size(128.dp)
        )

        Spacer(modifier = Modifier.height(1.dp))

        Text(
            text = "Spot",
            style = TextStyle(
                fontFamily = titleFont,
                fontSize = 40.sp,
                color = Slime
            )
        )

        Text(
            text = "Your Smart Fitness Buddy",
            style = TextStyle(
                fontFamily = captionFont,
                fontSize = 15.sp,
                color = DirtyWhite
            )
        )
    }
}
