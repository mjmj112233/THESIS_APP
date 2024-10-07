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
fun Navigation(startDestination: String = "third") {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
//        composable("first") {
//            splashScreen(navController)
//        }
//        composable("second") {
//            loadingScreen(navController)
//        }

        composable("third") {
            firstPage(navController)
        }

        composable("fourth") {
            secondPage(navController)
        }

        composable("fifth") {
            thirdPage(navController)
        }

        composable("sixth") {
            fourthPage(navController)
        }

        composable("signup") {
            signupPage(navController)
        }

        composable("login") {
            loginPage(navController)
        }

        composable("welcome") {
            Welcome(navController)
        }

        composable("bmi") {
            BMIScreen(navController)
        }

        composable("muscleGroup") {
            muscleGroupScreen(navController)
        }

        composable("pushup") {
            pushUp(navController)
        }

        composable("plank") {
            plank(navController)
        }

        composable("pullup") {
            pullup(navController)
        }

        composable("squat") {
            squat(navController)
        }

        composable("main") {
            mainPage(navController)
        }

        composable("workoutDay/{dayName}/{workouts}") { backStackEntry ->
            val dayName = backStackEntry.arguments?.getString("dayName")
            val workoutsString = backStackEntry.arguments?.getString("workouts")
            val workouts = workoutsString?.split(",") ?: emptyList()
            WorkoutDayPage(navController, dayName, workouts)
        }


        composable("workoutInfo/{workoutName}/{reps}/{sets}/{weight}") { backStackEntry ->
            val workoutName = backStackEntry.arguments?.getString("workoutName") ?: "Unknown"
            val reps = backStackEntry.arguments?.getString("reps")?.toInt() ?: 0
            val sets = backStackEntry.arguments?.getString("sets")?.toInt() ?: 0
            val weight = backStackEntry.arguments?.getString("weight")?.toDouble() ?: 0.0

            WorkoutInfo(navController, workoutName, reps, sets, weight)
        }



    }
}

