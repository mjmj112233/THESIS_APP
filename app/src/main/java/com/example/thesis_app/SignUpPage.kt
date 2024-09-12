package com.example.thesis_app

import androidx.compose.foundation.Image
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
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
import com.example.thesis_app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun signupPage(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGreen) // Background color for the entire screen
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(580.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(DirtyWhite)
                        .padding(20.dp)
                        .fillMaxWidth(0.8f)
                        .height(500.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    // State for text field values
                    var username by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }
                    var confirmPassword by remember { mutableStateOf("") }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 20.dp)
                    ) {
                        Text(
                            text = "Create your account",
                            color = DarkGreen,
                            style = TextStyle(fontFamily = titleFont, fontSize = 24.sp),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 40.dp, bottom = 10.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Username field
                        AnimatedTextField(
                            label = "Username",
                            value = username,
                            onValueChange = { username = it }
                        )

                        Spacer(modifier = Modifier.height(17.dp))

                        // Password field
                        AnimatedTextField(
                            label = "Password",
                            value = password,
                            onValueChange = { password = it }
                        )

                        Spacer(modifier = Modifier.height(17.dp))

                        // Confirm Password field
                        AnimatedTextField(
                            label = "Confirm Password",
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it }
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // FloatingActionButton and login text
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                FloatingActionButton(
                                    onClick = { navController.navigate("welcome") },
                                    containerColor = Slime,
                                    contentColor = DarkGreen,
                                    modifier = Modifier.size(60.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.baseline_arrow_forward),
                                        contentDescription = null
                                    )
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Already have an account?",
                                        style = TextStyle(
                                            fontFamily = captionFont,
                                            fontSize = 12.sp,
                                            color = Blackk
                                        )
                                    )

                                    Spacer(modifier = Modifier.width(4.dp))

                                    ClickableText(
                                        text = buildAnnotatedString {
                                            withStyle(style = SpanStyle(color = DarkGreen)) {
                                                append("Login here.")
                                            }
                                        },
                                        onClick = { navController.navigate("login") },
                                        style = TextStyle(
                                            fontFamily = titleFont,
                                            fontSize = 13.sp
                                        )
                                    )
                                }

                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    }
                }

                // Logo circle
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(shape = RoundedCornerShape(50.dp))
                        .background(Slime)
                        .align(Alignment.TopCenter)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.spot_logo_white),
                        contentDescription = "Logo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    val isLabelSmall = isFocused || value.isNotEmpty()
    val focusRequester = remember { FocusRequester() }

    // Animated properties for label based on focus or if text is present
    val labelOffset by animateDpAsState(if (isLabelSmall) 16.dp else 0.dp)
    val labelFontSize by animateFloatAsState(if (isLabelSmall) 10f else 16f)

    TextField(
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        label = {
            // Animated label
            AnimatedVisibility(
                visible = true, // Always visible
                modifier = Modifier.padding(bottom = labelOffset)
            ) {
                Text(
                    text = label,
                    style = TextStyle(
                        fontFamily = titleFont,
                        fontSize = labelFontSize.sp,
                        color = DarkerAsh
                    )
                )
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            containerColor = Ash
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp)
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            }
    )
}
