package com.example.thesis_app

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
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
import com.example.api.RetrofitInstance
import com.example.model.WorkoutRoutineRequest
import com.example.thesis_app.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView
import com.example.api.WorkoutRoutineService
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutInfoPage(navController: NavController, workoutName: String) {
    val context = LocalContext.current
    var workoutRoutines by remember { mutableStateOf<List<WorkoutRoutineRequest>>(emptyList()) }
    var filteredWorkout by remember { mutableStateOf<WorkoutRoutineRequest?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isFinished by remember { mutableStateOf(false) } // State to track if workout is finished
    var showDialog by remember { mutableStateOf(false) }

    val service = RetrofitInstance.WorkoutRoutineService(context)

    // Fetch workout routines and check finished status on launch
    LaunchedEffect(Unit) {
        isLoading = true
        fetchWorkoutRoutines(context) { routines, error ->
            workoutRoutines = routines
            errorMessage = error
            // Filter the workout that matches workoutName
            filteredWorkout = routines.find { it.workoutInfo?.workout?.name == workoutName }

            // Check if the filteredWorkout is finished
            if (filteredWorkout != null) {
                isFinished = filteredWorkout!!.isFinished // Assuming isFinished is part of your data model
            }

            isLoading = false
        }
    }

    // Show loading state or error message if any
    if (isLoading) {
        loadingScreen() // Show custom loading screen
    } else if (errorMessage != null) {
        Text(text = "Error: $errorMessage")
    } else {
        // Use filteredWorkout to display the selected workout information
        filteredWorkout?.let { workout ->
            workout.workoutInfo?.let { workoutInfo ->
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
                    ) {
                        // Title
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

                        // Video/Content Placeholder
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(275.dp)
                                .padding(top = 30.dp)
                                .background(BlueGreen)
                        ) {
                            workoutInfo.workout.demoUrl?.let { iframeString ->
                                AndroidView(
                                    modifier = Modifier.fillMaxSize(),
                                    factory = { context ->
                                        WebView(context).apply {
                                            webViewClient = WebViewClient()
                                            settings.javaScriptEnabled = true // Enable JavaScript for YouTube content
                                            settings.loadWithOverviewMode = true
                                            settings.useWideViewPort = true

                                            // Load the iframe string as HTML content
                                            loadData(
                                                """
                        <html>
                        <head>
                            <style>
                            body {
                                background-color: transparent; 
                                margin-right: 0;
                                padding: 0;
                            }
                        </style>
                        </head>
                        <body>
                            $iframeString
                        </body>
                        </html>
                        """.trimIndent(),
                                                "text/html",
                                                "utf-8"
                                            )
                                        }
                                    }
                                )
                            }
                        }

                        if (workoutInfo.workout.name != "Cardio") {
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
                                        .padding(horizontal = 10.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = "${workoutInfo.sets} sets",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            color = DarkGreen,
                                            fontFamily = alt
                                        ),
                                    )
                                }

                                // Spacer between sets and reps boxes
                                Spacer(modifier = Modifier.width(4.dp))

                                // Reps box
                                Box(
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(20.dp))
                                        .background(Slime)
                                        .padding(horizontal = 10.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = "${workoutInfo.reps} reps",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            color = DarkGreen,
                                            fontFamily = alt
                                        ),
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
                                        text = "${workoutInfo.weight} kg",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            color = Slime,
                                            fontFamily = alt
                                        ),
                                        modifier = Modifier
                                            .padding(horizontal = 10.dp, vertical = 8.dp)
                                            .clip(shape = RoundedCornerShape(20.dp))
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
                                        text = "${workoutInfo.workout.classification.name}",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            color = Slime,
                                            fontFamily = alt
                                        ),
                                        modifier = Modifier
                                            .padding(horizontal = 10.dp, vertical = 8.dp)
                                            .clip(shape = RoundedCornerShape(20.dp))
                                    )
                                }
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .padding(top = 16.dp) // Simulates margin-top by adding padding to the parent
                            ) {
                                OutlinedCard(
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.Transparent,
                                    ),
                                    border = BorderStroke(4.dp, Slime),
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(20.dp))
                                ) {
                                    Text(
                                        text = String.format("%.0f min", workoutInfo.weight), // Display duration in minutes
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            color = Slime,
                                            fontFamily = alt
                                        ),
                                        modifier = Modifier
                                            .padding(horizontal = 12.dp, vertical = 8.dp)
                                            .clip(shape = RoundedCornerShape(20.dp))
                                    )
                                }
                            }
                        }
                        // Automatically show dialog if the equipment is "Barbell"
                        LaunchedEffect(workoutInfo.workout.equipment) {
                            if (workoutInfo.workout.equipment.equals("Barbell", ignoreCase = true)) {
                                showDialog = true
                            }
                        }

                        if (showDialog) {
                            AlertDialog(
                                onDismissRequest = { showDialog = false },
                                text = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.baseline_warning_24),
                                            contentDescription = "Warning",
                                            tint = DarkGreen,
                                            modifier = Modifier
                                                .padding(end = 24.dp)
                                                .size(40.dp)
                                        )
                                        Column {
                                            Text(
                                                text = buildAnnotatedString {
                                                    append("For safety, only use the barbell bar without any weight plates.")
                                                },
                                                color = DarkGreen,
                                                fontFamily = titleFont,
                                                textAlign = TextAlign.Justify
                                            )
                                        }
                                    }
                                },
                                confirmButton = {
                                    Button(
                                        onClick = { showDialog = false },
                                        colors = ButtonDefaults.buttonColors(Slime),
                                    ) {
                                        Text(
                                            text = "OK",
                                            color = DarkGreen,
                                            fontFamily = titleFont
                                        )
                                    }
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(color = DirtyWhite, fontFamily = captionFont)) {
                                    append("Equipment needed: ")
                                }
                                append("${workoutInfo.workout.equipment} ")
                            },
                            fontFamily = titleFont,
                            fontSize = 16.sp,
                            color = Slime,
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .fillMaxWidth()
                                .align(Alignment.Start)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "${workoutInfo.workout.description} ",
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
                            // Button to toggle between Finish and Unfinish
                            Button(
                                onClick = {
                                    if (isFinished) {
                                        // Call the API to mark the workout as unfinished
                                        if (workout.id > 0) { // Check if workout has a valid ID
                                            CoroutineScope(Dispatchers.IO).launch {
                                                try {
                                                    val response = service.resetWorkout(workout.id) // Call the reset API
                                                    if (response.isSuccessful) {
                                                        // Switch to the main thread to show the Toast
                                                        withContext(Dispatchers.Main) {
                                                            Toast.makeText(context, "Workout marked as unfinished!", Toast.LENGTH_SHORT).show()
                                                        }
                                                        isFinished = false // Update local state
                                                    } else {
                                                        // Handle API error
                                                        withContext(Dispatchers.Main) {
                                                            Toast.makeText(context, "Error marking workout as unfinished", Toast.LENGTH_SHORT).show()
                                                        }
                                                    }
                                                } catch (e: Exception) {
                                                    // Handle exception
                                                    withContext(Dispatchers.Main) {
                                                        Toast.makeText(context, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        // Call the API to mark the workout as finished
                                        if (workout.id > 0) { // Check if workout has a valid ID
                                            CoroutineScope(Dispatchers.IO).launch {
                                                try {
                                                    val response = service.markWorkoutAsFinished(workout.id)
                                                    if (response.isSuccessful) {
                                                        // Switch to the main thread to show the Toast
                                                        withContext(Dispatchers.Main) {
                                                            Toast.makeText(context, "Workout marked as finished!", Toast.LENGTH_SHORT).show()
                                                        }
                                                        isFinished = true // Update local state
                                                    } else {
                                                        // Handle API error
                                                        withContext(Dispatchers.Main) {
                                                            Toast.makeText(context, "Error marking workout as finished", Toast.LENGTH_SHORT).show()
                                                        }
                                                    }
                                                } catch (e: Exception) {
                                                    // Handle exception
                                                    withContext(Dispatchers.Main) {
                                                        Toast.makeText(context, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                                                    }
                                                }
                                            }
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(DirtyWhite),
                                modifier = Modifier
                                    .width(180.dp)
                                    .height(60.dp)
                            ) {
                                Text(
                                    text = if (isFinished) "Unfinish" else "Finish", // Toggle text
                                    color = DarkGreen,
                                    fontFamily = titleFont,
                                    fontSize = 20.sp
                                )
                            }

                            // Navigation Buttons (unchanged)
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
        } ?: run {
            // Handle case where no workout matches the workoutName
            Text(text = "No workout found for: $workoutName")
        }
    }
}


private fun fetchWorkoutRoutines(context: Context, onResult: (List<WorkoutRoutineRequest>, String?) -> Unit) {
    val service = RetrofitInstance.WorkoutRoutineService(context)

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val routines = service.getWorkoutRoutines() // Call suspend function here
            onResult(routines, null)
        } catch (e: Exception) {
            onResult(emptyList(), e.message)
        }
    }
}


