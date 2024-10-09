package com.example.thesis_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.model.WorkoutRoutineRequest
import com.example.thesis_app.ui.theme.THESIS_APPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }
    }
}

@Composable
fun Navigation(startDestination: String = "third") {  // Change to the correct starting screen if needed
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {

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
            BMIScreen(navController)  // Pass the NavController
        }

        composable("muscleGroup?fitnessGoal={fitnessGoal}&height={height}&weight={weight}&bmiCategory={bmiCategory}") { backStackEntry ->
            val fitnessGoal = backStackEntry.arguments?.getString("fitnessGoal") ?: "Unknown"
            val height = backStackEntry.arguments?.getString("height")?.toDouble() ?: 0.0
            val weight = backStackEntry.arguments?.getString("weight")?.toDouble() ?: 0.0
            val bmiCategory = backStackEntry.arguments?.getString("bmiCategory") ?: "Unknown"

            // Log the height and weight for debugging
            Log.d("MuscleGroupScreen", "Height: $height, Weight: $weight")

            muscleGroupScreen(
                navController = navController,
                height = height,
                weight = weight,
                bmiCategory = bmiCategory,
                fitnessGoal = fitnessGoal,
                onMuscleGroupSelected = { muscleGroup ->
                    // Pass height and weight when navigating to the pushup screen
                    navController.navigate("pushup?height=$height&weight=$weight&bmiCategory=$bmiCategory&fitnessGoal=$fitnessGoal&muscleGroup=$muscleGroup") {
                        popUpTo("muscleGroup") { inclusive = true }  // Remove Muscle Group screen from backstack
                    }
                }
            )
        }


        composable("pushup?height={height}&weight={weight}&bmiCategory={bmiCategory}&fitnessGoal={fitnessGoal}&muscleGroup={muscleGroup}") { backStackEntry ->
            val height = backStackEntry.arguments?.getString("height")?.toDouble() ?: 0.0
            val weight = backStackEntry.arguments?.getString("weight")?.toDouble() ?: 0.0
            val bmiCategory = backStackEntry.arguments?.getString("bmiCategory") ?: "Unknown"
            val fitnessGoal = backStackEntry.arguments?.getString("fitnessGoal") ?: "Unknown"
            val muscleGroup = backStackEntry.arguments?.getString("muscleGroup") ?: "Unknown"

            Log.d("Pushups", "Height: $height, Weight: $weight")

            pushUp(
                navController = navController,
                height = height,
                weight = weight,
                bmiCategory = bmiCategory,
                fitnessGoal = fitnessGoal,
                muscleGroup = muscleGroup,
                onPushUpScore = { pushUpScore ->
                    navController.navigate("plank?height=$height&weight=$weight&bmiCategory=$bmiCategory&fitnessGoal=$fitnessGoal&muscleGroup=$muscleGroup&pushUpScore=$pushUpScore")
                }
            )
        }

        composable("plank?height={height}&weight={weight}&bmiCategory={bmiCategory}&fitnessGoal={fitnessGoal}&muscleGroup={muscleGroup}&pushUpScore={pushUpScore}") { backStackEntry ->
            val height = backStackEntry.arguments?.getString("height")?.toDouble() ?: 0.0
            val weight = backStackEntry.arguments?.getString("weight")?.toDouble() ?: 0.0
            val bmiCategory = backStackEntry.arguments?.getString("bmiCategory") ?: "Unknown"
            val fitnessGoal = backStackEntry.arguments?.getString("fitnessGoal") ?: "Unknown"
            val muscleGroup = backStackEntry.arguments?.getString("muscleGroup") ?: "Unknown"
            val pushUpScore = backStackEntry.arguments?.getString("pushUpScore")?.toInt() ?: 0

            Log.d("Plank", "Height: $height, Weight: $weight")

            plank(
                navController = navController,
                height = height,
                weight = weight,
                bmiCategory = bmiCategory,
                fitnessGoal = fitnessGoal,
                muscleGroup = muscleGroup,
                pushUpScore = pushUpScore,
                onPlankScore = { plankScore ->
                    navController.navigate("pullup?height=$height&weight=$weight&bmiCategory=$bmiCategory&fitnessGoal=$fitnessGoal&muscleGroup=$muscleGroup&pushUpScore=$pushUpScore&plankScore=$plankScore")
                }
            )
        }


        composable("pullup?height={height}&weight={weight}&bmiCategory={bmiCategory}&fitnessGoal={fitnessGoal}&muscleGroup={muscleGroup}&pushUpScore={pushUpScore}&plankScore={plankScore}") { backStackEntry ->
            val height = backStackEntry.arguments?.getString("height")?.toDouble() ?: 0.0
            val weight = backStackEntry.arguments?.getString("weight")?.toDouble() ?: 0.0
            val bmiCategory = backStackEntry.arguments?.getString("bmiCategory") ?: "Unknown"
            val fitnessGoal = backStackEntry.arguments?.getString("fitnessGoal") ?: "Unknown"
            val muscleGroup = backStackEntry.arguments?.getString("muscleGroup") ?: "Unknown"
            val pushUpScore = backStackEntry.arguments?.getString("pushUpScore")?.toInt() ?: 0
            val plankScore = backStackEntry.arguments?.getString("plankScore")?.toInt() ?: 0

            Log.d("Pullup", "Height: $height, Weight: $weight")

            pullup(
                navController = navController,
                height = height,
                weight = weight,
                bmiCategory = bmiCategory,
                fitnessGoal = fitnessGoal,
                muscleGroup = muscleGroup,
                pushUpScore = pushUpScore,
                plankScore = plankScore,
                onPullUpScore = { pullUpScore ->
                    navController.navigate("squat?height=$height&weight=$weight&bmiCategory=$bmiCategory&fitnessGoal=$fitnessGoal&muscleGroup=$muscleGroup&pushUpScore=$pushUpScore&plankScore=$plankScore&pullUpScore=$pullUpScore")
                }
            )
        }

        composable("squat?height={height}&weight={weight}&bmiCategory={bmiCategory}&fitnessGoal={fitnessGoal}&muscleGroup={muscleGroup}&pushUpScore={pushUpScore}&plankScore={plankScore}&pullUpScore={pullUpScore}") { backStackEntry ->
            val height = backStackEntry.arguments?.getString("height")?.toDouble() ?: 0.0
            val weight = backStackEntry.arguments?.getString("weight")?.toDouble() ?: 0.0
            val bmiCategory = backStackEntry.arguments?.getString("bmiCategory") ?: "Unknown"
            val fitnessGoal = backStackEntry.arguments?.getString("fitnessGoal") ?: "Unknown"
            val muscleGroup = backStackEntry.arguments?.getString("muscleGroup") ?: "Unknown"
            val pushUpScore = backStackEntry.arguments?.getString("pushUpScore")?.toInt() ?: 0
            val plankScore = backStackEntry.arguments?.getString("plankScore")?.toInt() ?: 0
            val pullUpScore = backStackEntry.arguments?.getString("pullUpScore")?.toInt() ?: 0

            Log.d("Squat", "Height: $height, Weight: $weight")
            Log.d("UserProfileRequest", "Height: $height, Weight: $weight, BMICategory: $bmiCategory, FitnessGoal: $fitnessGoal, MuscleGroup: $muscleGroup")


            SquatScreen(
                navController = navController,
                height = height,
                weight = weight,
                bmiCategory = bmiCategory,
                fitnessGoal = fitnessGoal,
                muscleGroup = muscleGroup,
                fitnessScore = pushUpScore + plankScore + pullUpScore
            )




        }

        composable("main") {
            mainPage(navController)
        }

        composable("workoutDay/{selectedDayNum}") { backStackEntry ->
            val selectedDayNum = backStackEntry.arguments?.getString("selectedDayNum")?.toInt() ?: 1
            WorkoutDayPage(navController, selectedDayNum)
        }

        composable("workoutRoutine") {
            WorkoutRoutinePage(navController)
        }
    }
}
