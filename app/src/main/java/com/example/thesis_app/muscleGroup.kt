package com.example.thesis_app

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thesis_app.ui.theme.*

@Composable
fun muscleGroupScreen(
    navController: NavController,
    height: Double,
    weight: Double,
    bmiCategory: String,
    fitnessGoal: String,
    onMuscleGroupSelected: (String) -> Unit
) {
    var selectedUpper by remember { mutableStateOf(false) }
    var selectedLower by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Automatically assign if fitness goal is Weight Loss
    var muscleGroup by remember {
        mutableStateOf(
            if (fitnessGoal == "Weight Loss") "Weight Loss" else ""
        )
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
                            text = "Muscle Group",
                            color = DirtyWhite,
                            style = TextStyle(fontFamily = titleFont, fontSize = 24.sp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Circle Logo
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
            }
        }

        // Centered Row for Muscle Selection (only if fitnessGoal != Weight Loss)
        if (fitnessGoal != "Weight Loss") {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Upper Body Selection
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(if (selectedUpper) Slime else Color.Gray)
                                .clickable {
                                    selectedUpper = !selectedUpper
                                    updateMuscleGroup(selectedUpper, selectedLower) { updatedGroup ->
                                        muscleGroup = updatedGroup
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.upper2),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(DirtyWhite),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Upper Muscles",
                            fontSize = 16.sp,
                            fontFamily = titleFont,
                            color = Slime,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(32.dp))

                    // Lower Body Selection
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(if (selectedLower) Slime else Color.Gray)
                                .clickable {
                                    selectedLower = !selectedLower
                                    updateMuscleGroup(selectedUpper, selectedLower) { updatedGroup ->
                                        muscleGroup = updatedGroup
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.lower),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(DirtyWhite),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Lower Muscles",
                            fontSize = 16.sp,
                            fontFamily = titleFont,
                            color = Slime,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 24.dp)
                        )
                    }
                }
            }
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Upper Body Selection
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(Slime)
                                .clickable {
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.wl),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(DirtyWhite),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Both Muscles",
                            fontSize = 16.sp,
                            fontFamily = titleFont,
                            color = Slime,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 24.dp)
                        )
                    }
                }
            }
        }

        // Finish Button
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            // Inside your muscleGroupScreen where you handle the button click
            Button(
                onClick = {
                    // Ensure muscleGroup is valid
                    if (muscleGroup.isNotEmpty()) {
                        // Log the values before navigating
                        Log.d("MuscleGroupScreen", "Navigating to PushupScreen with Height: $height, Weight: $weight")
                        // Proceed to the next screen, passing all necessary parameters
                        onMuscleGroupSelected(muscleGroup)

                    } else {
                        Toast.makeText(context, "Please choose your target muscle group", Toast.LENGTH_SHORT).show()
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

// Helper function to determine the muscle group based on user selection
fun updateMuscleGroup(selectedUpper: Boolean, selectedLower: Boolean, onUpdate: (String) -> Unit) {
    when {
        selectedUpper && selectedLower -> onUpdate("Both")
        selectedUpper -> onUpdate("Upper")
        selectedLower -> onUpdate("Lower")
        else -> onUpdate("") // No selection
    }
}

