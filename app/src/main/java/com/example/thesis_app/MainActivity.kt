package com.example.thesis_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.thesis_app.ui.theme.THESIS_APPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            com.example.thesis_app.firstPage()
            Navigation()
        }
    }
}

@Composable
fun Navigation(startDestination: String = "first") {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("first") {
            splashScreen(navController)
        }
        composable("second") {
            loadingScreen(navController)
        }
        composable("third") {
            firstPage()
        }

    }
}

