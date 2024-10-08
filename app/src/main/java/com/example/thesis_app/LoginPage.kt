package com.example.thesis_app

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.model.LoginRequest
import com.example.model.LoginResponse
import com.example.repository.AuthRepository
import com.example.thesis_app.ui.theme.*
import com.example.util.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun loginPage(navController: NavController) {
    // Add a coroutine scope for handling API calls
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val authRepository = AuthRepository(context) // Adjusted to not require context

    // State for error message
    var errorMessage by remember { mutableStateOf("") } // Error message state

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGreen)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier.fillMaxWidth().height(480.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(DirtyWhite)
                        .padding(20.dp)
                        .fillMaxWidth(0.8f)
                        .height(400.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    var username by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }
                    var isUsernameFocused by remember { mutableStateOf(false) }
                    var isPasswordFocused by remember { mutableStateOf(false) }
                    val usernameFocusRequester = remember { FocusRequester() }
                    val passwordFocusRequester = remember { FocusRequester() }

                    Column(
                        modifier = Modifier.fillMaxSize().padding(top = 8.dp)
                    ) {
                        Text(
                            text = "Login",
                            color = DarkGreen,
                            style = TextStyle(fontFamily = titleFont, fontSize = 24.sp),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 40.dp, bottom = 10.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Username TextField
                        TextField(
                            value = username,
                            onValueChange = { newValue -> username = newValue },
                            label = {
                                val labelOffset by animateDpAsState(if (isUsernameFocused || username.isNotEmpty()) 16.dp else 0.dp)
                                val labelFontSize by animateFloatAsState(if (isUsernameFocused || username.isNotEmpty()) 10f else 16f)

                                AnimatedVisibility(visible = true, modifier = Modifier.padding(bottom = labelOffset)) {
                                    Text(
                                        text = "Username",
                                        style = TextStyle(fontFamily = titleFont, fontSize = labelFontSize.sp, color = DarkerAsh)
                                    )
                                }
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                containerColor = Ash
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp)
                                .focusRequester(usernameFocusRequester)
                                .onFocusChanged { focusState -> isUsernameFocused = focusState.isFocused }
                        )

                        Spacer(modifier = Modifier.height(17.dp))

                        // Password TextField
                        TextField(
                            value = password,
                            onValueChange = { newValue -> password = newValue },
                            label = {
                                val labelOffset by animateDpAsState(if (isPasswordFocused || password.isNotEmpty()) 16.dp else 0.dp)
                                val labelFontSize by animateFloatAsState(if (isPasswordFocused || password.isNotEmpty()) 10f else 16f)

                                AnimatedVisibility(visible = true, modifier = Modifier.padding(bottom = labelOffset)) {
                                    Text(
                                        text = "Password",
                                        style = TextStyle(fontFamily = titleFont, fontSize = labelFontSize.sp, color = DarkerAsh)
                                    )
                                }
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                containerColor = Ash
                            ),
                            shape = RoundedCornerShape(20.dp),
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp)
                                .focusRequester(passwordFocusRequester)
                                .onFocusChanged { focusState -> isPasswordFocused = focusState.isFocused }
                        )

                        // Login button and functionality
                        Box(modifier = Modifier.height(160.dp), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                                Button(
                                    onClick = {
                                        if (username.isEmpty() || password.isEmpty()) {
                                            Toast.makeText(context, "Please enter your Username and Password", Toast.LENGTH_SHORT).show()
                                        } else {
                                            coroutineScope.launch {
                                                handleLogin(username, password, authRepository, context, navController, { error ->
                                                    errorMessage = error }
                                                )
                                            }
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(Slime),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "Login", color = DarkGreen, fontFamily = titleFont, fontSize = 20.sp)
                                }

                                if (errorMessage.isNotEmpty()) {
                                    Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(8.dp))
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Don't have an account yet?",
                                        style = TextStyle(fontFamily = captionFont, fontSize = 12.sp, color = Blackk)
                                    )

                                    Spacer(modifier = Modifier.width(4.dp))

                                    ClickableText(
                                        text = buildAnnotatedString {
                                            withStyle(style = SpanStyle(color = DarkGreen)) {
                                                append("Sign up here.")
                                            }
                                        },
                                        onClick = { navController.navigate("signup") },
                                        style = TextStyle(fontFamily = titleFont, fontSize = 13.sp)
                                    )
                                }
                            }
                        }
                    }
                }

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
                        modifier = Modifier.size(60.dp).align(Alignment.Center)
                    )
                }
            }
        }
    }
}

suspend fun handleLogin(
    username: String,
    password: String,
    authRepository: AuthRepository,
    context: Context,
    navController: NavController,
    setError: (String) -> Unit // Pass a lambda to set the error message
) {
    // Prepare User data
    val loginRequest = LoginRequest(username, password)

    // Make the API call
    authRepository.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
            if (response.isSuccessful) {
                // Save token using TokenManager
                val token = response.body()?.token
                token?.let {
                    TokenManager.saveToken(context, it) // Save the token in shared preferences
                }
                // Navigate to main screen
                navController.navigate("main")
            } else if (response.code() == 401){
                Toast.makeText(context, "Enter valid username or password", Toast.LENGTH_SHORT).show()
            } else {
                // Show login failed message
                Toast.makeText(context, "Login failed: ${response.message()}", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
            // Show error message
            Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()// Call the lambda to set error
        }
    })
}


