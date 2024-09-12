package com.example.thesis_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thesis_app.ui.theme.Blackk
import com.example.thesis_app.ui.theme.BlueGreen
import com.example.thesis_app.ui.theme.DarkGreen
import com.example.thesis_app.ui.theme.DirtyWhite
import com.example.thesis_app.ui.theme.Slime
import com.example.thesis_app.ui.theme.alt
import com.example.thesis_app.ui.theme.captionFont
import com.example.thesis_app.ui.theme.titleFont

@Composable
fun SexScreen(navController: NavController) {
    val male = "Male"
    val female = "Female"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGreen)
    ) {

    }

    // Header
    Box(
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp, top = 40.dp)
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 20.dp)
            ) {
                // Text container
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .height(60.dp)
                        .fillMaxWidth()
                        .background(Slime)
                        .padding(start = 20.dp)
                        .offset(x = 20.dp)
                ) {
                    Text(
                        text = "Biological Sex",
                        color = DirtyWhite,
                        style = TextStyle(fontFamily = titleFont, fontSize = 24.sp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Circle
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(shape = RoundedCornerShape(50.dp))
                    .background(Slime)
                    .align(Alignment.CenterStart)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.spot_logo_white),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(70.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 50.dp, end = 50.dp)
            .padding(top = 340.dp), // Adjust top padding as needed
        verticalArrangement = Arrangement.spacedBy(50.dp) // Space between rows
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly // Distribute space evenly
        ) {
            // Male
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Circle above Male label
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Slime)
                        .padding(8.dp)
                        .wrapContentSize(Alignment.Center) // Center content
                ) {
                    Icon(
                        painter = painterResource(R.drawable.male),
                        contentDescription = null,
                        tint = Color(0xFF036D19),
                        modifier = Modifier
                            .size(64.dp) // Adjust size as needed
                            .align(Alignment.Center) // Center icon within the circle
                    )
                }

                Spacer(modifier = Modifier.height(8.dp)) // Space between circle and label

                // Male label
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(BlueGreen)
                        .height(33.dp)
                        .width(155.dp)
                ) {
                    Text(
                        text = "$male",
                        color = Slime,
                        style = TextStyle(fontFamily = titleFont, fontSize = 15.sp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Female
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Circle above Female label
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Slime)
                        .padding(8.dp)
                        .wrapContentSize(Alignment.Center) // Center content
                ) {
                    Icon(
                        painter = painterResource(R.drawable.female),
                        contentDescription = null,
                        tint = Color(0xFF036D19),
                        modifier = Modifier
                            .size(64.dp) // Adjust size as needed
                            .align(Alignment.Center) // Center icon within the circle
                    )
                }

                Spacer(modifier = Modifier.height(8.dp)) // Space between circle and label

                // Female label
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(BlueGreen)
                        .height(33.dp)
                        .width(155.dp)
                ) {
                    Text(
                        text = "$female",
                        color = Slime,
                        style = TextStyle(fontFamily = titleFont, fontSize = 15.sp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    // Finish button
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Button
        Button(
            onClick = { navController.navigate("main") },
            colors = ButtonDefaults.buttonColors(Slime),
            modifier = Modifier
                .padding(start = 40.dp, bottom = 50.dp, end = 40.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Finish",
                color = DarkGreen,
                fontFamily = titleFont,
                fontSize = 28.sp
            )
        }
    }
}


