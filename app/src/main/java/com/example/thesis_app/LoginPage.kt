package com.example.thesis_app

import androidx.compose.foundation.Image
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thesis_app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun loginPage(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGreen) // Background color for the entire screen
    ) {
        // Column to vertically center content
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Box containing the circle and the login container
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(480.dp)
            ) {
                // Login container
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(DirtyWhite)
                        .padding(20.dp)
                        .fillMaxWidth(0.8f)
                        .height(400.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    // State for text field values and focus
                    var username by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }
                    var isUsernameFocused by remember { mutableStateOf(false) }
                    var isPasswordFocused by remember { mutableStateOf(false) }
                    val usernameFocusRequester = remember { FocusRequester() }
                    val passwordFocusRequester = remember { FocusRequester() }

                    // Column for text and text fields
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp)
                    ) {
                        // Login text
                        Text(
                            text = "Login",
                            color = DarkGreen,
                            style = TextStyle(fontFamily = titleFont, fontSize = 24.sp),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 40.dp, bottom = 10.dp)
                        )

                        // Spacer to create space between the text and the TextField
                        Spacer(modifier = Modifier.height(20.dp))

                        // Username TextField with animated label
                        TextField(
                            value = username,
                            onValueChange = { newValue -> username = newValue },
                            label = {
                                val labelOffset by animateDpAsState(if (isUsernameFocused || username.isNotEmpty()) 16.dp else 0.dp)
                                val labelFontSize by animateFloatAsState(if (isUsernameFocused || username.isNotEmpty()) 10f else 16f)

                                AnimatedVisibility(
                                    visible = true,
                                    modifier = Modifier.padding(bottom = labelOffset)
                                ) {
                                    Text(
                                        text = "Username",
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
                                .focusRequester(usernameFocusRequester)
                                .onFocusChanged { focusState -> isUsernameFocused = focusState.isFocused }
                        )

                        Spacer(modifier = Modifier.height(17.dp))

                        // Password TextField with animated label and password masking
                        TextField(
                            value = password,
                            onValueChange = { newValue -> password = newValue },
                            label = {
                                val labelOffset by animateDpAsState(if (isPasswordFocused || password.isNotEmpty()) 16.dp else 0.dp)
                                val labelFontSize by animateFloatAsState(if (isPasswordFocused || password.isNotEmpty()) 10f else 16f)

                                AnimatedVisibility(
                                    visible = true,
                                    modifier = Modifier.padding(bottom = labelOffset)
                                ) {
                                    Text(
                                        text = "Password",
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
                            visualTransformation = PasswordVisualTransformation(), // Masked input for password
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 2.dp)
                                .focusRequester(passwordFocusRequester)
                                .onFocusChanged { focusState -> isPasswordFocused = focusState.isFocused }
                        )

                        Box(
                            modifier = Modifier
                                .height(160.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    onClick = { navController.navigate("main") },
                                    colors = ButtonDefaults.buttonColors(Slime),
                                    modifier = Modifier
                                        .padding(start = 0.dp, bottom = 10.dp, end = 0.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Login",
                                        color = DarkGreen,
                                        fontFamily = titleFont,
                                        fontSize = 20.sp
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Don't have an account yet?",
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
                                                append("Sign up here.")
                                            }
                                        },
                                        onClick = {
                                            navController.navigate("signup")
                                        },
                                        style = TextStyle(
                                            fontFamily = titleFont,
                                            fontSize = 13.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(shape = RoundedCornerShape(50.dp)) // Circle shape
                        .background(Slime)
                        .align(Alignment.TopCenter)
                ) {
                    // Image inside the circle
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



