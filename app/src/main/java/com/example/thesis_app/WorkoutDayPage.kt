package com.example.thesis_app

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.api.RetrofitInstance
import com.example.model.WorkoutRoutineRequest
import com.example.thesis_app.ui.theme.BlueGreen
import com.example.thesis_app.ui.theme.DarkGreen
import com.example.thesis_app.ui.theme.DirtyWhite
import com.example.thesis_app.ui.theme.Slime
import com.example.thesis_app.ui.theme.alt
import com.example.thesis_app.ui.theme.titleFont
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDayPage(navController: NavController, selectedDayNum: Int) {

    val context = LocalContext.current
    var workoutRoutines by remember { mutableStateOf<List<WorkoutRoutineRequest>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Fetch workout routines on launch
    LaunchedEffect(Unit) {
        isLoading = true
        fetchWorkoutRoutines(context) { routines, error ->
            workoutRoutines = routines
            errorMessage = error
            isLoading = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                Surface(
                    color = Slime,
                    modifier = Modifier.fillMaxWidth(),
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .height(80.dp)
                    ) {
                        // Add top bar content here if needed
                    }
                }
            },
            content = { innerPadding ->
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .background(BlueGreen)
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 60.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "DAY $selectedDayNum",
                                fontSize = 20.sp,
                                fontFamily = titleFont,
                                color = DirtyWhite,
                                textAlign = TextAlign.Center
                            )
                        }
                        WorkoutItem(workoutRoutines, selectedDayNum, containerOpacity = 1f, navController)
                    }
                }
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = Slime,
                    modifier = Modifier.height(80.dp)
                ) {
                    // Add bottom bar content if needed
                }
            }
        )

        // Logo and FloatingActionButton
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 30.dp)
                .align(Alignment.TopCenter)
                .padding(start = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(shape = RoundedCornerShape(50.dp))
                    .background(BlueGreen)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.spot_logo),
                    contentDescription = "Spot Logo",
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.Center)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            FloatingActionButton(
                onClick = { navController.navigate("main") },
                shape = CircleShape,
                containerColor = DirtyWhite,
                modifier = Modifier
                    .size(100.dp)
                    .offset(y = (-20).dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.home),
                    contentDescription = "Home",
                    modifier = Modifier.size(50.dp),
                    tint = DarkGreen
                )
            }
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

@Composable
fun WorkoutItem(workoutRoutines: List<WorkoutRoutineRequest>, selectedDayNum: Int, containerOpacity: Float = 1f, navController: NavController)  {
    val groupedRoutines = workoutRoutines.groupBy { it.dayNum }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp) // Add bottom padding to avoid overlap with FAB
    ) {
        val routinesForSelectedDay = groupedRoutines[selectedDayNum] ?: emptyList()

        if (routinesForSelectedDay.isNotEmpty()) {
            items(routinesForSelectedDay) { routine ->
                WorkoutCard(routine, navController)
            }
        } else {
            // Optionally, show a message if no routines are available for the selected day
            item {
                Text(
                    text = "No routines available for Day $selectedDayNum",
                    modifier = Modifier.padding(16.dp),
                    style = TextStyle(fontSize = 18.sp, color = Color.White)
                )
            }
        }
    }
}

@Composable
fun WorkoutCard(routine: WorkoutRoutineRequest, navController: NavController, containerOpacity: Float = 1f ) {
    Box(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .height(140.dp)
            .clip(RoundedCornerShape(8.dp))  // Clip to rounded corners
            .clickable {
                // Navigate to the workout info screen and pass the workout name as a parameter
                routine.workoutInfo?.let { workoutInfo ->
                    navController.navigate("workoutInfoPage/${workoutInfo.workout.name}")
                }
            }
    ) {
        // Image as the background
        Image(
            painter = painterResource(id = R.drawable.treadmill),
            contentDescription = "Workout Image Background",
            contentScale = ContentScale.Crop,  // Crop to fill the Box
            modifier = Modifier
                .fillMaxSize()
                .alpha(containerOpacity)  // Apply opacity if needed
        )

        // Display workout info
        routine.workoutInfo?.let { workoutInfo ->
            // Text container with a gradient effect
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()  // Adjust the width to fit the text container
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                BlueGreen,
                                Color.Transparent
                            ), // Gradient from BlueGreen to transparent
                            startX = 0f,
                            endX = 600f  // Adjust this value for a smoother transition
                        )
                    )
                    .padding(16.dp)  // Padding around the text inside the gradient box
            ) {
                // Workout text positioned to the left
                Column(
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Text(
                        text = "${workoutInfo.workout.name}",
                        fontFamily = alt,
                        fontSize = 20.sp,
                        color = Color.White,  // Set text color for better contrast
                    )

                    // Row for sets and reps boxes
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, end = 8.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        // Sets box
                        Box(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(20.dp))
                                .background(Slime)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "${workoutInfo.sets} sets",
                                style = TextStyle(fontSize = 10.sp, color = DarkGreen, fontFamily = alt),
                            )
                        }

                        // Spacer between sets and reps boxes
                        Spacer(modifier = Modifier.width(4.dp))

                        // Reps box
                        Box(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(20.dp))
                                .background(Slime)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "${workoutInfo.reps} reps",
                                style = TextStyle(fontSize = 10.sp, color = DarkGreen, fontFamily = alt),
                            )
                        }
                    }
                }
            }
        }
    }
}
