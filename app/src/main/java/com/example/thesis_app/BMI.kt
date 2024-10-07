package com.example.thesis_app

import android.annotation.SuppressLint
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thesis_app.ui.theme.*

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BMIScreen(navController: NavController) {
    var heightInput by remember { mutableStateOf("") }
    var weightInput by remember { mutableStateOf("") }
    var bmiResult by remember { mutableStateOf("") }
    var bmiCategory by remember { mutableStateOf("") }
    var fitnessGoal by remember { mutableStateOf("") }
    var isBmiCalculated by remember { mutableStateOf(false) }

    // Image Background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGreen)
    ) {
        Image(
            painter = painterResource(id = R.drawable.body2),
            contentDescription = "body",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(800.dp)
                .offset(x = (-60).dp, y = (130).dp)
        )
    }

    // Header with Circle Logo
    Box(
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp, top = 40.dp)
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.padding(start = 20.dp)) {
                // Text Container
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
                        text = "Calculate your BMI",
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 95.dp, start = 80.dp), // Adjust spacing above the circles
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(6) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp) // Space between circles
                ) {

                }
                Box(
                    modifier = Modifier
                        .width(30.dp) // Adjust the size for smaller circles
                        .height(4.dp)
                        .background(if (it == 0) Slime else DirtyWhite) // Highlight first circle
                )
            }
        }
    }

    // Input Fields for Height and Weight
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(start = 40.dp, top = 200.dp, end = 40.dp, bottom = 100.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .offset(y = (-70).dp, x = 24.dp)
            ) {
                // Height Input
                InputField(
                    label = "Height (cm)",
                    inputValue = heightInput,
                    onInputChange = { heightInput = it }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .offset(x = 80.dp, y = (-40).dp)
            ) {
                InputField(
                    label = "Weight (kg)",
                    inputValue = weightInput,
                    onInputChange = { weightInput = it }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            if (!isBmiCalculated) {
                // Calculate Button
                Button(
                    onClick = {
                        val height = heightInput.trim().toFloatOrNull()?.div(100) // Convert cm to meters
                        val weight = weightInput.trim().toFloatOrNull()

                        if (height != null && weight != null && height > 0) {
                            val bmi = weight / (height * height)
                            bmiResult = String.format("%.2f", bmi)
                            isBmiCalculated = true

                            // Calculate BMI Category and Fitness Goal (cast bmi to Double)
                            bmiCategory = getBMICategory(bmi.toDouble())
                            fitnessGoal = determineFitnessGoal(bmiCategory)
                        } else {
                            println("Invalid input. Height: $height, Weight: $weight")
                        }

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Slime),
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                        .clip(CircleShape)
                ) {
                    Text(
                        text = "Calculate",
                        style = TextStyle(fontSize = 20.sp, color = DirtyWhite, fontFamily = titleFont),
                        textAlign = TextAlign.Center
                    )
                }

            } else {
                // BMI Result Display
                Box(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(Slime)
                        .height(56.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("Your BMI is ")
                            withStyle(style = SpanStyle(color = DirtyWhite, fontFamily = alt)) {
                                append(bmiResult)
                            }
                        },
                        style = TextStyle(fontSize = 20.sp, color = DarkGreen, fontFamily = alt),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Box {
                    Text(
                        text = "Press the reload button to re-calculate your BMI.",
                        style = TextStyle(fontSize = 12.sp, color = DirtyWhite, fontFamily = captionFont),
                        modifier = Modifier
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Row to hold two Floating Action Buttons
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp), // Space between the buttons
                verticalAlignment = Alignment.CenterVertically
            ) {
                // First FAB
                FloatingActionButton(
                    onClick = {
                        val height = heightInput.trim().toFloatOrNull()?.div(100) // Convert cm to meters
                        val weight = weightInput.trim().toFloatOrNull()

                        if (height != null && weight != null && height > 0) {
                            val bmi = weight / (height * height)
                            bmiResult = String.format("%.2f", bmi)
                            isBmiCalculated = true

                            // Calculate BMI Category and Fitness Goal (cast bmi to Double)
                            bmiCategory = getBMICategory(bmi.toDouble())
                            fitnessGoal = determineFitnessGoal(bmiCategory)
                        } else {
                            println("Invalid input. Height: $height, Weight: $weight")
                        }

                    },
                    containerColor = Slime,
                    contentColor = DarkGreen,
                    modifier = Modifier.size(60.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_autorenew_24),
                        contentDescription = null
                    )
                }

                // Second FAB
                FloatingActionButton(
                    onClick = {
                        if (isBmiCalculated) {
                            navController.navigate("muscleGroup?height=${heightInput}&weight=${weightInput}&bmiCategory=${bmiCategory}&fitnessGoal=${fitnessGoal}") {
                                popUpTo("bmi") { inclusive = true }  // Remove BMI screen from backstack
                                launchSingleTop = true
                            }
                        }
                    },
                    containerColor = Slime,
                    contentColor = DarkGreen,
                    modifier = Modifier.size(60.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_forward),
                        contentDescription = null
                    )
                }

            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(label: String, inputValue: String, onInputChange: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        // Input Box (Increased size)
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(DirtyWhite)
                .height(120.dp) // Increased height
                .width(120.dp) // Increased width
                .padding(8.dp)
        ) {
            TextField(
                value = inputValue,
                onValueChange = onInputChange,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp), // Reduced vertical padding to give more space for text
                textStyle = TextStyle(fontSize = 18.sp, color = DarkGreen, fontFamily = alt),
                maxLines = 1, // Ensure it stays as a single line
                singleLine = true, // Ensure it's a single line input
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Label Box
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(CircleShape)
                .background(Slime)
                .height(33.dp)
                .width(140.dp)
        ) {
            Text(
                text = label,
                color = DirtyWhite,
                style = TextStyle(fontFamily = alt, fontSize = 15.sp),
                textAlign = TextAlign.Center
            )
        }

    }
}

// Helper Functions

private fun getBMICategory(bmi: Double): String {
    return when {
        bmi < 18.5 -> "Underweight"
        bmi < 24.9 -> "Normal"
        bmi < 29.9 -> "Overweight"
        else -> "Obese"
    }
}

private fun determineFitnessGoal(bmiCategory: String): String {
    return when (bmiCategory) {
        "Underweight", "Normal" -> "Muscle Building"
        "Overweight", "Obese" -> "Weight Loss"
        else -> "Unknown"
    }
}

