package com.example.thesis_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thesis_app.ui.theme.BlueGreen
import com.example.thesis_app.ui.theme.DarkGreen
import com.example.thesis_app.ui.theme.DirtyWhite
import com.example.thesis_app.ui.theme.Slime
import com.example.thesis_app.ui.theme.alt
import com.example.thesis_app.ui.theme.captionFont
import com.example.thesis_app.ui.theme.titleFont

@Composable
fun fourthPage() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.b4),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize()
        )

        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, BlueGreen),
                        startY = 0f,
                        endY = 1500f
                    )
                )
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom
        ) {
            // text
            Text(
                text = buildAnnotatedString {
                    append("Whether you're a seasoned gym-goer or a complete beginner, ")
                    withStyle(style = SpanStyle(color = Slime, fontFamily = titleFont, fontSize = 20.sp)) {
                        append("Spot")
                    }
                    append(" is your ultimate gym buddy, guiding you every step of the ")
                    append("way towards your fitness goals.")
                },
                style = TextStyle(
                    fontFamily = captionFont,
                    fontSize = 16.sp,
                    lineHeight = 25.sp,
                    textAlign = TextAlign.Justify,
                    color = DirtyWhite
                ),
                modifier = Modifier.padding(start = 30.dp, end = 35.dp, bottom = 40.dp)
            )

            // Button
            Button(
                onClick = { /* Handle button click */ },
                colors = ButtonDefaults.buttonColors(Slime),
                modifier = Modifier
                    .padding(start = 30.dp, bottom = 50.dp, end = 30.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Let")
                        withStyle(style = SpanStyle(fontFamily = alt)) {
                            append("'")
                        }
                        append("s Get Fit")
                        withStyle(style = SpanStyle(fontFamily = alt)) {
                            append("!")
                        }
                    },
                    color = DarkGreen,
                    fontFamily = titleFont,
                    fontSize = 28.sp)
            }

        }
    }
}
