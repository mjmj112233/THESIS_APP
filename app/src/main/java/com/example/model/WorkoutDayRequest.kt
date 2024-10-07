package com.example.model

data class WorkoutDayRequest(
    val id: Int,
    val workoutInfo: WorkoutInfoRequest?,
    val dayNum: Int,
    val isRestDay: Boolean
)