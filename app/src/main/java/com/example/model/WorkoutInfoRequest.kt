package com.example.model

data class WorkoutInfoRequest(
    val id: Long,
    val workout: WorkoutRequest,
    val sets: Int,
    val reps: Int,
    val weight: Double,
    val fitnessGoal: String,
    val fitnessScore: String
)
