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
    var errorMessage by remember { mutableStateOf("") }
    var pushUpScore by remember { mutableStateOf(0) }  // Store the calculated score

    // Function to calculate the push-up score based on the count
    fun calculatePushUpScore(count: Int): Int {
        return when {
            count < 20 -> 1
            count in 20..39 -> 2
            else -> 3
        }
    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGreen)
    ) {

        // Header
        Box(
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp, top = 40.dp)
        ) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.padding(start = 20.dp)) {
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
                            text = "Push-Up Test",
                            color = DirtyWhite,
                            style = TextStyle(fontFamily = titleFont, fontSize = 24.sp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
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
                TextField(
                    value = pushUpCount,
                    onValueChange = { pushUpCount = it },
                    label = { Text("Enter push-up count", fontFamily = alt, color = DarkGreen) },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = DarkGreen,
                        unfocusedIndicatorColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val pushUps = pushUpCount.toIntOrNull()
                        if (pushUps != null && pushUps >= 0) {
                            pushUpScore = calculatePushUpScore(pushUps)  // Calculate score
                            onPushUpScore(pushUpScore)  // Pass the score to the next screen
                            isTestComplete = true
                            errorMessage = ""
                        } else {
                            errorMessage = "Please enter a valid push-up count"
                            isTestComplete = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Slime),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Submit",
                        color = DarkGreen,
                        fontSize = 18.sp,
                        fontFamily = titleFont
                    )
                }

                // Display error message if the input is invalid
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
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
                        navController.navigate("plank?height=$height&weight=$weight&bmiCategory=$bmiCategory&fitnessGoal=$fitnessGoal&muscleGroup=$muscleGroup&pushUpScore=$pushUpScore") {
                            popUpTo("pushup") { inclusive = true }  // Remove Push-up screen from backstack
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
