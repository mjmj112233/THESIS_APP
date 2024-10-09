package com.example.model

data class UserProfile (
    val id: Long,                // Unique ID of the profile (from the server)
    val height: Double,
    val weight: Double,
    val BMICategory: String,
    val fitnessGoal: String,
    val fitnessScore: Int,
    val muscleGroup: String,
    val createdAt: String?,      // Additional fields like creation timestamp
    val updatedAt: String?       // Additional fields like update timestamp
)