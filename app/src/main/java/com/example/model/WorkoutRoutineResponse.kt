package com.example.model

import com.example.thesis_app.WorkoutDayPage

data class WorkoutRoutineResponse(
    val routines: Map<String, List<WorkoutRoutineRequest>>
)

