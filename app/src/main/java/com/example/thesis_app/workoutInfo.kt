package com.example.thesis_app

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.thesis_app.ui.theme.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutInfo(navController: NavController, workoutName: String, reps: Int, sets: Int, weight: Double) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGreen)
            .padding(40.dp),
        contentAlignment = Alignment.TopCenter // Align content to the top center
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BlueGreen),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // Arrange items from the top
        ) {// Title

            Box(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .clip(shape = RoundedCornerShape(40.dp))
                    .background(Slime)
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "$workoutName",
                    fontFamily = alt,
                    fontSize = 32.sp,
                    color = DarkGreen,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Container for video
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp) // Adjust height as needed
                    .padding(top = 30.dp) // Padding below the title
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Gray) // Placeholder for video background
            ) {
                // replace to actual vid tut
                /*Image(
                    painter = painterResource(id = R.drawable.placeholder_video), // Replace with your placeholder image
                    contentDescription = "Video Placeholder",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )*/
            }

            Row(
                modifier = Modifier
                    .padding(top = 14.dp)
                    .fillMaxWidth()
                    .padding(top = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                // Sets box
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(Slime)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "$sets sets",
                        style = TextStyle(fontSize = 18.sp, color = DarkGreen, fontFamily = alt),
                    )
                }

                // Spacer between sets and reps boxes
                Spacer(modifier = Modifier.width(4.dp))

                // Reps box
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(Slime)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "$reps reps",
                        style = TextStyle(fontSize = 18.sp, color = DarkGreen, fontFamily = alt),
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                OutlinedCard(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent,
                    ),
                    border = BorderStroke(4.dp, Slime),
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                ) {
                    Text(
                        text = "$weight kg",
                        style = TextStyle(fontSize = 18.sp, color = Slime, fontFamily = alt),
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .clip(shape = RoundedCornerShape(20.dp))
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                fontFamily = captionFont,
                fontSize = 16.sp,
                color = DirtyWhite,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .padding(top = 20.dp)

                )

            Row(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Button(
                    onClick = {  /*navController.navigate("plank")*/  },
                    colors = ButtonDefaults.buttonColors(DirtyWhite),
                    modifier = Modifier
                        .width(160.dp)
                        .height(60.dp)
                ) {
                    Text(
                        text = "Finish",
                        color = DarkGreen,
                        fontFamily = titleFont,
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                FloatingActionButton(
                    onClick = {
                        navController.popBackStack() // Go back to the previous screen in the stack
                    },
                    containerColor = Slime,
                    contentColor = DarkGreen,
                    modifier = Modifier.size(60.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_back_24),
                        contentDescription = null,
                    )
                }


                Spacer(modifier = Modifier.width(12.dp))

                FloatingActionButton(
                    onClick = {
                        navController.navigate("main")
                    },
                    containerColor = Slime,
                    contentColor = DarkGreen,
                    modifier = Modifier.size(60.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.home),
                        contentDescription = null
                    )
                }
            }
        }

    }
}

