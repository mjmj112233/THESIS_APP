package com.example.thesis_app

import android.content.Context
import android.widget.Space
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.api.RetrofitInstance
import com.example.model.UserProfile
import com.example.model.UserProfileRequest
import com.example.model.WorkoutRoutineRequest
import com.example.thesis_app.ui.theme.BlueGreen
import com.example.thesis_app.ui.theme.DarkGreen
import com.example.thesis_app.ui.theme.DirtyWhite
import com.example.thesis_app.ui.theme.Slime
import com.example.thesis_app.ui.theme.alt
import com.example.thesis_app.ui.theme.captionFont
import com.example.thesis_app.ui.theme.titleFont
import com.example.util.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainPage(navController: NavController) {
    // State to control the visibility of the drawer
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    // State to control the visibility of the alert dialog
    var showDialog by remember { mutableStateOf(false) }

    //----------------------------------------------------------------------------

    val context = LocalContext.current
    var workoutRoutines by remember { mutableStateOf<List<WorkoutRoutineRequest>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var hasShownError by remember { mutableStateOf(false) }
    var userProfile by remember { mutableStateOf<UserProfile?>(null) }
    // Fetch workout routines on launch
    LaunchedEffect(Unit) {
        showDialog = false

        isLoading = true
        fetchUserProfile(context) { profile, error ->
            userProfile = profile
            errorMessage = error
        }

        fetchWorkoutRoutines(context) { routines, error ->
            workoutRoutines = routines
            errorMessage = error
            isLoading = false

            // Show the dialog if there's an error after loading is complete
            if (error != null) {
                showDialog = true
            }
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
                                .padding(horizontal = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp),
                            ) {
                                // Add a header or any other static content before the list
                                // Display the fitness goal before the header
                                userProfile?.fitnessGoal?.let {
                                    Box(
                                        modifier = Modifier
                                            .padding(horizontal = 12.dp)
                                            .clip(shape = RoundedCornerShape(20.dp))
                                            .width(160.dp)
                                            .align(Alignment.End)
                                            .background(DirtyWhite)
                                            .padding(horizontal = 8.dp, vertical = 6.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "$it",
                                            color = DarkGreen,
                                            style = TextStyle(
                                                fontFamily = titleFont,
                                                fontSize = 18.sp
                                            ),
                                        )
                                    }
                                }


                                if (workoutRoutines.isNotEmpty()) {
                                        WorkoutRoutinesList(navController, workoutRoutines, containerOpacity = 0.2f)
                                    } else {
                                    Column(
                                        modifier = Modifier
                                            .padding(bottom = 20.dp)
                                            .fillMaxSize(),  // This makes the column take up the whole screen
                                        verticalArrangement = Arrangement.Center,  // Centers the content vertically
                                        horizontalAlignment = Alignment.CenterHorizontally  // Centers the content horizontally
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(12.dp))
                                                .height(70.dp)
                                                .width(400.dp)
                                                .background(DirtyWhite)
                                                .padding(18.dp)
                                        ) {
                                            Text(
                                                text = "Tip: Generate your Workout Routine by clicking Spot button below!",
                                                color = DarkGreen,
                                                fontFamily = alt,
                                                textAlign = TextAlign.Justify,
                                                fontSize = 18.sp
                                            )
                                        }
                                    }
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
                CircularProgressIndicator(color = DarkGreen)
            } else {
                // Display error message if exists
                errorMessage?.let {
                    if (!hasShownError) {
                        hasShownError = true
                        showDialog = true
                    }
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically){
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
                                        text = "In order for us to recommend your personalized workout routine, a quick user assessment is needed.",
                                        color = DarkGreen,
                                        fontFamily = titleFont,
                                        textAlign = TextAlign.Justify
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = "To learn more about how we process your data, read our Terms and Conditions.",
                                        color = DarkGreen,
                                        fontFamily = titleFont,
                                        modifier = Modifier.clickable { /* Handle T&C click */ }
                                    )
                                }
                            }
                        },
                        confirmButton = { TextButton(
                            onClick = {
                                showDialog = false
                                navController.navigate("bmi")
                            },
                            modifier = Modifier.background(Slime, shape = RoundedCornerShape(16.dp))
                        ) {
                            Text("Proceed", color = DarkGreen, fontFamily = titleFont,)
                        } })
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

private fun fetchUserProfile(context: Context, onResult: (UserProfile?, String?) -> Unit) {
    val service = RetrofitInstance.UserProfileService(context)

    CoroutineScope(Dispatchers.IO).launch {
        try {
            // Call the suspend function to get a Response<UserProfile>
            val response = service.getProfile()

            // Check if the response is successful
            if (response.isSuccessful) {
                val profile = response.body() // Get the UserProfile
                onResult(profile, null) // Return the profile and null error
            } else {
                // Handle unsuccessful response
                onResult(null, response.message()) // Return null profile and error message
            }
        } catch (e: Exception) {
            // Handle any exceptions that occur
            onResult(null, e.message) // Return null profile and exception message
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

    val context = LocalContext.current

    LazyColumn(modifier = Modifier.padding(8.dp)) {
        items(groupedRoutines.toList()) { (dayNum, routines) ->

            val isRestDay = routines.all { it.workoutInfo?.workout?.name.isNullOrEmpty() }

            // Count finished workouts for the day
            val finishedCount = routines.count { it.isFinished }

            // Calculate the progress
            val totalWorkouts = routines.size // Get the total number of workouts for the day
            val progress = if (totalWorkouts > 0) {
                finishedCount.toFloat() / totalWorkouts.toFloat() // Use the total workouts for progress
            } else {
                0f // Avoid division by zero
            }

            Box(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(DirtyWhite.copy(alpha = containerOpacity), RoundedCornerShape(8.dp))
                    .clickable {
                        if (isRestDay) {
                            // Show a flash message for rest day
                            Toast
                                .makeText(
                                    context,
                                    "It's your rest day, please consider taking care of yourself!",
                                    Toast.LENGTH_LONG
                                )
                                .show()
                        } else {
                            // Navigate to workoutDay
                            navController.navigate("workoutDay/$dayNum")
                        }
                    }
                    .padding(16.dp)
            ) {

                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween // Arrange items between ends
                    ) {
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

                        Spacer(modifier = Modifier.padding(start=140.dp))

                        if (!isRestDay) {
                            // Move the progress Row to the rightmost side
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp) // Space between label and progress bar
                            ) {
                                Text(
                                    text = "Progress:",
                                    fontFamily = alt,
                                    fontSize = 16.sp,
                                    color = Slime
                                )

                                // Create a Box to hold the progress indicator and the count
                                Box(modifier = Modifier.weight(1f)) {
                                    Column(
                                        horizontalAlignment = Alignment.End,
                                        verticalArrangement = Arrangement.Top // Align elements to the top
                                    ) {
                                        // Show the count above the progress bar
                                        Text(
                                            text = "$finishedCount/$totalWorkouts",
                                            fontFamily = alt,
                                            fontSize = 12.sp,
                                            color = Slime,
                                            modifier = Modifier.padding(bottom = 2.dp) // Add spacing below the count
                                        )

                                        LinearProgressIndicator(
                                            progress = progress,
                                            color = Slime,
                                            modifier = Modifier
                                                .width(100.dp)
                                                .height(8.dp)
                                                .background(DirtyWhite)
                                        )
                                        Spacer(modifier = Modifier.padding(bottom = 12.dp))
                                    }
                                }
                            }
                        }
                    }

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
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

        Spacer(modifier = Modifier.height(30.dp))

        routine.workoutInfo?.let { workoutInfo ->
            // Check if the workout name is null or empty
            val workoutName = workoutInfo.workout.name ?: ""
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(top = 20.dp)
            ) {
                Text(
                    text = if (workoutName.isEmpty()) "Rest Day" else workoutName,
                    fontFamily = alt,
                    fontSize = 12.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Only show equipment if it's not a rest day
                if (workoutName.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(20.dp))
                            .background(Slime)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${workoutInfo.workout.equipment}",
                            style = TextStyle(fontSize = 8.sp, color = DarkGreen, fontFamily = alt),
                        )
                    }
                }
            }
        } ?: run {
            // Handle the case where workoutInfo is null
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 20.dp)
            ) {
                Text(
                    text = "Rest Day",
                    fontFamily = alt,
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
        }
    }
}


fun performLogout(context: Context, navController: NavController) {
    TokenManager.clearToken(context)
    navController.navigate("login") {
        popUpTo("main") { inclusive = true }  // Clear the backstack to prevent navigation back
    }
}