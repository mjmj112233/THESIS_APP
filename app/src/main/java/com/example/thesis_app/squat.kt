package com.example.thesis_app

import android.content.Context
import android.util.Log
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
import com.example.api.RetrofitInstance
import com.example.api.UserProfileService
import com.example.api.WorkoutRoutineService
import com.example.model.UserProfileRequest
import com.example.model.WorkoutRoutineRequest
import com.example.thesis_app.ui.theme.*
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.coroutineContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SquatScreen(
    navController: NavController,
    height: Double,
    weight: Double,
    bmiCategory: String,
    fitnessGoal: String,
    muscleGroup: String,
    fitnessScore: Int // Total fitness score accumulated from previous assessments
) {
    var timeLeft by remember { mutableStateOf(60) }
    var started by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showInputField by remember { mutableStateOf(false) }
    var squatCount by remember { mutableStateOf("") } // Store squat count entered by user
    var squatScore by remember { mutableStateOf(0) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var countdownTime by remember { mutableStateOf(3) } // Starting countdown from 3
    var showCountdown by remember { mutableStateOf(false) }

    // Function to check if the profile exists
    suspend fun checkProfileExists(): Boolean {
        return try {
            // Call the API to get the user profile
            val response = RetrofitInstance.UserProfileService(context).getProfile()

            // If the response is successful and the profile exists, return true
            response.isSuccessful && response.body() != null
        } catch (e: Exception) {
            // In case of any error (network or other), return false
            false
        }
    }

    // Function to submit or update user profile and generate workouts
    suspend fun submitUserProfile(isUpdating: Boolean) {
        try {
            isLoading = true
            val request = UserProfileRequest(
                height = height,
                weight = weight,
                BMICategory = bmiCategory,
                fitnessGoal = fitnessGoal,
                fitnessScore = fitnessScore + squatScore,
                muscleGroup = muscleGroup
            )

            // Log the request to check its content
            println("UserProfileRequest: ${Gson().toJson(request)}")
            Log.d("UserProfileRequest", Gson().toJson(request))

            // Depending on isUpdating, call either the create or update API
            val apiCall = if (isUpdating) {
                RetrofitInstance.UserProfileService(context).updateUserProfile(request)
            } else {
                RetrofitInstance.UserProfileService(context).createUserProfile(request)
            }

            apiCall.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        // Navigate to the main screen if the API call was successful
                        navController.navigate("main") {
                            popUpTo("plank") { inclusive = true }  // Remove Plank screen from backstack
                            launchSingleTop = true
                        }
                    } else {
                        // Handle error if response is not successful
                        errorMessage = response.errorBody()?.string() ?: "Error submitting profile"
                        println("Error submitting profile: $errorMessage") // Log the error message
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    errorMessage = "Error submitting profile: ${t.message}"
                    println("Exception submitting profile: ${t.message}") // Log the exception
                }
            })

        } catch (e: Exception) {
            errorMessage = "Error submitting profile: ${e.message}"
            println("Exception submitting profile: ${e.message}") // Log the exception
        } finally {
            isLoading = false
        }
    }


    // Countdown logic for the squat test
    LaunchedEffect(key1 = started, key2 = timeLeft) {
        if (started && timeLeft > 0) {
            delay(1000L) // 1 second delay
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
                                    .background(if (it == 5) Slime else DirtyWhite)
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
                    append("For safety, we highly recommend having someone assist you in timing your tests. ")
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
                    text = "Perform as many squats as possible within 1 minute.",
                    style = TextStyle(fontSize = 20.sp, color = DarkGreen, fontFamily = alt),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Start Timer Button
            if (!started) {
                Button(
                    onClick = {
                        showCountdown = true // Show the countdown when the button is clicked
                        showDialog = true // Open the dialog
                    },
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

            // Countdown logic
            LaunchedEffect(showCountdown) {
                if (showCountdown) {
                    while (countdownTime > 0) {
                        delay(1000L) // Delay for 1 second
                        countdownTime-- // Decrease countdown time
                    }
                    // Countdown finished, start the timer
                    countdownTime = 0 // Set countdown to 0 to show "Go!"
                    delay(500L) // Hold "Go!" before starting the timer
                    showCountdown = false // Hide the countdown
                    started = true // Start the timer after countdown
                }
            }

            // Timer Dialog
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { /* Handle dismiss */ },
                    confirmButton = {
                        if (showCountdown) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth() // Center the content horizontally
                            ) {
                                Text(
                                    text = "Please get into your squat position",
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        color = DarkGreen,
                                        fontFamily = alt
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(bottom = 10.dp) // Add padding between the message and the countdown
                                )
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center // Center the countdown text
                                ) {
                                    Text(
                                        text = if (countdownTime > 0) "$countdownTime" else "Go!",
                                        style = TextStyle(
                                            fontSize = 24.sp,
                                            color = DarkGreen,
                                            fontFamily = alt
                                        ),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        } else if (!started) {
                            Button(
                                onClick = {
                                    showCountdown = true // Trigger countdown before timer
                                },
                                colors = ButtonDefaults.buttonColors(Slime),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Start", color = DarkGreen, fontFamily = alt, fontSize = 16.sp)
                            }
                        } else {
                            Button(
                                onClick = {
                                    started = false // Stop the timer
                                    showDialog = false // Close the dialog when the user clicks stop
                                },
                                colors = ButtonDefaults.buttonColors(Slime),
                                modifier = Modifier.fillMaxWidth()
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
                                    painter = painterResource(
                                        id = if (showCountdown && countdownTime > 0) {
                                            R.drawable.baseline_warning_24 // Show warning icon during countdown
                                        } else {
                                            R.drawable.baseline_timer_24 // Show timer icon after countdown
                                        }
                                    ),
                                    contentDescription = if (showCountdown && countdownTime > 0) {
                                        "Warning"
                                    } else {
                                        "Timer"
                                    },
                                    tint = DarkGreen,
                                    modifier = Modifier.size(60.dp)
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                if (!showCountdown) {
                                    Text(
                                        text = "${timeLeft}s", // Display the time elapsed
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            color = DarkGreen,
                                            fontFamily = alt
                                        ),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    },
                    containerColor = DirtyWhite,
                    modifier = Modifier.clip(RoundedCornerShape(16.dp))
                )
            }
        }

        // Show input field after stopping the timer
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
                    // Squat Count Input
                    Box(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(20.dp))
                            .background(DirtyWhite)
                            .padding(20.dp)
                    ) {
                        TextField(
                            value = squatCount,
                            onValueChange = { squatCount = it },
                            label = { Text("How many squats did you do?", fontFamily = alt, color = DarkGreen) },
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

        // Finish and submit button
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = {
                    // Check if the input is empty
                    if (squatCount.trim().isEmpty()) {
                        // Handle the case where the input is empty (e.g., show an error message)
                        Toast.makeText(context, "Please enter your squat count.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val squats = squatCount.trim().toIntOrNull()

                    // Check if it's a valid integer
                    if (squats == null) {
                        // Handle invalid input (e.g., show an error message)
                        Toast.makeText(context, "Please enter a valid number for squats.", Toast.LENGTH_SHORT).show()
                        return@Button

                    }

                    squatScore = when {
                        squats != null && squats >= 41 -> 3
                        squats != null && squats in 20..40 -> 2
                        squats != null && squats < 20 -> 1
                        else -> 0
                    }

                    if (squatScore != 0) {
                        coroutineScope.launch {
                            val isUpdating = checkProfileExists()
                            submitUserProfile(isUpdating)
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(Slime),
                modifier = Modifier
                    .padding(start = 40.dp, bottom = 50.dp, end = 40.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Finish",
                    color = DarkGreen,
                    fontFamily = titleFont,
                    fontSize = 20.sp
                )
            }
        }

        // Loading and error handling
        if (isLoading) {
            CircularProgressIndicator()
        }
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red)
        }
    }
}




