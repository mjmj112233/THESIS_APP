package com.example.thesis_app

import android.widget.Space
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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thesis_app.ui.theme.BlueGreen
import com.example.thesis_app.ui.theme.DarkGreen
import com.example.thesis_app.ui.theme.DirtyWhite
import com.example.thesis_app.ui.theme.Slime
import com.example.thesis_app.ui.theme.alt
import com.example.thesis_app.ui.theme.titleFont
import kotlinx.coroutines.handleCoroutineException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDayPage(navController: NavController, dayName: String? = null, workouts: List<String>) {
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
                                text = (dayName ?: "No Day Selected").uppercase(),
                                fontSize = 20.sp,
                                fontFamily = titleFont,
                                color = DirtyWhite,
                                textAlign = TextAlign.Center
                            )
                        }

                        // Check if workouts list is empty
                        if (workouts.isEmpty()) {
                            // Display Rest Day message
                            Text(
                                text = "REST DAY",
                                fontSize = 24.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentSize(Alignment.Center)
                            )
                        } else {
                            // Display workouts in a LazyColumn
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(bottom = 100.dp) // Add bottom padding to avoid overlap with FAB
                            ) {
                                items(workouts) { workout ->
                                    // Example values for reps, sets, and weight, modify as needed
                                    val reps = 8  // Example value
                                    val sets = 3   // Example value
                                    val weight = 7.5 // Example value in kg
                                    WorkoutItem(workout, reps, sets, weight, containerOpacity = 1f,
                                        onClick = { // Handle click to navigate
                                            navController.navigate("workoutInfo/$workout/$reps/$sets/$weight")
                                        })
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



@Composable
fun WorkoutItem(workout: String, reps: Int, sets: Int, weight: Double, containerOpacity: Float = 1f, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .height(140.dp)
            .clip(RoundedCornerShape(8.dp))  // Clip to rounded corners
            .clickable { onClick() }
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

        // Text container with a gradient effect
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(200.dp)  // Adjust the width to fit the text container
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            BlueGreen,
                            Color.Transparent
                        ), // Gradient from Slime to transparent
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
                    text = workout,
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
                            text = "$sets sets",
                            style = TextStyle(fontSize = 14.sp, color = DarkGreen, fontFamily = alt),
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
                            text = "$reps reps",
                            style = TextStyle(fontSize = 14.sp, color = DarkGreen, fontFamily = alt),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Weight box with border
                OutlinedCard(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent,
                    ),
                    border = BorderStroke(4.dp, Slime),
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                ) {
                    Text(
                        text = "$weight kg",
                        style = TextStyle(fontSize = 14.sp, color = Slime, fontFamily = alt),
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .clip(shape = RoundedCornerShape(20.dp))
                    )
                }
            }
        }

        // Add the button to the rightmost side
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Slime) // Background can be adjusted if needed
                .clickable { /* Handle button click */ }
                .size(40.dp), // Set the size of the box
            contentAlignment = Alignment.Center // Center the icon inside the box
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_directions_run_24),
                contentDescription = "Run Icon",
                modifier = Modifier.size(20.dp), // Icon size
                tint = DarkGreen // Adjust tint color as needed
            )
        }
    }
}











