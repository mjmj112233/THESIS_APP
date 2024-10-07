package com.example.model

data class UserProfileRequest(
    val height: Double,
    val weight: Double,
    val BMICategory: String,
    val fitnessGoal: String,
    val fitnessScore: Int,
    val muscleGroup: String
)
