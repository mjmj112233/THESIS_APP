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
    var errorMessage by remember { mutableStateOf("") }
    var plankScore by remember { mutableStateOf(0) }  // Store calculated plank score

    // Function to calculate the plank score based on time
    fun calculatePlankScore(time: Int): Int {
        return when {
            time < 30 -> 1
            time in 30..60 -> 2
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
                            text = "Plank Test",
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
                    value = plankTime,
                    onValueChange = { plankTime = it },
                    label = { Text("Enter plank time (in seconds)", fontFamily = alt, color = DarkGreen) },
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
                        val plankTimeInSeconds = plankTime.toIntOrNull()
                        if (plankTimeInSeconds != null && plankTimeInSeconds >= 0) {
                            plankScore = calculatePlankScore(plankTimeInSeconds)  // Calculate plank score
                            onPlankScore(plankScore)  // Pass the score to the next screen
                            isTestComplete = true
                            errorMessage = ""
                        } else {
                            errorMessage = "Please enter a valid plank time"
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
                        text = "Plank test complete",
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
