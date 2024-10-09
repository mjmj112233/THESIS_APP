package com.example.thesis_app

import android.content.Context
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class WorkoutDay(val day: String, val workouts: List<String>?, val isRestDay: Boolean)

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
                            .clickable { navController.navigate("login") }
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
                                .padding(32.dp)
                        ) {
                                Text(
                                    text = "Your Personalized Workout Routine",
                                    color = DirtyWhite,
                                    style = TextStyle(fontFamily = titleFont, fontSize = 20.sp),
                                    modifier = Modifier.padding(top = 30.dp)
                                )

                            if (isLoading) {
                                CircularProgressIndicator()
                            } else {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 80.dp)
                                ) {
                                    item { // Wrap the content in an item block
                                        if (workoutRoutines.isNotEmpty()) {
                                            WorkoutRoutinesList(workoutRoutines, containerOpacity = 0.2f)
                                        } else {
                                            BasicText(text = "No workout routines available.")
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
                CircularProgressIndicator()
            } else {
                // Display error message if exists
                errorMessage?.let {
                    BasicText(text = it)
                }
                // Floating Action Button to generate workout routines
                FloatingActionButton(
                    onClick = {
                        // Handle button click to generate workout routines
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
                        modifier = Modifier.size(40.dp) // Adjust size as needed
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
            val response = service.generateWorkoutRoutine() // Call your generate function here
            // Flattening the map values into a single list
            val generatedRoutines = response.values.flatten() // Flattening if multiple keys exist
            onResult(generatedRoutines, null)
        } catch (e: Exception) {
            onResult(emptyList(), e.message)
        }
    }
}

@Composable
fun daysContainer(navController: NavController, workoutDay: WorkoutDay, workouts: List<String>, containerOpacity: Float = 1f) {
    Box(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .height(250.dp)
            .background(DirtyWhite.copy(alpha = containerOpacity), RoundedCornerShape(8.dp))
            .clickable {
                navController.navigate("workoutDay/${workoutDay.day}/${workouts.joinToString(",")}")
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
                    text = workoutDay.day,
                    fontFamily = alt,
                    fontSize = 16.sp,
                    color = DarkGreen
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (workoutDay.isRestDay) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LazyRow(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        contentPadding = PaddingValues(horizontal = 0.dp)
                    ) {
                        workoutDay.workouts?.let { workouts ->
                            items(workouts) { workout ->
                                workoutBox(workout, containerOpacity = 0.3f)
                            }
                        }
                    }

                    Text(
                        text = "Rest Day",
                        fontFamily = alt,
                        fontSize = 32.sp,
                        color = DirtyWhite,
                        modifier = Modifier
                            .offset(y = (-20).dp)
                            .wrapContentHeight()
                    )
                }
            } else {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 0.dp)
                ) {
                    workoutDay.workouts?.let { workouts ->
                        items(workouts) { workout ->
                            workoutBox(workout, containerOpacity = 0.3f)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WorkoutRoutinesList(workoutRoutines: List<WorkoutRoutineRequest>, containerOpacity: Float = 1f) {
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
                }



            }

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 0.dp)
            ) {
                items(routines) { routine ->
                    WorkoutRoutineCard(routine, containerOpacity = 0.3f)
                }
            }

            // Add a divider after each day
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
fun workoutBox(workout: String, containerOpacity: Float = 1f) {
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
        Text(
            text = workout,
            fontFamily = alt,
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        )
    }
}

@Composable
fun WorkoutRoutineCard(routine: WorkoutRoutineRequest, containerOpacity: Float = 1f) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Display workout info
            routine.workoutInfo?.let { workoutInfo ->
                Text(text = "Exercise: ${workoutInfo.workout.name}", style = MaterialTheme.typography.titleMedium)
            }
        }
    }

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
            Text(text = "Exercise: ${workoutInfo.workout.name}",
                fontFamily = alt,
                fontSize = 12.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp))
        }

    }
}