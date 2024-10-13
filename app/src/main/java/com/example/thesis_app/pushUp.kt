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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thesis_app.ui.theme.*
import kotlinx.coroutines.delay
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pushUp(
    navController: NavController,
    height: Double,
    weight: Double,
    bmiCategory: String,
    fitnessGoal: String,
    muscleGroup: String,
    onPushUpScore: (Int) -> Unit
) {
    var pushUpCount by remember { mutableStateOf("") }
    var isTestComplete by remember { mutableStateOf(false) }
    var pushUpScore by remember { mutableStateOf(0) }
    val context = LocalContext.current

    var timeLeft by remember { mutableStateOf(10) }
    var started by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showInputField by remember { mutableStateOf(false) }

    // Function to calculate the push-up score based on the count
    fun calculatePushUpScore(count: Int): Int {
        return when {
            count < 15 -> 1
            count in 15..25 -> 2
            else -> 3
        }
    }

    // Countdown logic
    LaunchedEffect(key1 = started, key2 = timeLeft) {
        if (started && timeLeft > 0) {
            delay(1000L)
            timeLeft -= 1
        }
        if (timeLeft == 0) {
            started = false
            showDialog = false
            showInputField = true
        }
    }

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
            // Header and other sections omitted for brevity...
            Spacer(modifier = Modifier.height(80.dp))

            if (!started) {
                Button(
                    onClick = { showDialog = true },
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

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { /* Optionally handle dismiss */ },
                confirmButton = {
                    Button(
                        onClick = {
                            started = true
                        },
                        colors = ButtonDefaults.buttonColors(Slime),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Start Timer", color = DarkGreen, fontFamily = alt, fontSize = 16.sp)
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
                                contentDescription = "Timer",
                                tint = DarkGreen,
                                modifier = Modifier.size(60.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "${timeLeft}s",
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
                    Box(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(20.dp))
                            .background(DirtyWhite)
                            .padding(20.dp)
                    ) {
                        TextField(
                            value = pushUpCount,
                            onValueChange = { pushUpCount = it },
                            label = { Text("How many push-ups did you do?", fontFamily = alt, color = DarkGreen) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = DirtyWhite,
                                focusedIndicatorColor = DarkGreen,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }

                    Button(
                        onClick = {
                            val pushUps = pushUpCount.toIntOrNull()
                            if (pushUps != null && pushUps >= 0) {
                                pushUpScore = calculatePushUpScore(pushUps)
                                isTestComplete = true
                            } else {
                                Toast.makeText(context, "Please enter a valid push-up count", Toast.LENGTH_SHORT).show()
                                isTestComplete = false
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
                            fontSize = 18.sp,
                            fontFamily = titleFont
                        )
                    }

                    if (isTestComplete) {
                        Text(
                            text = "Push-up test complete",
                            style = TextStyle(color = Color.Green, fontSize = 18.sp),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }

        if (isTestComplete) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = {
                        navController.navigate("plank?height=$height&weight=$weight&bmiCategory=$bmiCategory&fitnessGoal=$fitnessGoal&muscleGroup=$muscleGroup&pushUpScore=$pushUpScore") {
                            popUpTo("pushup") { inclusive = true }
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
