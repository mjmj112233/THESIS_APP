package com.example.model

import com.example.thesis_app.WorkoutDay

data class WorkoutRoutineResponse(
    val routines: Map<String, List<WorkoutRoutineRequest>>
)

