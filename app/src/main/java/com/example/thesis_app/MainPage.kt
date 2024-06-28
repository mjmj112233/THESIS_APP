package com.example.thesis_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thesis_app.ui.theme.BlueGreen
import com.example.thesis_app.ui.theme.DirtyWhite
import com.example.thesis_app.ui.theme.Slime
import com.example.thesis_app.ui.theme.captionFont
import com.example.thesis_app.ui.theme.titleFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun mainPage() {
    Scaffold(
        topBar = {
            Surface(
                color = BlueGreen,
                modifier = Modifier.fillMaxWidth(),
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                        .fillMaxWidth(),
                    content = {
                        Image(
                            painter = painterResource(id = R.drawable.spot_logo2),
                            contentDescription = "Spot Logo",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                )
            }
        },

        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(BlueGreen)
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(listOf("How to use Spot?", "Our Dataset", "How do we handle your data?", "About the Devs")) { title ->
                        ClickableContainer(
                            backgroundImage = painterResource(id = R.drawable.slider), // Replace with your actual image resource
                            cornerRadius = 12.dp, // Adjust corner radius as needed
                            onClick = { /* Handle click */ },
                            content = {
                                Column(
                                    modifier = Modifier.padding(start = 20.dp, end = 35.dp, bottom = 0.dp)
                                ) {
                                    Text(
                                        title,
                                        fontSize = 25.sp,
                                        fontFamily = titleFont,
                                        lineHeight = 25.sp,
                                        textAlign = TextAlign.Justify,
                                        color = DirtyWhite,
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        "Click here...",
                                        fontSize = 14.sp,
                                        fontFamily = captionFont,
                                        lineHeight = 20.sp,
                                        textAlign = TextAlign.Justify,
                                        color = DirtyWhite,
                                    )
                                }
                            }
                        )
                    }
                }
            }
        },

        floatingActionButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {
                FloatingActionButton(
                    onClick = { /* Handle button click */ },
                    shape = CircleShape,
                    containerColor = Slime,
                    contentColor = DirtyWhite,
                    modifier = Modifier
                        .size(80.dp)
                        .offset(x = 20.dp, y = 30.dp)
                ) {
                    Icon(Icons.Filled.Add, "Small floating action button.")
                }
            }
        },

        bottomBar = {
            BottomAppBar(
                containerColor = Slime,
                modifier = Modifier.height(40.dp)
            ) {}
        }
    )
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
            .clip(shape = androidx.compose.foundation.shape.RoundedCornerShape(cornerRadius))
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = backgroundImage,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.Center,
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
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

