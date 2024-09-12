package com.example.thesis_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thesis_app.ui.theme.BlueGreen
import com.example.thesis_app.ui.theme.DarkGreen
import com.example.thesis_app.ui.theme.DirtyWhite
import com.example.thesis_app.ui.theme.Slime
import com.example.thesis_app.ui.theme.captionFont
import com.example.thesis_app.ui.theme.titleFont

@Composable
fun secondPage(navController: NavController) {
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
                        endY = 1300f
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
            Text(
                text = buildAnnotatedString {
                    append("With ")
                    withStyle(style = SpanStyle(color = Slime, fontFamily = titleFont, fontSize = 20.sp)) {
                        append("Spot’s")
                    }
                    append(" algorithm, all you need to do is provide your BMI to know what exercises best fit you!  ")
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
                onClick = {
                    navController.navigate("fifth")
                },
                colors = ButtonDefaults.buttonColors(Slime),
                modifier = Modifier
                    .padding(start = 30.dp, bottom = 50.dp, end = 30.dp)
                    .fillMaxWidth()
            ) {
                Text("Continue", color = DarkGreen, fontFamily = titleFont, fontSize = 28.sp)
            }
        }
    }
}
