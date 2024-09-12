package com.example.thesis_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thesis_app.ui.theme.BlueGreen
import com.example.thesis_app.ui.theme.DarkGreen
import com.example.thesis_app.ui.theme.DirtyWhite
import com.example.thesis_app.ui.theme.Slime
import com.example.thesis_app.ui.theme.alt
import com.example.thesis_app.ui.theme.captionFont
import com.example.thesis_app.ui.theme.titleFont

@Composable
fun Welcome(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGreen)
    ) {
        // Content
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, bottom = 15.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Welcome to",
                    style = TextStyle(
                        fontFamily = captionFont,
                        fontSize = 30.sp,
                        color = DirtyWhite
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("Spot")
                            withStyle(style = SpanStyle(fontFamily = alt)) {
                                append("!")
                            }
                        },
                        color = Slime,
                        fontFamily = titleFont,
                        fontSize = 33.sp
                    )

                    Image(
                        painter = painterResource(id = R.drawable.spot_avatar),
                        contentDescription = "Spot logo",
                        modifier = Modifier
                            .size(80.dp)
                            .offset(y = (-23).dp )
                    )
                }
            }

            // Additional text
            Text(
                text = "Upon continuing your account creation, you will be asked multiple questions about your physical attributes to be analyzed by our Physical Therapist-verified algorithm. \n" +
                        "\n" +
                        "Once accomplished, a personalized list of equipment alongside its exercises will be recommended for you.",
                style = TextStyle(
                    fontFamily = captionFont,
                    fontSize = 16.sp,
                    lineHeight = 25.sp,
                    textAlign = TextAlign.Justify,
                    color = DirtyWhite
                ),
                modifier = Modifier.padding(top = 20.dp, start = 30.dp, end = 35.dp, bottom = 50.dp)
            )

            // Button
            Button(
                onClick = {
                    navController.navigate("bmi")
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

