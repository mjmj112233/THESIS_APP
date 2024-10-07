package com.example.thesis_app

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.api.RetrofitInstance
import com.example.model.WorkoutRoutineRequest
import com.example.model.WorkoutRoutineResponse
import com.example.api.WorkoutRoutineService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutRoutinePage(navController: NavController) {
    val context = LocalContext.current
    var workoutRoutines by remember { mutableStateOf<List<WorkoutRoutineRequest>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Fetch workout routines on launch
    LaunchedEffect(Unit) {
        isLoading = true
        fetchWorkoutRoutines(context) { routines, error ->
            workoutRoutines = routines
            errorMessage = error
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            // Display error message if exists
            errorMessage?.let {
                BasicText(text = it)
            }

            // Button to generate workout routines
            Button(onClick = {
                generateWorkoutRoutine(context) { generatedRoutines, error ->
                    workoutRoutines = generatedRoutines
                    errorMessage = error
                }
            }) {
                Text("Generate Workout Routine")
            }

            // Display workout routines if available
            if (workoutRoutines.isNotEmpty()) {
                WorkoutRoutinesList(workoutRoutines)
            } else {
                BasicText(text = "No workout routines available.")
            }
        }
    }
}

private fun fetchWorkoutRoutines(context: Context, onResult: (List<WorkoutRoutineRequest>, String?) -> Unit) {
    val service = RetrofitInstance.WorkoutRoutineService(context)

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val routines = service.getWorkoutRoutines() // Call suspend function here
            onResult(routines, null)
        } catch (e: Exception) {
            onResult(emptyList(), e.message)
        }
    }
}

private fun generateWorkoutRoutine(context: Context, onResult: (List<WorkoutRoutineRequest>, String?) -> Unit) {
    val service = RetrofitInstance.WorkoutRoutineService(context)

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = service.generateWorkoutRoutine() // Call your generate function here
            // Flattening the map values into a single list
            val generatedRoutines = response.values.flatten() // Flattening if multiple keys exist
            onResult(generatedRoutines, null)
        } catch (e: Exception) {
            onResult(emptyList(), e.message)
        }
    }
}
@Composable
fun WorkoutRoutinesList(workoutRoutines: List<WorkoutRoutineRequest>) {
    // Group the workout routines by day number
    val groupedRoutines = workoutRoutines.groupBy { it.dayNum }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(groupedRoutines.toList()) { (dayNum, routines) ->
            // Display the day number
            Text(
                text = "Day $dayNum",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Display each routine for the day
            routines.forEach { routine ->
                WorkoutRoutineCard(routine)
            }

            // Add a divider after each day
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}


@Composable
fun WorkoutRoutineCard(routine: WorkoutRoutineRequest) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Display workout info
            routine.workoutInfo?.let { workoutInfo ->
                Text(text = "Exercise: ${workoutInfo.workout.name}", style = MaterialTheme.typography.titleMedium)
                Text(text = "Description: ${workoutInfo.workout.description}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Sets: ${workoutInfo.sets}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Reps: ${workoutInfo.reps}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Weight: ${workoutInfo.weight}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}







