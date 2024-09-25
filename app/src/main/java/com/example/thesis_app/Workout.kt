package com.example.thesis_app

import android.text.method.TextKeyListener.Capitalize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.thesis_app.ui.theme.Ash
import com.example.thesis_app.ui.theme.BlueGreen
import com.example.thesis_app.ui.theme.DarkGreen
import com.example.thesis_app.ui.theme.DirtyWhite
import com.example.thesis_app.ui.theme.Slime
import com.example.thesis_app.ui.theme.captionFont
import com.example.thesis_app.ui.theme.titleFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Workout(navController: NavController, equipmentName: String? = null) {
    val categories = listOf("All", "Leg", "Chest", "Arm")
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    // List of workouts
    val workouts = listOf("Workout 1", "Workout 2", "Workout 3", "Workout 4")

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
                    ){
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
                                text = (equipmentName ?: "No Equipment Selected").uppercase(),
                                fontSize = 20.sp,
                                fontFamily = titleFont,
                                color = DirtyWhite,
                                textAlign = TextAlign.Center
                            )

                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = { expanded = !expanded }
                            ) {
                                TextField(
                                    readOnly = true,
                                    value = selectedCategory,
                                    onValueChange = { },

                                    modifier = Modifier
                                        .menuAnchor()
                                        .clip(RoundedCornerShape(8.dp))
                                        .height(40.dp)
                                        .width(90.dp)
                                        .wrapContentHeight(),

                                    textStyle = TextStyle(
                                        fontFamily = titleFont,
                                        color = DarkGreen,
                                        fontSize = 16.sp
                                    ),

                                    colors = TextFieldDefaults.textFieldColors(
                                        containerColor = Slime
                                    ),

                                    trailingIcon = {
                                        Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                                    }
                                )
                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    categories.forEach { category ->
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = category,
                                                    fontFamily = titleFont,
                                                    color = DarkGreen,
                                                    modifier = Modifier
                                                        .padding(vertical = 8.dp)
                                                        .wrapContentHeight()
                                                )
                                            },
                                            onClick = {
                                                selectedCategory = category
                                                expanded = false
                                            },

                                            modifier = Modifier
                                                .background(Slime)
                                                .wrapContentHeight()
                                        )
                                    }
                                }
                            }
                        }

                        // Display workouts in a LazyColumn
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(bottom = 100.dp) // Add bottom padding to avoid overlap with FAB
                        ) {
                            items(workouts) { workout ->
                                WorkoutItem(workout)
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
fun WorkoutItem(workout: String) {
    Box(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .height(140.dp)
            .background(DirtyWhite, RoundedCornerShape(8.dp))
            .clickable {
 }
            .padding(16.dp)
    ) {
        Text(
            text = workout,
            fontFamily = titleFont,
            fontSize = 16.sp,
            color = DarkGreen
        )
    }
}





