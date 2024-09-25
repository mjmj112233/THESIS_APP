package com.example.thesis_app

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
fun plank(navController: NavController) {
    var timeElapsed by remember { mutableStateOf(0) }  // Timer starting at 0 seconds (stopwatch)
    var started by remember { mutableStateOf(false) } // Control if the timer has started
    var showDialog by remember { mutableStateOf(false) } // Control the visibility of the dialog
    var showInputField by remember { mutableStateOf(false) } // Control visibility of the input field
    var pushUpsCount by remember { mutableStateOf("") } // Store the number of push-ups

    // Stopwatch logic
    LaunchedEffect(key1 = started, key2 = timeElapsed) {
        if (started) {
            delay(1000L) // 1 second delay
            timeElapsed += 1 // Increment time by 1 second
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGreen)
    ) {
        // Header and instructions
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
                                    .background(if (it == 3) Slime else DirtyWhite)
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
                    text = "Plank as long as you can.",
                    style = TextStyle(fontSize = 20.sp, color = DarkGreen, fontFamily = alt),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Start Stopwatch Button
            if (!started) {
                Button(
                    onClick = { showDialog = true }, // Open the dialog when the button is clicked
                    colors = ButtonDefaults.buttonColors(Slime),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = CircleShape)
                ) {
                    Text(
                        text = "Start Test",
                        color = DarkGreen,
                        fontFamily = titleFont,
                        fontSize = 20.sp
                    )
                }
            }
        }

        // Stopwatch Dialog
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { /* Optionally handle dismiss if needed */ },
                confirmButton = {
                    if (!started) {
                        Button(
                            onClick = {
                                started = true // Start the stopwatch
                            },
                            colors = ButtonDefaults.buttonColors(Slime),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text("Start", color = DarkGreen, fontFamily = alt, fontSize = 16.sp)
                        }
                    } else {
                        Button(
                            onClick = {
                                started = false // Stop the stopwatch
                                showDialog = false // Close the dialog when the user clicks stop
                                showInputField = true // Show input field
                            },
                            colors = ButtonDefaults.buttonColors(Slime),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text("Stop", color = DarkGreen, fontFamily = alt, fontSize = 16.sp)
                        }
                    }
                },
                text = {
                    Box(
                        modifier = Modifier
                            .background(DirtyWhite)
                            .padding(top = 16.dp)
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_timer_24),
                                contentDescription = "Stopwatch",
                                tint = DarkGreen,
                                modifier = Modifier.size(60.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "${timeElapsed}s",
                                style = TextStyle(fontSize = 20.sp, color = DarkGreen, fontFamily = alt),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                },
                containerColor = DirtyWhite,
                modifier = Modifier.clip(RoundedCornerShape(16.dp))
            )
        }

        // Show input field after stopping the stopwatch
        if (showInputField) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 220.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, bottom = 50.dp, end = 30.dp)
                ) {
                    // Push-Ups Count Input
                    Box(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(20.dp))
                            .background(DirtyWhite)
                            .padding(20.dp)
                    ) {
                        TextField(
                            value = pushUpsCount,
                            onValueChange = { pushUpsCount = it },
                            label = { Text("How long were you able to hold the plank?", fontFamily = alt, color = DarkGreen) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = DirtyWhite,
                                focusedIndicatorColor = DarkGreen,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }

        // Next button
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = {  navController.navigate("pullup")  },
                colors = ButtonDefaults.buttonColors(Slime),
                modifier = Modifier
                    .padding(start = 40.dp, bottom = 50.dp, end = 40.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Next",
                    color = DarkGreen,
                    fontFamily = titleFont,
                    fontSize = 20.sp
                )
            }
        }
    }
}
