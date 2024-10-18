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
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun plank(
    navController: NavController,
    height: Double,
    weight: Double,
    bmiCategory: String,
    fitnessGoal: String,
    muscleGroup: String,
    pushUpScore: Int,
    onPlankScore: (Int) -> Unit
) {
    var plankTime by remember { mutableStateOf("") }
    var isTestComplete by remember { mutableStateOf(false) }
    var plankScore by remember { mutableStateOf(0) }  // Store calculated plank score

    //timer
    var timeElapsed by remember { mutableStateOf(0L) }  // Timer starting at 0 seconds (stopwatch)
    var started by remember { mutableStateOf(false) } // Control if the timer has started
    var showDialog by remember { mutableStateOf(false) } // Control the visibility of the dialog
    var showPlankTime by remember { mutableStateOf(0L) }

    val context = LocalContext.current

    // Function to calculate the plank score based on time
    fun calculatePlankScore(time: Int): Int {
        return when {
            time < 30 -> 1
            time in 30..60 -> 2
            else -> 3
        }
    }

    // Stopwatch logic
    LaunchedEffect(key1 = started, key2 = timeElapsed) {
        if (started) {
            delay(1000L) // 1 second delay
            timeElapsed += 1 // Increment time by 1 second
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
                                    .background(if (it == 3) Slime else DirtyWhite)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Slime,
                            fontFamily = titleFont,
                            fontSize = 12.sp
                        )
                    ) {
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
            // display the plank time after the stopwatch stops
            if (!started && showPlankTime > 0) {
                Text(
                    text = "You held the plank for ${showPlankTime} seconds!",
                    style = TextStyle(fontSize = 20.sp, color = Slime, fontFamily = alt),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
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
                                showPlankTime = timeElapsed // Save the total plank time
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
                                text = "${timeElapsed}s", // Display the time elapsed
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = DarkGreen,
                                    fontFamily = alt
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                },
                containerColor = DirtyWhite,
                modifier = Modifier.clip(RoundedCornerShape(16.dp))
            )
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
                        if (showPlankTime > 0) { // Check if the stopwatch has been stopped and time recorded
                            plankScore = calculatePlankScore(showPlankTime.toInt()) // Use recorded time
                            onPlankScore(plankScore) // Proceed with the plank score
                            isTestComplete = true // Mark the test as complete
                        } else {
                            Toast.makeText(context, "Please start and stop the stopwatch to record your plank time", Toast.LENGTH_SHORT).show()
                            isTestComplete = false // Prevent proceeding if the stopwatch wasn't used
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Slime),
                    modifier = Modifier
                        .padding(28.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Submit",
                        color = DarkGreen,
                        fontSize = 20.sp,
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
                        navController.navigate("pullup?height=$height&weight=$weight&bmiCategory=$bmiCategory&fitnessGoal=$fitnessGoal&muscleGroup=$muscleGroup&pushUpScore=$pushUpScore&plankScore=$plankScore") {
                            popUpTo("plank") { inclusive = true }  // Remove Plank screen from backstack
                            launchSingleTop = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Slime),
                    modifier = Modifier
                        .padding(start = 40.dp, bottom = 50.dp, end = 40.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Proceed",
                        color = DarkGreen,
                        fontFamily = titleFont,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}
