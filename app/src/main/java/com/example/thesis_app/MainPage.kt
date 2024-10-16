package com.example.thesis_app

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.api.RetrofitInstance
import com.example.model.WorkoutRoutineRequest
import com.example.thesis_app.ui.theme.BlueGreen
import com.example.thesis_app.ui.theme.DarkGreen
import com.example.thesis_app.ui.theme.DirtyWhite
import com.example.thesis_app.ui.theme.Slime
import com.example.thesis_app.ui.theme.alt
import com.example.thesis_app.ui.theme.titleFont
import com.example.util.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainPage(navController: NavController) {
    // State to control the visibility of the drawer
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    //----------------------------------------------------------------------------

    val context = LocalContext.current
    var workoutRoutines by remember { mutableStateOf<List<WorkoutRoutineRequest>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var hasShownError by remember { mutableStateOf(false) }

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
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(300.dp)
                        .background(BlueGreen)
                        .zIndex(2f)
                ) {
                    Spacer(modifier = Modifier.padding(top = 50.dp))
                    Text(
                        text = "Spot",
                        color = Slime,
                        fontFamily = titleFont,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Example drawer items
                    Text(
                        text = "About the Devs",
                        color = DirtyWhite,
                        fontFamily = titleFont,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable { /* Handle Home click */ }
                    )
                    Text(
                        text = "Logout",
                        color = DirtyWhite,
                        fontFamily = titleFont,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable { performLogout(context, navController) }
                    )

                    Text(
                        text = "Edit User Profile",
                        color = DirtyWhite,
                        fontFamily = titleFont,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable { navController.navigate("bmi") }
                    )
                }
            },
            content = {
                Scaffold(
                    modifier = Modifier,
                    topBar = {
                        Surface(
                            color = Slime,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth()
                                    .height(80.dp)
                            ) {
                                Spacer(modifier = Modifier.weight(1f))
                                Icon(
                                    painter = painterResource(R.drawable.menu),
                                    contentDescription = "Menu",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clickable {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }
                                        .align(Alignment.CenterVertically),
                                    tint = DarkGreen
                                )
                            }
                        }
                    },
                    content = { innerPadding ->
                        Box(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                                .background(BlueGreen)
                                .padding(horizontal = 20.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 60.dp)
                            ) {
                                // Add a header or any other static content before the list

                                    Text(
                                        text = "Your Personalized Workout Routine",
                                        color = DirtyWhite,
                                        style = TextStyle(fontFamily = titleFont, fontSize = 20.sp),
                                    )

                                if (workoutRoutines.isNotEmpty()) {
                                        WorkoutRoutinesList(navController, workoutRoutines, containerOpacity = 0.2f)
                                    } else {
                                        BasicText(text = "No workout routines available.")
                                    }

                            }

                            }
                    },
                    bottomBar = {
                        BottomAppBar(
                            containerColor = Slime,
                            modifier = Modifier.height(80.dp)
                        ) {}
                    }
                )
            }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 30.dp)
                .align(Alignment.TopCenter)
                .padding(start = 20.dp)
                .zIndex(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(50.dp))
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
                .zIndex(1f)
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                // Display error message if exists
                errorMessage?.let {
                    if (!hasShownError) {
                        Toast.makeText(context, "Please edit your profile first.", Toast.LENGTH_SHORT).show()
                        hasShownError = true
                    }
                }
                // Floating Action Button to generate workout routines
                FloatingActionButton(
                    onClick = {
                        // Handle button click to generate workout routines
                        hasShownError = false
                        isLoading = true // Set loading state to true
                        generateWorkoutRoutine(context) { generatedRoutines, error ->
                            workoutRoutines = generatedRoutines
                            errorMessage = error
                            isLoading = false // Reset loading state
                        }
                    },
                    shape = CircleShape,
                    containerColor = DirtyWhite,
                    modifier = Modifier
                        .size(100.dp)
                        .offset(y = (-20).dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.spot_avatar),
                        contentDescription = "Generate Workout",
                        modifier = Modifier.size(60.dp) // Adjust size as needed
                    )
                }
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

private fun generateWorkoutRoutine(context: Context, onResult: (List<WorkoutRoutineRequest>, String?) -> Unit) {
    val service = RetrofitInstance.WorkoutRoutineService(context)

    CoroutineScope(Dispatchers.IO).launch {
        try {
            // Fetch existing workout routines for the user
            println("Making API request for existing routines")
            val existingRoutines = service.getWorkoutRoutines()
            println("API response: $existingRoutines")



            if (existingRoutines.isNotEmpty()) {
                // If routines exist, use the PUT request to regenerate the workout routine
                val updateResponse = service.updateWorkoutRoutine()

                if (updateResponse.isSuccessful) {
                    // Fetch the updated routines
                    val updatedRoutines = updateResponse.body()?.values?.flatten() ?: emptyList()
                    println("Successfully updated workout routines.")
                    onResult(updatedRoutines, null)
                } else {
                    // Handle failed update request
                    val errorMessage = updateResponse.errorBody()?.string() ?: "Failed to update workout routines"
                    println(errorMessage)
                    onResult(emptyList(), errorMessage)
                }
            } else {
                // If no routines exist, generate a new workout routine
                val response = service.generateWorkoutRoutine()
                val generatedRoutines = response.values.flatten() // Flatten the response if needed
                onResult(generatedRoutines, null)
            }
        } catch (e: Exception) {
            onResult(emptyList(), e.message)
        }
    }
}



@Composable
fun WorkoutRoutinesList(navController: NavController, workoutRoutines: List<WorkoutRoutineRequest>, containerOpacity: Float = 1f) {
    // Group the workout routines by day number
    val groupedRoutines = workoutRoutines.groupBy { it.dayNum }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(groupedRoutines.toList()) { (dayNum, routines) ->


            Box(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(DirtyWhite.copy(alpha = containerOpacity), RoundedCornerShape(8.dp))
                    .clickable {
                        navController.navigate("workoutDay/$dayNum")
                    }
                    .padding(16.dp)
            ) {

                Column {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Slime)
                            .width(75.dp)
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Day $dayNum",
                            fontFamily = alt,
                            fontSize = 16.sp,
                            color = DarkGreen
                        )
                    }

                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 0.dp),
                    ) {
                        items(routines) { routine ->
                            WorkoutRoutineCard(routine = routine, containerOpacity = 0.3f)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun WorkoutRoutineCard(routine: WorkoutRoutineRequest, containerOpacity: Float = 1f) {

    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(DirtyWhite.copy(alpha = containerOpacity), RoundedCornerShape(8.dp))
            .width(100.dp)
            .height(200.dp)
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .clip(shape = CircleShape)
                .background(BlueGreen)
                .height(80.dp)
                .width(80.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_directions_run_24),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.Center),
                tint = Slime
            )
        }

        routine.workoutInfo?.let { workoutInfo ->
            Text(text = "${workoutInfo.workout.name}",
                fontFamily = alt,
                fontSize = 12.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp))
        }

    }
}

fun performLogout(context: Context, navController: NavController) {
    TokenManager.clearToken(context)
    navController.navigate("login") {
        popUpTo("main") { inclusive = true }  // Clear the backstack to prevent navigation back
    }
}