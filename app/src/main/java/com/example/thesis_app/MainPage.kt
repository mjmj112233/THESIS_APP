package com.example.thesis_app

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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thesis_app.ui.theme.BlueGreen
import com.example.thesis_app.ui.theme.DarkGreen
import com.example.thesis_app.ui.theme.DirtyWhite
import com.example.thesis_app.ui.theme.Slime
import com.example.thesis_app.ui.theme.captionFont
import com.example.thesis_app.ui.theme.titleFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainPage(navController: NavController) {
    // Define the equipment list with names and placeholder icons
    val equipmentList = listOf(
        Equipment("Dumbbell", R.drawable.weights),
        Equipment("Exercise Bike", R.drawable.bike),
        Equipment("Treadmill", R.drawable.treadmill),
        Equipment("Bench Press", R.drawable.bench)
    )

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
                    contentAlignment = Alignment.TopCenter
                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = 80.dp, start = 20.dp, end = 20.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(DirtyWhite)
                            .fillMaxWidth()
                            .height(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Recommended Equipment",
                            fontFamily = titleFont,
                            textAlign = TextAlign.Center,
                            color = DarkGreen,
                            fontSize = 20.sp
                        )
                    }

                    // Display equipment icons and labels in a grid
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2), // Set the number of columns
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 160.dp) // Adjust padding as needed
                    ) {
                        items(equipmentList) { equipment ->
                            EquipmentItem(equipment, navController)
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
                .fillMaxSize() // Use fillMaxSize to ensure it takes the whole screen space
                .padding(bottom = 16.dp), // Add some padding to ensure it is not too close to the edge
            contentAlignment = Alignment.BottomCenter // Align content to the bottom center
        ) {
            FloatingActionButton(
                onClick = { /* Handle button click */ },
                shape = CircleShape,
                containerColor = DirtyWhite,
                modifier = Modifier
                    .size(100.dp)
                    .offset(y = (-20).dp) // Adjust this offset to move it up or down as needed
            ) {
                Icon(
                    painter = painterResource(R.drawable.scan),
                    contentDescription = "Scan",
                    modifier = Modifier.size(40.dp),
                    tint = DarkGreen
                )
            }
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-130).dp)
                .align(Alignment.BottomCenter)
                .padding(start = 110.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(shape = RoundedCornerShape(50.dp))
                    .background(DirtyWhite)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.spot_avatar),
                    contentDescription = "Spot Logo",
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}

// Data class for equipment items
data class Equipment(val name: String, val iconRes: Int)

@Composable
fun EquipmentItem(equipment: Equipment, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(start = 40.dp, end = 40.dp, bottom = 16.dp, top = 16.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Slime)
                .padding(8.dp)
                .clickable(onClick = {navController.navigate("workout/${equipment.name}")}),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = equipment.iconRes),
                contentDescription = equipment.name,
                modifier = Modifier
                    .size(80.dp) // Set size to 64x64 dp to fit within the circle
                    .clip(CircleShape) // Ensure image is clipped to the circle
                    .background(DirtyWhite), // Optional: background color if needed
                contentScale = ContentScale.Crop // Ensure image scales properly
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = equipment.name,
            fontSize = 16.sp,
            fontFamily = titleFont,
            color = DirtyWhite,
            textAlign = TextAlign.Center
        )
    }
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

