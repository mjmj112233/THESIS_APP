package com.example.thesis_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.thesis_app.ui.theme.BlueGreen
import com.example.thesis_app.ui.theme.DarkGreen
import com.example.thesis_app.ui.theme.DirtyWhite
import com.example.thesis_app.ui.theme.Slime
import com.example.thesis_app.ui.theme.alt
import com.example.thesis_app.ui.theme.captionFont
import com.example.thesis_app.ui.theme.titleFont
import kotlinx.coroutines.launch

data class WorkoutDay(val day: String, val workouts: List<String>?, val isRestDay: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainPage(navController: NavController) {
    // State to control the visibility of the drawer
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // State to control the visibility of the alert dialog
    var showDialog by remember { mutableStateOf(true) }

    // Empty list for testing
    val workoutDays = listOf<WorkoutDay>()

//    val workoutDays = listOf(
//        WorkoutDay("DAY 1", listOf("Lat Pulldown", "Chest Press Machine", "Workout 3", "Workout 4", "Workout 5", "Workout 6"), false),
//        WorkoutDay("DAY 2", listOf("Cardio 1"), true), // Rest day
//        WorkoutDay("DAY 3", listOf("Workout 1", "Workout 2", "Workout 3", "Workout 4", "Workout 5", "Workout 6"), false),
//        WorkoutDay("DAY 4", listOf("Cardio 1"), true), // Rest day
//        WorkoutDay("DAY 5", listOf("Workout 1", "Workout 2", "Workout 3", "Workout 4", "Workout 5", "Workout 6"), false),
//        WorkoutDay("DAY 6", listOf("Workout 1", "Workout 2", "Workout 3", "Workout 4", "Workout 5", "Workout 6"), false),
//        WorkoutDay("DAY 7", listOf("Cardio 1"), true) // Rest day
//    )

    if (workoutDays.isEmpty() && showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            text = {
                Row(verticalAlignment = Alignment.CenterVertically) {
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
            confirmButton = {
                TextButton(
                    onClick = { showDialog = false },
                    modifier = Modifier.background(Slime, shape = RoundedCornerShape(16.dp))
                ) {
                    Text("Proceed", color = DarkGreen, fontFamily = titleFont,)
                }
            }
        )
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
                            .clickable { /* Handle Settings click */ }
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

                            //check if workout is empty, to show button
                            if (workoutDays.isEmpty()) {
                                if (!showDialog) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Button(
                                            onClick = { navController.navigate("bmi") },
                                            colors = ButtonDefaults.buttonColors(containerColor = Slime),
                                            modifier = Modifier
                                                .padding(vertical = 20.dp)
                                        ) {
                                            Text(text = "Start User Assessment", color = DarkGreen, fontFamily = titleFont)
                                        }
                                    }
                                }
                            } else {

                                Text(
                                    text = "Your Personalized Workout Routine",
                                    color = DirtyWhite,
                                    style = TextStyle(fontFamily = titleFont, fontSize = 20.sp),
                                    modifier = Modifier.padding(top = 30.dp))

                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 80.dp)
                                ) {
                                    items(workoutDays) { workoutDay ->
                                        daysContainer(
                                            navController = navController,
                                            workoutDay = workoutDay,
                                            workouts = workoutDay.workouts ?: emptyList(),
                                            containerOpacity = 0.2f
                                        )
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
            FloatingActionButton(
                onClick = { /* Handle button click */ },
                shape = CircleShape,
                containerColor = DirtyWhite,
                modifier = Modifier
                    .size(100.dp)
                    .offset(y = (-20).dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.scan),
                    contentDescription = "Scan",
                    modifier = Modifier.size(40.dp),
                    tint = DarkGreen
                )
            }
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
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically // Align items vertically in the center
                ) {
                    // LazyRow for workouts
                    LazyRow(
                        modifier = Modifier
                            .weight(1f) // Take remaining space
                            .fillMaxHeight(), // Fill the height of the Row
                        contentPadding = PaddingValues(horizontal = 0.dp)
                    ) {
                        workoutDay.workouts?.let { workouts ->
                            items(workouts) { workout ->
                                workoutBox(workout, containerOpacity = 0.3f)
                            }
                        }
                    }

                    // Text for Rest Day
                    Text(
                        text = "Rest Day",
                        fontFamily = alt,
                        fontSize = 32.sp,
                        color = DirtyWhite,
                        modifier = Modifier
                            .offset(y = (-20).dp) // Use smaller padding for closer alignment
                            .wrapContentHeight() // Ensure height wraps the content
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
                        }}
                }
            }
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
        // Center the icon in the box
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter) // Position the icon at the top center
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







