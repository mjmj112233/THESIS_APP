package com.example.thesis_app

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thesis_app.ui.theme.*
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pullup(
    navController: NavController,
    height: Double,
    weight: Double,
    bmiCategory: String,
    fitnessGoal: String,
    muscleGroup: String,
    pushUpScore: Int,
    plankScore: Int,
    onPullUpScore: (Int) -> Unit
) {
    var pullUpCount by remember { mutableStateOf("") }
    var isTestComplete by remember { mutableStateOf(false) }
    var pullUpScore by remember { mutableStateOf(0) }  // Store calculated pull-up score
    val context = LocalContext.current

    // Function to calculate the pull-up score based on count
    fun calculatePullUpScore(count: Int): Int {
        return when {
            count < 10 -> 1
            count in 10..20 -> 2
            else -> 3
        }
    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGreen)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 30.dp, end = 30.dp, top = 40.dp),
            verticalArrangement = Arrangement.Top
        ) {
            // Header Section (Logo + Text)
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
                            text = "Strength and Endurance Test",
                            color = DirtyWhite,
                            style = TextStyle(fontFamily = titleFont, fontSize = 16.sp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Logo circle
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 95.dp, start = 80.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(6) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 3.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(4.dp)
                                    .background(if (it == 4) Slime else DirtyWhite)
                            )
                        }
                    }
                }
            }


            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Slime, fontFamily = titleFont, fontSize = 12.sp)) {
                        append("Note: ")
                    }
                    append("For safety, we highly recommend having someone assist you in timing your tests.")
                },
                style = TextStyle(fontSize = 12.sp, color = DirtyWhite, fontFamily = captionFont),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(80.dp))

            // Instruction Section
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(DirtyWhite)
                    .padding(20.dp)
            ) {
                Text(
                    text = "Do pull-ups as much as possible.",
                    style = TextStyle(fontSize = 20.sp, color = DarkGreen, fontFamily = alt),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Push-Ups Count Input
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(DirtyWhite)
                    .padding(20.dp)
            ) {
                TextField(
                    value = pullUpCount,
                    onValueChange = { pullUpCount = it },
                    label = { Text("How many pull-ups were you able to do?", fontFamily = alt, color = DarkGreen) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = DirtyWhite,
                        focusedIndicatorColor = BlueGreen,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
        }

        // Input Section
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(700.dp))

                Button(
                    onClick = {
                        val pullUps = pullUpCount.toIntOrNull()
                        if (pullUps != null && pullUps >= 0) {
                            pullUpScore = calculatePullUpScore(pullUps)  // Calculate pull-up score
                            onPullUpScore(pullUpScore)  // Pass the score to the next screen
                            isTestComplete = true
                        } else {
                            Toast.makeText(context, "Please enter a valid number of pull ups", Toast.LENGTH_SHORT).show()
                            isTestComplete = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Slime),
                    modifier = Modifier
                        .padding(28.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Next",
                        color = DarkGreen,
                        fontSize = 18.sp,
                        fontFamily = titleFont
                    )
                }
            }
        }

        // Next Button
        if (isTestComplete) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = {
                        navController.navigate("squat?height=$height&weight=$weight&bmiCategory=$bmiCategory&fitnessGoal=$fitnessGoal&muscleGroup=$muscleGroup&pushUpScore=$pushUpScore&plankScore=$plankScore&pullUpScore=$pullUpScore") {
                            popUpTo("pullup") { inclusive = true }  // Remove Pull-up screen from backstack
                            launchSingleTop = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Slime),
                    modifier = Modifier
                        .padding(start = 40.dp, bottom = 50.dp, end = 40.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Next",
                        color = DarkGreen,
                        fontFamily = titleFont,
                        fontSize = 28.sp
                    )
                }
            }
        }
    }
}
