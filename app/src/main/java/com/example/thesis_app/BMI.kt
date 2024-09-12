package com.example.thesis_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thesis_app.ui.theme.Blackk
import com.example.thesis_app.ui.theme.BlueGreen
import com.example.thesis_app.ui.theme.DarkGreen
import com.example.thesis_app.ui.theme.DirtyWhite
import com.example.thesis_app.ui.theme.Slime
import com.example.thesis_app.ui.theme.alt
import com.example.thesis_app.ui.theme.captionFont
import com.example.thesis_app.ui.theme.titleFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BMIScreen(navController: NavController) {
    val weightLevel = "Obese"

    // Image
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGreen)
    ) {
        Image(
            painter = painterResource(id = R.drawable.body2),
            contentDescription = "body",
            contentScale = ContentScale.Fit,  // Maintain aspect ratio, without stretching
            modifier = Modifier
                .size(800.dp) // Keep the size at least 700.dp
                .offset(x = (-60.dp), y = (130).dp)
        )
    }

    // Header
    Box(
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp, top = 40.dp)
    ) {
        Box(
            contentAlignment = Alignment.CenterStart, // Align circle to the start of the Box
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Box(
                modifier = Modifier
                    .padding(start = 20.dp)
            ) {
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
                        text = "Calculate your BMI",
                        color = DirtyWhite,
                        style = TextStyle(fontFamily = titleFont, fontSize = 24.sp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Circle
            Box(
                modifier = Modifier
                    .size(100.dp) // Set the size of the circle
                    .clip(shape = RoundedCornerShape(50.dp)) // Make it circular
                    .background(Slime) // Same background color as the text container
                    .align(Alignment.CenterStart) // Align circle to the start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.spot_logo_white), // Replace with your image resource
                    contentDescription = "Logo",
                    contentScale = ContentScale.Fit, // Ensures the image fits within the circle
                    modifier = Modifier
                        .size(70.dp) // Make the image smaller
                        .align(Alignment.Center)
                )
            }
        }
    }

    // Input fields
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(start = 40.dp, bottom = 390.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Label height
            Box(modifier = Modifier
                .offset(y = (105).dp)) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(Slime)
                        .height(33.dp)
                        .width(155.dp)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("Height")
                            withStyle(style = SpanStyle(fontFamily = alt)) {
                                append(" (")
                            }
                            append("m")
                            withStyle(style = SpanStyle(fontFamily = alt)) {
                                append(")")
                            }
                        },
                        color = DirtyWhite,
                        style = TextStyle(fontFamily = titleFont, fontSize = 15.sp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Input height
            Box(
                modifier = Modifier
                    .clip(CircleShape) // Clip to a circle
                    .background(DirtyWhite) // Set background color
                    .height(75.dp)
                    .width(75.dp) // Increased width to accommodate the "m" beside the input field
                    .padding(8.dp) // Add padding inside the Box
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically, // Align items vertically
                    horizontalArrangement = Arrangement.Center // Center items horizontally
                ) {
                    // Input field for height
                    TextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier
                            .width(20.dp) // Shorten width to fit in the circle
                            .height(30.dp)
                            .offset(y = (-10).dp),
                        textStyle = TextStyle(fontSize = 18.sp, color = DarkGreen), // Text color and size
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent, // Transparent background for TextField
                            focusedIndicatorColor = BlueGreen,  // Color when focused
                            unfocusedIndicatorColor = BlueGreen // Color when not focused
                        )
                    )

                    // Text beside the input field
                    Text(
                        text = "m",
                        style = TextStyle(fontSize = 15.sp, color = DarkGreen, fontFamily = titleFont), // Font size and color for the "m"
                        modifier = Modifier.padding(start = 4.dp) // Small padding between the input and "m"
                    )
                }
            }
        }

        // Weight input
        Column(
            modifier = Modifier
                .padding(start = 180.dp, bottom = 50.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Label weight
            Box(modifier = Modifier
                .offset(y = (105).dp)) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(Slime)
                        .height(33.dp)
                        .width(155.dp)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("Weight")
                            withStyle(style = SpanStyle(fontFamily = alt)) {
                                append(" (")
                            }
                            append("kg")
                            withStyle(style = SpanStyle(fontFamily = alt)) {
                                append(")")
                            }
                        },
                        color = DirtyWhite,
                        style = TextStyle(fontFamily = titleFont, fontSize = 15.sp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Input weight
            Box(
                modifier = Modifier
                    .clip(CircleShape) // Clip to a circle
                    .background(DirtyWhite) // Set background color
                    .height(75.dp)
                    .width(75.dp) // Increased width to accommodate the "kg" beside the input field
                    .padding(8.dp) // Add padding inside the Box
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp, vertical = 10.dp)
                        .offset(x = (0).dp), // Adjust padding as needed
                    verticalAlignment = Alignment.CenterVertically, // Align items vertically
                    horizontalArrangement = Arrangement.Center // Center items horizontally
                ) {
                    // Input field for weight
                    TextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier
                            .width(20.dp) // Shorten width to fit in the circle
                            .height(30.dp)
                            .offset(y = (-10).dp),
                        textStyle = TextStyle(fontSize = 18.sp, color = DarkGreen), // Text color and size
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent, // Transparent background for TextField
                            focusedIndicatorColor = BlueGreen,  // Color when focused
                            unfocusedIndicatorColor = BlueGreen // Color when not focused
                        )
                    )

                    // Text beside the input field
                    Text(
                        text = "kg",
                        style = TextStyle(fontSize = 15.sp, color = DarkGreen, fontFamily = titleFont), // Font size and color for the "kg"
                        modifier = Modifier.padding(start = 4.dp) // Small padding between the input and "kg"
                    )
                }
            }
        }

        // Display result
        Column(
            modifier = Modifier
                .padding(bottom = 180.dp, start = 20.dp, end = 20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            // Result text
            Box(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .background(Slime)
                    .height(56.dp)
                    .width(300.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically, // Align items vertically
                    horizontalArrangement = Arrangement.Center // Center items horizontally
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("You are ")
                            withStyle(style = SpanStyle(color = DirtyWhite)) {
                                append("$weightLevel")
                            }
                        },
                        style = TextStyle(fontSize = 20.sp, color = DarkGreen, fontFamily = titleFont),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp), // Adjusted padding to position the FAB below the result text
            contentAlignment = Alignment.BottomCenter
        ) {
            FloatingActionButton(
                onClick = { navController.navigate("sex") },
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
