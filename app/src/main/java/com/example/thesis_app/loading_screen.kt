package com.example.thesis_app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.thesis_app.ui.theme.BlueGreen
import com.example.thesis_app.ui.theme.Slime
import kotlinx.coroutines.delay

@Composable
fun loadingScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGreen),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Content of your second page goes here

        // Loading animation
        LoadingAnimation(
            circleSize = 10.dp,
            circleColor = Slime,
            spaceBetween = 10.dp,
            travelDistance = 5.dp
        )
    }

    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate("third")
    }
}
