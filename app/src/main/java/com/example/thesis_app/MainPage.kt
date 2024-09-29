package com.example.thesis_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thesis_app.ui.theme.BlueGreen
import com.example.thesis_app.ui.theme.DarkGreen
import com.example.thesis_app.ui.theme.DirtyWhite
import com.example.thesis_app.ui.theme.Slime
import com.example.thesis_app.ui.theme.alt
import com.example.thesis_app.ui.theme.captionFont
import com.example.thesis_app.ui.theme.titleFont

data class WorkoutDay(val day: String, val workouts: List<String>?, val isRestDay: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainPage(navController: NavController) {
    // List of workouts

    val workoutDays = listOf(
        WorkoutDay("DAY 1", listOf("Lat Pulldown", "Chest Press Machine", "Workout 3", "Workout 4", "Workout 5", "Workout 6"), false),
        WorkoutDay("DAY 2", listOf("Cardio 1"), true), // Rest day
        WorkoutDay("DAY 3", listOf("Workout 1", "Workout 2", "Workout 3", "Workout 4", "Workout 5", "Workout 6"), false),
        WorkoutDay("DAY 4", listOf("Cardio 1"), true), // Rest day
        WorkoutDay("DAY 5", listOf("Workout 1", "Workout 2", "Workout 3", "Workout 4", "Workout 5", "Workout 6"), false),
        WorkoutDay("DAY 6", listOf("Workout 1", "Workout 2", "Workout 3", "Workout 4", "Workout 5", "Workout 6"), false),
        WorkoutDay("DAY 7", listOf("Cardio 1"), true) // Rest day
    )


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
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            painter = painterResource(R.drawable.menu),
                            contentDescription = "Menu",
                            modifier = Modifier
                                .size(40.dp)
                                .clickable { /* Handle menu click */ }
                                .graphicsLayer(scaleX = -1f)
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
                        .padding(start = 32.dp, end = 32.dp, bottom = 28.dp),
                    contentAlignment = Alignment.TopStart
                ) {

                    LazyColumn(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 100.dp)
                    ) {
                        items(workoutDays) { workoutDay ->
                            daysContainer(navController = navController, workoutDay = workoutDay, workouts = workoutDay.workouts ?: emptyList(), containerOpacity = 0.2f)
                        }
                    }



                    Text(
                        text = "Your Personalized Workout Routine",
                        color = DirtyWhite,
                        style = TextStyle(fontFamily = titleFont, fontSize = 20.sp),
                        modifier = Modifier.padding(top = 60.dp)
                    )
                }
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = Slime,
                    modifier = Modifier.height(80.dp)
                ) {}
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
fun ClickableContainer(
    backgroundImage: Painter,
    cornerRadius: Dp = 12.dp, // Default corner radius
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(cornerRadius))
            .clickable(onClick = onClick)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = backgroundImage,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0xFF1DA355)),
                            startY = 0f,
                            endY = 500f
                        )
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                content()
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
            .height(250.dp) // Adjusted height for horizontal scrolling
            .background(DirtyWhite.copy(alpha = containerOpacity), RoundedCornerShape(8.dp))
            .clickable {
                navController.navigate("workoutDay/${workoutDay.day}/${workouts.joinToString(",")}")
            }
            .padding(16.dp)
    ) {
        Column {
            // Header for the day
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(12.dp))
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
                        }
                    }
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





