package com.example.thesis_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
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

@Composable
fun thirdPage(navController: NavController) {
    // State to track if the dialog is shown
    val showDialog = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.b2),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize()
        )

        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, BlueGreen),
                        startY = 0f,
                        endY = 1500f
                    )
                )
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = buildAnnotatedString {
                    append("We also value your data! Read ")
                    withStyle(style = SpanStyle(color = Slime, fontFamily = titleFont, fontSize = 20.sp)) {
                        append("Spot’s ")
                    }
                    append("Terms & Conditions here.")
                },
                style = TextStyle(
                    fontFamily = captionFont,
                    fontSize = 16.sp,
                    lineHeight = 25.sp,
                    textAlign = TextAlign.Justify,
                    color = DirtyWhite
                ),
                modifier = Modifier.padding(start = 30.dp, end = 35.dp, bottom = 40.dp)
            )

            // Button
            Button(
                onClick = { showDialog.value = true },
                colors = ButtonDefaults.buttonColors(Slime),
                modifier = Modifier
                    .padding(start = 30.dp, bottom = 50.dp, end = 30.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Terms ")
                        withStyle(style = SpanStyle(fontFamily = alt)) {
                            append("&")
                        }
                        append(" Conditions")
                    },
                    color = DarkGreen,
                    fontFamily = titleFont,
                    fontSize = 24.sp
                )

            }
        }

        // Terms and Conditions Dialog
        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = {
                    Text(
                        text = buildAnnotatedString {
                            append("Terms ")
                            withStyle(style = SpanStyle(fontFamily = alt)) {
                                append("&")
                            }
                            append(" Conditions")
                        },
                        fontFamily = titleFont,
                        fontSize = 20.sp,
                        color = DarkGreen,
                        textAlign = TextAlign.Center
                    )
                },
                text = {
                    Text(
                        text = "Here are the terms and conditions: " +
                                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras vel porta dolor, pharetra auctor orci. " +
                                "Duis scelerisque orci sit amet nisi feugiat accumsan. Praesent luctus tincidunt sapien sit amet vehicula. " +
                                "Curabitur egestas orci erat, vel sodales nunc pulvinar pretium. Aliquam iaculis tellus risus, nec accumsan " +
                                "justo porttitor vitae. Etiam gravida lorem eget turpis ultrices, vitae pulvinar mauris vehicula. Curabitur " +
                                "venenatis est non hendrerit tempus. Proin scelerisque diam ut ligula pellentesque, quis euismod urna " +
                                "facilisis. Interdum et malesuada fames ac ante ipsum primis in faucibus. Nullam finibus, massa pretium " +
                                "vestibulum commodo, nisl sapien placerat neque, a vestibulum diam arcu nec nisi. Ut congue, enim eu tempus " +
                                "venenatis, lacus orci dapibus ipsum, eu lobortis eros lorem at dui.",
                        fontFamily = captionFont,
                        fontSize = 13.sp,
                        lineHeight = 25.sp,
                        textAlign = TextAlign.Justify,
                        color = Blackk
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            navController.navigate("sixth")
                        },
                        colors = ButtonDefaults.buttonColors(Slime),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text("I accept", color = DarkGreen, fontFamily = titleFont, fontSize = 16.sp)
                    }
                }
            )
        }
    }
}
